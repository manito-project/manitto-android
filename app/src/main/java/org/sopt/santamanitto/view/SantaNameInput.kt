package org.sopt.santamanitto.view

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.InverseBindingListener
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
    private var preText: String? = null
    private var isWarning = false

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

        addTextChangeListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                preText = s.toString()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty() || s == preText) {
                    return
                }
                if (s.length > MAX_LENGTH) {
                    nameInput.run {
                        text = s.substring(0, MAX_LENGTH)
                        setSelection(MAX_LENGTH)
                    }
                    if (!isWarning) {
                        isWarning = true
                        alertMessage.setTextColorById(R.color.red)
                    }
                } else {
                    if (isWarning) {
                        isWarning = false
                        alertMessage.setTextColorById(R.color.gray_3)
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) { }
        })
    }

    override fun textAttrChanged(view: TextObservable, listener: InverseBindingListener)  {
        view.addTextChangeListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                listener.onChange()
            }

            override fun afterTextChanged(s: Editable?) { }
        })
    }


    override fun addTextChangeListener(textWatcher: TextWatcher) {
        nameInput.addTextChangeListener(textWatcher)
    }
}