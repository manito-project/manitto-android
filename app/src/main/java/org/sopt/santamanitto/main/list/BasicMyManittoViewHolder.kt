package org.sopt.santamanitto.main.list

import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import org.sopt.santamanitto.R
import org.sopt.santamanitto.databinding.ItemMymanittoBinding
import org.sopt.santamanitto.room.data.MyManittoModel
import org.sopt.santamanitto.room.data.PersonalRoomModel
import org.sopt.santamanitto.room.network.RoomRequest
import org.sopt.santamanitto.user.data.UserInfoModel
import org.sopt.santamanitto.user.data.controller.UserAuthController
import org.sopt.santamanitto.user.data.source.UserMetadataSource
import org.sopt.santamanitto.util.TimeUtil
import org.sopt.santamanitto.view.recyclerview.BaseViewHolder
import org.sopt.santamanitto.view.setBackgroundTint

class BasicMyManittoViewHolder(
    parent: ViewGroup,
    private val userAuthController: UserAuthController,
    private val roomRequest: RoomRequest,
    private val userMetadataSource: UserMetadataSource,
    private val cachedRoomInfo: HashMap<Int, MyManittoInfoModel>,
    private var listener: ((roomId: Int, isMatched: Boolean, isFinished: Boolean) -> Unit)? = null,
    private var exitListener: ((roomId: Int, roomName: String, isHost: Boolean) -> Unit)? = null
) : BaseViewHolder<MyManittoModel, ItemMymanittoBinding>(R.layout.item_mymanitto, parent) {

    private val contentText = binding.textviewMymanittoManittoinfo
    private val stateText = binding.textviewMymanittoState
    private val missionText = binding.textviewMymanittoMission
    private val loadingBar = binding.santaloadingJoinedroom
    private val exitButton = binding.buttonMymanittoExit
    private val roomName = binding.textviewMymanittoTitle

    override fun bind(data: MyManittoModel) {
        roomName.text = data.roomName

        listener?.let { listener ->
            val isFinished = !TimeUtil.isLaterThanNow(data.expiration)
            binding.root.setOnClickListener {
                listener.invoke(data.roomId, data.isMatchingDone, isFinished)
            }
        }
        exitListener?.let { listener ->
            exitButton.setOnClickListener {
                listener.invoke(
                    data.roomId, data.roomName,
                    data.creatorId == userMetadataSource.getUserId()
                )
            }
        }

        setRoomState(data)

        if (!data.isMatchingDone) {
            clearLoading()
            return
        }

        if (cachedRoomInfo.containsKey(data.roomId)) {
            val info = cachedRoomInfo[data.roomId]!!
            setManittoInfo(info = info, isMatched = true)
            clearLoading()
        } else {
            requestAndCacheInfo(roomId = data.roomId, data = data)
        }
    }

    override fun clear() {
        roomName.text = ""
        contentText.text = getString(R.string.joinedroom_santa_info_before_matching)
        stateText.text = ""
        missionText.text = ""
        loadingBar.visibility = View.VISIBLE
        exitButton.visibility = View.GONE
    }

    private fun requestAndCacheInfo(roomId: Int, data: MyManittoModel) {
        roomRequest.getPersonalRoomInfo(roomId, object : RoomRequest.GetPersonalRoomInfoCallback {
            override fun onLoadPersonalRoomInfo(personalRoom: PersonalRoomModel) {
                userAuthController.getUserInfo(
                    personalRoom.manittoUserId,
                    object : UserAuthController.GetUserInfoCallback {
                        override fun onUserInfoLoaded(userInfoModel: UserInfoModel) {
                            val info = MyManittoInfoModel(
                                userInfoModel.userName,
                                personalRoom.myMission?.content
                            )
                            cachedRoomInfo[roomId] = info
                            setManittoInfo(info = info, isMatched = data.isMatchingDone)
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

    private fun setManittoInfo(info: MyManittoInfoModel, isMatched: Boolean) {
        if (isMatched) {
            missionText.text = info.mission
            contentText.text =
                String.format(getString(R.string.joinedroom_manitto_info), info.manittoName)
        } else {
            contentText.text = getString(R.string.joinedroom_santa_info_before_matching)
        }

    }

    private fun clearLoading() {
        loadingBar.visibility = View.GONE
    }

    private fun setRoomState(data: MyManittoModel) {
        if (data.isMatchingDone) {
            showExitButton(false)
            if (TimeUtil.isLaterThanNow(data.expiration)) {
                //마니또 진행 중
                stateText.text = String.format(
                    getString(R.string.joinedroom_daydiff),
                    TimeUtil.getDayDiffFromNow(data.createdAt) * -1 + 1
                )
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