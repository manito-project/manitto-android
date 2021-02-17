package org.sopt.santamanitto.room.network

import org.sopt.santamanitto.network.RequestCallback
import org.sopt.santamanitto.network.start
import org.sopt.santamanitto.room.create.network.CreateRoomData
import org.sopt.santamanitto.room.create.network.CreateRoomResponse

class RoomRequestImpl(private val roomService: RoomService): RoomRequest {

    override fun createRoom(
        createRoomData: CreateRoomData,
        callback: RoomRequest.CreateRoomCallback
    ) {
        roomService.createRoom(createRoomData).start(object: RequestCallback<CreateRoomResponse> {
            override fun onSuccess(data: CreateRoomResponse) {
                callback.onRoomCreated(data)
            }

            override fun onFail() {
                callback.onFailed()
            }
        })
    }
}