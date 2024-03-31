package org.sopt.santamanitto

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.sopt.santamanitto.update.version.Version
import org.sopt.santamanitto.update.version.VersionChecker
import org.sopt.santamanitto.user.data.UserLoginModel
import org.sopt.santamanitto.user.data.controller.UserController
import org.sopt.santamanitto.user.data.source.UserMetadataSource
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class SplashViewModel @Inject constructor(
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
        viewModelScope.launch {
            try {
                _latestVersion.value = versionChecker.getLatestVersion()
            } catch (e: Exception) {
                Log.e(this.javaClass.simpleName, e.stackTraceToString())
                _versionCheckFail.value = true
            }
        }
    }

    fun login() {
        userController.login(serialNumber, object : UserController.LoginCallback {
            override fun onLoginSuccess(userLoginModel: UserLoginModel) {
                userMetadataSource.run {
                    userLoginModel.let {
                        setUserName(it.userName)
                        setAccessToken(it.accessToken)
                        setUserId(it.id)
                    }
                }
                _loginSuccess.value = LoginState.SUCCESS
            }

            override fun onLoginFailed(isError: Boolean) {
                _loginSuccess.value = if (isError) {
                    LoginState.ERROR
                } else {
                    LoginState.FAIL
                }
            }
        })
    }

    enum class LoginState {
        SUCCESS, FAIL, WAITING, ERROR
    }
}