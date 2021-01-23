package org.sopt.santamanitto.user.source

import com.google.gson.annotations.SerializedName
import org.sopt.santamanitto.data.JoinedRoom

data class UserJoinedRooms(
    @SerializedName("JoinedRooms")
    val joinedRooms: List<JoinedRoom>
)