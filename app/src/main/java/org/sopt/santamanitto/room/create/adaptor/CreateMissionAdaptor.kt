package org.sopt.santamanitto.room.create.adaptor

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sopt.santamanitto.room.create.CreateMissionViewHolder

open class CreateMissionAdaptor(private val createMissionCallback: CreateMissionCallback)
    : RecyclerView.Adapter<CreateMissionViewHolder>() {

    protected val missions = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreateMissionViewHolder {
        return CreateMissionViewHolder(createMissionCallback, parent)
    }

    override fun onBindViewHolder(holder: CreateMissionViewHolder, position: Int) {
        if (position == missions.size) {
            holder.bind(null)
        } else {
            holder.bind(missions[position])
        }
    }

    override fun getItemCount(): Int {
        return missions.size + 1
    }

    private fun addMission(mission: String) {
        missions.add(mission)
        notifyItemInserted(missions.size)
    }

    fun setList(data: List<String>) {
        missions.clear()
        missions.addAll(data)
        notifyDataSetChanged()
    }

    fun clear() {
        val size = missions.size
        missions.clear()
        notifyItemRangeRemoved(0, size)
    }

    fun addAll(data: List<String>?) {
        if (data == null) {
            return
        }

        val startIndex = missions.size
        missions.addAll(startIndex, data)
        notifyItemRangeInserted(startIndex, data.size)
    }

    interface CreateMissionCallback {
        fun onMissionInserted(mission: String)

        fun onMissionDeleted(mission: String)
    }
}