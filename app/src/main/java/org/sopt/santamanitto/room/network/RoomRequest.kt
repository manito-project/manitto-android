package org.sopt.santamanitto.room.network

import org.sopt.santamanitto.room.create.network.CreateRoomData
import org.sopt.santamanitto.room.create.network.CreateRoomResponse
import org.sopt.santamanitto.room.create.network.ModifyRoomData
import org.sopt.santamanitto.room.data.PersonalRoomInfo
import org.sopt.santamanitto.room.join.network.JoinRoomData
import org.sopt.santamanitto.room.join.network.JoinRoomResponse
import org.sopt.santamanitto.room.manittoroom.network.ManittoRoomData
import org.sopt.santamanitto.room.manittoroom.network.ManittoRoomMatchedMissions

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

    interface MatchManittoCallback {
        fun onSuccessMatching(missions: List<ManittoRoomMatchedMissions>)

        fun onFailed()
    }

    interface GetPersonalRoomInfoCallback {
        fun onLoadPersonalRoomInfo(personalRoomInfo: PersonalRoomInfo)

        fun onDataNotAvailable()
    }

    enum class JoinRoomError {
        WrongInvitationCode, DuplicatedMember, AlreadyMatched, Els
    }

    fun createRoom(createRoomData: CreateRoomData, callback: CreateRoomCallback)

    fun modifyRoom(roomId: Int, modifyRoomData: ModifyRoomData, callback: (onSuccess: Boolean) -> Unit)

    fun joinRoom(joinRoomData: JoinRoomData, callback: JoinRoomCallback)

    fun getManittoRoomData(roomId: Int, callback: GetManittoRoomCallback)

    fun matchManitto(roomId: Int, callback: MatchManittoCallback)

    fun getPersonalRoomInfo(roomId: Int, callback: GetPersonalRoomInfoCallback)

    fun exitRoom(roomId: Int, callback: (onSuccess: Boolean) -> Unit)

    fun removeHistory(roomId: Int, callback: (onSuccess: Boolean) -> Unit)

}