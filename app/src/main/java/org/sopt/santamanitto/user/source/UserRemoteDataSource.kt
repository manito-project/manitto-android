package org.sopt.santamanitto.user.source

import org.sopt.santamanitto.network.RequestCallback
import org.sopt.santamanitto.network.Response
import org.sopt.santamanitto.network.start
import org.sopt.santamanitto.user.User
import org.sopt.santamanitto.user.network.UserCheckResponse
import org.sopt.santamanitto.user.network.UserService
import retrofit2.Call
import retrofit2.Callback

class UserRemoteDataSource(
    private val userService: UserService,
    private val userSerialNumber: String
    ) : UserDataSource {
    override fun getUser(callback: UserDataSource.GetUserCallback) {
        userService.getUserFromSerialNumber(userSerialNumber)
            .enqueue(object: Callback<Response<UserCheckResponse>> {
            override fun onResponse(
                call: Call<Response<UserCheckResponse>>,
                response: retrofit2.Response<Response<UserCheckResponse>>
            ) {
                if (response.isSuccessful) {
                    if (response.body()!!.success) {
                        if (response.body()!!.message == "해당 시리얼 넘버를 가진 유저가 있습니다") {
                            val result = response.body()!!.data.user
                            val user = User(result.userName, result.serialNumber, id = result.id)
                            callback.onUserLoaded(user)
                        } else {
                            callback.onDataNotAvailable()
                        }
                    } else {
                        callback.onDataNotAvailable()
                    }
                } else {
                    callback.onDataNotAvailable()
                }
            }

            override fun onFailure(call: Call<Response<UserCheckResponse>>, t: Throwable) {
                callback.onDataNotAvailable()
            }
        })

//        userService.getUserFromSerialNumber(userSerialNumber).start(object: RequestCallback<UserCheckResponse> {
//            override fun onSuccess(data: UserCheckResponse) {
//                callback.onUserLoaded(data.user)
//            }
//
//            override fun onFail() {
//                callback.onDataNotAvailable()
//            }
//        })
    }

    override fun getUserId(callback: UserDataSource.GetUserIdCallback) {
        //호출되지 않는 메서드
    }

    override fun saveUser(user: User, callback: UserDataSource.SaveUserCallback?) {
        val tempUser = User(user.userName, userSerialNumber)
        userService.saveUser(tempUser).start(object: RequestCallback<User> {
            override fun onSuccess(data: User) {
                callback?.onUserSaved(data)
            }

            override fun onFail() {
                callback?.onSaveFailed()
            }
        })
    }

    override fun saveUser(name: String, callback: UserDataSource.SaveUserCallback?) {
        val tempUser = User(name, userSerialNumber)
        saveUser(tempUser, callback)
    }
}