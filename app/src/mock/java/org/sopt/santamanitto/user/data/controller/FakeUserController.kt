package org.sopt.santamanitto.user.data.controller

import org.sopt.santamanitto.user.data.LoginUserResponse

class FakeUserController: UserController {

    private val fakeLoginUser = LoginUserResponse(
        "fakeUser",
        "f0a1k2e3u4e5s6e7r8",
        1,
        "fake1access2token3"
    )

    override fun login(serialNumber: String, callback: UserController.LoginCallback) {
        callback.onLoginSuccess(fakeLoginUser)
    }

    override fun createAccount(
        userName: String,
        serialNumber: String,
        callback: UserController.CreateAccountCallback
    ) {
        callback.onCreateAccountSuccess(fakeLoginUser)
    }
}