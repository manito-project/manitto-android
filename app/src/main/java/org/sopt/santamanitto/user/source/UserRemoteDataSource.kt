package org.sopt.santamanitto.user.source

import org.sopt.santamanitto.user.User

class UserRemoteDataSource : UserDataSource {
    override fun getUser(callback: UserDataSource.GetUserCallback) {
        //Todo: 구현하여야 함
        callback.onUserLoaded(User("temp"))
    }

    override fun getUserId(callback: UserDataSource.GetUserIdCallback) {
        //Todo: 구현하여야 함
        callback.onUserIdLoaded(-1)
    }

    override fun saveUser(user: User, callback: UserDataSource.SaveUserCallback?) {
        //Todo: 구현하여야 함
    }

    override fun saveUser(name: String, callback: UserDataSource.SaveUserCallback?) {
        //Todo: 구현하여야 함
    }
}