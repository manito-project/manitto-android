package org.sopt.santamanitto.user.data.source

import org.sopt.santamanitto.network.RequestCallback
import org.sopt.santamanitto.network.Response
import org.sopt.santamanitto.network.start
import org.sopt.santamanitto.user.data.LoginUserResponse
import org.sopt.santamanitto.user.data.UserInfoResponse
import org.sopt.santamanitto.user.mypage.UserNameRequest
import org.sopt.santamanitto.user.mypage.UserNameResponse
import org.sopt.santamanitto.user.network.UserCheckResponse
import org.sopt.santamanitto.user.network.UserService
import retrofit2.Call
import retrofit2.Callback

@Deprecated("UserRemoteDataSource is deprecated")
class UserRemoteDataSource(
    private val userService: UserService
): UserDataSource {
    override fun login(serialNumber: String, callback: UserDataSource.LoginCallback) {
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
                            callback.onLoginFailed()
                        }
                    } else {
                        callback.onLoginFailed()
                    }
                } else {
                    callback.onLoginFailed()
                }
            }

            override fun onFailure(call: Call<Response<UserCheckResponse>>, t: Throwable) {
                callback.onLoginFailed()
            }
        })
    }

    override fun createAccount(userName: String, serialNumber: String, callback: UserDataSource.CreateAccountCallback) {
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

    override fun getUserId(): Int {
        throw Exception("You cannot get UserId from remote data source.")
    }

    override fun getAccessToken(): String {
        throw Exception("You cannot get AccessToken from remote data source.")
    }

    override fun getUserName(): String {
        throw Exception("You cannot get UserName from remote data source.")
    }

    override fun changeUserName(userId: Int, newName: String, callback: (isSuccess: Boolean) -> Unit) {
        userService.changeUserName(userId, UserNameRequest(newName)).start(object: RequestCallback<UserNameResponse> {
            override fun onSuccess(data: UserNameResponse) {
                callback.invoke(true)
            }

            override fun onFail() {
                callback.invoke(false)
            }
        })
    }

    override fun getJoinedRoom(userId: Int, callback: UserDataSource.GetJoinedRoomsCallback) {
        getUserInfo(userId, object: UserDataSource.GetUserInfoCallback {
            override fun onUserInfoLoaded(userInfoResponse: UserInfoResponse) {
                callback.onJoinedRoomsLoaded(userInfoResponse.joinedRooms.reversed())
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }
        })
    }

    override fun getUserInfo(userId: Int, callback: UserDataSource.GetUserInfoCallback) {
        userService.getUserInfo(userId).start(object : RequestCallback<UserInfoResponse> {
            override fun onSuccess(data: UserInfoResponse) {
                callback.onUserInfoLoaded(data)
            }

            override fun onFail() {
                callback.onDataNotAvailable()
            }
        })
    }
}