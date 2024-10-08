package org.sopt.santamanitto.room.data

import com.google.gson.annotations.SerializedName
import org.sopt.santamanitto.util.TimeUtil

data class MyManittoModel(
    @SerializedName("id") val roomId: Int,
    val roomName: String,
    val creatorId: String,
    val isDeletedByCreator: Boolean,
    val isMatchingDone: Boolean,
    val expiration: String,
    val createdAt: String
) {
    val isExpiredWithoutMatching: Boolean
        get() = !isMatchingDone && !TimeUtil.isLaterThanNow(expiration)
}