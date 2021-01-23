package org.sopt.santamanitto.user.network

import org.sopt.santamanitto.user.source.User

data class UserCheckResponse(
    val user: User,
    val accessToken: String
)