package org.sopt.santamanitto.user.signin.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class EnterNameViewModel: ViewModel() {

    var userName = MutableLiveData<String?>()

    var isUserNameValid: LiveData<Boolean> = Transformations.map(userName) { name ->
        !name.isNullOrBlank()
    }
}