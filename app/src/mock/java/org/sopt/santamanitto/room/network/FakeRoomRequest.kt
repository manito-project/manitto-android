package org.sopt.santamanitto.room.network

import org.sopt.santamanitto.room.create.network.CreateRoomData
import org.sopt.santamanitto.room.create.network.CreateRoomResponse
import org.sopt.santamanitto.util.TimeUtil

class FakeRoomRequest: RoomRequest {

    override fun createRoom(
        createRoomData: CreateRoomData,
        callback: RoomRequest.CreateRoomCallback
    ) {
        callback.onRoomCreated(
            CreateRoomResponse(
            false, 1, createRoomData.roomName, createRoomData.expiration,
            "oU3lsEo", TimeUtil.getCurrentTimeByServerFormat(),
            TimeUtil.getCurrentTimeByServerFormat())
        )
    }
}