package org.sopt.santamanitto.user.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import dagger.hilt.android.lifecycle.HiltViewModel
import org.sopt.santamanitto.NetworkViewModel
import org.sopt.santamanitto.user.data.controller.UserAuthController
import org.sopt.santamanitto.user.data.source.CachedUserMetadataSource
import javax.inject.Inject

@HiltViewModel
class EditNameViewModel
    @Inject
    constructor(
        private val userMetadataSource: CachedUserMetadataSource,
        private val userAuthController: UserAuthController,
    ) : NetworkViewModel() {
        val previousName = userMetadataSource.getUserName()

        val newName = MutableLiveData<String>(null)

        val isUserNameValid: LiveData<Boolean> =
            newName.map {
                !it.isNullOrBlank()
            }

        private val _requestDone = MutableLiveData(false)
        val requestDone: LiveData<Boolean>
            get() = _requestDone

        fun requestChangeName() {
            if (newName.value == previousName) {
                _requestDone.value = true
                return
            }
            userAuthController.changeUserName(
                userMetadataSource.getUserId(),
                newName.value.orEmpty(),
            ) { isSuccess ->
                if (isSuccess) {
                    userMetadataSource.setUserNameDirty()
                    _requestDone.value = true
                } else {
                    _networkErrorOccur.value = true
                }
            }
        }
    }
