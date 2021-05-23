package org.sopt.santamanitto.user.network

import org.sopt.santamanitto.network.Response
import org.sopt.santamanitto.user.data.UserInfoResponse
import org.sopt.santamanitto.user.mypage.UserNameRequest
import org.sopt.santamanitto.user.mypage.UserNameResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserAuthService {
    @GET("users/{userId}")
    fun getUserInfo(@Path("userId") userId: Int) : Call<Response<UserInfoResponse>>

    @PUT("users/{userId}")
    fun changeUserName(@Path("userId") userId: Int, @Body userNameRequest: UserNameRequest): Call<Response<UserNameResponse>>
}