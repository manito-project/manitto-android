package org.sopt.santamanitto.util

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics

object MetricsUtil {
    @JvmStatic
    fun convertDpToPixel(dp: Float, context: Context?): Float {
        return if (context != null) {
            val resources = context.resources
            val metrics = resources.displayMetrics
            (dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT))
        } else {
            val metrics = Resources.getSystem().displayMetrics
            (dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT))
        }
    }

    fun convertPixelsToDp(px: Float, context: Context?): Float {
        return if (context != null) {
            val resources = context.resources
            val metrics = resources.displayMetrics
            (px / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT))
        } else {
            val metrics = Resources.getSystem().displayMetrics
            (px / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT))
        }
    }
}

internal fun Int.toDP(): Int {
    return MetricsUtil.convertPixelsToDp(this.toFloat(), null).toInt()
}

internal fun Int.toPixel() : Int {
    return MetricsUtil.convertDpToPixel(this.toFloat(), null).toInt()
}