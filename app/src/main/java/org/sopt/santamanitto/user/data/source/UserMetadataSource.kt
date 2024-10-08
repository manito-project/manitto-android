package org.sopt.santamanitto.user.data.source

interface UserMetadataSource {
    fun getUserId(): String

    fun getAccessToken(): String

    fun getUserName(): String

    fun setUserId(userId: String)

    fun setAccessToken(accessToken: String)

    fun setUserName(userName: String)
}