package org.sopt.santamanitto.user.source

import org.sopt.santamanitto.user.User

class UserRepository(
        private val userLocalDataSource: UserDataSource,
        private val userRemoteDataSource: UserDataSource
): UserDataSource {

    var cachedUser: User? = null

    override fun getUser(callback: UserDataSource.GetUserCallback) {
        if (cachedUser != null) {
            callback.onUserLoaded(cachedUser!!)
            return
        }

        userLocalDataSource.getUser(object : UserDataSource.GetUserCallback {
            override fun onUserLoaded(user: User) {
                cacheAndPerform(user) {
                    callback.onUserLoaded(user)
                }
            }

            override fun onDataNotAvailable() {
                getUserFromRemoteDataSource(callback)
            }
        })
    }

    override fun getUserId(callback: UserDataSource.GetUserIdCallback) {
        if (cachedUser != null) {
            callback.onUserIdLoaded(cachedUser!!.id)
            return
        }

        userLocalDataSource.getUserId(object : UserDataSource.GetUserIdCallback {
            override fun onUserIdLoaded(id: Int) {
                callback.onUserIdLoaded(id)
            }

            override fun onDataNotAvailable() {
                getUserFromRemoteDataSource(object: UserDataSource.GetUserCallback {
                    override fun onUserLoaded(user: User) {
                        callback.onUserIdLoaded(user.id)
                    }

                    override fun onDataNotAvailable() {
                        callback.onDataNotAvailable()
                    }
                })
            }
        })
    }

    override fun saveUser(user: User, callback: UserDataSource.SaveUserCallback?) {
        userRemoteDataSource.saveUser(user, object : UserDataSource.SaveUserCallback {
            override fun onUserSaved(user: User) {
                cacheAndPerform(user) {
                    userLocalDataSource.saveUser(user, null)
                    callback?.onUserSaved(user)
                }
            }

            override fun onSaveFailed() {
                callback?.onSaveFailed()
            }
        })
    }

    override fun saveUser(name: String, callback: UserDataSource.SaveUserCallback?) {
        val user = User(name)
        saveUser(user, callback)
    }

    private fun getUserFromRemoteDataSource(callback: UserDataSource.GetUserCallback) {
        userRemoteDataSource.getUser(object : UserDataSource.GetUserCallback {

            override fun onUserLoaded(user: User) {
                cacheAndPerform(user) {
                    userLocalDataSource.saveUser(user, null)
                    callback.onUserLoaded(user)
                }
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }
        })
    }

    private inline fun cacheAndPerform(user: User, perform: (User) -> Unit) {
        cachedUser = user
        perform(user)
    }
}