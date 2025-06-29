package org.sopt.santamanitto.chat.single.list

import androidx.recyclerview.widget.RecyclerView
import org.sopt.santamanitto.chat.single.network.SingleChatUiModel
import org.sopt.santamanitto.databinding.ItemChatDateBinding

class ChatDateViewHolder(
    private val binding: ItemChatDateBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: SingleChatUiModel) {
        with(binding) {
            textviewChatDateItem.text = item.dateText
        }
    }
}