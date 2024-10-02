package org.sopt.santamanitto.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import org.sopt.santamanitto.R

class SantaBottomButton : AppCompatButton {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr,
    ) {
        init(attrs)
    }

    private var isGrayButton = false

    private fun init(attrs: AttributeSet?) {
        initAttribute(attrs)
        setBackgroundResource(R.drawable.round_ractangle_background)
        if (isEnabled) {
            enable()
        }
        setTextSize(R.dimen.size_santabottombutton_text)
        setPaddingVerticalById(R.dimen.padding_santabottombutton_vertical)
        setTextColorById(R.color.white)
        includeFontPadding = false
        setRippleEffect(true)
        setBold(true)
        elevation = getDimen(R.dimen.elevation_shadow)
    }

    private fun initAttribute(attrs: AttributeSet?) {
        val typeArray =
            context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.SantaBottomButton,
                0,
                0,
            )

        if (typeArray.hasValue(R.styleable.SantaBottomButton_isGrayButton)) {
            isGrayButton = typeArray.getBoolean(R.styleable.SantaBottomButton_isGrayButton, false)
        }

        if (typeArray.hasValue(R.styleable.SantaBottomButton_darkShadow)) {
            val darkShadow = typeArray.getBoolean(R.styleable.SantaBottomButton_darkShadow, false)
            if (!darkShadow && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                outlineSpotShadowColor = getColor(R.color.shadow_gray)
            }
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                outlineSpotShadowColor = getColor(R.color.shadow_gray)
            }
        }
    }

    override fun setEnabled(enabled: Boolean) {
        setBackgroundResource(R.drawable.round_ractangle_background)

        if (enabled) {
            enable()
        } else {
            setBackgroundTint(R.color.light_gray)
            elevation = 0F
        }

        super.setEnabled(enabled)
    }

    private fun enable() {
        if (isGrayButton) {
            setBackgroundTint(R.color.dark_gray)
        } else {
            setBackgroundTint(R.color.red)
        }
        elevation = getDimen(R.dimen.elevation_shadow)
    }
}
