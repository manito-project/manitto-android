package org.sopt.santamanitto.room.create.adaptor

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.RecyclerView
import org.sopt.santamanitto.databinding.ItemAddMissionBinding

class AddMissionViewHolder(
    private val callback: CreateMissionAdaptor.CreateMissionCallback,
    private val binding: ItemAddMissionBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind() {}

    fun updateText(mission: String?) {
        binding.btnAddMission.setOnClickListener {
            if (!mission.isNullOrBlank()) {
                callback.onMissionInserted(mission)
                hideKeyboard()
            }
        }
    }

    private fun hideKeyboard() {
        val imm =
            itemView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(itemView.windowToken, 0)
    }
}
