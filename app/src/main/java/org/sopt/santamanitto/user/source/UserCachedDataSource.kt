package org.sopt.santamanitto.user.source

import org.sopt.santamanitto.data.JoinedRoom
import javax.inject.Named

class UserCachedDataSource(
    @Named("remote") private val userRemoteDataSource: UserDataSource
): UserDataSource {

    private var _cachedUser: User? = null
    val cachedUser: User?
        get() = _cachedUser

    private var _cachedJoinedRooms: List<JoinedRoom>? = null
    val cachedJoinedRooms: List<JoinedRoom>?
        get() = _cachedJoinedRooms

    private var isJoinedRoomDirty = false

    override fun login(serialNumber: String, callback: UserDataSource.LoginCallback) {
        userRemoteDataSource.login(serialNumber, object : UserDataSource.LoginCallback {
            override fun onLoginSuccess(user: User) {
                _cachedUser = user
                callback.onLoginSuccess(user)
            }

            override fun onLoginFailed() {
                callback.onLoginFailed()
            }
        })
    }

    override fun createAccount(userName: String, serialNumber: String, callback: UserDataSource.CreateAccountCallback) {
        userRemoteDataSource.createAccount(userName, serialNumber, object: UserDataSource.CreateAccountCallback {
            override fun onCreateAccountSuccess(user: User) {
                _cachedUser = user
                callback.onCreateAccountSuccess(user)
            }

            override fun onCreateAccountFailed() {
                callback.onCreateAccountFailed()
            }
        })
    }

    override fun getUserId(): Int {
        return cachedUser!!.id
    }

    override fun getAccessToken(): String {
        return cachedUser!!.accessToken
    }

    override fun getUserName(): String {
        return cachedUser!!.userName
    }

    override fun getJoinedRoom(callback: UserDataSource.GetJoinedRoomsCallback) {
        if (cachedJoinedRooms != null && !isJoinedRoomDirty) {
            callback.onJoinedRoomsLoaded(cachedJoinedRooms!!)
        } else {
            userRemoteDataSource.getJoinedRoom(object : UserDataSource.GetJoinedRoomsCallback {
                override fun onJoinedRoomsLoaded(rooms: List<JoinedRoom>) {
                    _cachedJoinedRooms = rooms
                    isJoinedRoomDirty = false
                    callback.onJoinedRoomsLoaded(rooms)
                }

                override fun onDataNotAvailable() {
                    callback.onDataNotAvailable()
                }
            })
        }
    }
}