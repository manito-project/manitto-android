package org.sopt.santamanitto.view

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import org.sopt.santamanitto.databinding.SantaNameInputBinding

class SantaNameInput @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : SantaCardView(context, attrs, defStyleAttr) {

    private val binding = SantaNameInputBinding.inflate(
        LayoutInflater.from(context),
        this
    )

    private val nameInput = binding.edittextNameinputName
//    private val alertMessage = binding.textviewNameinputAlert

    var hint: String?
        get() = nameInput.hint
        set(value) {
            nameInput.hint = value
        }

    fun observeTextChanges(onTextChanged: (String) -> Unit) {
        nameInput.addTextChangeListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                onTextChanged(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    fun setText(text: String) {
        if (nameInput.text.toString() != text) {
            nameInput.text = text
        }
    }

    fun getText(): String {
        return nameInput.text.toString()
    }

//    init {
//        alertMessage.setTextByIdWithArgs(R.string.santanameinput_alert, MAX_LENGTH)
//
//        addTextChangeListener(SantaEditText.SantaEditLimitLengthWatcher(nameInput, MAX_LENGTH) { isWarning ->
//            if (isWarning) {
//                alertMessage.setTextColorById(R.color.red)
//            } else {
//                alertMessage.setTextColorById(R.color.gray_3)
//            }
//        })
//
//    }

}