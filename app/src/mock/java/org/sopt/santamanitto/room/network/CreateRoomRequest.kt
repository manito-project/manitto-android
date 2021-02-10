package org.sopt.santamanitto.room.network

import org.sopt.santamanitto.room.data.CreateRoomData

interface CreateRoomRequest {

    interface CreateRoomCallback {
        fun onRoomCreated(createdRoom: CreateRoomResponse)

        fun onFailed()
    }

    fun createRoom(createRoomData: CreateRoomData, callback: CreateRoomCallback)
}