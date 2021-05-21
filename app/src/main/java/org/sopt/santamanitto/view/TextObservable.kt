package org.sopt.santamanitto.view

import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.InverseBindingListener

interface TextObservable {

    var text: String?

    fun addTextChangeListener(textWatcher: TextWatcher)

    fun textAttrChanged(view: TextObservable, listener: InverseBindingListener)
}