package org.sopt.santamanitto.room.manittoroom.fragment

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.sopt.santamanitto.databinding.FragmentMatchingBinding
import org.sopt.santamanitto.room.manittoroom.ManittoRoomViewModel

@AndroidEntryPoint
class MatchingFragment: Fragment() {

    companion object {
        private const val DELAY_MILLIS = 2000L
    }

    private lateinit var binding: FragmentMatchingBinding

    private val viewModel: ManittoRoomViewModel by activityViewModels()

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

        setAnimation()

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

    private fun setAnimation() {
        ObjectAnimator.ofFloat(binding.imageviewMatchingRoulette, View.ROTATION, -360f, 0f)
            .apply {
                duration = 500
                repeatCount = ValueAnimator.INFINITE
                repeatMode = ValueAnimator.RESTART
                interpolator = LinearInterpolator()
            }
            .start()
    }

    private fun navigateMissionFragment() {
        viewModel.viewModelScope.launch(Dispatchers.Default) {
            while ((!viewModel.isMatched || !isDelayDone) && !isInBackground) { }
            if (isInBackground) {
                return@launch
            }

            findNavController().navigate(MatchingFragmentDirections.actionMatchingFragmentToMatchedFragment())
        }
    }
}