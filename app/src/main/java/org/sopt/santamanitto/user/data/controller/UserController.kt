package org.sopt.santamanitto.user.data.controller

import org.sopt.santamanitto.auth.data.response.SignInResponseModel
import org.sopt.santamanitto.auth.data.response.SignUpResponseModel

interface UserController {
    interface CreateAccountCallback {
        fun onCreateAccountSuccess(signUpResponseModel: SignUpResponseModel)

        fun onCreateAccountFailed()
    }

    interface LoginCallback {
        fun onLoginSuccess(signInResponseModel: SignInResponseModel)

        fun onLoginFailed(isError: Boolean)
    }

    fun login(serialNumber: String, callback: LoginCallback)

    fun createAccount(userName: String, serialNumber: String, callback: CreateAccountCallback)
}