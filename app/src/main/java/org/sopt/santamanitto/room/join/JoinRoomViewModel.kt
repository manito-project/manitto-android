package org.sopt.santamanitto.room.join

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import org.sopt.santamanitto.NetworkViewModel
import org.sopt.santamanitto.room.join.network.JoinRoomData
import org.sopt.santamanitto.room.join.network.JoinRoomResponse
import org.sopt.santamanitto.room.network.RoomRequest
import org.sopt.santamanitto.room.network.RoomRequest.JoinRoomError
import org.sopt.santamanitto.user.data.source.CachedMainUserDataSource

class JoinRoomViewModel @ViewModelInject constructor(
        private val cachedMainUserDataSource: CachedMainUserDataSource,
        private val roomRequest: RoomRequest
) : NetworkViewModel() {

    val invitationCode = MutableLiveData<String>(null)

    val isInvitationCodeEmpty = Transformations.map(invitationCode) {
        it.isNullOrEmpty()
    }

    private val _isDuplicatedMember = MutableLiveData(false)
    val isDuplicatedMember: LiveData<Boolean>
        get() = _isDuplicatedMember

    private val _isWrongInvitationCode = MutableLiveData(false)
    val isWrongInvitationCode: LiveData<Boolean>
        get() = _isWrongInvitationCode

    private val _isAlreadyMatchedRoom = MutableLiveData(false)
    val isAlreadyMatchedRoom: LiveData<Boolean>
        get() = _isAlreadyMatchedRoom

    fun joinRoom(callback: (JoinRoomResponse) -> Unit) {
        roomRequest.joinRoom(JoinRoomData(invitationCode.value!!),
                object : RoomRequest.JoinRoomCallback {

                    override fun onSuccessJoinRoom(joinedRoom: JoinRoomResponse) {
                        cachedMainUserDataSource.isJoinedRoomDirty = true
                        callback(joinedRoom)
                    }

                    override fun onFailed(joinRoomError: JoinRoomError) {
                        when (joinRoomError) {
                            JoinRoomError.WrongInvitationCode -> _isWrongInvitationCode.value = true
                            JoinRoomError.AlreadyMatched -> _isAlreadyMatchedRoom.value = true
                            JoinRoomError.DuplicatedMember -> _isDuplicatedMember.value = true
                            else -> _networkErrorOccur.value = true
                        }
                    }
                })
    }
}