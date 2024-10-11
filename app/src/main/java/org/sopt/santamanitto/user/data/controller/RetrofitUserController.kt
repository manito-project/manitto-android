package org.sopt.santamanitto.user.data.controller

import org.sopt.santamanitto.auth.data.request.SignInRequestModel
import org.sopt.santamanitto.auth.data.request.SignUpRequestModel
import org.sopt.santamanitto.auth.data.response.SignInResponseModel
import org.sopt.santamanitto.auth.data.response.SignUpResponseModel
import org.sopt.santamanitto.user.network.UserService

class RetrofitUserController(private val userService: UserService) : UserController {
    override suspend fun login(serialNumber: String): Result<SignInResponseModel> {
        return try {
            val response = userService.login(SignInRequestModel(serialNumber))
            if (response.statusCode == 200) {
                val result = response.data
                Result.success(result)
            } else {
                Result.failure(Exception("${response.statusCode}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createAccount(
        userName: String,
        serialNumber: String,
    ): Result<SignUpResponseModel> {
        return try {
            val response = userService.createAccount(SignUpRequestModel(serialNumber, userName))
            if (response.statusCode == 201) {
                val result = response.data
                Result.success(result)
            } else {
                Result.failure(Exception("${response.statusCode}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}