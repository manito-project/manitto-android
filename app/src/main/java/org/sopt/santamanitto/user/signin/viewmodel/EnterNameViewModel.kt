package org.sopt.santamanitto.user.signin.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map

class EnterNameViewModel : ViewModel() {

    var userName = MutableLiveData<String?>()

    var isUserNameValid: LiveData<Boolean> = userName.map {
        !it.isNullOrBlank()
    }
}