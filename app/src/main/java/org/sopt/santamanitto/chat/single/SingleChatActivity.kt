package org.sopt.santamanitto.chat.single

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.santamanitto.chat.single.list.ChatAdapter
import org.sopt.santamanitto.databinding.ActivityChatOverviewBinding

@AndroidEntryPoint
class SingleChatActivity : AppCompatActivity() {
    private val viewModel by viewModels<SingleChatViewModel>()

    private lateinit var binding: ActivityChatOverviewBinding

    private var _adapter: ChatAdapter? = null
    val adapter
        get() = requireNotNull(_adapter)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatOverviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onDestroy() {
        super.onDestroy()
        _adapter = null
    }
}