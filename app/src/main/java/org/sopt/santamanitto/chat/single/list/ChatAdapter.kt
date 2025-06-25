package org.sopt.santamanitto.chat.single.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.sopt.santamanitto.chat.single.network.SingleChatType
import org.sopt.santamanitto.chat.single.network.SingleChatUiModel
import org.sopt.santamanitto.databinding.ItemChatDateBinding
import org.sopt.santamanitto.databinding.ItemChatLeftBinding
import org.sopt.santamanitto.databinding.ItemChatRightBinding
import org.sopt.santamanitto.util.ItemDiffCallback

class ChatAdapter() : ListAdapter<SingleChatUiModel, RecyclerView.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater by lazy { LayoutInflater.from(parent.context) }
        return when (SingleChatType.entries[viewType]) {
            SingleChatType.TYPE_DATE -> ChatDateViewHolder(
                ItemChatDateBinding.inflate(inflater, parent, false)
            )

            SingleChatType.TYPE_OPPONENT -> ChatLeftViewHolder(
                ItemChatLeftBinding.inflate(inflater, parent, false)
            )

            SingleChatType.TYPE_MINE -> ChatRightViewHolder(
                ItemChatRightBinding.inflate(inflater, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is ChatDateViewHolder -> holder.onBind(item)
            is ChatLeftViewHolder -> holder.onBind(item)
            is ChatRightViewHolder -> holder.onBind(item)
            else -> throw IllegalArgumentException("Unknown ViewHolder: $holder")
        }
    }

    override fun getItemCount(): Int = currentList.size

    override fun getItemViewType(position: Int): Int = getItem(position).chatType.ordinal

    companion object {
        private val diffUtil = ItemDiffCallback<SingleChatUiModel>(
            onItemsTheSame = { old, new -> old.createdAt == new.createdAt },
            onContentsTheSame = { old, new -> old == new },
        )
    }
}