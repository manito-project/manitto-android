package org.sopt.santamanitto.room.network

import android.os.Handler
import android.os.Looper
import android.util.Log
import org.sopt.santamanitto.room.create.network.CreateRoomRequestModel
import org.sopt.santamanitto.room.create.network.CreateRoomModel
import org.sopt.santamanitto.room.create.network.ModifyRoomRequestModel
import org.sopt.santamanitto.room.data.MissionContentModel
import org.sopt.santamanitto.room.data.PersonalRoomModel
import org.sopt.santamanitto.room.join.network.JoinRoomRequestModel
import org.sopt.santamanitto.room.join.network.JoinRoomModel
import org.sopt.santamanitto.room.join.network.JoinRoomModel.JoinRoomInfo
import org.sopt.santamanitto.room.join.network.JoinRoomModel.JoinRoomUserInfo
import org.sopt.santamanitto.room.manittoroom.network.MatchedMissionsModel
import org.sopt.santamanitto.room.manittoroom.network.ManittoRoomMember
import org.sopt.santamanitto.room.manittoroom.network.ManittoRoomModel
import org.sopt.santamanitto.room.manittoroom.network.ManittoRoomModel.ManittoRoomCreator
import org.sopt.santamanitto.room.manittoroom.network.ManittoRoomModel.ManittoRoomMission
import org.sopt.santamanitto.room.manittoroom.network.ManittoRoomRelations
import org.sopt.santamanitto.util.TimeUtil

class FakeRoomRequest : RoomRequest {

    companion object {
        private const val TAG = "FakeRoomRequest"
    }

    override fun createRoom(
        request: CreateRoomRequestModel,
        callback: RoomRequest.CreateRoomCallback
    ) {
        callback.onRoomCreated(
            CreateRoomModel(
                false, 1, request.roomName, request.expiration,
                "oU3lsEo", TimeUtil.getCurrentTimeByServerFormat(),
                TimeUtil.getCurrentTimeByServerFormat()
            )
        )
    }

    override fun modifyRoom(
        roomId: Int,
        request: ModifyRoomRequestModel,
        callback: (onSuccess: Boolean) -> Unit
    ) {
        callback.invoke(true)
    }

    override fun joinRoom(request: JoinRoomRequestModel, callback: RoomRequest.JoinRoomCallback) {
        when (request.invitationCode) {
            "success" ->
                callback.onSuccessJoinRoom(
                    JoinRoomModel(
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

    override fun getManittoRoomData(
        roomId: Int,
        callback: RoomRequest.GetManittoRoomCallback
    ) {
        callback.onLoadManittoRoomData(
            ManittoRoomModel(
                roomId,
                "FakeRoom",
                "oU3lsEo-",
                "false",
                "2021-02-28 11:01:00",
                "2021-02-21 14:47:10",
                ManittoRoomCreator(
                    1,
                    "FakeFirstUser",
                    "12fsfe2"
                ),
                mutableListOf<ManittoRoomMission>().apply {
                    add(
                        ManittoRoomMission(
                            1,
                            "Fake Mission 1"
                        )
                    )
                    add(
                        ManittoRoomMission(
                            2,
                            "Fake Mission 2"
                        )
                    )
                    add(
                        ManittoRoomMission(
                            3,
                            "Fake Mission 3"
                        )
                    )
                    add(
                        ManittoRoomMission(
                            4,
                            "Fake Mission 4"
                        )
                    )
                },
                mutableListOf<ManittoRoomMember>().apply {
                    add(
                        ManittoRoomMember(
                            1,
                            "FakeFirstUser",
                            ManittoRoomRelations(
                                2,
                                3
                            )
                        )
                    )
                    add(
                        ManittoRoomMember(
                            2,
                            "FakeSecondUser",
                            ManittoRoomRelations(
                                3,
                                1
                            )
                        )
                    )
                    add(
                        ManittoRoomMember(
                            3,
                            "FakeThirdUser",
                            ManittoRoomRelations(
                                1,
                                2
                            )
                        )
                    )
                }
            )
        )
    }

    override fun matchManitto(roomId: Int, callback: RoomRequest.MatchManittoCallback) {
        Handler(Looper.getMainLooper()).postDelayed({

            callback.onSuccessMatching(mutableListOf<MatchedMissionsModel>().apply {
                add(
                    MatchedMissionsModel(
                        1,
                        2,
                        3,
                        MissionContentModel("FakeMission1")
                    )
                )
                add(
                    MatchedMissionsModel(
                        2,
                        3,
                        1,
                        MissionContentModel("FakeMission2")
                    )
                )
                add(
                    MatchedMissionsModel(
                        3,
                        1,
                        2,
                        MissionContentModel("FakeMission3")
                    )
                )
            })
        }, 5000L)
    }

    private val fakePersonalRoomInfos = HashMap<Int, PersonalRoomModel>().apply {
        put(1, PersonalRoomModel(
            1,
            2,
            MissionContentModel("fake my mission"),
            MissionContentModel("fake mission to me")
        )
        )
        put(2, PersonalRoomModel(
            1,
            3,
            MissionContentModel("fake my mission"),
            MissionContentModel("fake mission to me")
        )
        )
        put(3, PersonalRoomModel(
            1,
            4,
            MissionContentModel("fake my mission"),
            MissionContentModel("fake mission to me")
        )
        )
        put(4, PersonalRoomModel(
            1,
            5,
            MissionContentModel("fake my mission"),
            MissionContentModel("fake mission to me")
        )
        )
        put(5, PersonalRoomModel(
            1,
            6,
            MissionContentModel("fake my mission"),
            MissionContentModel("fake mission to me")
        )
        )
    }

    override fun getPersonalRoomInfo(
        roomId: Int,
        callback: RoomRequest.GetPersonalRoomInfoCallback
    ) {
        if (fakePersonalRoomInfos.containsKey(roomId)) {
            callback.onLoadPersonalRoomInfo(fakePersonalRoomInfos[roomId]!!)
        } else {
            callback.onDataNotAvailable()
        }
    }

    override fun exitRoom(roomId: Int, callback: (onSuccess: Boolean) -> Unit) {
        Log.d(TAG, "exitRoom: room(id : $roomId) is exited")
        callback.invoke(true)
    }

    override fun removeHistory(roomId: Int, callback: (onSuccess: Boolean) -> Unit) {
        Log.d(TAG, "removeHistory: room(id : $roomId) is removed from history")
        callback.invoke(true)
    }
}
