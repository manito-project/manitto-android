package org.sopt.santamanitto.signin.viewmodel

import android.provider.Settings
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sopt.santamanitto.preference.UserPreferenceManager

class ConditionViewModel @ViewModelInject constructor(
        private val userPreferenceManager: UserPreferenceManager
) : ViewModel() {

    val isReady = MutableLiveData<Boolean>()

    fun setIsReady(boolean: Boolean) {
        isReady.value = boolean
    }

    fun signIn(userName: String) {
        userPreferenceManager.setUserName(userName)
        userPreferenceManager.setSerialNumber(Settings.Secure.ANDROID_ID)
    }
}