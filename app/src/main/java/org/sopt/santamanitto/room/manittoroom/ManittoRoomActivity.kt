package org.sopt.santamanitto.room.manittoroom

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.santamanitto.R
import org.sopt.santamanitto.databinding.ActivityManittoRoomBinding

@AndroidEntryPoint
class ManittoRoomActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "ManittoRoomActivity"
        const val EXTRA_ROOM_ID = "roomId"
        const val EXTRA_IS_MATCHED = "isMatched"
        const val EXTRA_IS_FINISHED = "isFinished"
    }

    private val manittoRoomViewModel: ManittoRoomViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivityManittoRoomBinding>(this, R.layout.activity_manitto_room)

        val roomId = intent.getIntExtra(EXTRA_ROOM_ID, -1)
        if (roomId == -1) {
            Log.e(TAG, "Wrong access.")
            finish()
        } else {
            manittoRoomViewModel.roomId = roomId
        }
        val isMatched = intent.getBooleanExtra(EXTRA_IS_MATCHED, false)
        manittoRoomViewModel.isMatched = isMatched
        val isFinished = intent.getBooleanExtra(EXTRA_IS_FINISHED, false)
        manittoRoomViewModel.isFinished = isFinished
    }
}