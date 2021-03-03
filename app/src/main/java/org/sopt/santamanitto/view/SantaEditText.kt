package org.sopt.santamanitto.view

import android.content.Context
import android.text.InputFilter
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import org.sopt.santamanitto.R
import org.sopt.santamanitto.databinding.SantaEditTextBinding


class SantaEditText @JvmOverloads
constructor(
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

    private var rightButton = binding.santasmallbuttonSantaedittext

    private var buttonStyle = BUTTON_NONE

    private var hint: String? = null

    private var addListener: ((String) -> Unit)? = null

    private var deleteListener: ((String) -> Unit)? = null


    var text: String?
        get() = editText.text.toString()
        set(value) {
            editText.setText(value)
        }

    var isEditable = true
        set(value) {
            editText.isEnabled = value
            field = value
        }

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

        if (typeArray.hasValue(R.styleable.SantaEditText_isSingleLine)) {
            editText.isSingleLine = typeArray.getBoolean(R.styleable.SantaEditText_isSingleLine, true)
        }

        if (typeArray.hasValue(R.styleable.SantaEditText_isEditable)) {
            isEditable = typeArray.getBoolean(R.styleable.SantaEditText_isEditable, true)
        }

        if (typeArray.hasValue(R.styleable.SantaEditText_isSlim)) {
            val isSlim = typeArray.getBoolean(R.styleable.SantaEditText_isSlim, false)
            if (isSlim) {

            }
        }

        if (typeArray.hasValue(R.styleable.SantaEditText_maxLength)) {
            val maxLength = typeArray.getInt(R.styleable.SantaEditText_maxLength, -1)
            if (maxLength != -1) {
                setMaxLength(maxLength)
            }
        }

        typeArray.recycle()

        hint?.let { editText.hint = it }

        when (buttonStyle) {
            BUTTON_NONE -> rightButton.visibility = View.INVISIBLE
            BUTTON_ADD -> setAddImage()
            BUTTON_DELETE -> setDeleteImage()
        }

        rightButton.setOnClickListener {
            if (text.isNullOrBlank()) {
                return@setOnClickListener
            }
            if (buttonStyle == BUTTON_ADD) {
                addListener?.let { listener ->
                    setDeleteImage()
                    isEditable = false
                    listener(text!!)
                }
            } else {
                deleteListener?.let { listener -> listener(text!!) }
            }
        }
    }

    fun setAddClickListener(listener: (String) -> Unit) {
        addListener = listener
    }

    fun setDeleteClickListener(listener: (String) -> Unit) {
        deleteListener = listener
    }

    fun setAddImage() {
        rightButton.run {
            rightButton.visibility = View.VISIBLE
            setPlusImage()
        }
        buttonStyle = BUTTON_ADD
    }

    fun setDeleteImage() {
        rightButton.run {
            rightButton.visibility = View.VISIBLE
            setCancelImage()
        }
        buttonStyle = BUTTON_DELETE
    }

    fun setMaxLength(maxLength: Int) {
        editText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))
    }
}