package org.sopt.santamanitto.user.data.source

import org.sopt.santamanitto.room.data.MyManittoModel

interface MainUserDataSource {

    interface GetJoinedRoomsCallback {
        fun onMyManittoListLoaded(myManittoModels: List<MyManittoModel>)

        fun onDataNotAvailable()
    }

    fun getMyManittoList(callback: GetJoinedRoomsCallback)
}