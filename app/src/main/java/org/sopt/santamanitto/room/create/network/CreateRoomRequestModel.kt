package org.sopt.santamanitto.room.create.network

data class CreateRoomRequestModel(
    val roomName: String,
    val expirationDate: String,
    val missionContents: List<String> = mutableListOf(),
)
