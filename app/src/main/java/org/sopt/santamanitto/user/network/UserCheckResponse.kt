package org.sopt.santamanitto.user.network

import org.sopt.santamanitto.user.data.LoginUserResponse

data class UserCheckResponse(
        val user: LoginUserResponse,
        val accessToken: String
)