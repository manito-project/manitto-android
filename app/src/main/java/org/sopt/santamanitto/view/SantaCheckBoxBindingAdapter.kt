package org.sopt.santamanitto.view

import android.widget.CheckBox
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import org.sopt.santamanitto.R

object SantaCheckBoxBindingAdapter {
    @BindingAdapter("setChecked")
    @JvmStatic
    fun setChecked(view: SantaCheckBox, isChecked: Boolean) {
        if (view.isChecked != isChecked) {
            view.isChecked = isChecked
        }
    }

    @InverseBindingAdapter(attribute = "setChecked", event = "checkedAttrChanged")
    @JvmStatic
    fun getChecked(view: SantaCheckBox): Boolean {
        return view.findViewById<CheckBox>(R.id.checkbox_santacheckbox).isChecked
    }

    @BindingAdapter("checkedAttrChanged")
    @JvmStatic
    fun setListener(view: SantaCheckBox, listener: InverseBindingListener) {
        val checkBox = view.findViewById<CheckBox>(R.id.checkbox_santacheckbox)
        checkBox.setOnCheckedChangeListener { _, _ ->
            listener.onChange()
        }
    }
}