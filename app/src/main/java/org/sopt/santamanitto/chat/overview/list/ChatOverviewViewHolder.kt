package org.sopt.santamanitto.chat.overview.list

import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import org.sopt.santamanitto.R
import org.sopt.santamanitto.chat.overview.network.ChatItemModel
import org.sopt.santamanitto.databinding.ItemChatOverviewBinding
import org.sopt.santamanitto.util.TimeUtil.convertToElapsedTime
import org.sopt.santamanitto.util.TimeUtil.isExpired
import java.util.Locale

class ChatOverviewViewHolder(
    val binding: ItemChatOverviewBinding,
    val itemClick: (String, String, String, Boolean, String, Boolean) -> Unit,
    val itemLongClick: (String, String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private val santaDrawable by lazy {
        ContextCompat.getDrawable(binding.root.context, R.drawable.ic_santa_ic)
    }
    private val rudolfDrawable by lazy {
        ContextCompat.getDrawable(binding.root.context, R.drawable.ic_rudolf_ic)
    }

    fun onBind(item: ChatItemModel) {
        with(binding) {
            textviewOverviewItemTitle.text = item.roomName

            textviewOverviewItemContent.text = item.lastContent.takeIf { !it.isNullOrBlank() }
                ?: "${item.opponentName}에게 쪽지를 보내보자!"

            textviewOverviewItemTime.text = item.lastMessageAt.takeIf { !it.isNullOrBlank() }
                ?.let { convertToElapsedTime(it) }.orEmpty()

            textviewOverviewItemUnread.text =
                String.format(Locale.getDefault(), "%d", item.unreadMessage)

            textviewOverviewItemUnread.isVisible = item.unreadMessage > 0

            imageviewOverviewItemLogo.setImageDrawable(if (item.isMyManitto) santaDrawable else rudolfDrawable)

            constraintlayoutOverviewItem.setBackgroundResource(
                if (isExpired(item.expirationDate)) R.color.gray_3 else R.color.white
            )

            root.setOnClickListener {
                item.conversationId?.let { convId ->
                    itemClick(
                        item.roomName,
                        item.roomId,
                        convId,
                        item.isMyManitto,
                        item.opponentName,
                        isExpired(item.expirationDate)
                    )
                }
            }
            root.setOnLongClickListener {
                item.conversationId?.let { convId -> itemLongClick(item.roomId, convId) }
                true
            }

            executePendingBindings()
        }
    }
}