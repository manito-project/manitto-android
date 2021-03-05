package org.sopt.santamanitto.view

import android.view.View
import androidx.databinding.BindingAdapter


@BindingAdapter("layout_height")
fun setLayoutHeight(view: View, height: Int) {
    val layoutParams = view.layoutParams
    layoutParams.height = height
    view.layoutParams = layoutParams
}