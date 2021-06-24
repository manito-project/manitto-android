package org.sopt.santamanitto.user.data.controller

import org.sopt.santamanitto.network.RequestCallback
import org.sopt.santamanitto.network.Response
import org.sopt.santamanitto.network.start
import org.sopt.santamanitto.user.data.LoginUserResponse
import org.sopt.santamanitto.user.network.UserCheckResponse
import org.sopt.santamanitto.user.network.UserService
import retrofit2.Call
import retrofit2.Callback

class RetrofitUserController(private val userService: UserService): UserController {
    override fun login(serialNumber: String, callback: UserController.LoginCallback) {
        userService.login(serialNumber).enqueue(object: Callback<Response<UserCheckResponse>> {
            override fun onResponse(
                    call: Call<Response<UserCheckResponse>>,
                    response: retrofit2.Response<Response<UserCheckResponse>>
            ) {
                if (response.isSuccessful) {
                    if (response.body()!!.success) {
                        if (response.body()!!.message == "해당 시리얼 넘버를 가진 유저가 있습니다") {
                            val result = response.body()!!.data.user
                            val accessToken = response.body()!!.data.accessToken
                            callback.onLoginSuccess(LoginUserResponse(result.userName, result.serialNumber, result.id, accessToken))
                        } else {
                            callback.onLoginFailed(false)
                        }
                    } else {
                        callback.onLoginFailed(false)
                    }
                } else {
                    callback.onLoginFailed(true)
                }
            }

            override fun onFailure(call: Call<Response<UserCheckResponse>>, t: Throwable) {
                callback.onLoginFailed(true)
            }
        })
    }

    override fun createAccount(userName: String, serialNumber: String, callback: UserController.CreateAccountCallback) {
        userService.createAccount(LoginUserResponse(userName, serialNumber)).start(object:
                RequestCallback<LoginUserResponse> {
            override fun onSuccess(data: LoginUserResponse) {
                callback.onCreateAccountSuccess(data)
            }

            override fun onFail() {
                callback.onCreateAccountFailed()
            }
        })
    }
}