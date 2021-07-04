package org.sopt.santamanitto.user.data.source

import org.sopt.santamanitto.room.data.MyManitto

interface MainUserDataSource {

    interface GetJoinedRoomsCallback {
        fun onMyManittoListLoaded(myManittos: List<MyManitto>)

        fun onDataNotAvailable()
    }

    fun getMyManittoList(callback: GetJoinedRoomsCallback)
}