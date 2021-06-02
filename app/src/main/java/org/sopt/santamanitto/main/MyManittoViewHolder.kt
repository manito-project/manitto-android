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

    private val contentText = binding.textviewMymanittoManittoinfo
    private val stateText = binding.textviewMymanittoState
    private val missionText = binding.textviewMymanittoMission
    private val exitButton = binding.buttonMymanittoExit
    private val loadingBar = binding.santaloadingJoinedroom

    override fun bind(data: MyManitto) {
        binding.myManitto = data

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
                        contentText.text = String.format(getString(R.string.joinedroom_manitto_info), userInfoResponse.userName)
                        clearLoading()
                    }

                    override fun onDataNotAvailable() {
                        loadingBar.setError(true)
                    }
                })
            }

            override fun onDataNotAvailable() {
                loadingBar.setError(true)
            }
        })
    }

    private fun clearLoading() {
        loadingBar.visibility = View.GONE
    }

    private fun setRoomState(data: MyManitto) {
        if (data.isMatchingDone) {
            showExitButton(false)
            if (TimeUtil.isLaterThanNow(data.expiration)) {
                //마니또 진행 중
                stateText.text = String.format(getString(R.string.joinedroom_daydiff),
                    TimeUtil.getDayDiffFromNow(data.createdAt) * -1 + 1)
                stateText.setBackgroundTint(R.color.red)
            } else {
                //결과 발표 완료 시
                stateText.text = getString(R.string.joinedroom_state_done)
                stateText.setBackgroundTint(R.color.gray_2)
            }
        } else {
            //마니또 매칭 전
            stateText.text = getString(R.string.joinedroom_state_matching)
            stateText.setBackgroundTint(R.color.gray_3)
            showExitButton(true)
        }
    }

    private fun showExitButton(isShow: Boolean) {
        if (isShow) {
            exitButton.visibility = View.VISIBLE
            missionText.visibility = View.GONE
        } else {
            exitButton.visibility = View.GONE
            missionText.visibility = View.VISIBLE
        }
    }

    private fun getString(@StringRes resId: Int): String {
        return itemView.context.getString(resId)
    }
}