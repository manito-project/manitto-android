package org.sopt.santamanitto.room.network

import org.sopt.santamanitto.room.create.network.CreateRoomData
import org.sopt.santamanitto.room.create.network.CreateRoomResponse
import org.sopt.santamanitto.room.join.network.JoinRoomData
import org.sopt.santamanitto.room.join.network.JoinRoomInfo
import org.sopt.santamanitto.room.join.network.JoinRoomResponse
import org.sopt.santamanitto.room.join.network.JoinRoomUserInfo
import org.sopt.santamanitto.util.TimeUtil

class FakeRoomRequest : RoomRequest {

    override fun createRoom(
        createRoomData: CreateRoomData,
        callback: RoomRequest.CreateRoomCallback
    ) {
        callback.onRoomCreated(
            CreateRoomResponse(
                false, 1, createRoomData.roomName, createRoomData.expiration,
                "oU3lsEo", TimeUtil.getCurrentTimeByServerFormat(),
                TimeUtil.getCurrentTimeByServerFormat()
            )
        )
    }

    override fun joinRoom(joinRoomData: JoinRoomData, callback: RoomRequest.JoinRoomCallback) {
        when (joinRoomData.invitationCode) {
            "success" ->
                callback.onSuccessJoinRoom(
                    JoinRoomResponse(
                        JoinRoomInfo(
                            1, "TEST ROOM",
                            TimeUtil.getCurrentTimeByServerFormat(), "oU3lsEo"
                        ),
                        JoinRoomUserInfo(1, "TEST USER")
                    )
                )

            "member" ->
                callback.onFailed(RoomRequest.JoinRoomError.DuplicatedMember)

            "matched" ->
                callback.onFailed(RoomRequest.JoinRoomError.AlreadyMatched)

            else ->
                callback.onFailed(RoomRequest.JoinRoomError.WrongInvitationCode)
        }
    }
}