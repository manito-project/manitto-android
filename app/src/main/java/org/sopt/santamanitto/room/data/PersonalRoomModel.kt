package org.sopt.santamanitto.room.data

import com.google.gson.annotations.SerializedName

data class PersonalRoomModel(
    @SerializedName("SantaUserId") val santaUserId: String,
    @SerializedName("ManittoUserId") val manittoUserId: String,
    @SerializedName("MyMission") val myMission: MissionContentModel?,
    @SerializedName("MissionToMe") val missionToMe: MissionContentModel?
)