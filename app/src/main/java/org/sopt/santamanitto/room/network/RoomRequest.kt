package org.sopt.santamanitto.room.network

import org.sopt.santamanitto.room.create.network.CreateRoomData
import org.sopt.santamanitto.room.create.network.CreateRoomResponse

interface RoomRequest {

    interface CreateRoomCallback {
        fun onRoomCreated(createdRoom: CreateRoomResponse)

        fun onFailed()
    }

    fun createRoom(createRoomData: CreateRoomData, callback: CreateRoomCallback)
}