package org.sopt.santamanitto.user.network

import org.sopt.santamanitto.network.Response
import org.sopt.santamanitto.user.User
import retrofit2.Call
import retrofit2.http.*


interface UserService {
    @POST("users")
    fun saveUser(@Body body: User) : Call<Response<User>>

    @GET("users/check-serial/{serialNumber}")
    fun getUserFromSerialNumber(@Path("serialNumber") serialNumber: String)
        : Call<Response<UserCheckResponse>>
}