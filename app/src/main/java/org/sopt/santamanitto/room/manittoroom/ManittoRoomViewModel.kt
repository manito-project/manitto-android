package org.sopt.santamanitto.room.manittoroom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.sopt.santamanitto.NetworkViewModel

class ManittoRoomViewModel: NetworkViewModel() {

    private var _roomId = -1
    var roomId: Int
        get() = _roomId
        set(value) {
            _roomId = value
        }

    private var _isMatched = false
    var isMatched: Boolean
        get() = _isMatched
        set(value) {
            _isMatched = value
        }

    private val _roomName = MutableLiveData<String>(null)
    val roomName: LiveData<String>
        get() = _roomName

    private val _roomDescription = MutableLiveData<String>(null)
    val roomDescription: LiveData<String>
        get() = _roomDescription

}