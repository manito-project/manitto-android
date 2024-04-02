package org.sopt.santamanitto.room.data

import com.google.gson.annotations.SerializedName

data class PersonalRoomModel(
    @SerializedName("SantaUserId") val santaUserId: Int,
    @SerializedName("ManittoUserId") val manittoUserId: Int,
    @SerializedName("MyMission") val myMission: MissionContentModel?,
    @SerializedName("MissionToMe") val missionToMe: MissionContentModel?
)