package org.sopt.santamanitto.preference

class UserPreferenceManager(private val preferenceManager: SharedPreferenceManager) {

    companion object {
        private const val PREF_KEY_USER_ID = "user_id"
        private const val PREF_KEY_SERIAL_NUMBER = "serial_number"
        private const val PREF_KEY_USER_NAME = "user_name"
        private const val PREF_KEY_USER_UPDATE_AT = "user_update_at"
        private const val PREF_KEY_USER_CREATE_AT = "user_update_at"
        private const val PREF_KEY_ACCESS_TOKEN = "access_token"
    }

    fun setUserId(userId: Int) {
        preferenceManager.setInt(PREF_KEY_USER_ID, userId)
    }

    fun getUserId(): Int? {
        val result = preferenceManager.getInt(PREF_KEY_USER_ID, -1)

        return if (result == -1) {
            null
        } else {
            result
        }
    }

    fun setSerialNumber(serialNumber: String) {
        preferenceManager.setString(PREF_KEY_SERIAL_NUMBER, serialNumber)
    }

    fun getSerialNumber(): String? {
        return preferenceManager.getString(PREF_KEY_SERIAL_NUMBER)
    }

    fun setUserName(userName: String) {
        preferenceManager.setString(PREF_KEY_USER_NAME, userName)
    }

    fun getUserName(): String? {
        return preferenceManager.getString(PREF_KEY_USER_NAME)
    }

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

    fun setAccessToken(accessToken: String) {
        preferenceManager.setString(PREF_KEY_ACCESS_TOKEN, accessToken)
    }

    fun getAccessToken(): String? {
        return preferenceManager.getString(PREF_KEY_ACCESS_TOKEN)
    }
}