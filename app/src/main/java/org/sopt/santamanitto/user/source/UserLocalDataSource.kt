package org.sopt.santamanitto.user.source

import org.sopt.santamanitto.preference.UserPreferenceManager
import org.sopt.santamanitto.user.User

class UserLocalDataSource(
        private val userPreferenceManager: UserPreferenceManager
): UserDataSource {

    override fun getUser(callback: UserDataSource.GetUserCallback) {
        val userId = userPreferenceManager.getUserId()
        val userName = userPreferenceManager.getUserName()
        val userSerialNumber = userPreferenceManager.getSerialNumber()
        val userUpdateTime = userPreferenceManager.getUserUpdateTime()
        val userCreateTime = userPreferenceManager.getUserCreateTime()
        val userAccessToken = userPreferenceManager.getAccessToken()

        if (userId == null || userName == null || userSerialNumber == null || userUpdateTime == null ||
                userCreateTime == null || userAccessToken == null) {
            callback.onDataNotAvailable()
        } else {
            callback.onUserLoaded(User(userName, userId, userSerialNumber, userUpdateTime, userCreateTime, userAccessToken))
        }
    }

    override fun getUserId(callback: UserDataSource.GetUserIdCallback) {
        val result = userPreferenceManager.getUserId()

        if (result == null) {
            callback.onDataNotAvailable()
        } else {
            callback.onUserIdLoaded(result)
        }
    }

    override fun saveUser(user: User, callback: UserDataSource.SaveUserCallback?) {
        userPreferenceManager.run {
            setUserId(user.id)
            setUserName(user.userName)
            setSerialNumber(user.serialNumber)
            setUserUpdateTime(user.updateAt)
            setUserCreateTime(user.createAt)
            setAccessToken(user.accessToken)
        }
    }

    override fun saveUser(name: String, callback: UserDataSource.SaveUserCallback?) {
       userPreferenceManager.setUserName(name)
    }
}