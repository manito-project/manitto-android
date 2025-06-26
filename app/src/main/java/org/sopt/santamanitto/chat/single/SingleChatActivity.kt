package org.sopt.santamanitto.chat.single

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.santamanitto.chat.single.list.ChatAdapter
import org.sopt.santamanitto.chat.single.list.TopPaddingDecoration
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

        binding.lifecycleOwner = this
        binding.vm = viewModel
        initViewWithIntent()
        initAdapter()
        initBackBtnClickListener()
        initSendBtnClickListener()
        getChatList()
    }

    private fun initViewWithIntent() {
        with(viewModel) {
            roomId = intent.getStringExtra(EXTRA_ROOM_ID).orEmpty()
            conversationId = intent.getStringExtra(EXTRA_CONVERSATION_ID).orEmpty()
            isMyManitto = intent.getBooleanExtra(EXTRA_IS_MY_MANITTO, false)
            opponentName = intent.getStringExtra(EXTRA_OPPONENT_NAME).orEmpty()
        }
        binding.textviewSingleChatTitle.text = intent.getStringExtra(EXTRA_ROOM_NAME).orEmpty()
    }

    private fun initAdapter() {
        _adapter = ChatAdapter()
        binding.recyclerviewSingleChat.adapter = adapter
        binding.recyclerviewSingleChat.addItemDecoration(TopPaddingDecoration(24))
    }

    private fun initBackBtnClickListener() {
        binding.buttonSingleChatBack.setOnClickListener {
            finish()
        }
    }

    private fun initSendBtnClickListener() {
        // TODO : 글자수 제한 & 오늘 쪽지 보냄 여부 확인 후 제한
        binding.buttonSingleChatInput.setOnClickListener {
            adapter.addItems(viewModel.postNewChat())
            binding.recyclerviewSingleChat.smoothScrollToPosition(adapter.itemCount + 1)
            viewModel.inputText.value = ""
        }
    }

    private fun getChatList() {
        adapter.submitList(viewModel.getChatList())
        binding.recyclerviewSingleChat.scrollToPosition(adapter.itemCount - 1)
    }

    override fun onDestroy() {
        super.onDestroy()
        _adapter = null
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val btnRect = Rect().apply { binding.buttonSingleChatInput.getGlobalVisibleRect(this) }
        if (btnRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
            return super.dispatchTouchEvent(ev)
        }
        if (ev.action == MotionEvent.ACTION_DOWN) {
            currentFocus?.let { v ->
                if (v is EditText) {
                    val outRect = Rect()
                    v.getGlobalVisibleRect(outRect)
                    if (!outRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                        v.clearFocus()
                        (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)
                            ?.hideSoftInputFromWindow(v.windowToken, 0)
                    }
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    companion object {
        private const val EXTRA_ROOM_NAME = "EXTRA_ROOM_NAME"
        private const val EXTRA_ROOM_ID = "EXTRA_ROOM_ID"
        private const val EXTRA_CONVERSATION_ID = "EXTRA_CONVERSATION_ID"
        private const val EXTRA_IS_MY_MANITTO = "EXTRA_IS_MY_MANITTO"
        private const val EXTRA_OPPONENT_NAME = "EXTRA_OPPONENT_NAME"

        @JvmStatic
        fun createIntent(
            context: Context,
            roomName: String,
            roomId: String,
            conversationId: String,
            isMyManitto: Boolean,
            opponentName: String
        ): Intent =
            Intent(context, SingleChatActivity::class.java).apply {
                putExtra(EXTRA_ROOM_NAME, roomName)
                putExtra(EXTRA_ROOM_ID, roomId)
                putExtra(EXTRA_CONVERSATION_ID, conversationId)
                putExtra(EXTRA_IS_MY_MANITTO, isMyManitto)
                putExtra(EXTRA_OPPONENT_NAME, opponentName)
            }
    }
}