package org.sopt.santamanitto.main

import android.view.ViewGroup
import org.sopt.santamanitto.R
import org.sopt.santamanitto.data.MyManittoHistory
import org.sopt.santamanitto.databinding.ViewholderMyManittoHistoryBinding
import org.sopt.santamanitto.recyclerview.BaseViewHolder

class MyManittoViewHolder(parent: ViewGroup):
    BaseViewHolder<MyManittoHistory, ViewholderMyManittoHistoryBinding>(
        R.layout.viewholder_my_manitto_history,
        parent
    ) {

    override fun bind(data: MyManittoHistory) {
        binding.myManittoHistory = data
    }

}