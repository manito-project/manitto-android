package org.sopt.santamanitto.user.network

import org.sopt.santamanitto.user.data.LoginUser

data class UserCheckResponse(
        val loginUser: LoginUser,
        val accessToken: String
)