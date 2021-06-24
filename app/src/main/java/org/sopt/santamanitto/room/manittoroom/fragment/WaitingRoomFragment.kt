package org.sopt.santamanitto.room.manittoroom.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.santamanitto.R
import org.sopt.santamanitto.base.BaseFragment
import org.sopt.santamanitto.databinding.FragmentWaitingRoomBinding
import org.sopt.santamanitto.room.manittoroom.ManittoRoomViewModel
import org.sopt.santamanitto.room.manittoroom.MemberAdapter
import org.sopt.santamanitto.util.ClipBoardUtil


@AndroidEntryPoint
class WaitingRoomFragment : BaseFragment<FragmentWaitingRoomBinding>(R.layout.fragment_waiting_room, false){

    companion object {
        private const val TAG = "WaitingRoomFragment"
        const val INVITATION_CODE_LABEL = "InvitationCode"
    }

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
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            viewModel = manittoRoomViewModel
            recyclerviewWaitingroom.adapter = memberAdapter
        }
        setOnClickListener()
        manittoRoomViewModel.isExpired.observe(viewLifecycleOwner) {
            if (it) {
                //Todo: 매칭 전에 기간이 만료된 경우 처리
                Log.e(TAG, "Expired! ${manittoRoomViewModel.expiration.value}")
                requireActivity().finish()
            }
        }
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
            santabottombuttonWaitingroomModify.setOnClickListener {
                navigateModifyFragment()
            }
            santabottombuttonWaitingroomMatch.setOnClickListener {
                manittoRoomViewModel.match()
                navigateMatchingFragment()
            }
        }
    }

    private fun navigateModifyFragment() {
        findNavController().navigate(WaitingRoomFragmentDirections
                        .actionWaitingRoomFragmentToCreateRoomFragmentModify(manittoRoomViewModel.roomId))
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