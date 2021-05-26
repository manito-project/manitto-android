package org.sopt.santamanitto.user.mypage

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import org.sopt.santamanitto.NetworkViewModel
import org.sopt.santamanitto.user.data.controller.UserAuthController
import org.sopt.santamanitto.user.data.source.CachedUserMetadataSource

class EditNameViewModel @ViewModelInject constructor(
        private val userMetadataSource: CachedUserMetadataSource,
        private val userAuthController: UserAuthController
) : NetworkViewModel() {

    val previousName = userMetadataSource.getUserName()

    val newName = MutableLiveData<String>(null)

    val isUserNameValid: LiveData<Boolean> = Transformations.map(newName) { name ->
        !name.isNullOrBlank()
    }

    private val _requestDone = MutableLiveData(false)
    val requestDone: LiveData<Boolean>
        get() = _requestDone

    fun requestChangeName() {
        if (newName.value == previousName) {
            _requestDone.value = true
            return
        }
        userAuthController.changeUserName(userMetadataSource.getUserId(), newName.value!!) {
            if (it) {
                userMetadataSource.setUserNameDirty()
                _requestDone.value = true
            } else {
                _networkErrorOccur.value = true
            }
        }
    }
}