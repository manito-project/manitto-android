package org.sopt.santamanitto.room.create.network

data class CreateRoomRequestModel(
    val roomName: String,
    val expiration: String,
    val missionContents: List<String> = mutableListOf()
)