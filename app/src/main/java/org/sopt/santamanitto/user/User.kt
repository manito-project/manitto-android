package org.sopt.santamanitto.user

import com.google.gson.annotations.SerializedName
import org.sopt.santamanitto.util.TimeUtil

data class User(
        @SerializedName("username")
        val userName: String,
        val serialNumber: String,
        val id: Int = 0,
        val updatedAt: String = TimeUtil.getCurrentTime(),
        val createdAt: String = TimeUtil.getCurrentTime(),
        val accessToken: String = "null"
)