package org.sopt.santamanitto.room.create.network

import org.sopt.santamanitto.util.TimeUtil

class FakeCreateRoomRequest: CreateRoomRequest {

    override fun createRoom(
        createRoomData: CreateRoomData,
        callback: CreateRoomRequest.CreateRoomCallback
    ) {
        callback.onRoomCreated(
            CreateRoomResponse(
            false, 1, createRoomData.roomName, createRoomData.expiration,
            "oU3lsEo", TimeUtil.getCurrentTimeByServerFormat(),
            TimeUtil.getCurrentTimeByServerFormat())
        )
    }
}