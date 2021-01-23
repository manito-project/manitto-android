package org.sopt.santamanitto.main

import android.view.ViewGroup
import org.sopt.santamanitto.R
import org.sopt.santamanitto.data.JoinedRoom
import org.sopt.santamanitto.databinding.ViewholderJoinedRoomBinding
import org.sopt.santamanitto.recyclerview.BaseViewHolder

class JoinedRoomViewHolder(parent: ViewGroup):
    BaseViewHolder<JoinedRoom, ViewholderJoinedRoomBinding>(
        R.layout.viewholder_joined_room, parent) {

    override fun bind(data: JoinedRoom) {
        binding.joinedRoom = data
    }
}