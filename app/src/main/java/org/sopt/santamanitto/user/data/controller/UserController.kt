package org.sopt.santamanitto.user.data.controller

import org.sopt.santamanitto.auth.data.response.SignInResponseModel
import org.sopt.santamanitto.auth.data.response.SignUpResponseModel

interface UserController {
    suspend fun login(serialNumber: String): Result<SignInResponseModel>

    suspend fun createAccount(
        userName: String,
        serialNumber: String,
    ): Result<SignUpResponseModel>
}