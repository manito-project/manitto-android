package org.sopt.santamanitto.user.signin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.sopt.santamanitto.user.data.controller.UserController
import org.sopt.santamanitto.user.data.source.UserMetadataSource
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ConditionViewModel @Inject constructor(
    private val userController: UserController,
    private val userMetadataSource: UserMetadataSource,
    @Named("serialNumber") private val serialNumber: String
) : ViewModel() {

    val isReady = MutableLiveData<Boolean>()
    val userSaveSuccess = MutableLiveData(false)
    val userSaveFail = MutableLiveData(false)
    val isUserExist = MutableLiveData(false)

    private var _isWaitingForResponse = false
    val isWaitingForResponse: Boolean
        get() = _isWaitingForResponse

    fun signIn(userName: String) {
        if (_isWaitingForResponse) {
            return
        }
        _isWaitingForResponse = true

        viewModelScope.launch {
            val result = userController.createAccount(userName, serialNumber)
            _isWaitingForResponse = false

            result.onSuccess { signUpResponseModel ->
                userMetadataSource.run {
                    setAccessToken(signUpResponseModel.accessToken)
                    setUserId(signUpResponseModel.id)
                }
                userSaveSuccess.value = true
                _isWaitingForResponse = false
            }.onFailure { exception ->
                when {
                    exception.message?.contains("409") == true -> {
                        isUserExist.value = true
                        _isWaitingForResponse = false
                    }

                    else -> {
                        userSaveFail.value = true
                        _isWaitingForResponse = false
                    }
                }
            }
        }
    }
}