package org.sopt.santamanitto.room.manittoroom

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.sopt.santamanitto.NetworkViewModel
import org.sopt.santamanitto.room.manittoroom.network.ManittoRoomData
import org.sopt.santamanitto.room.manittoroom.network.ManittoRoomMatchedMissions
import org.sopt.santamanitto.room.manittoroom.network.ManittoRoomMember
import org.sopt.santamanitto.room.network.RoomRequest
import org.sopt.santamanitto.user.data.User
import org.sopt.santamanitto.user.data.source.UserDataSource
import javax.inject.Named

class ManittoRoomViewModel @ViewModelInject constructor(
    @Named("cached") private val userDataSource: UserDataSource,
    private val roomRequest: RoomRequest
): NetworkViewModel() {

    companion object {
        private const val TAG = "ManittoRoomViewModel"
    }

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

    private val _myManittoName = MutableLiveData("")
    val myManittoName : LiveData<String>
        get() = _myManittoName

    private val _myMission = MutableLiveData("")
    val myMission: LiveData<String>
        get() = _myMission

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

    fun match() {
        roomRequest.matchManitto(roomId, object : RoomRequest.MatchManittoCallback {
            override fun onSuccessMatching(missions: List<ManittoRoomMatchedMissions>) {
                isMatched = true
                findMyMission(missions)
                Log.d(TAG, "matchResult : $missions")
            }

            override fun onFailed() {
                _networkErrorOccur.value = true
            }
        })
    }

    private fun findMyMission(missions: List<ManittoRoomMatchedMissions>) {
        for (mission in missions) {
            if (mission.userId == userDataSource.getUserId()) {
                setMyMissionInfo(mission)
                return
            }
        }
        _networkErrorOccur.value = true
    }

    private fun setMyMissionInfo(mission: ManittoRoomMatchedMissions) {
        _myMission.value = mission.myMission.content
        Log.d(TAG, "myMission : ${mission.myMission.content}")
        userDataSource.getUserInfo(mission.manittoUserId, object: UserDataSource.GetUserInfoCallback {
            override fun onUserInfoLoaded(user: User) {
                _myManittoName.value = user.userName
            }

            override fun onDataNotAvailable() {
                _networkErrorOccur.value = true
            }
        })
    }

}