package org.sopt.santamanitto.main

import android.view.ViewGroup
import org.sopt.santamanitto.data.MyManittoHistory
import org.sopt.santamanitto.recyclerview.BaseAdapter
import org.sopt.santamanitto.recyclerview.BaseViewHolder

class MyManittoAdapter : BaseAdapter<MyManittoHistory>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<MyManittoHistory, *> {
        return MyManittoViewHolder(parent)
    }
}