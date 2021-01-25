package org.sopt.santamanitto.view.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginDecoration(private val margin: Float, private val orientation: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount

        orientation.let {
            if (position != itemCount - 1) {
                when (orientation) {
                    RecyclerView.HORIZONTAL -> outRect.right = margin.toInt()
                    RecyclerView.VERTICAL -> outRect.bottom = margin.toInt()
                }
            }
        }
    }
}