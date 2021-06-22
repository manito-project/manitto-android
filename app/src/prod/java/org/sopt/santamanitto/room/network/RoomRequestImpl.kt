package org.sopt.santamanitto.room.network

import okhttp3.ResponseBody
import org.sopt.santamanitto.network.*
import org.sopt.santamanitto.room.create.network.CreateRoomData
import org.sopt.santamanitto.room.create.network.CreateRoomResponse
import org.sopt.santamanitto.room.create.network.ModifyRoomData
import org.sopt.santamanitto.room.data.PersonalRoomInfo
import org.sopt.santamanitto.room.data.source.RoomDataSource
import org.sopt.santamanitto.room.join.network.JoinRoomData
import org.sopt.santamanitto.room.join.network.JoinRoomErrorBody
import org.sopt.santamanitto.room.join.network.JoinRoomResponse
import org.sopt.santamanitto.room.manittoroom.network.ManittoMatchingData
import org.sopt.santamanitto.room.manittoroom.network.ManittoRoomData
import org.sopt.santamanitto.room.manittoroom.network.ManittoRoomMatchedMissions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit

class RoomRequestImpl(
    private val roomService: RoomService,
    @AuthRetrofitClient private val retrofitClient: Retrofit
) : RoomRequest {

    companion object {
        private const val JOIN_ROOM_ERROR_ALREADY_MATCHED = "이미 매칭이 완료된 방입니다"
        private const val JOIN_ROOM_ERROR_DUPLICATED_MEMBER = "이미 있는 멤버입니다"
        private const val JOIN_ROOM_ERROR_WRONG_INVITATION_CODE = "초대코드가 잘못되었습니다"
    }

    override fun createRoom(
        createRoomData: CreateRoomData,
        callback: RoomRequest.CreateRoomCallback
    ) {
        roomService.createRoom(createRoomData).start(object : RequestCallback<CreateRoomResponse> {
            override fun onSuccess(data: CreateRoomResponse) {
                callback.onRoomCreated(data)
            }

            override fun onFail() {
                callback.onFailed()
            }
        })
    }

    override fun modifyRoom(roomId: Int, modifyRoomData: ModifyRoomData, callback: (onSuccess: Boolean) -> Unit) {
        roomService.modifyRoom(roomId, modifyRoomData).start(object: RequestCallback<SimpleResponse> {
            override fun onSuccess(data: SimpleResponse) {
                callback.invoke(true)
            }

            override fun onFail() {
                callback.invoke(false)
            }
        })
    }

    override fun joinRoom(joinRoomData: JoinRoomData, callback: RoomRequest.JoinRoomCallback) {
        roomService.joinRoom(joinRoomData).enqueue(object : Callback<Response<JoinRoomResponse>> {
            override fun onResponse(
                call: Call<Response<JoinRoomResponse>>,
                response: retrofit2.Response<Response<JoinRoomResponse>>
            ) {
                if (response.isSuccessful) {
                    callback.onSuccessJoinRoom(response.body()!!.data)
                } else {
                    val error = when (convert(response.errorBody()!!).message) {
                        JOIN_ROOM_ERROR_ALREADY_MATCHED -> RoomRequest.JoinRoomError.AlreadyMatched
                        JOIN_ROOM_ERROR_DUPLICATED_MEMBER -> RoomRequest.JoinRoomError.DuplicatedMember
                        JOIN_ROOM_ERROR_WRONG_INVITATION_CODE -> RoomRequest.JoinRoomError.WrongInvitationCode
                        else -> RoomRequest.JoinRoomError.Els
                    }
                    callback.onFailed(error)
                }
            }

            override fun onFailure(call: Call<Response<JoinRoomResponse>>, t: Throwable) {
                callback.onFailed(RoomRequest.JoinRoomError.Els)
            }
        })
    }

    override fun getManittoRoomData(roomId: Int, callback: RoomRequest.GetManittoRoomCallback) {
        roomService.getManittoRoomData(roomId).start(object: RequestCallback<ManittoRoomData> {
            override fun onSuccess(data: ManittoRoomData) {
                callback.onLoadManittoRoomData(data)
            }

            override fun onFail() {
                callback.onFailed()
            }
        })
    }

    override fun matchManitto(roomId: Int, callback: RoomRequest.MatchManittoCallback) {
        roomService.matchManitto(ManittoMatchingData(roomId)).start(object: RequestCallback<List<ManittoRoomMatchedMissions>> {
            override fun onSuccess(data: List<ManittoRoomMatchedMissions>) {
                callback.onSuccessMatching(data)
            }

            override fun onFail() {
                callback.onFailed()
            }
        })
    }

    override fun getPersonalRoomInfo(roomId: Int, callback: RoomRequest.GetPersonalRoomInfoCallback) {
        roomService.getRoomPersonalInfo(roomId).start(object: RequestCallback<PersonalRoomInfo> {
            override fun onSuccess(data: PersonalRoomInfo) {
                callback.onLoadPersonalRoomInfo(data)
            }

            override fun onFail() {
                callback.onDataNotAvailable()
            }
        })
    }

    override fun exitRoom(roomId: Int, callback: (onSuccess: Boolean) -> Unit) {
        roomService.exitRoom(ExitRoomRequest(roomId.toString())).start(callback)
    }

    override fun removeHistory(roomId: Int, callback: (onSuccess: Boolean) -> Unit) {
        roomService.removeHistory(roomId).start(callback)
    }

    fun convert(errorBody: ResponseBody): JoinRoomErrorBody {
        return retrofitClient.responseBodyConverter<JoinRoomErrorBody>(
            JoinRoomErrorBody::class.java,
            JoinRoomErrorBody::class.java.annotations
        ).convert(errorBody)!!
    }
}