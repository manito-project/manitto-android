package org.sopt.santamanitto

import android.view.View

fun View.setPaddingByDP(horizontalPadding: Int, verticalPadding: Int) {
    setPadding(getPxFromDp(horizontalPadding), getPxFromDp(verticalPadding)
        , getPxFromDp(horizontalPadding), getPxFromDp(verticalPadding))
}