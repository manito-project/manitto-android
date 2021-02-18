package org.sopt.santamanitto.room.manittoroom.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.santamanitto.databinding.FragmentWaitingRoomBinding
import org.sopt.santamanitto.room.manittoroom.ManittoRoomViewModel

@AndroidEntryPoint
class WaitingRoomFragment: Fragment() {

    companion object {
        private const val TAG = "WaitingRoomFragment"
    }

    private lateinit var binding: FragmentWaitingRoomBinding

    private val manittoRoomViewModel: ManittoRoomViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWaitingRoomBinding.inflate(inflater, container, false)

        Log.d(TAG, "roomId : ${manittoRoomViewModel.roomId}")
        Log.d(TAG, "isMatched : ${manittoRoomViewModel.isMatched}")

        return binding.root
    }
}