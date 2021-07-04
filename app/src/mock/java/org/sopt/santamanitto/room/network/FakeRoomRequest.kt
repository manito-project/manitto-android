package org.sopt.santamanitto.room.network

import android.os.Handler
import android.os.Looper
import android.util.Log
import kotlinx.coroutines.*
import org.sopt.santamanitto.room.create.network.CreateRoomData
import org.sopt.santamanitto.room.create.network.CreateRoomResponse
import org.sopt.santamanitto.room.create.network.ModifyRoomData
import org.sopt.santamanitto.room.data.MissionContent
import org.sopt.santamanitto.room.data.PersonalRoomInfo
import org.sopt.santamanitto.room.data.source.RoomDataSource
import org.sopt.santamanitto.room.join.network.JoinRoomData
import org.sopt.santamanitto.room.join.network.JoinRoomInfo
import org.sopt.santamanitto.room.join.network.JoinRoomResponse
import org.sopt.santamanitto.room.join.network.JoinRoomUserInfo
import org.sopt.santamanitto.room.manittoroom.network.*
import org.sopt.santamanitto.util.TimeUtil

class FakeRoomRequest : RoomRequest {

    companion object {
        private const val TAG = "FakeRoomRequest"
    }

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

    override fun modifyRoom(
        roomId: Int,
        modifyRoomData: ModifyRoomData,
        callback: (onSuccess: Boolean) -> Unit
    ) {
        callback.invoke(true)
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

    override fun getManittoRoomData(
        roomId: Int,
        callback: RoomRequest.GetManittoRoomCallback
    ) {
        callback.onLoadManittoRoomData(
            ManittoRoomData(
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

            callback.onSuccessMatching(mutableListOf<ManittoRoomMatchedMissions>().apply {
                add(
                    ManittoRoomMatchedMissions(
                        1,
                        2,
                        3,
                        MissionContent("FakeMission1")
                    )
                )
                add(
                    ManittoRoomMatchedMissions(
                        2,
                        3,
                        1,
                        MissionContent("FakeMission2")
                    )
                )
                add(
                    ManittoRoomMatchedMissions(
                        3,
                        1,
                        2,
                        MissionContent("FakeMission3")
                    )
                )
            })
        }, 5000L)
    }

    private val fakePersonalRoomInfos = HashMap<Int, PersonalRoomInfo>().apply {
        put(1, PersonalRoomInfo(
            1,
            2,
            MissionContent("fake my mission"),
            MissionContent("fake mission to me")
        )
        )
        put(2, PersonalRoomInfo(
            1,
            3,
            MissionContent("fake my mission"),
            MissionContent("fake mission to me")
        )
        )
        put(3, PersonalRoomInfo(
            1,
            4,
            MissionContent("fake my mission"),
            MissionContent("fake mission to me")
        )
        )
        put(4, PersonalRoomInfo(
            1,
            5,
            MissionContent("fake my mission"),
            MissionContent("fake mission to me")
        )
        )
        put(5, PersonalRoomInfo(
            1,
            6,
            MissionContent("fake my mission"),
            MissionContent("fake mission to me")
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
