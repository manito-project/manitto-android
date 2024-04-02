package org.sopt.santamanitto.user.mypage

import com.google.gson.annotations.SerializedName

data class UserNameModel(
        val id: Int,
        @SerializedName("username")
        val userName: String,
        val serialNumber: String,
        val isDeleted: Boolean,
        val createdAt: Boolean,
        val updatedAt: Boolean
)