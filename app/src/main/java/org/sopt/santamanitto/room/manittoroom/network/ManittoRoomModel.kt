package org.sopt.santamanitto.room.manittoroom.network

import com.google.gson.annotations.SerializedName

data class ManittoRoomModel(
    @SerializedName("id") val roomId: Int,
    val roomName: String,
    val invitationCode: String,
    @SerializedName("isMatchingDone") val _isMatched: String,
    val expiration: String,
    val createdAt: String,
    @SerializedName("Creator") val creator: ManittoRoomCreator,
    @SerializedName("Missions") val missions: List<ManittoRoomMission>,
    @SerializedName("Members") val members: List<ManittoRoomMember>
) {
    data class ManittoRoomCreator(
        @SerializedName("id") val userId: Int,
        @SerializedName("username") val userName: String,
        val serialNumber: String
    )

    data class ManittoRoomMission(
        @SerializedName("id") val missionId: Int,
        val content: String
    )

    val isMatched: Boolean
        get() = _isMatched == "true"
}