package org.sopt.santamanitto.room.manittoroom.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.sopt.santamanitto.databinding.FragmentMatchingBinding
import org.sopt.santamanitto.room.manittoroom.ManittoRoomViewModel

@AndroidEntryPoint
class MatchingFragment: Fragment() {

    companion object {
        private const val TAG = "MatchingFragment"
        private const val DELAY_MILLIS = 2000L
    }

    private lateinit var binding: FragmentMatchingBinding

    private val manittoRoomViewModel: ManittoRoomViewModel by activityViewModels()

    private var isDelayDone = false

    private var isInBackground = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMatchingBinding.inflate(inflater, container, false)

        Handler(Looper.getMainLooper()).postDelayed({
            isDelayDone = true
        }, DELAY_MILLIS)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        isInBackground = false
        navigateMissionFragment()
    }

    override fun onPause() {
        super.onPause()
        isInBackground = true
    }

    private fun navigateMissionFragment() {
        Log.d(TAG, "coroutine(): start")
        manittoRoomViewModel.viewModelScope.launch(Dispatchers.Default) {
            while ((!manittoRoomViewModel.isMatched || !isDelayDone) && !isInBackground) { }
            if (isInBackground) {
                return@launch
            }

            //Todo: 미션 프래그먼트로 이동
        }
    }
}