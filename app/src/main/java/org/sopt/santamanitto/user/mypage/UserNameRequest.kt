package org.sopt.santamanitto.user.mypage

import com.google.gson.annotations.SerializedName

data class UserNameRequest(
        @SerializedName("username") val userName: String
)
