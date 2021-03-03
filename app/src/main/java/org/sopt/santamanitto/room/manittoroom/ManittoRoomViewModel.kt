package org.sopt.santamanitto.room.manittoroom

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.sopt.santamanitto.NetworkViewModel
import org.sopt.santamanitto.room.data.PersonalRoomInfo
import org.sopt.santamanitto.room.manittoroom.network.ManittoRoomData
import org.sopt.santamanitto.room.manittoroom.network.ManittoRoomMatchedMissions
import org.sopt.santamanitto.room.manittoroom.network.ManittoRoomMember
import org.sopt.santamanitto.room.network.RoomRequest
import org.sopt.santamanitto.user.data.User
import org.sopt.santamanitto.user.data.source.UserDataSource
import org.sopt.santamanitto.util.TimeUtil
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

    var isMatched = false
    var isFinished = false

    private var _invitationCode = ""
    val invitationCode: String
        get() = _invitationCode

    private val _roomName = MutableLiveData<String>(null)
    val roomName: LiveData<String>
        get() = _roomName

    private val _period = MutableLiveData<Int>()
    val period: LiveData<Int>
        get() = _period

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

    private val _mySantaName = MutableLiveData("")
    val mySantaName : LiveData<String>
        get() = _mySantaName

    private val _myMission = MutableLiveData<String?>("")
    val myMission: LiveData<String?>
        get() = _myMission

    private val _missionToMe = MutableLiveData("")
    val missionToMe: LiveData<String?>
        get() = _missionToMe

    val myName: String
        get() = userDataSource.getUserName()

    fun refreshManittoRoomInfo() {
        roomRequest.getManittoRoomData(roomId, object: RoomRequest.GetManittoRoomCallback {
            override fun onLoadManittoRoomData(manittoRoomData: ManittoRoomData) {
                manittoRoomData.run {
                    _roomName.value = roomName
                    _expiration.value = expiration
                    _members.value = members
                    _invitationCode = invitationCode
                    _isAdmin.value = userDataSource.getUserId() == creator.userId
                    this@ManittoRoomViewModel.isMatched = isMatched
                    _period.value = getPeriod(createdAt, expiration)
                }
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
            }

            override fun onFailed() {
                _networkErrorOccur.value = true
            }
        })
    }

    fun getPersonalRelationInfo() {
        roomRequest.getPersonalRoomInfo(roomId, object : RoomRequest.GetPersonalRoomInfoCallback {
            override fun onLoadPersonalRoomInfo(personalRoomInfo: PersonalRoomInfo) {
                userDataSource.getUserInfo(personalRoomInfo.manittoUserId, object: UserDataSource.GetUserInfoCallback {
                    override fun onUserInfoLoaded(user: User) {
                        _myManittoName.value = user.userName
                    }

                    override fun onDataNotAvailable() {
                        _networkErrorOccur.value = true
                    }
                })

                userDataSource.getUserInfo(personalRoomInfo.santaUserId, object : UserDataSource.GetUserInfoCallback {
                    override fun onUserInfoLoaded(user: User) {
                        _mySantaName.value = user.userName
                    }

                    override fun onDataNotAvailable() {
                        _networkErrorOccur.value = true
                    }
                })

                _myMission.value = personalRoomInfo.myMission?.content
                _missionToMe.value = personalRoomInfo.missionToMe?.content
            }

            override fun onDataNotAvailable() {
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
        _myMission.value = mission.myMission?.content
        userDataSource.getUserInfo(mission.manittoUserId, object: UserDataSource.GetUserInfoCallback {
            override fun onUserInfoLoaded(user: User) {
                _myManittoName.value = user.userName
            }

            override fun onDataNotAvailable() {
                _networkErrorOccur.value = true
            }
        })
    }

    private fun getPeriod(createdAt: String, expiration: String): Int {
        return TimeUtil.getDayDiff(expiration, createdAt)
    }
}