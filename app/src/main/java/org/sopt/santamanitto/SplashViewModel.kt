package org.sopt.santamanitto

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sopt.santamanitto.user.data.LoginUserResponse
import org.sopt.santamanitto.user.data.controller.UserController
import org.sopt.santamanitto.user.data.source.CachedUserMetadataSource
import javax.inject.Named

class SplashViewModel @ViewModelInject constructor(
        private val userController: UserController,
        private val cachedUserMetadataSource: CachedUserMetadataSource,
        @Named("serialNumber") private val serialNumber: String
) : ViewModel() {

    private val _loginSuccess = MutableLiveData(LoginState.WAITING)
    val loginSuccess: LiveData<LoginState>
        get() = _loginSuccess

    fun login() {
        userController.login(serialNumber, object : UserController.LoginCallback {
            override fun onLoginSuccess(loginUserResponse: LoginUserResponse) {
                cachedUserMetadataSource.run {
                    loginUserResponse.let {
                        setUserName(it.userName)
                        setAccessToken(it.accessToken)
                        setUserId(it.id)
                    }
                }
                _loginSuccess.value = LoginState.SUCCESS
            }

            override fun onLoginFailed() {
                _loginSuccess.value = LoginState.SUCCESS
            }
        })
    }

    enum class LoginState {
        SUCCESS, FAIL, WAITING
    }
}