package org.sopt.santamanitto.user.source

import com.google.gson.annotations.SerializedName
import org.sopt.santamanitto.data.JoinedRoom

data class UserJoinedRoomsResponse(
    @SerializedName("JoinedRooms")
    val joinedRooms: List<JoinedRoom>
)