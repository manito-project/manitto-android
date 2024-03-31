package org.sopt.santamanitto.user.network

import org.sopt.santamanitto.network.Response
import org.sopt.santamanitto.user.data.UserLoginModel
import org.sopt.santamanitto.user.data.UserInfoModel
import org.sopt.santamanitto.user.mypage.UserNameRequestModel
import org.sopt.santamanitto.user.mypage.UserNameModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface UserService {
    @POST("users")
    fun createAccount(
        @Body body: UserLoginModel
    ): Call<Response<UserLoginModel>>

    @GET("users/check-serial/{serialNumber}")
    fun login(
        @Path("serialNumber") serialNumber: String
    ): Call<Response<UserCheckModel>>

    @Deprecated("deprecated", ReplaceWith("UserAuthService#getUserInfo"))
    @GET("users/{userId}")
    fun getUserInfo(
        @Path("userId") userId: Int
    ): Call<Response<UserInfoModel>>

    @Deprecated("deprecated", ReplaceWith("UserAuthService#changeUserName"))
    @PUT("users/{userId}")
    fun changeUserName(
        @Path("userId") userId: Int,
        @Body request: UserNameRequestModel
    ): Call<Response<UserNameModel>>
}