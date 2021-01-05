package org.sopt.santamanitto.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import org.sopt.santamanitto.R
import org.sopt.santamanitto.databinding.SantaEditTextBinding

class SantaEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    companion object {
        private const val BUTTON_NONE = 0
        private const val BUTTON_ADD = 1
        private const val BUTTON_DELETE = 2
    }

    private var binding: SantaEditTextBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.santa_edit_text,
        this,
        true
    )

    private val editText = binding.edittextSantaedittext

    private var rightButton = binding.imagebuttonSantaedittext


    private var buttonStyle = BUTTON_NONE

    private var hint: String? = null

    private var addListener: (() -> Unit)? = null

    private var deleteListener: (() -> Unit)? = null

    val text: String
        get() = editText.text.toString()

    init {
        val typeArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.SantaEditText,
            0, 0)

        if (typeArray.hasValue(R.styleable.SantaEditText_rightButton)) {
            buttonStyle = typeArray.getInt(R.styleable.SantaEditText_rightButton, BUTTON_NONE)
        }

        if (typeArray.hasValue(R.styleable.SantaEditText_hint)) {
            hint = typeArray.getString(R.styleable.SantaEditText_hint)
        }

        typeArray.recycle()

        hint?.let { editText.hint = it }

        when (buttonStyle) {
            BUTTON_NONE -> rightButton.visibility = View.GONE
            BUTTON_ADD -> setAddImage()
            //Todo: Please change the drawable to delete image
            BUTTON_DELETE -> setDeleteImage()
        }

        rightButton.setOnClickListener {
            if (buttonStyle == BUTTON_ADD) {
                setDeleteImage()
                editText.isEnabled = false
                addListener?.let { listener -> listener() }
            } else {
                deleteListener?.let { listener -> listener() }
            }
        }
    }

    fun setAddClickListener(listener: () -> Unit) {
        addListener = listener
    }

    fun setDeleteClickListener(listener: () -> Unit) {
        deleteListener = listener
    }

    private fun setAddImage() {
        rightButton.setImageResource(R.drawable.bottonnn)
        buttonStyle = BUTTON_ADD
    }

    private fun setDeleteImage() {
        rightButton.setImageResource(R.drawable.circle_add_button)
        buttonStyle = BUTTON_DELETE
    }
}