package org.sopt.santamanitto.user.source

import com.google.gson.annotations.SerializedName
import org.sopt.santamanitto.data.JoinedRoom

data class User(
        @SerializedName("id") val userId: Int,
        @SerializedName("username") val userName: String,
        @SerializedName("JoinedRooms") val joinedRooms: List<JoinedRoom>
)