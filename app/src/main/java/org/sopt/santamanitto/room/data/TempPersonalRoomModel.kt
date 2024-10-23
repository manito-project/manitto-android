package org.sopt.santamanitto.room.data

import com.google.gson.annotations.SerializedName
import org.sopt.santamanitto.room.data.TempMyManittoModel.Member.Manitto
import org.sopt.santamanitto.room.data.TempMyManittoModel.Mission

data class TempPersonalRoomModel(
    @SerializedName("manitto") val manitto: Manitto,
    @SerializedName("mission") val mission: Mission
)
