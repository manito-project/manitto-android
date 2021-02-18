package org.sopt.santamanitto.room.manittoroom.network

import com.google.gson.annotations.SerializedName

data class ManittoRoomMission(
    @SerializedName("id") val missionId: Int,
    val content: String
)
