package org.sopt.santamanitto.view

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import org.sopt.santamanitto.R
import org.sopt.santamanitto.databinding.SantaEditTextBinding
import java.util.*


class SantaEditText @JvmOverloads
constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), TextObservable {

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

    private val preview = binding.textviewSantaedittextPreview

    private var rightButton = binding.santasmallbuttonSantaedittext

    private var buttonStyle = BUTTON_NONE

    private var addListener: ((String) -> Unit)? = null

    var hint: String?
        get() = editText.hint.toString()
        set(value) {
            editText.hint = value
        }


    override var text: String?
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

        if (typeArray.hasValue(R.styleable.SantaEditText_maxLines)) {
            val maxLines = typeArray.getInt(R.styleable.SantaEditText_maxLines, -1)
            if (maxLines != -1) {
                setMaxLines(maxLines)
            }
        }

        typeArray.recycle()

        when (buttonStyle) {
            BUTTON_NONE -> rightButton.visibility = View.INVISIBLE
            BUTTON_ADD -> setAddImage()
            BUTTON_DELETE -> setDeleteImage()
        }

        rightButton.setOnClickListener {
            if (text.isNullOrBlank()) {
                return@setOnClickListener
            }
            addListener?.invoke(text!!)
        }
    }

    fun setButtonClickListener(listener: (String) -> Unit) {
        addListener = listener
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

    fun setMaxLines(maxLines: Int) {
        editText.maxLines = maxLines
    }

    fun setContentWidth(width: Int) {
        editText.layoutParams.width = width
        preview.layoutParams.width = width
    }

    override fun setOnKeyListener(l: OnKeyListener) {
        editText.setOnKeyListener(l)
    }

    override fun addTextChangeListener(textWatcher: TextWatcher) {
        editText.addTextChangedListener(textWatcher)
    }

    fun compress(isCompress: Boolean) {
        if (isCompress) {
            preview.text = text
            editText.visibility = View.GONE
        } else {
            preview.text = null
            editText.visibility = View.VISIBLE
        }
    }


    fun setSelection(index: Int) {
        editText.setSelection(index)
    }

    class SantaEditLimitLengthWatcher(
            private val nameInput: SantaEditText,
            private val maxLength: Int,
            private val onWarning: (isWarning: Boolean) -> Unit
    ) : TextWatcher {

        private var isWarning = false
        private var preText = ""
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            preText = s.toString()
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s.isNullOrEmpty() || s == preText) {
                return
            }
            if (s.length > maxLength) {
                nameInput.run {
                    text = s.substring(0, maxLength)
                    setSelection(maxLength)
                }
                if (!isWarning) {
                    isWarning = true
                    onWarning.invoke(isWarning)
                }
            } else {
                if (isWarning) {
                    isWarning = false
                    onWarning.invoke(isWarning)
                }
            }
        }

        override fun afterTextChanged(s: Editable?) {}
    }
}