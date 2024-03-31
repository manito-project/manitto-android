package org.sopt.santamanitto.room.join.network

data class JoinRoomModel(
    val room: JoinRoomInfo,
    val user: JoinRoomUserInfo
) {
    data class JoinRoomInfo(
        val id: Int,
        val roomName: String,
        val expiration: String,
        val invitationCode: String
    )

    data class JoinRoomUserInfo(
        val id: Int,
        val userName: String
    )
}