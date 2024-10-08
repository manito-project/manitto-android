package org.sopt.santamanitto.user.data.controller

import org.sopt.santamanitto.auth.data.request.SignInRequestModel
import org.sopt.santamanitto.auth.data.request.SignUpRequestModel
import org.sopt.santamanitto.auth.data.response.SignInResponseModel
import org.sopt.santamanitto.auth.data.response.SignUpResponseModel
import org.sopt.santamanitto.network.RequestCallback
import org.sopt.santamanitto.network.Response
import org.sopt.santamanitto.network.start
import org.sopt.santamanitto.user.network.UserService
import retrofit2.Call
import retrofit2.Callback

class RetrofitUserController(private val userService: UserService) : UserController {
    override fun login(serialNumber: String, callback: UserController.LoginCallback) {
        userService.login(
            SignInRequestModel(serialNumber)
        ).enqueue(object : Callback<Response<SignInResponseModel>> {
            override fun onResponse(
                call: Call<Response<SignInResponseModel>>,
                response: retrofit2.Response<Response<SignInResponseModel>>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()!!.data
                    callback.onLoginSuccess(
                        SignInResponseModel(
                            accessToken = result.accessToken,
                            id = result.id
                        )
                    )
                } else {
                    callback.onLoginFailed(false)
                }
            }

            override fun onFailure(call: Call<Response<SignInResponseModel>>, t: Throwable) {
                callback.onLoginFailed(true)
            }
        })
    }

    override fun createAccount(
        userName: String,
        serialNumber: String,
        callback: UserController.CreateAccountCallback
    ) {
        userService.createAccount(
            SignUpRequestModel(
                serialNumber = serialNumber,
                name = userName
            )
        ).start(object :
            RequestCallback<SignUpResponseModel> {
            override fun onSuccess(data: SignUpResponseModel) {
                callback.onCreateAccountSuccess(data)
            }

            override fun onFail() {
                callback.onCreateAccountFailed()
            }
        })
    }
}