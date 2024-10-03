package org.sopt.santamanitto.room.create.adaptor

import androidx.recyclerview.widget.RecyclerView
import org.sopt.santamanitto.databinding.ItemAddMissionBinding

class AddMissionViewHolder(
    private val callback: CreateMissionAdaptor.CreateMissionCallback,
    private val binding: ItemAddMissionBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind() {}

    fun updateText(mission: String?) {
        binding.root.setOnClickListener {
            if (mission != null) {
                callback.onMissionInserted(mission)
            }
        }
    }
}
