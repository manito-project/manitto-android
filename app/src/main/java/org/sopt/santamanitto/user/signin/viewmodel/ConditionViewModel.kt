package org.sopt.santamanitto.user.signin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.sopt.santamanitto.user.data.LoginUserResponse
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

    private var _isWaitingForResponse = false
    val isWaitingForResponse: Boolean
        get() = _isWaitingForResponse

    fun signIn(userName: String) {
        if (_isWaitingForResponse) {
            return
        }
        _isWaitingForResponse = true
        userController.createAccount(userName, serialNumber, object : UserController.CreateAccountCallback {

            override fun onCreateAccountSuccess(loginUserResponse: LoginUserResponse) {
                userMetadataSource.run {
                    loginUserResponse.let {
                        setUserName(it.userName)
                        setAccessToken(it.accessToken)
                        setUserId(it.id)
                    }
                }
                userSaveSuccess.value = true
                _isWaitingForResponse = false
            }

            override fun onCreateAccountFailed() {
                userSaveFail.value = true
                _isWaitingForResponse = false
            }
        })
    }
}