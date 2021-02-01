package org.sopt.santamanitto.room.data

data class CreateRoomData(
    val roomName: String,
    val expiration: String,
    val missionContents: List<String> = mutableListOf()
)