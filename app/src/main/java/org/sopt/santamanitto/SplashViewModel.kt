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
import org.sopt.santamanitto.user.data.controller.UserController
import org.sopt.santamanitto.user.data.source.UserMetadataSource
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userController: UserController,
    private val userMetadataSource: UserMetadataSource,
    @Named("serialNumber") private val serialNumber: String
) : ViewModel() {

    private val _latestVersion = MutableLiveData<Version?>(null)
    val latestVersion: LiveData<Version?> = _latestVersion

    private val _versionCheckFail = MutableLiveData(false)
    val versionCheckFail: LiveData<Boolean> = _versionCheckFail

    private val _loginSuccess = MutableLiveData(LoginState.WAITING)
    val loginSuccess: LiveData<LoginState> = _loginSuccess

    private val _remoteServerCheck = MutableStateFlow(false)
    val remoteServerCheck: StateFlow<Boolean> = _remoteServerCheck

    private val _remoteServerCheckMessage = MutableStateFlow("")
    val remoteServerCheckMessage: StateFlow<String> = _remoteServerCheckMessage

    init {
        fetchRemoteConfig()
    }

    fun checkUpdate() {
        val versionString = getRemoteConfigInstance().getString("latest_version")
        if (versionString.isNotEmpty()) {
            try {
                _latestVersion.value = Version.create(versionString)
            } catch (e: Exception) {
                _versionCheckFail.value = true
            }
        } else {
            _versionCheckFail.value = true
        }
    }

    fun login() {
        viewModelScope.launch {
            val result = userController.login(serialNumber)
            result.onSuccess { signInResponseModel ->
                userMetadataSource.run {
                    setAccessToken(signInResponseModel.accessToken)
                    setUserId(signInResponseModel.id)
                }
                _loginSuccess.value = LoginState.SUCCESS
            }.onFailure { exception ->
                _loginSuccess.value = if (exception.message?.contains("404") == false) {
                    LoginState.ERROR
                } else {
                    LoginState.FAIL
                }
            }
        }
    }

    private fun fetchRemoteConfig() {
        getRemoteConfigInstance().fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _remoteServerCheck.value = getRemoteConfigInstance().getBoolean("server_check")
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