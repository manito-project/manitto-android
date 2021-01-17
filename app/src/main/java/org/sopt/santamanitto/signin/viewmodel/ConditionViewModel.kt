package org.sopt.santamanitto.signin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ConditionViewModel : ViewModel() {

    val isReady = MutableLiveData<Boolean>()

    fun setIsReady(boolean: Boolean) {
        isReady.value = boolean
    }
}