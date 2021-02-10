package org.sopt.santamanitto.room.create

import org.sopt.santamanitto.room.data.CreateRoomData
import org.sopt.santamanitto.room.network.CreateRoomRequest
import org.sopt.santamanitto.room.network.CreateRoomResponse
import org.sopt.santamanitto.util.TimeUtil

class FakeCreateRoomRequest: CreateRoomRequest {

    override fun createRoom(
        createRoomData: CreateRoomData,
        callback: CreateRoomRequest.CreateRoomCallback
    ) {
        callback.onRoomCreated(CreateRoomResponse(
            false, 1, createRoomData.roomName, createRoomData.expiration,
            "oU3lsEo", TimeUtil.getCurrentTimeByServerFormat(),
            TimeUtil.getCurrentTimeByServerFormat()))
    }
}