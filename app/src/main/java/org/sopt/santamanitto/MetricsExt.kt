package org.sopt.santamanitto

import android.view.View

fun Int.toDP(): Int {
    return MetricsUtil.convertPixelsToDp(this, null)
}

fun Int.toPixel() : Int {
    return MetricsUtil.convertDpToPixel(this, null)
}

fun View.getDpFromPx(px: Int): Int {
    return MetricsUtil.convertPixelsToDp(px, this.context)
}

fun View.getPxFromDp(dp: Int): Int {
    return MetricsUtil.convertDpToPixel(dp, this.context)
}