package org.sopt.santamanitto.chat.overview.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import org.sopt.santamanitto.chat.overview.network.ChatItemModel
import org.sopt.santamanitto.databinding.ItemChatOverviewBinding
import org.sopt.santamanitto.util.ItemDiffCallback

class ChatOverviewAdapter(
    private val itemClick: (String, String, String, Boolean, String, Boolean) -> Unit,
    private val itemLongClick: (String, String) -> Unit
) : ListAdapter<ChatItemModel, ChatOverviewViewHolder>(diffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ChatOverviewViewHolder {
        val inflater by lazy { LayoutInflater.from(parent.context) }
        val binding: ItemChatOverviewBinding =
            ItemChatOverviewBinding.inflate(inflater, parent, false)
        return ChatOverviewViewHolder(binding, itemClick, itemLongClick)
    }

    override fun onBindViewHolder(
        holder: ChatOverviewViewHolder,
        position: Int,
    ) {
        val item = getItem(position) ?: return
        holder.onBind(item)
    }

    companion object {
        private val diffUtil = ItemDiffCallback<ChatItemModel>(
            onItemsTheSame = { old, new -> old.roomId == new.roomId },
            onContentsTheSame = { old, new -> old == new },
        )
    }
}