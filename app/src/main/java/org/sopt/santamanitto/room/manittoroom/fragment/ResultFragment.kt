package org.sopt.santamanitto.room.manittoroom.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.santamanitto.R
import org.sopt.santamanitto.databinding.FragmentResultBinding
import org.sopt.santamanitto.room.manittoroom.ManittoRoomViewModel
import org.sopt.santamanitto.room.manittoroom.ResultAdapter
import javax.inject.Inject

@AndroidEntryPoint
class ResultFragment: Fragment() {

    private lateinit var binding: FragmentResultBinding

    private val manittoRoomViewModel: ManittoRoomViewModel by activityViewModels()

    @Inject
    lateinit var resultAdapter: ResultAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentResultBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = manittoRoomViewModel
            recyclerviewResult.adapter = resultAdapter
        }

        if (manittoRoomViewModel.members.value.isNullOrEmpty()) {
            manittoRoomViewModel.refreshManittoRoomInfo()
        }

        manittoRoomViewModel.members.observe(viewLifecycleOwner) {
            binding.textviewResultTitle.text = String.format(getString(R.string.result_title), it.size)
        }

        binding.santabottombuttonResult.setOnClickListener {
            requireActivity().finish()
        }

        return binding.root
    }
}