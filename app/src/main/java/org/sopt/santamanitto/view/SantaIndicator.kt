package org.sopt.santamanitto.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import org.sopt.santamanitto.R
import org.sopt.santamanitto.databinding.SantaIndicatorBinding

class SantaIndicator @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding = SantaIndicatorBinding.inflate(
        LayoutInflater.from(context),
        this, true
    )

    private val circle = binding.imageviewSantaindicator

    private val number = binding.textviewSantaindicator

    var isCurrentIndicator: Boolean
        get() = circle.imageTintList == ContextCompat.getColorStateList(context, R.color.navy)
        set(value) {
            if (value) {
                circle.imageTintList = ContextCompat.getColorStateList(context, R.color.navy)
            } else {
                circle.imageTintList = ContextCompat.getColorStateList(context, R.color.gray_1)
            }
        }

    init {
        val typeArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.SantaIndicator,
            0, 0)

        //선택 여부
        if (typeArray.hasValue(R.styleable.SantaIndicator_isSelected)) {
            isCurrentIndicator = typeArray.getBoolean(R.styleable.SantaIndicator_isSelected, true)
        }

        //넘버
        if (typeArray.hasValue(R.styleable.SantaIndicator_number)) {
            number.text = typeArray.getInt(R.styleable.SantaIndicator_number, 1).toString()
        }

        typeArray.recycle()
    }
}