package org.sopt.santamanitto.main.viewholder

import android.view.ViewGroup
import org.sopt.santamanitto.R
import org.sopt.santamanitto.databinding.ItemMymanittoRemovedBinding
import org.sopt.santamanitto.room.data.MyManitto
import org.sopt.santamanitto.view.recyclerview.BaseViewHolder

class ExpiredMyManittoViewHolder(
    parent: ViewGroup,
    enterListener: ((roomId: Int, isMatched: Boolean, isFinished: Boolean) -> Unit)?,
    removeListener: ((roomId: Int) -> Unit)?
)
    : BaseViewHolder<MyManitto, ItemMymanittoRemovedBinding>(R.layout.item_mymanitto_removed, parent) {

    private val removeButton = binding.textviewMymanittoremovedButton
    private val title = binding.textviewMymanittoremovedTitle
    private val content = binding.textviewMymanittoremovedContent

    private var roomId = -1

    init {
        removeListener?.let {
            removeButton.setOnClickListener {
                it(roomId)
            }
        }
        enterListener?.let {
            binding.root.setOnClickListener {
                it(roomId, false, false)
            }
        }
        content.text = itemView.context.getString(R.string.mymanitto_epired_content)
    }

    override fun bind(data: MyManitto) {
        title.text = data.roomName
        roomId = data.roomId
    }
}