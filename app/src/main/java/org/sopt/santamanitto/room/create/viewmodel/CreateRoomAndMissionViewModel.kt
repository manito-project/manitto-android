package org.sopt.santamanitto.room.create.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import org.sopt.santamanitto.NetworkViewModel
import org.sopt.santamanitto.room.create.data.CreateMissionLiveList
import org.sopt.santamanitto.room.create.network.CreateRoomData
import org.sopt.santamanitto.room.create.data.ExpirationLiveData
import org.sopt.santamanitto.room.create.network.CreateRoomRequest
import org.sopt.santamanitto.room.create.network.CreateRoomResponse

class CreateRoomAndMissionViewModel @ViewModelInject constructor(
        private val createRoomRequest: CreateRoomRequest
) : NetworkViewModel() {

    val expirationLiveData = ExpirationLiveData()

    var roomName = MutableLiveData<String?>(null)

    val missions = CreateMissionLiveList()

    val missionIsEmpty = Transformations.map(missions) {
        it.isEmpty()
    }

    var heightOfRecyclerView: Int = 0

    var nameIsNullOrEmpty = Transformations.map(roomName) {
        it.isNullOrBlank()
    }

    fun setDayDiff(dayDIff: Int) {
        expirationLiveData.dayDiff = dayDIff
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
        createRoomRequest.createRoom(createRoomData, object : CreateRoomRequest.CreateRoomCallback {
            override fun onRoomCreated(createdRoom: CreateRoomResponse) {
                callback(createdRoom)
            }

            override fun onFailed() {
                _networkErrorOccur.value = true
            }
        })
    }
}