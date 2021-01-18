package org.sopt.santamanitto.preference

class UserPreferenceManager(private val preferenceManager: SharedPreferenceManager) {

    companion object {
        private const val PREF_KEY_USER_NAME = "user_name"
    }

    fun setUserName(userName: String) {
        preferenceManager.setString(PREF_KEY_USER_NAME, userName)
    }

    fun getUserName(): String? {
        return preferenceManager.getString(PREF_KEY_USER_NAME)
    }

    fun setUuid(uuid: String) {
        //Todo: UUID 값 저장
    }

    fun getUuid() : String {
        //Todo: UUID 값 꺼내기
        return ""
    }
}