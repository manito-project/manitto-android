package org.sopt.santamanitto.user.network

import org.sopt.santamanitto.network.Response
import org.sopt.santamanitto.user.source.User
import org.sopt.santamanitto.user.source.UserJoinedRoomsResponse
import retrofit2.Call
import retrofit2.http.*


interface UserService {
    @POST("users")
    fun createAccount(@Body body: User) : Call<Response<User>>

    @GET("users/check-serial/{serialNumber}")
    fun login(@Path("serialNumber") serialNumber: String)
        : Call<Response<UserCheckResponse>>

    @GET("users/{userId}")
    fun getJoinedRooms(@Path("userId") userId: Int) : Call<Response<UserJoinedRoomsResponse>>
}