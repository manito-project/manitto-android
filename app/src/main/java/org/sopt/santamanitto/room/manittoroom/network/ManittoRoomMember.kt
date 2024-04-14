package org.sopt.santamanitto.room.manittoroom.network

import com.google.gson.annotations.SerializedName

data class ManittoRoomMember(
    @SerializedName("id") val userId: Int,
    @SerializedName("username") val userName: String,
    val relations: ManittoRoomRelations
)
