package org.sopt.santamanitto.room.manittoroom

import android.view.ViewGroup
import org.sopt.santamanitto.room.manittoroom.network.ManittoRoomMember
import org.sopt.santamanitto.view.recyclerview.BaseAdapter
import org.sopt.santamanitto.view.recyclerview.BaseViewHolder

class MemberAdapter: BaseAdapter<ManittoRoomMember>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ManittoRoomMember, *> {
        return MemberViewHolder(parent)
    }
}