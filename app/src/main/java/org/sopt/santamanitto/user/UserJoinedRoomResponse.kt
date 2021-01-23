package org.sopt.santamanitto.user

import com.google.gson.annotations.SerializedName
import org.sopt.santamanitto.data.JoinedRoom

data class UserJoinedRoomResponse(
    @SerializedName("JoinedRooms")
    val joinedRooms: List<JoinedRoom>
)