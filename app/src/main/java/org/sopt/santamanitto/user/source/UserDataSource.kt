package org.sopt.santamanitto.user.source

import org.sopt.santamanitto.user.User

interface UserDataSource {

    interface GetUserIdCallback {
        fun onUserIdLoaded(id: Int)

        fun onDataNotAvailable()
    }

    interface GetUserCallback {
        fun onUserLoaded(user: User)

        fun onDataNotAvailable()
    }

    interface SaveUserCallback {
        fun onUserSaved(user: User)

        fun onSaveFailed()
    }

    fun getUser(callback: GetUserCallback)

    fun getUserId(callback: GetUserIdCallback)

    fun saveUser(user: User, callback: SaveUserCallback?)

    fun saveUser(name: String, callback: SaveUserCallback?)
}