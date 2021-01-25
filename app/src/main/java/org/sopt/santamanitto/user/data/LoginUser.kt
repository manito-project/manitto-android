package org.sopt.santamanitto.user.data

import com.google.gson.annotations.SerializedName

data class LoginUser(
        @SerializedName("username")
        val userName: String,
        val serialNumber: String,
        val id: Int = 0,
        val accessToken: String = "null"
)