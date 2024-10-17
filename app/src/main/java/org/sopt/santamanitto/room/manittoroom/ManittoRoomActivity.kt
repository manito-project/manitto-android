package org.sopt.santamanitto.room.manittoroom

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.santamanitto.R
import org.sopt.santamanitto.databinding.ActivityManittoRoomBinding
import timber.log.Timber

@AndroidEntryPoint
class ManittoRoomActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "ManittoRoomActivity"
        const val EXTRA_ROOM_ID = "roomId"
        const val EXTRA_IS_MATCHED = "isMatched"
        const val EXTRA_IS_FINISHED = "isFinished"
    }

    private val viewModel: ManittoRoomViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivityManittoRoomBinding>(this, R.layout.activity_manitto_room)

        val roomId = intent.getStringExtra(EXTRA_ROOM_ID) ?: ""
        if (roomId.isBlank()) {
            Timber.tag(TAG).e("Wrong access.")
            finish()
        } else {
            viewModel.roomId = roomId
        }
        val isMatched = intent.getBooleanExtra(EXTRA_IS_MATCHED, false)
        viewModel.isMatched = isMatched
        val isFinished = intent.getBooleanExtra(EXTRA_IS_FINISHED, false)
        viewModel.isFinished = isFinished
    }
}