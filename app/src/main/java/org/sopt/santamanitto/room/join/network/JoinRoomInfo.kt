package org.sopt.santamanitto.room.join.network

data class JoinRoomInfo(
    val id: Int,
    val roomName: String,
    val expiration: String,
    val invitationCode: String
)