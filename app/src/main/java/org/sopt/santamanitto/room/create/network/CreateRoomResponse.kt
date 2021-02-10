package org.sopt.santamanitto.room.create.network

data class CreateRoomResponse(
    val isMatchingDone: Boolean,
    val id: Int,
    val roomName: String,
    val expiration: String,
    val invitationCode: String,
    val updatedAt: String,
    val createdAt: String
)