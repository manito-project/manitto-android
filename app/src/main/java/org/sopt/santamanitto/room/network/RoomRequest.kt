package org.sopt.santamanitto.room.network

import org.sopt.santamanitto.room.create.network.CreateRoomData
import org.sopt.santamanitto.room.create.network.CreateRoomResponse
import org.sopt.santamanitto.room.join.exception.JoinRoomException
import org.sopt.santamanitto.room.join.network.JoinRoomData
import org.sopt.santamanitto.room.join.network.JoinRoomResponse

interface RoomRequest {

    interface CreateRoomCallback {
        fun onRoomCreated(createdRoom: CreateRoomResponse)

        fun onFailed()
    }

    interface JoinRoomCallback {
        fun onSuccessJoinRoom(joinedRoom: JoinRoomResponse)

        fun onFailed()
    }

    fun createRoom(createRoomData: CreateRoomData, callback: CreateRoomCallback)

    @Throws(JoinRoomException::class)
    fun joinRoom(joinRoomData: JoinRoomData, callback: JoinRoomCallback)
}