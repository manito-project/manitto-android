package org.sopt.santamanitto.setting

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.sopt.santamanitto.R
import org.sopt.santamanitto.databinding.ItemSettingBinding

class SettingListView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    companion object {
        private const val VIEW_HOLDER_RES = R.layout.item_setting
    }

    private val adapter = SettingListViewAdapter()

    init {
        itemAnimator = null
        setHasFixedSize(true)
        overScrollMode = OVER_SCROLL_NEVER
        layoutManager = LinearLayoutManager(context)
        setAdapter(adapter)
    }

    fun addSetting(title: String, listener: () -> Unit): SettingListView {
        adapter.addSetting(Setting(title, listener))
        return this
    }

    fun commit() {
        adapter.notifyDataSetChanged()
    }

    private class SettingListViewAdapter : RecyclerView.Adapter<SettingListViewHolder>() {

        private val settingList = mutableListOf<Setting>()

        fun addSetting(setting: Setting) {
            settingList.add(setting)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingListViewHolder {
            return SettingListViewHolder(parent)
        }

        override fun onBindViewHolder(holder: SettingListViewHolder, position: Int) {
            holder.onBind(settingList[position].title, settingList[position].listener)
        }

        override fun getItemCount(): Int {
            return settingList.size
        }
    }

    private class SettingListViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context)
            .inflate(VIEW_HOLDER_RES, parent, false)) {

        private val binding = ItemSettingBinding.bind(itemView)

        fun onBind(title: String, listener: () -> Unit) {
            binding.run {
                root.setOnClickListener { listener.invoke() }
                textviewSettingitemTitle.text = title
            }
        }
    }

    private data class Setting(
            val title: String,
            val listener: () -> Unit
    )
}