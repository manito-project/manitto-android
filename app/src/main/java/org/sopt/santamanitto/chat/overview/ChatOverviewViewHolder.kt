package org.sopt.santamanitto.chat.overview


import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import org.sopt.santamanitto.R
import org.sopt.santamanitto.databinding.ItemChatOverviewBinding
import org.sopt.santamanitto.util.TimeUtil.convertToElapsedTime
import org.sopt.santamanitto.util.TimeUtil.isExpired
import java.util.Locale

class ChatOverviewViewHolder(
    val binding: ItemChatOverviewBinding,
    val itemClick: (String) -> Unit,
    val itemLongClick: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: ChatItemModel) {
        with(binding) {
            textviewOverviewItemTitle.text = item.roomName

            textviewOverviewItemContent.text = if (item.lastContent.isNullOrBlank()) {
                "${item.opponentName}에게 쪽지를 보내보자!"
            } else {
                item.lastContent
            }

            textviewOverviewItemTime.text = if (item.lastMessageAt.isNullOrBlank()) {
                ""
            } else {
                convertToElapsedTime(item.lastMessageAt)
            }

            textviewOverviewItemUnread.text =
                String.format(Locale.getDefault(), "%d", item.unreadMessage)

            textviewOverviewItemUnread.isVisible = item.unreadMessage > 0

            if (item.isMyManitto) {
                imageviewOverviewItemLogo.setImageResource(R.drawable.ic_santa_ic)
            } else {
                imageviewOverviewItemLogo.setImageResource(R.drawable.ic_rudolf_ic)
            }

            constraintlayoutOverviewItem.setBackgroundResource(
                if (isExpired(item.expirationDate)) R.color.gray_3 else R.color.white
            )

            root.setOnClickListener {
                item.conversationId?.let { itemClick(it) }
            }
            root.setOnLongClickListener {
                item.conversationId?.let { itemLongClick(it) }
                true
            }
        }
    }
}