package org.sopt.santamanitto.view

import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import org.sopt.santamanitto.R

object SantaNameInputBindingAdapter {
    @BindingAdapter("setText")
    @JvmStatic
    fun setText(view: SantaNameInput, text: String?) {
        if (view.text != text) {
            view.text = text
        }
    }

    @InverseBindingAdapter(attribute = "setText", event = "textAttrChanged")
    @JvmStatic
    fun getText(view: SantaNameInput) : String? {
        return view.text
    }

    @BindingAdapter("textAttrChanged")
    @JvmStatic
    fun setListener(view: SantaNameInput, listener: InverseBindingListener) {
        view.addTextChangeListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                listener.onChange()
            }

            override fun afterTextChanged(s: Editable?) { }
        })
    }
}