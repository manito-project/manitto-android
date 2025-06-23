package org.sopt.santamanitto.chat.overview

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.santamanitto.databinding.ActivityChatOverviewBinding

@AndroidEntryPoint
class ChatOverviewActivity : AppCompatActivity() {
    private val viewModel by viewModels<ChatOverviewViewModel>()

    private lateinit var binding: ActivityChatOverviewBinding

    private var _adapter: ChatOverviewAdapter? = null
    val adapter
        get() = requireNotNull(_adapter)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatOverviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()
        // TODO: 배너광고 추가
    }

    private fun initAdapter() {
        _adapter = ChatOverviewAdapter(
            itemClick = ::initItemClickListener,
            itemLongClick = ::initItemLongClickListener,
        )
        binding.recyclerviewChatOverview.adapter = adapter
    }

    private fun initItemClickListener(roomId: String) {

    }

    private fun initItemLongClickListener(roomId: String) {
        // TODO: 삭제 기능 추가
    }

    override fun onDestroy() {
        super.onDestroy()

        _adapter = null
    }
}