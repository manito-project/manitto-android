package org.sopt.santamanitto.view

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import org.sopt.santamanitto.R

open class SantaCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    init {
        radius = getDimen(R.dimen.radius_cardview)
        setCardBackgroundColor(getColor(R.color.white))
        cardElevation = getDimen(R.dimen.elevation_shadow)
        setRippleEffect(true)

        val typeArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.SantaCardView,
            0, 0
        )

        //버튼 문구
        if (typeArray.hasValue(R.styleable.SantaCardView_moreRound)) {
            val moreRound = typeArray.getBoolean(R.styleable.SantaCardView_moreRound, false)
            if (moreRound) {
                radius = getDimen(R.dimen.radius_cardview_more)
            }
        }
    }
}