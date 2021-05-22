package org.sopt.santamanitto.user.data.source

import org.sopt.santamanitto.room.data.JoinedRoom
import org.sopt.santamanitto.user.data.LoginUser
import org.sopt.santamanitto.user.data.User

interface UserDataSource {

    interface LoginCallback {
        fun onLoginSuccess(loginUser: LoginUser)

        fun onLoginFailed()
    }

    interface CreateAccountCallback {
        fun onCreateAccountSuccess(loginUser: LoginUser)

        fun onCreateAccountFailed()
    }

    interface GetJoinedRoomsCallback {
        fun onJoinedRoomsLoaded(rooms: List<JoinedRoom>)

        fun onDataNotAvailable()
    }

    interface GetUserInfoCallback {
        fun onUserInfoLoaded(user: User)

        fun onDataNotAvailable()
    }

    fun login(serialNumber: String, callback: LoginCallback)

    fun createAccount(userName: String, serialNumber: String, callback: CreateAccountCallback)

    fun getUserId(): Int

    fun getAccessToken(): String

    fun getUserName(): String

    fun changeUserName(newName: String, callback: (isSuccess: Boolean) -> Unit)

    fun getJoinedRoom(userId: Int, callback: GetJoinedRoomsCallback)

    fun getUserInfo(userId: Int, callback: GetUserInfoCallback)
}