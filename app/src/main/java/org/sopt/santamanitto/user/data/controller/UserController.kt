package org.sopt.santamanitto.user.data.controller

import org.sopt.santamanitto.user.data.UserLoginModel

interface UserController {
    interface CreateAccountCallback {
        fun onCreateAccountSuccess(userLoginModel: UserLoginModel)

        fun onCreateAccountFailed()
    }

    interface LoginCallback {
        fun onLoginSuccess(userLoginModel: UserLoginModel)

        fun onLoginFailed(isError: Boolean)
    }

    fun login(serialNumber: String, callback: LoginCallback)

    fun createAccount(userName: String, serialNumber: String, callback: CreateAccountCallback)
}