package org.sopt.santamanitto

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics

object MetricsUtil {
    fun convertDpToPixel(dp: Int, context: Context?): Int {
        return if (context != null) {
            val resources = context.resources
            val metrics = resources.displayMetrics
            (dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
        } else {
            val metrics = Resources.getSystem().displayMetrics
            (dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
        }
    }

    fun convertPixelsToDp(px: Int, context: Context?): Int {
        return if (context != null) {
            val resources = context.resources
            val metrics = resources.displayMetrics
            (px / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
        } else {
            val metrics = Resources.getSystem().displayMetrics
            (px / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
        }
    }
}