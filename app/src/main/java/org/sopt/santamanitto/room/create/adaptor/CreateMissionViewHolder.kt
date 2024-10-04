package org.sopt.santamanitto.room.create.adaptor

import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import androidx.recyclerview.widget.RecyclerView
import org.sopt.santamanitto.databinding.ItemCreateMissionBinding

class CreateMissionViewHolder(
    private val callback: CreateMissionAdaptor.CreateMissionCallback,
    private val binding: ItemCreateMissionBinding,
    private val onTextChanged: (String?) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        with(binding) {
            etCreateMission.setOnKeyListener { _, keyCode, _ ->
                return@setOnKeyListener keyCode == KeyEvent.KEYCODE_ENTER
            }
            etCreateMission.addTextChangeListener(
                object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int,
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int,
                    ) {
                    }

                    override fun afterTextChanged(editable: Editable?) {
                        if (editable?.isNotBlank() == true) {
                            etCreateMission.setDeleteImage()
                        } else {
                            etCreateMission.removeButton()
                        }
                        if (bindingAdapterPosition == bindingAdapter?.itemCount?.minus(2)) {
                            onTextChanged(editable?.toString())
                        }
                    }
                },
            )
        }
    }

    fun bind(mission: String?) {
        binding.etCreateMission.run {
            text = mission
            setButtonClickListener { text -> callback.onMissionDeleted(text) }
            if (mission == null) {
                compress(false)
                isEditable = true
            } else {
                compress(true)
            }
        }
    }
}
