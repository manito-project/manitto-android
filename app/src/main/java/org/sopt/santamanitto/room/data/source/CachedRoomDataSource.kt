package org.sopt.santamanitto.room.data.source

import org.sopt.santamanitto.room.data.PersonalRoomModel

class CachedRoomDataSource(
    private val remoteRoomDataSource: RoomDataSource
): RoomDataSource {

    val cachedPersonalRoomModel = LinkedHashMap<Int, PersonalRoomModel>()
    private var isPersonalRoomInfoDirty = false

    override fun getPersonalRoomInfo(
        roomId: Int,
        callback: RoomDataSource.GetPersonalRoomInfoCallback
    ) {
        if (cachedPersonalRoomModel.containsKey(roomId) && !isPersonalRoomInfoDirty) {
            callback.onLoadPersonalRoomInfo(cachedPersonalRoomModel[roomId]!!)
            return
        }

        remoteRoomDataSource.getPersonalRoomInfo(roomId, object:
            RoomDataSource.GetPersonalRoomInfoCallback {
            override fun onLoadPersonalRoomInfo(personalRoomModel: PersonalRoomModel) {
                cachedPersonalRoomModel[roomId] = personalRoomModel
                isPersonalRoomInfoDirty = false
                callback.onLoadPersonalRoomInfo(personalRoomModel)
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }
        })
    }
}