package org.sopt.santamanitto.room.network

import okhttp3.ResponseBody
import org.sopt.santamanitto.network.AuthRetrofitClient
import org.sopt.santamanitto.network.RequestCallback
import org.sopt.santamanitto.network.Response
import org.sopt.santamanitto.network.start
import org.sopt.santamanitto.room.create.network.CreateRoomData
import org.sopt.santamanitto.room.create.network.CreateRoomResponse
import org.sopt.santamanitto.room.join.network.JoinRoomData
import org.sopt.santamanitto.room.join.network.JoinRoomErrorBody
import org.sopt.santamanitto.room.join.network.JoinRoomResponse
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

    fun convert(errorBody: ResponseBody): JoinRoomErrorBody {
        return retrofitClient.responseBodyConverter<JoinRoomErrorBody>(
            JoinRoomErrorBody::class.java,
            JoinRoomErrorBody::class.java.annotations
        ).convert(errorBody)!!
    }
}