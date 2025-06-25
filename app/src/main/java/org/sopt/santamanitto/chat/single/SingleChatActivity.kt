package org.sopt.santamanitto.chat.single

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.santamanitto.chat.single.list.ChatAdapter
import org.sopt.santamanitto.databinding.ActivitySingleChatBinding

@AndroidEntryPoint
class SingleChatActivity : AppCompatActivity() {
    private val viewModel by viewModels<SingleChatViewModel>()

    private lateinit var binding: ActivitySingleChatBinding

    private var _adapter: ChatAdapter? = null
    val adapter
        get() = requireNotNull(_adapter)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBackBtnClickListener()
    }

    private fun initBackBtnClickListener() {
        binding.buttonSingleChatBack.setOnClickListener {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _adapter = null
    }
}