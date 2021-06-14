package org.sopt.santamanitto

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sopt.santamanitto.update.version.Version
import org.sopt.santamanitto.update.version.VersionChecker
import org.sopt.santamanitto.user.data.LoginUserResponse
import org.sopt.santamanitto.user.data.controller.UserController
import org.sopt.santamanitto.user.data.source.UserMetadataSource
import javax.inject.Named

class SplashViewModel @ViewModelInject constructor(
    private val userController: UserController,
    private val userMetadataSource: UserMetadataSource,
    private val versionChecker: VersionChecker,
    @Named("serialNumber") private val serialNumber: String
) : ViewModel() {

    private val _latestVersion = MutableLiveData<Version?>(null)
    val latestVersion: LiveData<Version?>
        get() = _latestVersion

    private val _versionCheckFail = MutableLiveData(false)
    val versionCheckFail: LiveData<Boolean>
        get() = _versionCheckFail


    private val _loginSuccess = MutableLiveData(LoginState.WAITING)
    val loginSuccess: LiveData<LoginState>
        get() = _loginSuccess

    fun checkUpdate() {
        versionChecker.getLatestVersion(object: VersionChecker.GetLatestVersionCallback {
            override fun onLoadLatestVersion(version: String) {
                _latestVersion.value = Version.create(version)
            }

            override fun onFailure(networkError: Boolean) {
                _versionCheckFail.value = true
            }
        })
    }

    fun login() {
        userController.login(serialNumber, object : UserController.LoginCallback {
            override fun onLoginSuccess(loginUserResponse: LoginUserResponse) {
                userMetadataSource.run {
                    loginUserResponse.let {
                        setUserName(it.userName)
                        setAccessToken(it.accessToken)
                        setUserId(it.id)
                    }
                }
                _loginSuccess.value = LoginState.SUCCESS
            }

            override fun onLoginFailed() {
                _loginSuccess.value = LoginState.FAIL
            }
        })
    }

    enum class LoginState {
        SUCCESS, FAIL, WAITING
    }
}