package org.sopt.santamanitto

import android.view.View

fun Float.toDP(): Float {
    return MetricsUtil.convertPixelsToDp(this, null)
}

fun Float.toPixel() : Float {
    return MetricsUtil.convertDpToPixel(this, null)
}

fun View.getDpFromPx(px: Float): Float {
    return MetricsUtil.convertPixelsToDp(px, this.context)
}

fun View.getPxFromDp(dp: Float): Float {
    return MetricsUtil.convertDpToPixel(dp, this.context)
}