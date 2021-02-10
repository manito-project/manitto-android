package org.sopt.santamanitto.room.create.network

import org.sopt.santamanitto.network.RequestCallback
import org.sopt.santamanitto.network.start

class CreateRoomRequestImpl(private val roomService: RoomService): CreateRoomRequest {

    override fun createRoom(
        createRoomData: CreateRoomData,
        callback: CreateRoomRequest.CreateRoomCallback
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