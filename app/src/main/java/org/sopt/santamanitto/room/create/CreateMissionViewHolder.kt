package org.sopt.santamanitto.room.create

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sopt.santamanitto.R
import org.sopt.santamanitto.view.SantaEditText

class CreateMissionViewHolder(private val callback: CreateMissionAdaptor.CreateMissionCallback,
                              parent: ViewGroup): RecyclerView.ViewHolder(LayoutInflater.from(parent.context)
        .inflate(R.layout.item_create_mission, parent, false)) {
    private val editText = (itemView as SantaEditText)

    fun bind(mission: String?) {
        editText.run {
            text = mission
            if (mission == null) {
                setAddImage()
                isEditable = true
                setAddClickListener { text -> callback.onMissionInserted(text) }
            } else {
                setDeleteImage()
                setDeleteClickListener { text -> callback.onMissionDeleted(text) }
            }
        }
    }
}