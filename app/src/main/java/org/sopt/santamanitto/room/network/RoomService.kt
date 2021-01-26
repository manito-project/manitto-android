package org.sopt.santamanitto.room.network

import org.sopt.santamanitto.network.Response
import org.sopt.santamanitto.room.data.PersonalRoomInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RoomService {

    @GET("rooms/{roomId}/my")
    fun getRoomPersonalInfo(@Path("roomId") roomId: Int): Call<Response<PersonalRoomInfo>>
}