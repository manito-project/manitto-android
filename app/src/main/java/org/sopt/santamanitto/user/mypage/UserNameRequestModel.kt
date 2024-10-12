package org.sopt.santamanitto.user.mypage

import com.google.gson.annotations.SerializedName

data class UserNameRequestModel(
    @SerializedName("username") val userName: String
)
