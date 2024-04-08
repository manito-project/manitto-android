package org.sopt.santamanitto.room.network

import org.sopt.santamanitto.room.create.network.CreateRoomRequestModel
import org.sopt.santamanitto.room.create.network.CreateRoomModel
import org.sopt.santamanitto.room.create.network.ModifyRoomRequestModel
import org.sopt.santamanitto.room.data.PersonalRoomModel
import org.sopt.santamanitto.room.join.network.JoinRoomRequestModel
import org.sopt.santamanitto.room.join.network.JoinRoomModel
import org.sopt.santamanitto.room.manittoroom.network.ManittoRoomModel
import org.sopt.santamanitto.room.manittoroom.network.MatchedMissionsModel

interface RoomRequest {

    interface CreateRoomCallback {
        fun onRoomCreated(createdRoom: CreateRoomModel)

        fun onFailed()
    }

    interface JoinRoomCallback {
        fun onSuccessJoinRoom(joinedRoom: JoinRoomModel)

        fun onFailed(joinRoomError: JoinRoomError)
    }

    interface GetManittoRoomCallback {
        fun onLoadManittoRoomData(manittoRoom: ManittoRoomModel)

        fun onFailed()
    }

    interface MatchManittoCallback {
        fun onSuccessMatching(missions: List<MatchedMissionsModel>)

        fun onFailed()
    }

    interface GetPersonalRoomInfoCallback {
        fun onLoadPersonalRoomInfo(personalRoom: PersonalRoomModel)

        fun onDataNotAvailable()
    }

    enum class JoinRoomError {
        WrongInvitationCode, DuplicatedMember, AlreadyMatched, Els
    }

    fun createRoom(request: CreateRoomRequestModel, callback: CreateRoomCallback)

    fun modifyRoom(roomId: Int, request: ModifyRoomRequestModel, callback: (onSuccess: Boolean) -> Unit)

    fun joinRoom(request: JoinRoomRequestModel, callback: JoinRoomCallback)

    fun getManittoRoomData(roomId: Int, callback: GetManittoRoomCallback)

    fun matchManitto(roomId: Int, callback: MatchManittoCallback)

    fun getPersonalRoomInfo(roomId: Int, callback: GetPersonalRoomInfoCallback)

    fun exitRoom(roomId: Int, callback: (onSuccess: Boolean) -> Unit)

    fun removeHistory(roomId: Int, callback: (onSuccess: Boolean) -> Unit)

}