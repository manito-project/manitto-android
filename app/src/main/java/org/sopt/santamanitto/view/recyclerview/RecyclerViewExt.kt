package org.sopt.santamanitto.view.recyclerview

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("replaceAll")
fun <T> RecyclerView.replaceAll(list: List<T>?) {
    (this.adapter as? ListAdapter<T, *>)?.let { adapter ->
        adapter.submitList(list?.toList())
        this.visibility = if (list.isNullOrEmpty()) View.INVISIBLE else View.VISIBLE
    } ?: (this.adapter as? BaseAdapter<T>)?.run {
        if (list != null) {
            setList(list)
            this@replaceAll.visibility = if (list.isEmpty()) {
                View.INVISIBLE
            } else {
                View.VISIBLE
            }
        } else {
            clear()
            this@replaceAll.visibility = View.INVISIBLE
        }
    }
}

@BindingAdapter("setItemMargin")
fun RecyclerView.setItemMargin(dimen: Float) {
    val orientation = (layoutManager as LinearLayoutManager?)?.orientation ?: return
    addItemDecoration(MarginDecoration(dimen, orientation))
}