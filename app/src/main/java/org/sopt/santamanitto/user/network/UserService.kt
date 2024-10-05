package org.sopt.santamanitto.user.network

import org.sopt.santamanitto.auth.SignInRequestModel
import org.sopt.santamanitto.auth.SignUpRequestModel
import org.sopt.santamanitto.network.Response
import org.sopt.santamanitto.user.data.UserInfoModel
import org.sopt.santamanitto.user.data.UserLoginModel
import org.sopt.santamanitto.user.mypage.UserNameModel
import org.sopt.santamanitto.user.mypage.UserNameRequestModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface UserService {
    @POST("auth/sign-up")
    fun createAccount(
        @Body body: SignUpRequestModel
    ): Call<Response<UserLoginModel>>

    @POST("auth/sign-in")
    fun login(
        @Body body: SignInRequestModel
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