package org.sopt.santamanitto.main

import android.view.ViewGroup
import org.sopt.santamanitto.R
import org.sopt.santamanitto.databinding.ItemMymanittoRemovedBinding
import org.sopt.santamanitto.room.data.MyManitto
import org.sopt.santamanitto.view.recyclerview.BaseViewHolder

class RemovedMyManttioViewHolder(parent: ViewGroup)
    : BaseViewHolder<MyManitto, ItemMymanittoRemovedBinding>(R.layout.item_mymanitto_removed, parent) {

    private val removeButton = binding.textviewMymanittoremovedButton

    private var roomId = -1

    init {
        removeButton.setOnClickListener {
            //Todo: 방 나가기 or 히스토리 삭제 API 호출
        }
    }

    override fun bind(data: MyManitto) {
        roomId = data.roomId
    }
}