package org.sopt.santamanitto.user.data

import com.google.gson.annotations.SerializedName
import org.sopt.santamanitto.room.data.JoinedRoom

data class UserInfoResponse(
        @SerializedName("id") val userId: Int,
        @SerializedName("username") val userName: String,
        @SerializedName("JoinedRooms") val joinedRooms: List<JoinedRoom>
)