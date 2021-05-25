package org.sopt.santamanitto.view

import android.content.Context
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import org.sopt.santamanitto.R
import org.sopt.santamanitto.databinding.SantaNameInputBinding

class SantaNameInput @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : SantaCardView(context, attrs, defStyleAttr), TextObservable {

    companion object {
        private const val MAX_LENGTH = 10
    }

    private val binding = SantaNameInputBinding.inflate(
            LayoutInflater.from(context),
            this
    )

    private val nameInput = binding.edittextNameinputName
    private val alertMessage = binding.textviewNameinputAlert

    override var text: String?
        get() = nameInput.text
        set(value) {
            nameInput.text = value
        }

    var hint: String?
        get() = nameInput.hint
        set(value) {
            nameInput.hint = value
        }

    init {
        alertMessage.setTextByIdWithArgs(R.string.santanameinput_alert, MAX_LENGTH)

        addTextChangeListener(SantaEditText.SantaEditLimitLengthWatcher(nameInput, MAX_LENGTH) { isWarning ->
            if (isWarning) {
                alertMessage.setTextColorById(R.color.red)
            } else {
                alertMessage.setTextColorById(R.color.gray_3)
            }
        })

    }

    override fun addTextChangeListener(textWatcher: TextWatcher) {
        nameInput.addTextChangeListener(textWatcher)
    }
}