package org.sopt.santamanitto.main.list

import android.view.ViewGroup
import org.sopt.santamanitto.room.data.MyManittoModel
import org.sopt.santamanitto.room.network.RoomRequest
import org.sopt.santamanitto.user.data.controller.UserAuthController
import org.sopt.santamanitto.user.data.source.UserMetadataSource
import org.sopt.santamanitto.view.recyclerview.BaseAdapter
import org.sopt.santamanitto.view.recyclerview.BaseViewHolder

class MyManittoListAdapter(
    private val userAuthController: UserAuthController,
    private val userMetadataSource: UserMetadataSource,
    private val roomRequest: RoomRequest
) : BaseAdapter<MyManittoModel>() {

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

    override fun getItemViewType(position: Int): Int {
        return when {
            items[position].isDeletedByCreator -> 1
            items[position].isExpiredWithoutMatching -> 2
            else -> 0
        }
    }

    override fun clear() {
        cachedMyManittoInfoModel.clear()
        super.clear()
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
}