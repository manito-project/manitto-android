package org.sopt.santamanitto.room.data

import com.google.gson.annotations.SerializedName

data class MyManitto(
    @SerializedName("id") val roomId: Int,
    val roomName: String,
    val creatorId: Int,
    val isMatchingDone: Boolean,
    val expiration: String,
    val createdAt: String
)