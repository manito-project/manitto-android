package org.sopt.santamanitto.room.data.source

import org.sopt.santamanitto.room.data.PersonalRoomInfo

class CachedRoomDataSource(
    private val remoteRoomDataSource: RoomDataSource
): RoomDataSource {

    val cachedPersonalRoomInfo = LinkedHashMap<Int, PersonalRoomInfo>()
    private var isPersonalRoomInfoDirty = false

    override fun getPersonalRoomInfo(
        roomId: Int,
        callback: RoomDataSource.GetPersonalRoomInfoCallback
    ) {
        if (cachedPersonalRoomInfo.containsKey(roomId) && !isPersonalRoomInfoDirty) {
            callback.onLoadPersonalRoomInfo(cachedPersonalRoomInfo[roomId]!!)
            return
        }

        remoteRoomDataSource.getPersonalRoomInfo(roomId, object:
            RoomDataSource.GetPersonalRoomInfoCallback {
            override fun onLoadPersonalRoomInfo(personalRoomInfo: PersonalRoomInfo) {
                cachedPersonalRoomInfo[roomId] = personalRoomInfo
                isPersonalRoomInfoDirty = false
                callback.onLoadPersonalRoomInfo(personalRoomInfo)
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }
        })
    }
}