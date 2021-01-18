package org.sopt.santamanitto.view

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import org.sopt.santamanitto.R

class SantaCardView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    init {
        radius = getDimen(R.dimen.radius_cardview)
        setCardBackgroundColor(getColor(R.color.white))
        cardElevation = getDimen(R.dimen.elevation_shadow)
        setRippleEffect(true)
    }
}