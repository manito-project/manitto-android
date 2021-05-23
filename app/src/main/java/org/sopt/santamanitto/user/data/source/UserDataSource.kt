package org.sopt.santamanitto.user.data.source

import org.sopt.santamanitto.room.data.JoinedRoom
import org.sopt.santamanitto.user.data.LoginUserResponse
import org.sopt.santamanitto.user.data.UserInfoResponse
@Deprecated("UserDataSource is deprecated")
interface UserDataSource {

    interface LoginCallback {
        fun onLoginSuccess(loginUserResponse: LoginUserResponse)

        fun onLoginFailed()
    }

    interface CreateAccountCallback {
        fun onCreateAccountSuccess(loginUserResponse: LoginUserResponse)

        fun onCreateAccountFailed()
    }

    interface GetJoinedRoomsCallback {
        fun onJoinedRoomsLoaded(rooms: List<JoinedRoom>)

        fun onDataNotAvailable()
    }

    interface GetUserInfoCallback {
        fun onUserInfoLoaded(userInfoResponse: UserInfoResponse)

        fun onDataNotAvailable()
    }

    fun login(serialNumber: String, callback: LoginCallback)

    fun createAccount(userName: String, serialNumber: String, callback: CreateAccountCallback)

    fun getUserId(): Int

    fun getAccessToken(): String

    fun getUserName(): String

    fun changeUserName(userId: Int, newName: String, callback: (isSuccess: Boolean) -> Unit)

    fun getJoinedRoom(userId: Int, callback: GetJoinedRoomsCallback)

    fun getUserInfo(userId: Int, callback: GetUserInfoCallback)
}