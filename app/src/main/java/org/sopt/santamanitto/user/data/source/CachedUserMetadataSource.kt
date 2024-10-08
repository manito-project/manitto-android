package org.sopt.santamanitto.user.data.source

class CachedUserMetadataSource(private val userPreferenceManager: UserMetadataSource) :
    UserMetadataSource {
    private var cachedUserId: String? = null
    private var cachedAccessToken: String? = null
    private var cachedUserName: String? = null

    private var userNameIsDirty = false

    override fun getUserId(): String {
        if (cachedUserId == null) {
            cachedUserId = userPreferenceManager.getUserId()
        }
        return cachedUserId!!
    }

    override fun getAccessToken(): String {
        if (cachedAccessToken == null) {
            cachedAccessToken = userPreferenceManager.getAccessToken()
        }
        return cachedAccessToken!!
    }

    override fun getUserName(): String {
        if (cachedUserName == null || userNameIsDirty) {
            userNameIsDirty = true
            cachedUserName = userPreferenceManager.getUserName()
        }
        return cachedUserName!!
    }

    override fun setUserId(userId: String) {
        cachedUserId = userId
        userPreferenceManager.setUserId(userId)
    }

    override fun setAccessToken(accessToken: String) {
        cachedAccessToken = accessToken
        userPreferenceManager.setAccessToken(accessToken)
    }

    override fun setUserName(userName: String) {
        cachedUserName = userName
        userPreferenceManager.setUserName(userName)
    }

    fun setUserNameDirty() {
        userNameIsDirty = true
    }
}
