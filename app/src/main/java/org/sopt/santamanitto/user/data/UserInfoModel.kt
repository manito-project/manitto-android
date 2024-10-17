package org.sopt.santamanitto.user.data

import com.google.gson.annotations.SerializedName

data class UserInfoModel(
    @SerializedName("id") val userId: Int,
    @SerializedName("username") val userName: String,
)