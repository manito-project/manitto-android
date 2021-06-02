package org.sopt.santamanitto.main

import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import org.sopt.santamanitto.R
import org.sopt.santamanitto.databinding.ItemMymanittoBinding
import org.sopt.santamanitto.room.data.MyManitto
import org.sopt.santamanitto.room.data.PersonalRoomInfo
import org.sopt.santamanitto.room.network.RoomRequest
import org.sopt.santamanitto.user.data.UserInfoResponse
import org.sopt.santamanitto.user.data.controller.UserAuthController
import org.sopt.santamanitto.util.TimeUtil
import org.sopt.santamanitto.view.recyclerview.BaseViewHolder
import org.sopt.santamanitto.view.setBackgroundTint

class MyManittoViewHolder(
    parent: ViewGroup,
    private val userAuthController: UserAuthController,
    private val roomRequest: RoomRequest,
    private var listener: ((roomId: Int, isMatched: Boolean, isFinished: Boolean) -> Unit)? = null
) : BaseViewHolder<MyManitto, ItemMymanittoBinding>(R.layout.item_mymanitto, parent) {

    override fun bind(data: MyManitto) {
        binding.joinedRoom = data

        listener?.let {
            val isFinished = !TimeUtil.isLaterThanNow(data.expiration)
            binding.root.setOnClickListener {
                it(data.roomId, data.isMatchingDone, isFinished)
            }
        }

        setRoomState(data)

        if (!data.isMatchingDone) {
            clearLoading()
            return
        }

        roomRequest.getPersonalRoomInfo(data.roomId, object : RoomRequest.GetPersonalRoomInfoCallback {
            override fun onLoadPersonalRoomInfo(personalRoomInfo: PersonalRoomInfo) {
                binding.personalRoomInfo = personalRoomInfo

                userAuthController.getUserInfo(personalRoomInfo.manittoUserId, object: UserAuthController.GetUserInfoCallback {
                    override fun onUserInfoLoaded(userInfoResponse: UserInfoResponse) {
                        binding.textviewMymanittoManittoinfo.text = String.format(getString(R.string.joinedroom_manitto_info), userInfoResponse.userName)
                        clearLoading()
                    }

                    override fun onDataNotAvailable() {
                        binding.santaloadingJoinedroom.setError(true)
                    }
                })
            }

            override fun onDataNotAvailable() {
                binding.santaloadingJoinedroom.setError(true)
            }
        })
    }

    private fun clearLoading() {
        binding.santaloadingJoinedroom.visibility = View.GONE
    }

    private fun setRoomState(data: MyManitto) {
        binding.textviewMymanittoState.text = if (data.isMatchingDone) {
            if (TimeUtil.isLaterThanNow(data.expiration)) {
                String.format(getString(R.string.joinedroom_daydiff), TimeUtil.getDayDiffFromNow(data.createdAt) * -1 + 1)
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