package org.sopt.santamanitto.room.data

import com.google.gson.annotations.SerializedName

data class MyManittoModel(
    @SerializedName("id") val roomId: Int,
    val roomName: String,
    val creatorId: String,
    val isDeletedByCreator: Boolean,
    val isMatchingDone: Boolean,
    val expiration: String,
    val createdAt: String
)