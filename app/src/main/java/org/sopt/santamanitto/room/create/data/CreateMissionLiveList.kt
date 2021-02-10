package org.sopt.santamanitto.room.create.data

import androidx.lifecycle.LiveData

class CreateMissionLiveList : LiveData<CreateMissionLiveList>() {

    private val missions = mutableListOf<String>()

    init {
        value = this
    }

    fun addMission(mission: String) {
        missions.add(mission)
        value = this
    }

    fun deleteMission(mission: String) {
        missions.remove(mission)
        value = this
    }

    fun getMissions(): List<String> {
        return missions
    }

    fun isEmpty(): Boolean {
        return missions.isEmpty()
    }

    fun clear() {
        missions.clear()
        value = this
    }
}