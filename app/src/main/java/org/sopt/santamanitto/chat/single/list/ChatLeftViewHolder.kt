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
    private val darkGrayBackground by lazy {
        ContextCompat.getDrawable(binding.root.context, R.drawable.shape_dark_gray_fill_leftchat)
    }

    fun onBind(item: SingleChatUiModel) {
        // TODO 기획 의도 따라가기
        with(binding) {
            if (item.isPlaceholder) {
                imageviewChatLeftItemLogo.setImageDrawable(santaDrawable)
                textviewChatLeftItemName.text = "산타마니또"
                textviewChatLeftItemContent.apply {
                    text = "마니또에게 응원의 메시지를 보내볼까?\n메세지는 10자 이내로 보낼 수 있어!"
                    background = darkGrayBackground
                    setTextColor(ContextCompat.getColor(context, R.color.white))
                }
                return
            }
            imageviewChatLeftItemLogo.setImageDrawable(if (item.isMyManitto) santaDrawable else rudolfDrawable)
            textviewChatLeftItemName.text = if (item.isMyManitto) "익명의 마니또" else item.opponentName
            textviewChatLeftItemContent.text = item.content
            textviewChatLeftItemTime.text = item.timeText
        }
    }
}