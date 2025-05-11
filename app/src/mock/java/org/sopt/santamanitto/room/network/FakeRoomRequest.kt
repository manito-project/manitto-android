package org.sopt.santamanitto.room.network

import android.os.Handler
import android.os.Looper
import org.sopt.santamanitto.room.create.network.CreateRoomModel
import org.sopt.santamanitto.room.create.network.CreateRoomRequestModel
import org.sopt.santamanitto.room.create.network.ModifyRoomRequestModel
import org.sopt.santamanitto.room.data.MyManittoModel
import org.sopt.santamanitto.room.join.network.JoinRoomRequestModel
import org.sopt.santamanitto.room.join.network.JoinRoomResponseModel
import org.sopt.santamanitto.room.network.FakeRoomItems.getFakeManittoRoomData
import org.sopt.santamanitto.room.network.FakeRoomItems.getFakePersonalRoomInfo
import org.sopt.santamanitto.room.network.FakeRoomItems.getMyManittoList
import timber.log.Timber

class FakeRoomRequest : RoomRequest {

    companion object {
        private const val TAG = "FakeRoomRequest"
    }

    override suspend fun getRooms(): List<MyManittoModel> {
        return getMyManittoList()
    }

    override fun createRoom(
        request: CreateRoomRequestModel,
        callback: RoomRequest.CreateRoomCallback
    ) {
        callback.onRoomCreated(
            CreateRoomModel("oU3lsEo")
        )
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
            // TODO : 코드가 뭘까
            "success" -> callback.onSuccessJoinRoom(JoinRoomResponseModel("1"))
            "member" -> callback.onFailed(RoomRequest.JoinRoomError.AlreadyEntered)
            "matched" -> callback.onFailed(RoomRequest.JoinRoomError.AlreadyMatched)
            else -> callback.onFailed(RoomRequest.JoinRoomError.WrongInvitationCode)
        }
    }

    override fun getManittoRoomData(
        roomId: String,
        callback: RoomRequest.GetManittoRoomCallback
    ) {
        callback.onLoadManittoRoomData(getFakeManittoRoomData(roomId))
    }

    override fun matchManitto(
        roomId: String,
        callback: (onSuccess: Boolean) -> Unit
    ) {
        Handler(Looper.getMainLooper()).postDelayed({
            Timber.tag(TAG).d("matchManitto: room(id : $roomId) is matched")
            callback.invoke(true)
        }, 5000L)
    }

    override fun getPersonalRoomInfo(
        roomId: String,
        callback: RoomRequest.GetPersonalRoomInfoCallback
    ) {
        val personalRoomInfo = getFakePersonalRoomInfo(roomId)
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
        Timber.tag(TAG).d("exitRoom: room(id : $roomId) is exited")
        callback.invoke(true)
    }

    override fun removeHistory(
        roomId: String,
        callback: (onSuccess: Boolean) -> Unit
    ) {
        Timber.tag(TAG).d("removeHistory: room(id : $roomId) is removed from history")
        callback.invoke(true)
    }

    override suspend fun deleteRoom(roomId: String): Result<Unit> {
        Timber.tag(TAG).d("deleteRoom: room(id : $roomId) is deleted")
        return Result.success(Unit)
    }
}
