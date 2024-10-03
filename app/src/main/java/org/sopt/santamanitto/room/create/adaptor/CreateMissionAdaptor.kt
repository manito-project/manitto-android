package org.sopt.santamanitto.room.create.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.sopt.santamanitto.databinding.ItemAddMissionBinding
import org.sopt.santamanitto.databinding.ItemCreateMissionBinding

open class CreateMissionAdaptor(
    private val createMissionCallback: CreateMissionCallback,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    protected val missions = mutableListOf<String>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        if (viewType == VIEW_TYPE_ITEM) {
            val binding =
                ItemCreateMissionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            CreateMissionViewHolder(createMissionCallback, binding)
        } else {
            val binding =
                ItemAddMissionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            AddMissionViewHolder(binding)
        }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        if (holder is CreateMissionViewHolder) {
            if (position == missions.size) {
                holder.bind(null)
            } else {
                holder.bind(missions[position])
            }
        } else if (holder is AddMissionViewHolder) {
            holder.bind()
        }
    }

    override fun getItemCount(): Int = missions.size + 2

    override fun getItemViewType(position: Int): Int = if (position == missions.size + 1) VIEW_TYPE_FOOTER else VIEW_TYPE_ITEM

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

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_FOOTER = 1
    }
}
