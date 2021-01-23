package org.sopt.santamanitto.main

import android.view.ViewGroup
import org.sopt.santamanitto.data.JoinedRoom
import org.sopt.santamanitto.recyclerview.BaseAdapter
import org.sopt.santamanitto.recyclerview.BaseViewHolder

class JoinedRoomsAdapter : BaseAdapter<JoinedRoom>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<JoinedRoom, *> {
        return JoinedRoomViewHolder(parent)
    }
}