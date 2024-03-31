package org.sopt.santamanitto.user.network

import org.sopt.santamanitto.user.data.UserLoginModel

data class UserCheckModel(
    val user: UserLoginModel,
    val accessToken: String
)