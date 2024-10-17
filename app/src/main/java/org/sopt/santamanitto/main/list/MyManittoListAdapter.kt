package org.sopt.santamanitto.main.list

import android.os.Build
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.ListAdapter
import org.sopt.santamanitto.room.data.TempMyManittoModel
import org.sopt.santamanitto.room.network.RoomRequest
import org.sopt.santamanitto.user.data.controller.UserAuthController
import org.sopt.santamanitto.user.data.source.UserMetadataSource
import org.sopt.santamanitto.util.ItemDiffCallback
import org.sopt.santamanitto.util.TimeUtil.isExpired
import org.sopt.santamanitto.view.recyclerview.BaseViewHolder

class MyManittoListAdapter(
    private val userAuthController: UserAuthController,
    private val userMetadataSource: UserMetadataSource,
    private val roomRequest: RoomRequest
) : ListAdapter<TempMyManittoModel, BaseViewHolder<TempMyManittoModel, *>>(DiffUtil) {

    private var enterListener: ((roomId: String, isMatched: Boolean, isFinished: Boolean) -> Unit)? =
        null
    private var exitListener: ((roomId: String, roomName: String, isHost: Boolean) -> Unit)? = null
    private var removeListener: ((roomId: String) -> Unit)? = null

    private val cachedMyManittoInfoModel = HashMap<String, MyManittoInfoModel>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<TempMyManittoModel, *> {
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

    override fun onBindViewHolder(holder: BaseViewHolder<TempMyManittoModel, *>, position: Int) {
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getItemViewType(position: Int): Int {
        val item = currentList[position]

        return when {
            item.deletedByCreatorDate != null -> 1  // 삭제된 경우
            item.expirationDate != null && item.matchingDate == null && isExpired(item.expirationDate) -> 2  // 매칭되지 않고 만료된 경우
            else -> 0  // 기본
        }
    }

    fun clear() {
        cachedMyManittoInfoModel.clear()
        submitList(emptyList())
    }

    fun setOnItemClickListener(
        listener: (roomId: String, isMatched: Boolean, isFinished: Boolean) -> Unit
    ) {
        this.enterListener = listener
    }

    fun setOnExitClickListener(
        listener: (roomId: String, roomName: String, isHost: Boolean) -> Unit
    ) {
        this.exitListener = listener
    }

    fun setOnRemoveClickListener(listener: (roomId: String) -> Unit) {
        this.removeListener = listener
    }

    companion object {
        private val DiffUtil = ItemDiffCallback<TempMyManittoModel>(
            onContentsTheSame = { oldItem, newItem -> oldItem.roomId == newItem.roomId },
            onItemsTheSame = { oldItem, newItem -> oldItem == newItem }
        )
    }
}
