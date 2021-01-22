package org.sopt.santamanitto.signin.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sopt.santamanitto.user.User
import org.sopt.santamanitto.user.source.UserDataSource
import javax.inject.Named

class ConditionViewModel @ViewModelInject constructor(
        @Named("userRepository") private val userRepository: UserDataSource
) : ViewModel() {

    val isReady = MutableLiveData<Boolean>()

    val userSaveSuccess = MutableLiveData(false)

    val userSaveFail = MutableLiveData(false)

    fun signIn(userName: String) {
        userRepository.saveUser(userName, object: UserDataSource.SaveUserCallback {
            override fun onUserSaved(user: User) {
                userSaveSuccess.value = true
            }

            override fun onSaveFailed() {
                userSaveFail.value = true
            }
        })
    }
}