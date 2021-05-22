package org.sopt.santamanitto.user.data.source

import org.sopt.santamanitto.preference.UserPreferenceManager
import org.sopt.santamanitto.room.data.JoinedRoom
import org.sopt.santamanitto.user.data.LoginUser
import org.sopt.santamanitto.user.data.User
import org.sopt.santamanitto.user.data.source.UserDataSource.*
import javax.inject.Named

class UserCachedDataSource(
    @Named("remote") private val userRemoteDataSource: UserDataSource,
    private val userPreferenceManager: UserPreferenceManager
): UserDataSource {

    private var _cachedLoginUser: LoginUser? = null
    val cachedLoginUser: LoginUser?
        get() = _cachedLoginUser

    private var _cachedJoinedRooms: List<JoinedRoom>? = null
    val cachedJoinedRooms: List<JoinedRoom>?
        get() = _cachedJoinedRooms

    var isJoinedRoomDirty = false

    private var isUserNameDirty = false

    override fun login(serialNumber: String, callback: LoginCallback) {
        userRemoteDataSource.login(serialNumber, object : LoginCallback {
            override fun onLoginSuccess(loginUser: LoginUser) {
                initUserPreference(loginUser, serialNumber)
                _cachedLoginUser = loginUser
                callback.onLoginSuccess(loginUser)
            }

            override fun onLoginFailed() {
                callback.onLoginFailed()
            }
        })
    }

    override fun createAccount(userName: String, serialNumber: String, callback: CreateAccountCallback) {
        userRemoteDataSource.createAccount(userName, serialNumber, object: CreateAccountCallback {
            override fun onCreateAccountSuccess(loginUser: LoginUser) {
                initUserPreference(loginUser, serialNumber)
                _cachedLoginUser = loginUser
                callback.onCreateAccountSuccess(loginUser)
            }

            override fun onCreateAccountFailed() {
                callback.onCreateAccountFailed()
            }
        })
    }

    override fun getUserId(): Int {
        initCachedLoginUser()
        return cachedLoginUser!!.id
    }

    override fun getAccessToken(): String {
        initCachedLoginUser()
        return cachedLoginUser!!.accessToken
    }

    override fun getUserName(): String {
        initCachedLoginUser()
        return cachedLoginUser!!.userName
    }

    override fun changeUserName(newName: String, callback: (isSuccess: Boolean) -> Unit) {
        userRemoteDataSource.changeUserName(newName) {
            if (it) {
                isUserNameDirty = true
                userPreferenceManager.setUserName(newName)
            }
            callback.invoke(it)
        }
    }

    override fun getJoinedRoom(userId: Int, callback: GetJoinedRoomsCallback) {
        if (cachedJoinedRooms != null && !isJoinedRoomDirty) {
            callback.onJoinedRoomsLoaded(cachedJoinedRooms!!)
        } else {
            userRemoteDataSource.getJoinedRoom(userId, object : GetJoinedRoomsCallback {
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

    override fun getUserInfo(userId: Int, callback: GetUserInfoCallback) {
            userRemoteDataSource.getUserInfo(userId, object: GetUserInfoCallback {
                override fun onUserInfoLoaded(user: User) {
                    callback.onUserInfoLoaded(user)
                }

                override fun onDataNotAvailable() {
                    callback.onDataNotAvailable()
                }
            })
    }

    private fun initUserPreference(loginUser: LoginUser, serialNumber: String) {
        userPreferenceManager.run {
            setAccessToken(loginUser.accessToken)
            setUserId(loginUser.id)
            setUserName(loginUser.userName)
            setSerialNumber(serialNumber)
        }
    }

    private fun initCachedLoginUser() {
        if (_cachedLoginUser == null || isUserNameDirty) {
            isUserNameDirty = false
            userPreferenceManager.let {
                _cachedLoginUser = LoginUser(it.getUserName()!!, it.getSerialNumber()!!, it.getUserId()!!, it.getAccessToken()!!)
            }
        }
    }
}