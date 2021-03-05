package org.sopt.santamanitto.room.manittoroom.network

import com.google.gson.annotations.SerializedName

data class ManittoRoomRelations(
    @SerializedName("SantaUserId") val santaUserId: Int,
    @SerializedName("ManittoUserId") val manittoUserId: Int
)
