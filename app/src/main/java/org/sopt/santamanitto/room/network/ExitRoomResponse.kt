package org.sopt.santamanitto.room.network

data class ExitRoomResponse(
        val status: Int,
        val success: Boolean,
        val message: String
)
