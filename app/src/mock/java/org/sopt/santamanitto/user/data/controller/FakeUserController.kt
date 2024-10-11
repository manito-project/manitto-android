package org.sopt.santamanitto.user.data.controller

import kotlinx.coroutines.delay
import org.sopt.santamanitto.auth.data.response.SignInResponseModel
import org.sopt.santamanitto.auth.data.response.SignUpResponseModel

class FakeUserController : UserController {

    private val fakeLoginUser = SignInResponseModel(
        accessToken = "f0a1k2e3u4e5s6e7r8",
        id = "1"
    )

    private val fakeSignUpUser = SignUpResponseModel(
        accessToken = "fake1access2token3",
        id = "1"
    )

    override suspend fun login(serialNumber: String): Result<SignInResponseModel> {
        return try {
            Result.success(fakeLoginUser)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createAccount(
        userName: String,
        serialNumber: String
    ): Result<SignUpResponseModel> {
        return try {
            delay(2000)
            Result.success(fakeSignUpUser)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
