package org.sopt.santamanitto.user.mypage

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import org.sopt.santamanitto.NetworkViewModel
import org.sopt.santamanitto.user.data.source.UserDataSource
import javax.inject.Named

class EditNameViewModel @ViewModelInject constructor(
    @Named("cached") private val userCachedDataSource: UserDataSource
) : NetworkViewModel() {

    val previousName = userCachedDataSource.getUserName()

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
        userCachedDataSource.changeUserName(newName.value!!) {
            if (it) {
                _requestDone.value = true
            } else {
                _networkErrorOccur.value = true
            }
        }
    }
}