package org.sopt.santamanitto.user.data

import com.google.gson.annotations.SerializedName
import org.sopt.santamanitto.room.data.MyManittoModel

data class UserInfoModel(
        @SerializedName("id") val userId: Int,
        @SerializedName("username") val userName: String,
        @SerializedName("JoinedRooms") val myManittoModels: List<MyManittoModel>
)