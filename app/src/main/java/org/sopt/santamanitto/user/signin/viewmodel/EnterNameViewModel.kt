package org.sopt.santamanitto.user.signin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class EnterNameViewModel : ViewModel() {

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName

    val isUserNameValid: StateFlow<Boolean> = userName.map { it.isNotBlank() }
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    fun setUserName(newName: String) {
        _userName.value = newName
    }
}
