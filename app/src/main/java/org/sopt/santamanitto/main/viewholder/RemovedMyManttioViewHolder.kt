package org.sopt.santamanitto.main.viewholder

import android.view.ViewGroup
import org.sopt.santamanitto.R
import org.sopt.santamanitto.databinding.ItemMymanittoRemovedBinding
import org.sopt.santamanitto.room.data.MyManitto
import org.sopt.santamanitto.view.recyclerview.BaseViewHolder

class RemovedMyManttioViewHolder(
    parent: ViewGroup,
    removeListener: ((roomId: Int) -> Unit)?
)
    : BaseViewHolder<MyManitto, ItemMymanittoRemovedBinding>(R.layout.item_mymanitto_removed, parent) {

    private val removeButton = binding.textviewMymanittoremovedButton

    private var roomId = -1

    init {
        removeListener?.let {
            removeButton.setOnClickListener {
                it(roomId)
            }
        }
    }

    override fun bind(data: MyManitto) {
        roomId = data.roomId
    }
}