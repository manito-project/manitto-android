package org.sopt.santamanitto.user.network

import org.sopt.santamanitto.user.User

data class UserCheckResponse(
    val user: User,
    val assessToken: String
)