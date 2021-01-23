package org.sopt.santamanitto.data

import com.google.gson.annotations.SerializedName

data class JoinedRoom(
    @SerializedName("id") val roomId: Int,
    val roomName: String?,
    val isMatchingDone: Boolean?,
    val expiration: String?,
    val createdAt: String?,
    @SerializedName("SantaUserId") var santaUserId: Int?,
    @SerializedName("ManittoUserId") var manittoUserId: Int?,
    @SerializedName("MyMission") var myMission: MissionContent?,
    @SerializedName("MissionToMe") var missionToMe: MissionContent?
)