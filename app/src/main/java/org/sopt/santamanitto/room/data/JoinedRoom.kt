package org.sopt.santamanitto.room.data

import com.google.gson.annotations.SerializedName

data class JoinedRoom(
    @SerializedName("id") val roomId: Int,
    val roomName: String,
    val isMatchingDone: Boolean,
    val expiration: String,
    val createdAt: String
)