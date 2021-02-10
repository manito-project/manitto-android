package org.sopt.santamanitto.room.create.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import org.sopt.santamanitto.NetworkViewModel
import org.sopt.santamanitto.room.data.CreateMissionLiveList
import org.sopt.santamanitto.room.data.ExpirationLiveData

class CreateRoomAndMissionViewModel : NetworkViewModel() {

    val expirationLiveData = ExpirationLiveData()

    var roomName = MutableLiveData<String?>(null)

    val missions = CreateMissionLiveList()

    val missionIsEmpty = Transformations.map(missions) {
        it.getMissions().isEmpty()
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
 }