package org.sopt.santamanitto.room.data

import com.google.gson.annotations.SerializedName

data class PersonalRoomInfo(
        @SerializedName("SantaUserId") val santaUserId: Int,
        @SerializedName("ManittoUserId") val manittoUserId: Int,
        @SerializedName("MyMission") val myMission: MissionContent,
        @SerializedName("MissionToMe") val missionToMe: MissionContent
)