package org.sopt.santamanitto.user.data.source

interface UserMetadataSource {
    fun getUserId(): Int

    fun getAccessToken(): String

    fun getUserName(): String

    fun setUserId(userId: Int)

    fun setAccessToken(accessToken: String)

    fun setUserName(userName: String)
}