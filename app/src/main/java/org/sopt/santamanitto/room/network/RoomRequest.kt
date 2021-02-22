package org.sopt.santamanitto.room.network

import org.sopt.santamanitto.room.create.network.CreateRoomData
import org.sopt.santamanitto.room.create.network.CreateRoomResponse
import org.sopt.santamanitto.room.join.network.JoinRoomData
import org.sopt.santamanitto.room.join.network.JoinRoomResponse
import org.sopt.santamanitto.room.manittoroom.network.ManittoRoomData

interface RoomRequest {

    interface CreateRoomCallback {
        fun onRoomCreated(createdRoom: CreateRoomResponse)

        fun onFailed()
    }

    interface JoinRoomCallback {
        fun onSuccessJoinRoom(joinedRoom: JoinRoomResponse)

        fun onFailed(joinRoomError: JoinRoomError)
    }

    interface GetManittoRoomCallback {
        fun onLoadManittoRoomData(manittoRoomData: ManittoRoomData)

        fun onFailed()
    }

    enum class JoinRoomError {
        WrongInvitationCode, DuplicatedMember, AlreadyMatched, Els
    }

    fun createRoom(createRoomData: CreateRoomData, callback: CreateRoomCallback)

    fun joinRoom(joinRoomData: JoinRoomData, callback: JoinRoomCallback)

    fun getManittoRoomData(roomId: Int, callback: GetManittoRoomCallback)
}