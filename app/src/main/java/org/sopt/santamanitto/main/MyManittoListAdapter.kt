package org.sopt.santamanitto.main

import android.view.ViewGroup
import org.sopt.santamanitto.room.data.MyManitto
import org.sopt.santamanitto.room.network.RoomRequest
import org.sopt.santamanitto.user.data.controller.UserAuthController
import org.sopt.santamanitto.user.data.source.UserMetadataSource
import org.sopt.santamanitto.view.recyclerview.BaseAdapter
import org.sopt.santamanitto.view.recyclerview.BaseViewHolder

class MyManittoListAdapter(
    private val userAuthController: UserAuthController,
    private val userMetadataSource: UserMetadataSource,
    private val roomRequest: RoomRequest
) : BaseAdapter<MyManitto>() {

    private var listener: ((roomId: Int, isMatched: Boolean, isFinished: Boolean) -> Unit)? = null
    private var exitListener: ((roomId: Int, roomName: String, isHost: Boolean) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<MyManitto, *> {
        return MyManittoViewHolder(
            parent, userAuthController, roomRequest, userMetadataSource, listener, exitListener)
    }

    fun setOnItemClickListener(
        listener: (roomId: Int, isMatched: Boolean, isFinished: Boolean) -> Unit
    ) {
        this.listener = listener
    }

    fun setOnExitClickListener(
        listener: (roomId: Int, roomName: String, isHost: Boolean) -> Unit
    ) {
        this.exitListener = listener
    }
}