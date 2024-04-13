package org.sopt.santamanitto.room.manittoroom

import android.view.ViewGroup
import org.sopt.santamanitto.R
import org.sopt.santamanitto.databinding.ItemMemberBinding
import org.sopt.santamanitto.room.manittoroom.network.ManittoRoomMember
import org.sopt.santamanitto.util.RandomUtil
import org.sopt.santamanitto.view.recyclerview.BaseViewHolder

class MemberViewHolder(parent: ViewGroup)
    : BaseViewHolder<ManittoRoomMember, ItemMemberBinding>(R.layout.item_member, parent) {

    companion object {
        private val imageSource = arrayOf(R.drawable.ic_santa_ic, R.drawable.ic_rudolf_ic)
    }

    override fun bind(data: ManittoRoomMember) {
        binding.run {
            imageviewMemberitemImage.setImageResource(
                imageSource[RandomUtil.getRandomValue(imageSource.size)]
            )
            textviewMemberitemName.text = data.userName
        }
    }

    override fun clear() {
        binding.run {
            imageviewMemberitemImage.setImageResource(0)
            textviewMemberitemName.text = ""
        }
    }
}