package org.sopt.santamanitto.main

import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import org.sopt.santamanitto.R
import org.sopt.santamanitto.room.data.JoinedRoom
import org.sopt.santamanitto.databinding.ViewholderJoinedRoomBinding
import org.sopt.santamanitto.view.recyclerview.BaseViewHolder
import org.sopt.santamanitto.room.data.PersonalRoomInfo
import org.sopt.santamanitto.room.data.source.RoomDataSource
import org.sopt.santamanitto.user.data.User
import org.sopt.santamanitto.user.data.source.UserDataSource
import org.sopt.santamanitto.util.TimeUtil
import org.sopt.santamanitto.view.setBackgroundTint

class JoinedRoomViewHolder(
        parent: ViewGroup,
        private val cachedUserDataSource: UserDataSource,
        private val cachedRoomDataSource: RoomDataSource,
        private var listener: ((roomId: Int, isMatched: Boolean) -> Unit)? = null
) : BaseViewHolder<JoinedRoom, ViewholderJoinedRoomBinding>(R.layout.viewholder_joined_room, parent) {

    override fun bind(data: JoinedRoom) {
        binding.joinedRoom = data

        listener?.let {
            binding.root.setOnClickListener {
                it(data.roomId, data.isMatchingDone)
            }
        }

        setRoomState(data)

        if (!data.isMatchingDone) {
            clearLoading()
            return
        }

        cachedRoomDataSource.getPersonalRoomInfo(data.roomId, object : RoomDataSource.GetPersonalRoomInfoCallback {
            override fun onLoadPersonalRoomInfo(personalRoomInfo: PersonalRoomInfo) {
                binding.personalRoomInfo = personalRoomInfo

                cachedUserDataSource.getUserInfo(personalRoomInfo.manittoUserId, object: UserDataSource.GetUserInfoCallback {
                    override fun onUserInfoLoaded(user: User) {
                        binding.userInfo = user
                        clearLoading()
                    }

                    override fun onDataNotAvailable() {
                        binding.santaloadingJoinedroom.setDataNotAvailable()
                    }
                })
            }

            override fun onDataNotAvailable() {
                binding.santaloadingJoinedroom.setDataNotAvailable()
            }
        })
    }

    private fun clearLoading() {
        binding.santaloadingJoinedroom.visibility = View.GONE
    }

    private fun setRoomState(data: JoinedRoom) {
        binding.textviewMymanittoState.text = if (data.isMatchingDone) {
            if (TimeUtil.isLaterThanNow(data.expiration)) {
                String.format(getString(R.string.joinedroom_daydiff), TimeUtil.getDifferentOfDaysFromNow(data.createdAt + 1))
            } else {
                binding.textviewMymanittoState.setBackgroundTint(R.color.gray_3)
                getString(R.string.joinedroom_state_done)
            }
        } else {
            getString(R.string.joinedroom_state_matching)
        }
    }

    private fun getString(@StringRes resId: Int): String {
        return itemView.context.getString(resId)
    }
}