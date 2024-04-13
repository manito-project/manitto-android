package org.sopt.santamanitto.main.list

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import org.sopt.santamanitto.room.data.MyManittoModel
import org.sopt.santamanitto.room.network.RoomRequest
import org.sopt.santamanitto.user.data.controller.UserAuthController
import org.sopt.santamanitto.user.data.source.UserMetadataSource
import org.sopt.santamanitto.util.ItemDiffCallback
import org.sopt.santamanitto.view.recyclerview.BaseViewHolder

class MyManittoListAdapter(
    private val userAuthController: UserAuthController,
    private val userMetadataSource: UserMetadataSource,
    private val roomRequest: RoomRequest
) : ListAdapter<MyManittoModel, BaseViewHolder<MyManittoModel, *>>(DiffUtil) {

    private var enterListener: ((roomId: Int, isMatched: Boolean, isFinished: Boolean) -> Unit)? = null
    private var exitListener: ((roomId: Int, roomName: String, isHost: Boolean) -> Unit)? = null
    private var removeListener: ((roomId: Int) -> Unit)? = null

    private val cachedMyManittoInfoModel = HashMap<Int, MyManittoInfoModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<MyManittoModel, *> {
        return when (viewType) {
            1 -> RemovedMyManttioViewHolder(parent, removeListener)
            2 -> ExpiredMyManittoViewHolder(parent, enterListener, removeListener)
            else -> BasicMyManittoViewHolder(
                parent,
                userAuthController,
                roomRequest,
                userMetadataSource,
                cachedMyManittoInfoModel,
                enterListener,
                exitListener
            )
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<MyManittoModel, *>, position: Int) {
        when (holder) {
            is BasicMyManittoViewHolder -> {
                holder.clear()
                holder.bind(getItem(position))
            }
            is RemovedMyManttioViewHolder -> {
                holder.clear()
                holder.bind(getItem(position))
            }
            is ExpiredMyManittoViewHolder -> {
                holder.clear()
                holder.bind(getItem(position))
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            currentList[position].isDeletedByCreator -> 1
            currentList[position].isExpiredWithoutMatching -> 2
            else -> 0
        }
    }

     fun clear() {
        cachedMyManittoInfoModel.clear()
        submitList(emptyList())
    }

    fun setOnItemClickListener(
        listener: (roomId: Int, isMatched: Boolean, isFinished: Boolean) -> Unit
    ) {
        this.enterListener = listener
    }

    fun setOnExitClickListener(
        listener: (roomId: Int, roomName: String, isHost: Boolean) -> Unit
    ) {
        this.exitListener = listener
    }

    fun setOnRemoveClickListener(listener: (roomId: Int) -> Unit) {
        this.removeListener = listener
    }

    companion object {
        private val DiffUtil = ItemDiffCallback<MyManittoModel>(
            onContentsTheSame = { oldItem, newItem -> oldItem.roomId == newItem.roomId },
            onItemsTheSame = { oldItem, newItem -> oldItem == newItem }
        )
    }
}
