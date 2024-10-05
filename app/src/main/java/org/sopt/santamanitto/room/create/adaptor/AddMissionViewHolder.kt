package org.sopt.santamanitto.room.create.adaptor

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.RecyclerView
import org.sopt.santamanitto.databinding.ItemAddMissionBinding

class AddMissionViewHolder(
    private val callback: CreateMissionAdaptor.CreateMissionCallback,
    private val binding: ItemAddMissionBinding,
) : RecyclerView.ViewHolder(binding.root) {
    private var currentMission: String? = null

    fun bind() {
        binding.btnAddMission.setOnClickListener {
            if (!currentMission.isNullOrBlank()) {
                callback.onMissionInserted(currentMission!!)
                hideKeyboard()
            }
        }
    }

    fun updateText(mission: String?) {
        currentMission = mission
    }

    private fun hideKeyboard() {
        val imm =
            itemView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(itemView.windowToken, 0)
    }
}
