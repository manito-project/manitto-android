package org.sopt.santamanitto.signin.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sopt.santamanitto.user.source.LoginUser
import org.sopt.santamanitto.user.source.UserDataSource
import javax.inject.Named

class ConditionViewModel @ViewModelInject constructor(
        @Named("cached") private val userCachedDataSource: UserDataSource,
        @Named("serialNumber") private val serialNumber: String
) : ViewModel() {

    val isReady = MutableLiveData<Boolean>()

    val userSaveSuccess = MutableLiveData(false)

    val userSaveFail = MutableLiveData(false)

    fun signIn(userName: String) {
        userCachedDataSource.createAccount(userName, serialNumber, object : UserDataSource.CreateAccountCallback {

            override fun onCreateAccountSuccess(loginUser: LoginUser) {
                userSaveSuccess.value = true
            }

            override fun onCreateAccountFailed() {
                userSaveFail.value = true
            }
        })
    }
}