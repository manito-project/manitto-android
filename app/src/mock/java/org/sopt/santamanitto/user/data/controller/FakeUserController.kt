package org.sopt.santamanitto.user.data.controller

import android.util.Log
import kotlinx.coroutines.*
import org.sopt.santamanitto.user.data.LoginUserResponse

class FakeUserController : UserController {

    private val fakeLoginUser = LoginUserResponse(
        "fakeUser",
        "f0a1k2e3u4e5s6e7r8",
        1,
        "fake1access2token3"
    )

    override fun login(serialNumber: String, callback: UserController.LoginCallback) {
//        callback.onLoginSuccess(fakeLoginUser)
        callback.onLoginFailed(false)
    }

    override fun createAccount(
        userName: String,
        serialNumber: String,
        callback: UserController.CreateAccountCallback
    ) {
        GlobalScope.launch {
            Log.d(javaClass.simpleName, "createAccount(): request ...")
            delay(2000)
            Log.d(javaClass.simpleName, "createAccount(): response!")
            withContext(Dispatchers.Main) {
                callback.onCreateAccountSuccess(fakeLoginUser)
            }
        }
    }
}