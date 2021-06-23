package org.sopt.santamanitto.room.create.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import org.sopt.santamanitto.NetworkViewModel
import org.sopt.santamanitto.room.create.data.CreateMissionLiveList
import org.sopt.santamanitto.room.create.data.ExpirationLiveData
import org.sopt.santamanitto.room.create.network.CreateRoomData
import org.sopt.santamanitto.room.create.network.CreateRoomResponse
import org.sopt.santamanitto.room.create.network.ModifyRoomData
import org.sopt.santamanitto.room.manittoroom.network.ManittoRoomData
import org.sopt.santamanitto.room.network.RoomRequest
import org.sopt.santamanitto.user.data.source.CachedMainUserDataSource

class CreateRoomAndMissionViewModel @ViewModelInject constructor(
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

    val missionIsEmpty = Transformations.map(missions) {
        it.isEmpty()
    }

    var heightOfRecyclerView: Int = 0

    var nameIsNullOrEmpty = Transformations.map(roomName) {
        it.isNullOrBlank()
    }

    fun start(roomId: Int) {
        if (roomId == -1) {
            return
        }
        this.roomId = roomId
        roomRequest.getManittoRoomData(roomId, object : RoomRequest.GetManittoRoomCallback {
            override fun onLoadManittoRoomData(manittoRoomData: ManittoRoomData) {
                roomName.value = manittoRoomData.roomName
                _hint.value = manittoRoomData.roomName
                expirationLiveData.init(manittoRoomData.expiration)
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

    fun createRoom(callback: (CreateRoomResponse) -> Unit) {
        val createRoomData = CreateRoomData(roomName.value!!, expirationLiveData.toString(), missions.getMissions())
        roomRequest.createRoom(createRoomData, object : RoomRequest.CreateRoomCallback {
            override fun onRoomCreated(createdRoom: CreateRoomResponse) {
                callback(createdRoom)
            }

            override fun onFailed() {
                _networkErrorOccur.value = true
            }
        })

        cachedMainUserDataSource.isMyManittoDirty = true
    }

    fun modifyRoom(callback: () -> Unit) {
        roomRequest.modifyRoom(roomId, ModifyRoomData(roomName.value!!, expirationLiveData.toString())) {
            if (it) {
                callback.invoke()
            } else {
                _networkErrorOccur.value = true
            }
        }
    }
}