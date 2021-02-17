package org.sopt.santamanitto.room.join

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import org.sopt.santamanitto.NetworkViewModel

class JoinRoomViewModel: NetworkViewModel() {

    val joinCode = MutableLiveData<String>(null)

    val isJoinCodeEmpty = Transformations.map(joinCode) {
        it.isNullOrEmpty()
    }
}