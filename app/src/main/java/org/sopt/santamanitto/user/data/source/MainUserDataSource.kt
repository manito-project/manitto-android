package org.sopt.santamanitto.user.data.source

import org.sopt.santamanitto.room.data.JoinedRoom

interface MainUserDataSource {

    interface GetJoinedRoomsCallback {
        fun onJoinedRoomsLoaded(joinedRooms: List<JoinedRoom>)

        fun onDataNotAvailable()
    }

    fun getJoinedRooms(callback: GetJoinedRoomsCallback)
}