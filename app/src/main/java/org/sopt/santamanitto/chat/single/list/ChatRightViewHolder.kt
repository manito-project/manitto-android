package org.sopt.santamanitto.chat.single.list

import androidx.recyclerview.widget.RecyclerView
import org.sopt.santamanitto.chat.single.network.SingleChatUiModel
import org.sopt.santamanitto.databinding.ItemChatRightBinding

class ChatRightViewHolder(
    private val binding: ItemChatRightBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: SingleChatUiModel) {
        with(binding) {
            textviewChatRightItemContent.text = item.content
            textviewChatRightItemTime.text = item.timeText
        }
    }
}