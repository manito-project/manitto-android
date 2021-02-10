package org.sopt.santamanitto.room.create.network

interface CreateRoomRequest {

    interface CreateRoomCallback {
        fun onRoomCreated(createdRoom: CreateRoomResponse)

        fun onFailed()
    }

    fun createRoom(createRoomData: CreateRoomData, callback: CreateRoomCallback)
}