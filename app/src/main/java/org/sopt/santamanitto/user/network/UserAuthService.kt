package org.sopt.santamanitto.user.network

import org.sopt.santamanitto.network.Response
import org.sopt.santamanitto.user.data.UserInfoModel
import org.sopt.santamanitto.user.mypage.UserNameRequestModel
import org.sopt.santamanitto.user.mypage.UserNameModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserAuthService {
    @GET("users/{userId}")
    fun getUserInfo(
        @Path("userId") userId: Int
    ) : Call<Response<UserInfoModel>>

    @PUT("users/{userId}")
    fun changeUserName(
        @Path("userId") userId: Int,
        @Body request: UserNameRequestModel
    ): Call<Response<UserNameModel>>
}