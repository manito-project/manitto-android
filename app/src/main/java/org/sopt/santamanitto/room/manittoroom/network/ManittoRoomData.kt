package org.sopt.santamanitto.room.manittoroom.network

import com.google.gson.annotations.SerializedName

data class ManittoRoomData(
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
    val isMatched: Boolean
        get() = _isMatched == "true"
}