package org.sopt.santamanitto.room.create.adaptor

import android.view.KeyEvent
import androidx.recyclerview.widget.RecyclerView
import org.sopt.santamanitto.databinding.ItemCreateMissionBinding
import org.sopt.santamanitto.view.SantaEditText

class CreateMissionViewHolder(
    private val callback: CreateMissionAdaptor.CreateMissionCallback,
    private val binding: ItemCreateMissionBinding,
) : RecyclerView.ViewHolder(binding.root) {
    private val editText = (itemView as SantaEditText)

    init {
        editText.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }

    fun bind(mission: String?) {
        editText.run {
            text = mission
            setButtonClickListener { text -> callback.onMissionDeleted(text) }
            setDeleteImage()
            if (mission == null) {
                compress(false)
                isEditable = true
            } else {
                compress(true)
            }
        }
    }
}
