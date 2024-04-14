package org.sopt.santamanitto.room.manittoroom

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import org.sopt.santamanitto.room.manittoroom.network.ManittoRoomMember
import org.sopt.santamanitto.util.ItemDiffCallback

class MemberAdapter : ListAdapter<ManittoRoomMember, MemberViewHolder>(DiffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MemberViewHolder = MemberViewHolder(parent)

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DiffUtil = ItemDiffCallback<ManittoRoomMember>(
            onContentsTheSame = { oldItem, newItem -> oldItem.userId == newItem.userId },
            onItemsTheSame = { oldItem, newItem -> oldItem == newItem }
        )
    }
}