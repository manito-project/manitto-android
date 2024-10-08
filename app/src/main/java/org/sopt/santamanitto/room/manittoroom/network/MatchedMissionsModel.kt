package org.sopt.santamanitto.room.manittoroom.network

import com.google.gson.annotations.SerializedName
import org.sopt.santamanitto.room.data.MissionContentModel

data class MatchedMissionsModel(
    @SerializedName("UserId") val userId: String,
    @SerializedName("SantaUserId") val santaUserId: Int,
    @SerializedName("ManittoUserId") val manittoUserId: String,
    @SerializedName("MyMission") val myMission: MissionContentModel?
)