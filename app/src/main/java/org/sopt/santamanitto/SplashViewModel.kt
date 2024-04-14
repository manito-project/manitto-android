package org.sopt.santamanitto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.sopt.santamanitto.update.version.Version
import org.sopt.santamanitto.update.version.VersionChecker
import org.sopt.santamanitto.user.data.UserLoginModel
import org.sopt.santamanitto.user.data.controller.UserController
import org.sopt.santamanitto.user.data.source.UserMetadataSource
import timber.log.Timber
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

    private val _remoteServerCheck = MutableStateFlow(false)
    val remoteServerCheck: StateFlow<Boolean>
        get() = _remoteServerCheck

    private val _remoteServerCheckMessage = MutableStateFlow("")
    val remoteServerCheckMessage: StateFlow<String>
        get() = _remoteServerCheckMessage

    init {
        fetchRemoteConfig()
    }

    fun checkUpdate() {
        viewModelScope.launch {
            try {
                _latestVersion.value = versionChecker.getLatestVersion()
            } catch (e: Exception) {
                Timber.tag(this.javaClass.simpleName).e(e.stackTraceToString())
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

    private fun fetchRemoteConfig() {
        getRemoteConfigInstance().fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _remoteServerCheck.value = getRemoteConfigInstance().getBoolean("test_server_check")
                _remoteServerCheckMessage.value =
                    getRemoteConfigInstance().getString("server_check_message")
            } else {
                // TODO : Firebase Remote Config fetch 실패 시 로직 추가
                Timber.tag("RemoteConfig").e("Fetch Failed")
            }
        }
    }

    companion object {
        private val remoteConfig by lazy {
            Firebase.remoteConfig.apply {
                setConfigSettingsAsync(
                    remoteConfigSettings {
                        minimumFetchIntervalInSeconds = 0
                    }
                )
            }
        }

        fun getRemoteConfigInstance(): FirebaseRemoteConfig = remoteConfig
    }


    enum class LoginState {
        SUCCESS, FAIL, WAITING, ERROR
    }
}