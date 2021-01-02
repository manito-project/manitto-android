package org.sopt.santamanitto

import android.graphics.Typeface
import android.util.TypedValue

import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.core.content.ContextCompat

fun View.setPaddingVerticalById(@DimenRes id: Int) {
    val dimen = resources.getDimension(id).toInt()
    setPadding(paddingLeft, dimen, paddingRight, dimen)
}

fun View.setBackgroundTint(@ColorRes id: Int) {
    backgroundTintList = ContextCompat.getColorStateList(context, id)
}

fun View.setRippleEffect(isEnabled: Boolean) {
    foreground = if (isEnabled) {
        val outValue = TypedValue()
        context.theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
        ContextCompat.getDrawable(context, outValue.resourceId)
    } else {
        null
    }
}

fun TextView.setTextColorById(@ColorRes id: Int) {
    setTextColor(ContextCompat.getColor(context, id))
}

fun TextView.setTextSize(@DimenRes id: Int) {
    setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(id))
}

fun TextView.setBold(isEnabled: Boolean) {
    if (isEnabled) {
        setTypeface(typeface, Typeface.BOLD)
    } else {
        typeface = Typeface.DEFAULT
    }
}