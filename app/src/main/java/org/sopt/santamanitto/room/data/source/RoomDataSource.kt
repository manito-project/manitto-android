package org.sopt.santamanitto.room.data.source

import org.sopt.santamanitto.room.data.PersonalRoomModel

interface RoomDataSource {
    interface GetPersonalRoomInfoCallback {
        fun onLoadPersonalRoomInfo(personalRoomModel: PersonalRoomModel)

        fun onDataNotAvailable()
    }

    fun getPersonalRoomInfo(roomId: Int, callback: GetPersonalRoomInfoCallback)

}