package org.sopt.santamanitto.view.santanumberpicker

import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView

class SantaNumberPickerSnapScrollListener(
        private val recyclerView: RecyclerView,
        private val onSnapPositionChangedListener : (position: Int) -> Unit
): RecyclerView.OnScrollListener() {

    private val snapHelper = LinearSnapHelper().apply { attachToRecyclerView(recyclerView) }
    private var previousSnapPosition = RecyclerView.NO_POSITION

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val currentSnapPosition = getSnapPosition(recyclerView)
        if (previousSnapPosition != currentSnapPosition) {
            onSnapPositionChangedListener(currentSnapPosition)
            previousSnapPosition = currentSnapPosition
        }
    }

    private fun getSnapPosition(recyclerView: RecyclerView): Int {
        val layoutManager = recyclerView.layoutManager ?: return RecyclerView.NO_POSITION
        val snapView = snapHelper.findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION
        return layoutManager.getPosition(snapView)
    }
}