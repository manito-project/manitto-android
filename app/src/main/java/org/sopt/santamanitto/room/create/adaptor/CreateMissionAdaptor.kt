package org.sopt.santamanitto.room.create.adaptor

import android.content.Context
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.recyclerview.widget.RecyclerView
import org.sopt.santamanitto.room.create.CreateMissionViewHolder
import org.sopt.santamanitto.view.SantaEditText

open class CreateMissionAdaptor(private val createMissionCallback: CreateMissionCallback)
    : RecyclerView.Adapter<CreateMissionViewHolder>() {

    protected val missions = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreateMissionViewHolder {
        val editText = getEditText(parent.context)
        return CreateMissionViewHolder(createMissionCallback, editText)
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

    private fun getEditText(context: Context): SantaEditText {
        return SantaEditText(context).apply {
            layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
            isEnabled
        }
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