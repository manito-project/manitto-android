package org.sopt.santamanitto.room.network

import android.os.Handler
import android.os.Looper
import org.sopt.santamanitto.room.create.network.CreateRoomModel
import org.sopt.santamanitto.room.create.network.CreateRoomRequestModel
import org.sopt.santamanitto.room.create.network.ModifyRoomRequestModel
import org.sopt.santamanitto.room.data.MyManittoModel
import org.sopt.santamanitto.room.join.network.JoinRoomRequestModel
import org.sopt.santamanitto.room.join.network.JoinRoomResponseModel

class FakeRoomRequest : RoomRequest {

    override suspend fun getRooms(): List<MyManittoModel> {
        return FakeRoomItems.getMyManittoList()
    }

    override fun createRoom(
        request: CreateRoomRequestModel,
        callback: RoomRequest.CreateRoomCallback
    ) {
        callback.onRoomCreated(CreateRoomModel("oU3lsEo"))
    }

    override fun modifyRoom(
        roomId: String,
        request: ModifyRoomRequestModel,
        callback: (onSuccess: Boolean) -> Unit
    ) {
        callback.invoke(true)
    }

    override fun joinRoom(
        request: JoinRoomRequestModel,
        callback: RoomRequest.JoinRoomCallback
    ) {
        when (request.invitationCode) {
            // 코드 창에 해당 문자 입력
            "success" -> callback.onSuccessJoinRoom(JoinRoomResponseModel("0"))
            "member" -> callback.onFailed(RoomRequest.JoinRoomError.AlreadyEntered)
            "matched" -> callback.onFailed(RoomRequest.JoinRoomError.AlreadyMatched)
            else -> callback.onFailed(RoomRequest.JoinRoomError.WrongInvitationCode)
        }
    }

    override fun getManittoRoomData(
        roomId: String,
        callback: RoomRequest.GetManittoRoomCallback
    ) {
        val manittoRoomData = FakeRoomItems.getFakeManittoRoomData(roomId)
        if (manittoRoomData != null) {
            callback.onLoadManittoRoomData(manittoRoomData)
        } else {
            callback.onFailed()
        }
    }

    override fun matchManitto(
        roomId: String,
        callback: (onSuccess: Boolean) -> Unit
    ) {
        Handler(Looper.getMainLooper()).postDelayed({
            callback.invoke(true)
        }, 5000L)
    }

    override fun getPersonalRoomInfo(
        roomId: String,
        callback: RoomRequest.GetPersonalRoomInfoCallback
    ) {
        val personalRoomInfo = FakeRoomItems.getFakePersonalRoomInfo(roomId)
        if (personalRoomInfo != null) {
            callback.onLoadPersonalRoomInfo(personalRoomInfo)
        } else {
            callback.onDataNotAvailable()
        }
    }

    override fun exitRoom(
        roomId: String,
        callback: (onSuccess: Boolean) -> Unit
    ) {
        callback.invoke(true)
    }

    override fun removeHistory(
        roomId: String,
        callback: (onSuccess: Boolean) -> Unit
    ) {
        callback.invoke(true)
    }

    override suspend fun deleteRoom(roomId: String): Result<Unit> {
        return Result.success(Unit)
    }
}
