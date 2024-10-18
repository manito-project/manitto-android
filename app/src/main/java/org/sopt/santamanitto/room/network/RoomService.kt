package org.sopt.santamanitto.room.network

import org.sopt.santamanitto.network.Response
import org.sopt.santamanitto.network.SimpleResponse
import org.sopt.santamanitto.room.create.network.CreateRoomModel
import org.sopt.santamanitto.room.create.network.CreateRoomRequestModel
import org.sopt.santamanitto.room.create.network.ModifyRoomRequestModel
import org.sopt.santamanitto.room.data.PersonalRoomModel
import org.sopt.santamanitto.room.data.TempMyManittoModel
import org.sopt.santamanitto.room.join.network.JoinRoomModel
import org.sopt.santamanitto.room.join.network.JoinRoomRequestModel
import org.sopt.santamanitto.room.manittoroom.network.ManittoRoomModel
import org.sopt.santamanitto.room.manittoroom.network.MatchedMissionsModel
import org.sopt.santamanitto.room.manittoroom.network.MatchingRequestModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface RoomService {
    @GET("rooms")
    suspend fun getRooms(): Response<List<TempMyManittoModel>>

    @GET("rooms/{roomId}/my")
    fun getRoomPersonalInfo(
        @Path("roomId") roomId: String
    ): Call<Response<PersonalRoomModel>>

    @POST("rooms")
    fun createRoom(
        @Body request: CreateRoomRequestModel
    ): Call<Response<CreateRoomModel>>

    @PATCH("rooms/{roomId}")
    fun modifyRoom(
        @Path("roomId") roomId: String,
        @Body request: ModifyRoomRequestModel
    ): Call<Response<SimpleResponse>>

    @POST("rooms/enter")
    fun joinRoom(
        @Body request: JoinRoomRequestModel
    ): Call<Response<JoinRoomModel>>

    @GET("rooms/{roomId}")
    fun getManittoRoomData(
        @Path("roomId") roomId: String
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
        @Path("roomId") roomId: String
    ): Call<SimpleResponse>
}