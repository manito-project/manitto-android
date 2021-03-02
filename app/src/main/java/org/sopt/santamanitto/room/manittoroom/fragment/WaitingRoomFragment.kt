package org.sopt.santamanitto.room.manittoroom.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.santamanitto.databinding.FragmentWaitingRoomBinding
import org.sopt.santamanitto.room.manittoroom.ManittoRoomViewModel
import org.sopt.santamanitto.room.manittoroom.MemberAdapter
import org.sopt.santamanitto.util.ClipBoardUtil


@AndroidEntryPoint
class WaitingRoomFragment : Fragment() {

    companion object {
        private const val TAG = "WaitingRoomFragment"
        const val INVITATION_CODE_LABEL = "InvitationCode"
    }

    private lateinit var binding: FragmentWaitingRoomBinding

    private val manittoRoomViewModel: ManittoRoomViewModel by activityViewModels()

    private val memberAdapter = MemberAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (manittoRoomViewModel.isMatched) {
            if (manittoRoomViewModel.isFinished) {
                navigateFinishFragment()
            } else {
                navigateMatchingFragment()
            }
        }

        binding = FragmentWaitingRoomBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = manittoRoomViewModel
            recyclerviewWaitingroom.adapter = memberAdapter
        }

        setOnClickListener()

        return binding.root
    }

    private fun setOnClickListener() {
        binding.run {
            santabackgroundWaitingroom.setOnBackKeyClickListener {
                requireActivity().finish()
            }
            textviewWaitingroomInvitationcode.setOnClickListener {
                ClipBoardUtil.copy(
                    requireContext(),
                    INVITATION_CODE_LABEL, manittoRoomViewModel.invitationCode
                )
                Snackbar.make(
                    binding.root,
                    getString(org.sopt.santamanitto.R.string.waitingroom_snackbar_invitation_code),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
            santabottombuttonWaitingroom.setOnClickListener {
                manittoRoomViewModel.match()
                navigateMatchingFragment()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        manittoRoomViewModel.refreshManittoRoomInfo()
    }

    private fun navigateFinishFragment() {
        findNavController()
            .navigate(WaitingRoomFragmentDirections.actionWaitingRoomFragmentToFinishFragment())
    }

    private fun navigateMatchingFragment() {
        findNavController()
            .navigate(WaitingRoomFragmentDirections.actionWaitingRoomFragmentToMatchingFragment())
    }
}