package org.sopt.santamanitto.room.manittoroom.network

import com.google.gson.annotations.SerializedName
import org.sopt.santamanitto.room.data.MissionContentModel

data class MatchedMissionsModel(
    @SerializedName("UserId") val userId: Int,
    @SerializedName("SantaUserId") val santaUserId: Int,
    @SerializedName("ManittoUserId") val manittoUserId: Int,
    @SerializedName("MyMission") val myMission: MissionContentModel?
)