package org.sopt.santamanitto.preference

import org.sopt.santamanitto.user.data.source.UserMetadataSource

class UserPreferenceManager(private val preferenceManager: SharedPreferenceManager) :
    UserMetadataSource {
    companion object {
        private const val PREF_KEY_USER_ID = "user_id"
        private const val PREF_KEY_SERIAL_NUMBER = "serial_number"
        private const val PREF_KEY_USER_NAME = "user_name"
        private const val PREF_KEY_USER_UPDATE_AT = "user_update_at"
        private const val PREF_KEY_USER_CREATE_AT = "user_update_at"
        private const val PREF_KEY_ACCESS_TOKEN = "access_token"
    }

    override fun setUserId(userId: Int) {
        preferenceManager.setInt(PREF_KEY_USER_ID, userId)
    }

    override fun getUserId(): Int = preferenceManager.getInt(PREF_KEY_USER_ID, -1)

    fun setSerialNumber(serialNumber: String) {
        preferenceManager.setString(PREF_KEY_SERIAL_NUMBER, serialNumber)
    }

    fun getSerialNumber(): String? {
        return preferenceManager.getString(PREF_KEY_SERIAL_NUMBER)
    }

    override fun setUserName(userName: String) {
        preferenceManager.setString(PREF_KEY_USER_NAME, userName)
    }

    override fun getUserName(): String = preferenceManager.getString(PREF_KEY_USER_NAME) ?: ""

    fun setUserUpdateTime(updateAt: String) {
        preferenceManager.setString(PREF_KEY_USER_UPDATE_AT, updateAt)
    }

    fun getUserUpdateTime(): String? {
        return preferenceManager.getString(PREF_KEY_USER_UPDATE_AT)
    }

    fun setUserCreateTime(createAt: String) {
        preferenceManager.setString(PREF_KEY_USER_CREATE_AT, createAt)
    }

    fun getUserCreateTime(): String? {
        return preferenceManager.getString(PREF_KEY_USER_CREATE_AT)
    }

    override fun setAccessToken(accessToken: String) {
        preferenceManager.setString(PREF_KEY_ACCESS_TOKEN, accessToken)
    }

    override fun getAccessToken(): String = preferenceManager.getString(PREF_KEY_ACCESS_TOKEN) ?: ""
}
