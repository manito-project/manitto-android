package org.sopt.santamanitto.room.data.source

import org.sopt.santamanitto.network.RequestCallback
import org.sopt.santamanitto.network.start
import org.sopt.santamanitto.room.data.PersonalRoomInfo
import org.sopt.santamanitto.room.network.RoomService

class RoomRemoteDataSource(private val roomService: RoomService) : RoomDataSource {

    override fun getPersonalRoomInfo(roomId: Int, callback: RoomDataSource.GetPersonalRoomInfoCallback) {
        roomService.getRoomPersonalInfo(roomId).start(object: RequestCallback<PersonalRoomInfo> {
            override fun onSuccess(data: PersonalRoomInfo) {
                callback.onLoadPersonalRoomInfo(data)
            }

            override fun onFail() {
                callback.onDataNotAvailable()
            }
        })
    }
}