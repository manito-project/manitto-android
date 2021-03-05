package org.sopt.santamanitto.view.recyclerview

import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import org.sopt.santamanitto.R
import org.sopt.santamanitto.view.SantaEditText

object SantaEditTextBindingAdapter {
    @BindingAdapter("setText")
    @JvmStatic
    fun setText(view: SantaEditText, text: String?) {
        if (view.text != text) {
            view.text = text
        }
    }

    @InverseBindingAdapter(attribute = "setText", event = "textAttrChanged")
    @JvmStatic
    fun getText(view: SantaEditText) : String? {
        return view.findViewById<AppCompatEditText>(R.id.edittext_santaedittext).text?.toString()
    }

    @BindingAdapter("textAttrChanged")
    @JvmStatic
    fun setListener(view: SantaEditText, listener: InverseBindingListener) {
        val input = view.findViewById<AppCompatEditText>(R.id.edittext_santaedittext)
        input.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                listener.onChange()
            }

            override fun afterTextChanged(s: Editable?) { }
        })
    }
}