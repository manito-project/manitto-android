package org.sopt.santamanitto.recyclerview

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("replaceAll")
fun RecyclerView.replaceAll(list: List<Nothing>?) {
    if (adapter != null) {
        (adapter as BaseAdapter<*>).run {
            if (list != null) {
                visibility = if (list.isEmpty()) {
                    View.INVISIBLE
                } else {
                    View.VISIBLE
                }
                setList(list)
            } else {
                clear()
                visibility = View.INVISIBLE
            }
        }
    }
}

@BindingAdapter("setItemMargin")
fun RecyclerView.setItemMargin(dimen: Float) {
    val orientation = (layoutManager as LinearLayoutManager?)?.orientation ?: return
    addItemDecoration(MarginDecoration(dimen, orientation))
}