package org.sopt.santamanitto.room.network

import okhttp3.ResponseBody
import org.sopt.santamanitto.network.*
import org.sopt.santamanitto.room.create.network.CreateRoomModel
import org.sopt.santamanitto.room.create.network.CreateRoomRequestModel
import org.sopt.santamanitto.room.create.network.ModifyRoomRequestModel
import org.sopt.santamanitto.room.data.PersonalRoomModel
import org.sopt.santamanitto.room.join.network.JoinRoomErrorModel
import org.sopt.santamanitto.room.join.network.JoinRoomModel
import org.sopt.santamanitto.room.join.network.JoinRoomRequestModel
import org.sopt.santamanitto.room.manittoroom.network.ManittoRoomModel
import org.sopt.santamanitto.room.manittoroom.network.MatchedMissionsModel
import org.sopt.santamanitto.room.manittoroom.network.MatchingRequestModel
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
        request: CreateRoomRequestModel,
        callback: RoomRequest.CreateRoomCallback
    ) {
        roomService.createRoom(request).start(object : RequestCallback<CreateRoomModel> {
            override fun onSuccess(data: CreateRoomModel) {
                callback.onRoomCreated(data)
            }

            override fun onFail() {
                callback.onFailed()
            }
        })
    }

    override fun modifyRoom(
        roomId: Int,
        request: ModifyRoomRequestModel,
        callback: (onSuccess: Boolean) -> Unit
    ) {
        roomService.modifyRoom(roomId, request)
            .start(object : RequestCallback<SimpleResponse> {
                override fun onSuccess(data: SimpleResponse) {
                    callback.invoke(true)
                }

                override fun onFail() {
                    callback.invoke(false)
                }
            })
    }

    override fun joinRoom(request: JoinRoomRequestModel, callback: RoomRequest.JoinRoomCallback) {
        roomService.joinRoom(request).enqueue(object : Callback<Response<JoinRoomModel>> {
            override fun onResponse(
                call: Call<Response<JoinRoomModel>>,
                response: retrofit2.Response<Response<JoinRoomModel>>
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

            override fun onFailure(call: Call<Response<JoinRoomModel>>, t: Throwable) {
                callback.onFailed(RoomRequest.JoinRoomError.Els)
            }
        })
    }

    override fun getManittoRoomData(roomId: Int, callback: RoomRequest.GetManittoRoomCallback) {
        roomService.getManittoRoomData(roomId).start(object : RequestCallback<ManittoRoomModel> {
            override fun onSuccess(data: ManittoRoomModel) {
                callback.onLoadManittoRoomData(data)
            }

            override fun onFail() {
                callback.onFailed()
            }
        })
    }

    override fun matchManitto(roomId: Int, callback: RoomRequest.MatchManittoCallback) {
        roomService.matchManitto(MatchingRequestModel(roomId))
            .start(object : RequestCallback<List<MatchedMissionsModel>> {
                override fun onSuccess(data: List<MatchedMissionsModel>) {
                    callback.onSuccessMatching(data)
                }

                override fun onFail() {
                    callback.onFailed()
                }
            })
    }

    override fun getPersonalRoomInfo(
        roomId: Int,
        callback: RoomRequest.GetPersonalRoomInfoCallback
    ) {
        roomService.getRoomPersonalInfo(roomId).start(object : RequestCallback<PersonalRoomModel> {
            override fun onSuccess(data: PersonalRoomModel) {
                callback.onLoadPersonalRoomInfo(data)
            }

            override fun onFail() {
                callback.onDataNotAvailable()
            }
        })
    }

    override fun exitRoom(roomId: Int, callback: (onSuccess: Boolean) -> Unit) {
        roomService.exitRoom(ExitRoomRequestModel(roomId.toString())).start(callback)
    }

    override fun removeHistory(roomId: Int, callback: (onSuccess: Boolean) -> Unit) {
        roomService.removeHistory(roomId).start(callback)
    }

    fun convert(errorBody: ResponseBody): JoinRoomErrorModel {
        return retrofitClient.responseBodyConverter<JoinRoomErrorModel>(
            JoinRoomErrorModel::class.java,
            JoinRoomErrorModel::class.java.annotations
        ).convert(errorBody)!!
    }
}