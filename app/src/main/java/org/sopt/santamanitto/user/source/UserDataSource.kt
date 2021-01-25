package org.sopt.santamanitto.user.source

import org.sopt.santamanitto.data.JoinedRoom

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

    fun getJoinedRoom(userId: Int, callback: GetJoinedRoomsCallback)

    fun getUserInfo(userId: Int, callback: GetUserInfoCallback)
}