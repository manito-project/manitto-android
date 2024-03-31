package org.sopt.santamanitto.room.network

import org.sopt.santamanitto.network.Response
import org.sopt.santamanitto.network.SimpleResponse
import org.sopt.santamanitto.room.create.network.CreateRoomRequestModel
import org.sopt.santamanitto.room.create.network.CreateRoomModel
import org.sopt.santamanitto.room.create.network.ModifyRoomRequestModel
import org.sopt.santamanitto.room.data.PersonalRoomModel
import org.sopt.santamanitto.room.join.network.JoinRoomRequestModel
import org.sopt.santamanitto.room.join.network.JoinRoomModel
import org.sopt.santamanitto.room.manittoroom.network.MatchingRequestModel
import org.sopt.santamanitto.room.manittoroom.network.ManittoRoomModel
import org.sopt.santamanitto.room.manittoroom.network.MatchedMissionsModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RoomService {

    @GET("rooms/{roomId}/my")
    fun getRoomPersonalInfo(
        @Path("roomId") roomId: Int
    ): Call<Response<PersonalRoomModel>>

    @POST("rooms")
    fun createRoom(
        @Body request: CreateRoomRequestModel
    ): Call<Response<CreateRoomModel>>

    @PUT("rooms/{roomId}")
    fun modifyRoom(
        @Path("roomId") roomId: Int,
        @Body request: ModifyRoomRequestModel
    ): Call<Response<SimpleResponse>>

    @POST("rooms/enter")
    fun joinRoom(
        @Body request: JoinRoomRequestModel
    ): Call<Response<JoinRoomModel>>

    @GET("rooms/{roomId}")
    fun getManittoRoomData(
        @Path("roomId") roomId: Int
    ): Call<Response<ManittoRoomModel>>

    @POST("rooms/match")
    fun matchManitto(
        @Body request: MatchingRequestModel
    ): Call<Response<List<MatchedMissionsModel>>>

    @POST("rooms/exit")
    fun exitRoom(
        @Body request: ExitRoomRequestModel
    ): Call<SimpleResponse>

    @DELETE("rooms/{roomId}/history")
    fun removeHistory(
        @Path("roomId") roomId: Int
    ): Call<SimpleResponse>
}