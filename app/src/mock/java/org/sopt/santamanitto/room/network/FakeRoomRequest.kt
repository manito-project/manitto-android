package org.sopt.santamanitto.room.network

import android.os.Handler
import android.os.Looper
import org.sopt.santamanitto.room.create.network.CreateRoomModel
import org.sopt.santamanitto.room.create.network.CreateRoomRequestModel
import org.sopt.santamanitto.room.create.network.ModifyRoomRequestModel
import org.sopt.santamanitto.room.data.MyManittoModel
import org.sopt.santamanitto.room.data.PersonalRoomModel
import org.sopt.santamanitto.room.join.network.JoinRoomRequestModel
import org.sopt.santamanitto.room.join.network.JoinRoomResponseModel
import org.sopt.santamanitto.room.manittoroom.network.ManittoRoomMember
import org.sopt.santamanitto.room.manittoroom.network.ManittoRoomModel
import org.sopt.santamanitto.room.manittoroom.network.ManittoRoomModel.ManittoRoomCreator
import org.sopt.santamanitto.room.manittoroom.network.ManittoRoomModel.ManittoRoomMission
import timber.log.Timber

class FakeRoomRequest : RoomRequest {

    companion object {
        private const val TAG = "FakeRoomRequest"
    }

    override suspend fun getRooms(): List<MyManittoModel> {
        // TODO
        return emptyList()
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
        callback.onLoadManittoRoomData(
            ManittoRoomModel(
                roomId = roomId,
                roomName = "FakeRoom",
                invitationCode = "oU3lsEo-",
                createdAt = "false",
                expirationDate = "2021-02-28 11:01:00",
                matchingDate = "2021-02-21 14:47:10",
                deletedByCreatorDate = null,
                creator = ManittoRoomCreator(
                    userId = "1",
                    userName = "FakeFirstUser",
                    manittoUserId = "12fsfe2"
                ),
                missions = mutableListOf<ManittoRoomMission>().apply {
                    add(ManittoRoomMission("1", "Fake Mission 1"))
                    add(ManittoRoomMission("2", "Fake Mission 2"))
                    add(ManittoRoomMission("3", "Fake Mission 3"))
                    add(ManittoRoomMission("4", "Fake Mission 4"))
                },
                members = mutableListOf<ManittoRoomMember>().apply {
                    add(
                        ManittoRoomMember(
                            santa = ManittoRoomMember.SantaRoomInfo("1", "FakeFirstUser", "1"),
                            manitto = ManittoRoomMember.ManittoRoomInfo("2", "FakeSecondUser")
                        )
                    )
                    add(
                        ManittoRoomMember(
                            santa = ManittoRoomMember.SantaRoomInfo("2", "FakeSecondUser", "1"),
                            manitto = ManittoRoomMember.ManittoRoomInfo("3", "FakeThirdUser")
                        )
                    )
                    add(
                        ManittoRoomMember(
                            santa = ManittoRoomMember.SantaRoomInfo("3", "FakeThirdUser", "1"),
                            manitto = ManittoRoomMember.ManittoRoomInfo("1", "FakeFirstUser")
                        )
                    )
                }
            )
        )
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

    private val fakePersonalRoomInfos = HashMap<String, PersonalRoomModel>().apply {
        put(
            "1", PersonalRoomModel(
                manitto = MyManittoModel.Member.Manitto("1", "FakeFirstUser"),
                mission = MyManittoModel.Mission("Fake Mission 1", "1")
            )
        )
        put(
            "2", PersonalRoomModel(
                manitto = MyManittoModel.Member.Manitto("2", "FakeSecondUser"),
                mission = MyManittoModel.Mission("Fake Mission 2", "2")
            )
        )
        put(
            "3", PersonalRoomModel(
                manitto = MyManittoModel.Member.Manitto("3", "FakeThirdUser"),
                mission = MyManittoModel.Mission("Fake Mission 3", "3")
            )
        )
        put(
            "4", PersonalRoomModel(
                manitto = MyManittoModel.Member.Manitto("4", "FakeFourthUser"),
                mission = MyManittoModel.Mission("Fake Mission 4", "4")
            )
        )
        put(
            "5", PersonalRoomModel(
                manitto = MyManittoModel.Member.Manitto("5", "FakeFifthUser"),
                mission = MyManittoModel.Mission("Fake Mission 5", "5")
            )
        )
    }

    override fun getPersonalRoomInfo(
        roomId: String,
        callback: RoomRequest.GetPersonalRoomInfoCallback
    ) {
        if (fakePersonalRoomInfos.containsKey(roomId)) {
            callback.onLoadPersonalRoomInfo(fakePersonalRoomInfos[roomId]!!)
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
