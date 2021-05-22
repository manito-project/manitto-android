package org.sopt.santamanitto.user.mypage

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import org.sopt.santamanitto.NetworkViewModel
import org.sopt.santamanitto.user.data.source.UserDataSource
import javax.inject.Named

class EditNameViewModel @ViewModelInject constructor(
    @Named("cached") private val userCachedDataSource: UserDataSource
) : NetworkViewModel() {

    val previousName = userCachedDataSource.getUserName()

    var newName = MutableLiveData<String>()
}