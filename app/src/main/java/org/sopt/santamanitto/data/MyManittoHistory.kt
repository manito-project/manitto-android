package org.sopt.santamanitto.data

import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import org.sopt.santamanitto.R

data class MyManittoHistory(
    val roomName: String,
    val manitto: String,
    val date: String,
    val mission: String
) {

    companion object {
        @BindingAdapter("setManittoInfo")
        @JvmStatic
        fun AppCompatTextView.setManittoInfo(manitto: String) {
            text = String.format(
                context.getString(R.string.mymanittoviewholder_manitto_info),
                manitto
            )
        }

        @BindingAdapter("setState")
        @JvmStatic
        fun AppCompatTextView.setState(manittoHistory: MyManittoHistory) {
            //Todo: ~일째, 매칭 대기중 등 상태에 따라 텍스트랑 배경 변환
        }
    }
}
