package org.sopt.santamanitto.room.data.source

import org.sopt.santamanitto.room.data.MissionContent
import org.sopt.santamanitto.room.data.PersonalRoomInfo

class FakeRoomRemoteDataSource: RoomDataSource {

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
        callback: RoomDataSource.GetPersonalRoomInfoCallback
    ) {
        if (fakePersonalRoomInfos.containsKey(roomId)) {
            callback.onLoadPersonalRoomInfo(fakePersonalRoomInfos[roomId]!!)
        } else {
            callback.onDataNotAvailable()
        }
    }
}