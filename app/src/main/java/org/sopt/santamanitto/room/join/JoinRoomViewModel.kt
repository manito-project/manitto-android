package org.sopt.santamanitto.room.join

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import org.sopt.santamanitto.NetworkViewModel
import org.sopt.santamanitto.room.join.exception.AlreadyMatchedException
import org.sopt.santamanitto.room.join.exception.DuplicatedMemberException
import org.sopt.santamanitto.room.join.exception.JoinRoomException
import org.sopt.santamanitto.room.join.exception.WrongInvitationCodeException
import org.sopt.santamanitto.room.join.network.JoinRoomData
import org.sopt.santamanitto.room.join.network.JoinRoomResponse
import org.sopt.santamanitto.room.network.RoomRequest

class JoinRoomViewModel @ViewModelInject constructor(
    private val roomRequest: RoomRequest
) : NetworkViewModel() {

    val invitationCode = MutableLiveData<String>(null)

    val isInvitationCodeEmpty = Transformations.map(invitationCode) {
        it.isNullOrEmpty()
    }

    private val _isDuplicatedMember = MutableLiveData(false)
    val isDuplicatedMember : LiveData<Boolean>
        get() = _isDuplicatedMember

    private val _isWrongInvitationCode = MutableLiveData(false)
    val isWrongInvitationCode : LiveData<Boolean>
        get() = _isWrongInvitationCode

    private val _isAlreadyMatchedRoom = MutableLiveData(false)
    val isAlreadyMatchedRoom: LiveData<Boolean>
        get() = _isAlreadyMatchedRoom

    fun joinRoom(callback: (JoinRoomResponse) -> Unit) {
        try {
            roomRequest.joinRoom(JoinRoomData(invitationCode.value!!),
                object : RoomRequest.JoinRoomCallback {

                    override fun onSuccessJoinRoom(joinedRoom: JoinRoomResponse) {
                        callback(joinedRoom)
                    }

                    override fun onFailed() {
                        _networkErrorOccur.value = true
                    }
                })
        } catch (e: JoinRoomException) {
            when (e) {
                is DuplicatedMemberException -> _isDuplicatedMember.value = true
                is WrongInvitationCodeException -> _isWrongInvitationCode.value = true
                is AlreadyMatchedException -> _isAlreadyMatchedRoom.value = true
            }
        }
    }
}