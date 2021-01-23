package org.sopt.santamanitto.user.source

import org.sopt.santamanitto.data.JoinedRoom

interface UserDataSource {

    interface LoginCallback {
        fun onLoginSuccess(user: User)

        fun onLoginFailed()
    }

    interface CreateAccountCallback {
        fun onCreateAccountSuccess(user: User)

        fun onCreateAccountFailed()
    }

    interface GetJoinedRoomsCallback {
        fun onJoinedRoomsLoaded(rooms: List<JoinedRoom>)

        fun onDataNotAvailable()
    }

    fun login(serialNumber: String, callback: LoginCallback)

    fun createAccount(userName: String, serialNumber: String, callback: CreateAccountCallback)

    fun getUserId(): Int

    fun getAccessToken(): String

    fun getUserName(): String

    fun getJoinedRoom(callback: GetJoinedRoomsCallback)
}