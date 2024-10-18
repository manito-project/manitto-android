package org.sopt.santamanitto.room.manittoroom.network

import com.google.gson.annotations.SerializedName

data class ManittoRoomModel(
    @SerializedName("id") val roomId: String,
    val roomName: String,
    val invitationCode: String,
    val createdAt: String,
    val expirationDate: String,
    val matchingDate: String?,
    val deletedByCreatorDate: String?,
    @SerializedName("Creator") val creator: ManittoRoomCreator,
    @SerializedName("Missions") val missions: List<ManittoRoomMission>,
    @SerializedName("Members") val members: List<ManittoRoomMember>,
) {
    data class ManittoRoomCreator(
        @SerializedName("id") val userId: String,
        @SerializedName("username") val userName: String,
        val manittoUserId: String?,
    )

    data class ManittoRoomMission(
        @SerializedName("id") val missionId: String,
        val content: String,
    )

    val isMatched: Boolean
        get() = matchingDate != null
}
