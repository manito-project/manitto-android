package org.sopt.santamanitto.room.source

import org.sopt.santamanitto.data.MissionContent
import org.sopt.santamanitto.room.PersonalRoomInfo

class FakeRoomRemoteDataSource: RoomDataSource {

    private val fakePersonalRoomInfo = PersonalRoomInfo(
        1,
        2,
        MissionContent("fake my mission"),
        MissionContent("fake mission to me")
    )

    override fun getPersonalRoomInfo(
        roomId: Int,
        callback: RoomDataSource.GetPersonalRoomInfoCallback
    ) {
        callback.onLoadPersonalRoomInfo(fakePersonalRoomInfo)
    }
}