package org.sopt.santamanitto.main

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import org.sopt.santamanitto.R
import org.sopt.santamanitto.data.JoinedRoom
import org.sopt.santamanitto.databinding.ViewholderJoinedRoomBinding
import org.sopt.santamanitto.recyclerview.BaseViewHolder
import org.sopt.santamanitto.room.PersonalRoomInfo
import org.sopt.santamanitto.room.source.RoomDataSource
import org.sopt.santamanitto.user.source.User
import org.sopt.santamanitto.user.source.UserDataSource
import org.sopt.santamanitto.util.TimeUtil

class JoinedRoomViewHolder(
        parent: ViewGroup,
        private val cachedUserDataSource: UserDataSource,
        private val cachedRoomDataSource: RoomDataSource
) : BaseViewHolder<JoinedRoom, ViewholderJoinedRoomBinding>(R.layout.viewholder_joined_room, parent) {

    override fun bind(data: JoinedRoom) {
        binding.joinedRoom = data

        cachedRoomDataSource.getPersonalRoomInfo(data.roomId, object : RoomDataSource.GetPersonalRoomInfoCallback {
            override fun onLoadPersonalRoomInfo(personalRoomInfo: PersonalRoomInfo) {
                binding.personalRoomInfo = personalRoomInfo

                cachedUserDataSource.getUserInfo(personalRoomInfo.manittoUserId, object: UserDataSource.GetUserInfoCallback {
                    override fun onUserInfoLoaded(user: User) {
                        binding.userInfo = user
                        binding.santaloadingJoinedroom.visibility = View.GONE
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

    companion object {
        @BindingAdapter("app:setDayDifferent")
        @JvmStatic
        fun AppCompatTextView.getThOfDayToString(from: String) {
            val gap = TimeUtil.getDifferentOfDaysFromNow(from) * -1
            text = String.format(context.getString(R.string.joinedroom_daydiff), gap)
        }

        @BindingAdapter("app:setManittoInfo")
        @JvmStatic
        fun AppCompatTextView.setManittoInfo(name: String) {
            text = String.format(context.getString(R.string.joinedroom_manitto_info), name)
       }
    }
}