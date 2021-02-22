package org.sopt.santamanitto.room.manittoroom

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.sopt.santamanitto.NetworkViewModel
import org.sopt.santamanitto.room.manittoroom.network.ManittoRoomData
import org.sopt.santamanitto.room.manittoroom.network.ManittoRoomMember
import org.sopt.santamanitto.room.network.RoomRequest
import org.sopt.santamanitto.user.data.source.UserDataSource
import javax.inject.Named

class ManittoRoomViewModel @ViewModelInject constructor(
    @Named("cached") private val userDataSource: UserDataSource,
    private val roomRequest: RoomRequest
): NetworkViewModel() {

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

    private var _invitationCode = ""
    val invitationCode: String
        get() = _invitationCode

    private val _roomName = MutableLiveData<String>(null)
    val roomName: LiveData<String>
        get() = _roomName

    private val _expiration = MutableLiveData<String>(null)
    val expiration: LiveData<String>
        get() = _expiration

    private val _members = MutableLiveData<List<ManittoRoomMember>>()
    val members : LiveData<List<ManittoRoomMember>>
        get() = _members

    private val _isAdmin = MutableLiveData(false)
    val isAdmin: LiveData<Boolean>
        get() = _isAdmin

    fun refreshManittoRoomInfo() {
        roomRequest.getManittoRoomData(roomId, object: RoomRequest.GetManittoRoomCallback {
            override fun onLoadManittoRoomData(manittoRoomData: ManittoRoomData) {
                _roomName.value = manittoRoomData.roomName
                _expiration.value = manittoRoomData.expiration
                _members.value = manittoRoomData.members
                _invitationCode = manittoRoomData.invitationCode
                _isAdmin.value = userDataSource.getUserId() == manittoRoomData.creator.userId
            }

            override fun onFailed() {
                _networkErrorOccur.value = true
            }
        })
    }

}