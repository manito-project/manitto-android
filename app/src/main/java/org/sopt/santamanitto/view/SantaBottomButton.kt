package org.sopt.santamanitto.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import org.sopt.santamanitto.*
import org.sopt.santamanitto.view.santanumberpicker.SantaNumberPicker

class SantaBottomButton : AppCompatButton {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
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
        setRippleEffect(true)
        setBold(true)
        elevation = getDimen(R.dimen.elevation_shadow)
    }

    private fun initAttribute(attrs: AttributeSet?) {
        val typeArray = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.SantaBottomButton,
                0, 0)


        if (typeArray.hasValue(R.styleable.SantaBottomButton_isGrayButton)) {
            isGrayButton = typeArray.getBoolean(R.styleable.SantaBottomButton_isGrayButton, false)
        }
    }

    override fun setEnabled(enabled: Boolean) {
        setBackgroundResource(R.drawable.round_ractangle_background)

        if (enabled) {
            enable()
        } else {
            setBackgroundTint(R.color.gray_1)
            setTextColorById(R.color.gray_2)
            elevation = 0F
        }

        super.setEnabled(enabled)
    }

    private fun enable() {
        if (isGrayButton) {
            setBackgroundTint(R.color.gray_1)
            setTextColorById(R.color.gray_3)
        } else {
            setBackgroundTint(R.color.red)
            setTextColorById(R.color.white)
        }
        elevation = getDimen(R.dimen.elevation_shadow)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        height = getDimen(R.dimen.height_santabottombutton).toInt()
    }
}