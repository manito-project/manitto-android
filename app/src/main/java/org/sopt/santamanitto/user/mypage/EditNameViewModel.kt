package org.sopt.santamanitto.user.mypage

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.sopt.santamanitto.NetworkViewModel
import org.sopt.santamanitto.user.data.controller.UserAuthController
import org.sopt.santamanitto.user.data.source.CachedUserMetadataSource
import javax.inject.Inject

@HiltViewModel
class EditNameViewModel @Inject constructor(
    private val userMetadataSource: CachedUserMetadataSource,
    private val userAuthController: UserAuthController,
) : NetworkViewModel() {

    val previousName = userMetadataSource.getUserName()

    private val _newName = MutableStateFlow("")
    val newName: StateFlow<String> = _newName

    val isUserNameValid: StateFlow<Boolean> =
        _newName.map {
            it.isNotBlank() && it.length <= 10 && it != previousName
        }.stateIn(viewModelScope, SharingStarted.Lazily, false)

    private val _requestDone = MutableStateFlow(false)
    val requestDone: StateFlow<Boolean> = _requestDone

    fun setNewName(newName: String) {
        _newName.value = newName
    }

    fun requestChangeName() {
        viewModelScope.launch {
            if (newName.value == previousName) {
                _requestDone.value = true
                return@launch
            }
            val result = userAuthController.changeUserName(newName.value)

            if (result.isSuccess) {
                userMetadataSource.setUserNameDirty()
                userMetadataSource.setUserName(newName.value)
                _requestDone.value = true
            } else {
                _networkErrorOccur.value = true
            }
        }
    }
}
