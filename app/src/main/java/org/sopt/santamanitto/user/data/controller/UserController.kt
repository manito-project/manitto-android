package org.sopt.santamanitto.user.data.controller

import org.sopt.santamanitto.user.data.LoginUserResponse

interface UserController {
    interface CreateAccountCallback {
        fun onCreateAccountSuccess(loginUserResponse: LoginUserResponse)

        fun onCreateAccountFailed()
    }

    interface LoginCallback {
        fun onLoginSuccess(loginUserResponse: LoginUserResponse)

        fun onLoginFailed(isError: Boolean)
    }

    fun login(serialNumber: String, callback: LoginCallback)

    fun createAccount(userName: String, serialNumber: String, callback: CreateAccountCallback)
}