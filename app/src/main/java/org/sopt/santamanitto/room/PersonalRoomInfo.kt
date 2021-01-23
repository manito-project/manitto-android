package org.sopt.santamanitto.room

import com.google.gson.annotations.SerializedName
import org.sopt.santamanitto.data.MissionContent

data class PersonalRoomInfo(
    @SerializedName("SantaUserId") val santaUserId: Int,
    @SerializedName("ManittoUserId") val manittoUserId: Int,
    @SerializedName("MyMission") val myMission: MissionContent,
    @SerializedName("MissionToMe") val missionToMe: MissionContent
)