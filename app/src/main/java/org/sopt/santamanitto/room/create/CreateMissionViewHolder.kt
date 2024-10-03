package org.sopt.santamanitto.room.create

import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sopt.santamanitto.R
import org.sopt.santamanitto.room.create.adaptor.CreateMissionAdaptor
import org.sopt.santamanitto.view.SantaEditText

class CreateMissionViewHolder(
    private val callback: CreateMissionAdaptor.CreateMissionCallback,
    parent: ViewGroup,
) : RecyclerView.ViewHolder(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_create_mission, parent, false),
    ) {
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
