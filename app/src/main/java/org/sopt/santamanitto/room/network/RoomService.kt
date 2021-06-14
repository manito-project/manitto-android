package org.sopt.santamanitto.room.network

import org.sopt.santamanitto.network.Response
import org.sopt.santamanitto.network.SimpleResponse
import org.sopt.santamanitto.room.create.network.CreateRoomData
import org.sopt.santamanitto.room.create.network.CreateRoomResponse
import org.sopt.santamanitto.room.data.PersonalRoomInfo
import org.sopt.santamanitto.room.join.network.JoinRoomData
import org.sopt.santamanitto.room.join.network.JoinRoomResponse
import org.sopt.santamanitto.room.manittoroom.network.ManittoMatchingData
import org.sopt.santamanitto.room.manittoroom.network.ManittoRoomData
import org.sopt.santamanitto.room.manittoroom.network.ManittoRoomMatchedMissions
import retrofit2.Call
import retrofit2.http.*

interface RoomService {

    @GET("rooms/{roomId}/my")
    fun getRoomPersonalInfo(@Path("roomId") roomId: Int): Call<Response<PersonalRoomInfo>>

    @POST("rooms")
    fun createRoom(@Body createRoomData: CreateRoomData): Call<Response<CreateRoomResponse>>

    @POST("rooms/enter")
    fun joinRoom(@Body joinRoomData: JoinRoomData): Call<Response<JoinRoomResponse>>

    @GET("rooms/{roomId}")
    fun getManittoRoomData(@Path("roomId") roomId: Int): Call<Response<ManittoRoomData>>

    @POST("rooms/match")
    fun matchManitto(@Body manittoMatchingData: ManittoMatchingData): Call<Response<List<ManittoRoomMatchedMissions>>>

    @POST("rooms/exit")
    fun exitRoom(@Body exitRoomRequest: ExitRoomRequest): Call<SimpleResponse>

    @DELETE("rooms/{roomId}/history")
    fun removeHistory(@Path("roomId") roomId: Int): Call<SimpleResponse>
}