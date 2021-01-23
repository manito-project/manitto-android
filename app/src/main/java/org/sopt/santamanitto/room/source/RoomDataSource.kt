package org.sopt.santamanitto.room.source

import org.sopt.santamanitto.room.PersonalRoomInfo

interface RoomDataSource {
    interface GetPersonalRoomInfoCallback {
        fun onLoadPersonalRoomInfo(personalRoomInfo: PersonalRoomInfo)

        fun onDataNotAvailable()
    }

    fun getPersonalRoomInfo(roomId: Int, callback: GetPersonalRoomInfoCallback)

}