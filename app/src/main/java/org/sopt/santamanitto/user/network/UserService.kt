package org.sopt.santamanitto.user.network

import org.sopt.santamanitto.auth.data.request.SignInRequestModel
import org.sopt.santamanitto.auth.data.request.SignUpRequestModel
import org.sopt.santamanitto.auth.data.response.SignInResponseModel
import org.sopt.santamanitto.auth.data.response.SignUpResponseModel
import org.sopt.santamanitto.network.Response
import org.sopt.santamanitto.user.data.UserInfoModel
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
    suspend fun createAccount(
        @Body body: SignUpRequestModel
    ): Response<SignUpResponseModel>

    @POST("auth/sign-in")
    suspend fun login(
        @Body body: SignInRequestModel
    ): Response<SignInResponseModel>

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