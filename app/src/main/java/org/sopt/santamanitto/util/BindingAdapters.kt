package org.sopt.santamanitto.util

import android.view.View
import androidx.databinding.BindingAdapter

object BindingAdapters {
    @BindingAdapter("layout_height")
    fun setLayoutHeight(view: View, height: Int) {
        val layoutParams = view.layoutParams
        layoutParams.height = height
        view.layoutParams = layoutParams
    }
}