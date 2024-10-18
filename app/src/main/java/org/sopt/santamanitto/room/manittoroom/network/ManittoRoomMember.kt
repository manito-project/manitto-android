package org.sopt.santamanitto.room.manittoroom.network

import com.google.gson.annotations.SerializedName

data class ManittoRoomMember(
    val santa: ManittoRoomInfo,
    val manitto: ManittoRoomInfo
) {
    data class ManittoRoomInfo(
        @SerializedName("id") val userId: String,
        @SerializedName("username") val userName: String,
    )
}
