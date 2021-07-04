package org.sopt.santamanitto.user.network

import org.sopt.santamanitto.network.Response
import org.sopt.santamanitto.user.data.LoginUserResponse
import org.sopt.santamanitto.user.data.UserInfoResponse
import org.sopt.santamanitto.user.mypage.UserNameRequest
import org.sopt.santamanitto.user.mypage.UserNameResponse
import retrofit2.Call
import retrofit2.http.*


interface UserService {
    @POST("users")
    fun createAccount(@Body body: LoginUserResponse) : Call<Response<LoginUserResponse>>

    @GET("users/check-serial/{serialNumber}")
    fun login(@Path("serialNumber") serialNumber: String)
        : Call<Response<UserCheckResponse>>

    @Deprecated("deprecated", ReplaceWith("UserAuthService#getUserInfo"))
    @GET("users/{userId}")
    fun getUserInfo(@Path("userId") userId: Int) : Call<Response<UserInfoResponse>>

    @Deprecated("deprecated", ReplaceWith("UserAuthService#changeUserName"))
    @PUT("users/{userId}")
    fun changeUserName(@Path("userId") userId: Int, @Body userNameRequest: UserNameRequest): Call<Response<UserNameResponse>>
}