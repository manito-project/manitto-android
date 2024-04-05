package org.sopt.santamanitto.room.create.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import dagger.hilt.android.lifecycle.HiltViewModel
import org.sopt.santamanitto.NetworkViewModel
import org.sopt.santamanitto.room.create.data.CreateMissionLiveList
import org.sopt.santamanitto.room.create.data.ExpirationLiveData
import org.sopt.santamanitto.room.create.network.CreateRoomRequestModel
import org.sopt.santamanitto.room.create.network.CreateRoomModel
import org.sopt.santamanitto.room.create.network.ModifyRoomRequestModel
import org.sopt.santamanitto.room.manittoroom.network.ManittoRoomModel
import org.sopt.santamanitto.room.network.RoomRequest
import org.sopt.santamanitto.user.data.source.CachedMainUserDataSource
import javax.inject.Inject


@HiltViewModel
class CreateRoomAndMissionViewModel @Inject constructor(
        private val cachedMainUserDataSource: CachedMainUserDataSource,
        private val roomRequest: RoomRequest
) : NetworkViewModel() {

    private var roomId = -1

    var expirationLiveData = ExpirationLiveData()

    var roomName = MutableLiveData<String?>(null)
    private val _hint = MutableLiveData<String?>(null)
    val hint: LiveData<String?>
        get() = _hint

    val missions = CreateMissionLiveList()

    val missionIsEmpty = missions.map { it.isEmpty() }
    var heightOfRecyclerView: Int = 0

    var nameIsNullOrEmpty = roomName.map { it.isNullOrBlank() }

    fun getRoomData(roomId: Int) {
        if (roomId == -1) {
            return
        }
        this.roomId = roomId
        roomRequest.getManittoRoomData(roomId, object : RoomRequest.GetManittoRoomCallback {
            override fun onLoadManittoRoomData(manittoRoomModel: ManittoRoomModel) {
                roomName.value = manittoRoomModel.roomName
                _hint.value = manittoRoomModel.roomName
                expirationLiveData.init(manittoRoomModel.expiration)
            }

            override fun onFailed() {
                _networkErrorOccur.value = true
            }
        })
    }

    fun setPeriod(period: Int) {
        expirationLiveData.period = period
    }

    fun setAmPm(isAm: Boolean) {
        expirationLiveData.setAmPm(isAm)
    }

    fun setTime(hour: Int, minute: Int) {
        expirationLiveData.setTime(hour, minute)
    }

    fun addMission(mission: String) {
        missions.addMission(mission)
    }

    fun deleteMission(mission: String) {
        missions.deleteMission(mission)
    }

    fun hasMissions(): Boolean {
        return missions.getMissions().isNotEmpty()
    }

    fun clearMission() {
        missions.clear()
    }

    fun createRoom(callback: (CreateRoomModel) -> Unit) {
        val request = CreateRoomRequestModel(roomName.value!!, expirationLiveData.toString(), missions.getMissions())
        roomRequest.createRoom(request, object : RoomRequest.CreateRoomCallback {
            override fun onRoomCreated(createdRoom: CreateRoomModel) {
                callback(createdRoom)
            }

            override fun onFailed() {
                _networkErrorOccur.value = true
            }
        })

        cachedMainUserDataSource.isMyManittoDirty = true
    }

    fun modifyRoom(callback: () -> Unit) {
        roomRequest.modifyRoom(roomId, ModifyRoomRequestModel(roomName.value!!, expirationLiveData.toString())) {
            if (it) {
                callback.invoke()
            } else {
                _networkErrorOccur.value = true
            }
        }
    }
}