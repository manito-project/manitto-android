package org.sopt.santamanitto.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import org.sopt.santamanitto.R
import org.sopt.santamanitto.databinding.SantaCheckBoxBinding

class SantaCheckBox @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding = DataBindingUtil.inflate<SantaCheckBoxBinding>(
            LayoutInflater.from(context),
            R.layout.santa_check_box,
            this, true
    )

    private val checkBox = binding.checkboxSantacheckbox

    private val button = binding.buttonSantacheckbox

    var isChecked: Boolean
        get() = checkBox.isChecked
        set(value) {
            checkBox.isChecked = value
        }

    var text: String
        get() = button.text.toString()
        set(value) {
            button.text = value
        }

    init {
        val typeArray = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.SantaCheckBox,
                0, 0)

        if (typeArray.hasValue(R.styleable.SantaCheckBox_text)) {
            text = typeArray.getString(R.styleable.SantaCheckBox_text)?: ""
        }

        if (typeArray.hasValue(R.styleable.SantaCheckBox_isChecked)) {
            isChecked = typeArray.getBoolean(R.styleable.SantaCheckBox_isChecked, false)
        }

        typeArray.recycle()
    }

    override fun setOnClickListener(l: OnClickListener?) {
        button.setOnClickListener(l)
    }

}