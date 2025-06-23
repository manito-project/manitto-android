package org.sopt.santamanitto.chat.overview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.santamanitto.R
import org.sopt.santamanitto.databinding.ActivityChatOverviewBinding

@AndroidEntryPoint
class ChatOverviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivityChatOverviewBinding>(this, R.layout.activity_chat_overview)
    }
}