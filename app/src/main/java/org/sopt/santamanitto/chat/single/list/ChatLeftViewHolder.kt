package org.sopt.santamanitto.chat.single.list

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import org.sopt.santamanitto.R
import org.sopt.santamanitto.chat.single.network.SingleChatUiModel
import org.sopt.santamanitto.databinding.ItemChatLeftBinding

class ChatLeftViewHolder(
    private val binding: ItemChatLeftBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val santaDrawable by lazy {
        ContextCompat.getDrawable(binding.root.context, R.drawable.ic_santa_ic)
    }
    private val rudolfDrawable by lazy {
        ContextCompat.getDrawable(binding.root.context, R.drawable.ic_rudolf_ic)
    }

    fun onBind(item: SingleChatUiModel) {
        with(binding) {
            // TODO 기획 의도 따라가기
            imageviewChatLeftItemLogo.setImageDrawable(if (item.isMyManitto) santaDrawable else rudolfDrawable)
            textviewChatLeftItemName.text = if (item.isMyManitto) "익명의 마니또" else item.opponentName
            textviewChatLeftItemContent.text = item.content
            textviewChatLeftItemTime.text = item.timeText
        }
    }
}