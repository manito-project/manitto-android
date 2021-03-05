package org.sopt.santamanitto.room.join.network

data class JoinRoomResponse(
    val room: JoinRoomInfo,
    val user: JoinRoomUserInfo
)