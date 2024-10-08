package org.sopt.santamanitto.room.data.source

import org.sopt.santamanitto.room.data.MissionContentModel
import org.sopt.santamanitto.room.data.PersonalRoomModel

class FakeRoomRemoteDataSource : RoomDataSource {

    private val fakePersonalRoomInfos = HashMap<Int, PersonalRoomModel>().apply {
        put(
            1, PersonalRoomModel(
                "1",
                "2",
                MissionContentModel("fake my mission"),
                MissionContentModel("fake mission to me")
            )
        )
        put(
            2, PersonalRoomModel(
                "1",
                "3",
                MissionContentModel("fake my mission"),
                MissionContentModel("fake mission to me")
            )
        )
        put(
            3, PersonalRoomModel(
                "1",
                "4",
                MissionContentModel("fake my mission"),
                MissionContentModel("fake mission to me")
            )
        )
        put(
            4, PersonalRoomModel(
                "1",
                "5",
                MissionContentModel("fake my mission"),
                MissionContentModel("fake mission to me")
            )
        )
        put(
            5, PersonalRoomModel(
                "1",
                "6",
                MissionContentModel("fake my mission"),
                MissionContentModel("fake mission to me")
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