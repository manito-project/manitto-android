package org.sopt.santamanitto.room.join

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.santamanitto.R
import org.sopt.santamanitto.databinding.FragmentJoinRoomBinding
import org.sopt.santamanitto.dialog.RoundDialogBuilder
import org.sopt.santamanitto.room.join.network.JoinRoomResponse

@AndroidEntryPoint
class JoinRoomFragment: Fragment() {

    companion object {
        private const val TAG = "JoinRoomFragment"
    }

    private lateinit var binding: FragmentJoinRoomBinding

    private val joinRoomViewModel: JoinRoomViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentJoinRoomBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = joinRoomViewModel
        }

        subscribeUI()

        setOnClickListener()

        return binding.root
    }

    private fun subscribeUI() {
        joinRoomViewModel.run {
            isAlreadyMatchedRoom.observe(
                viewLifecycleOwner, this@JoinRoomFragment::showAlreadyMatchedDialog)
            isDuplicatedMember.observe(
                viewLifecycleOwner, this@JoinRoomFragment::showDuplicatedMemberDialog)
            isWrongInvitationCode.observe(
                viewLifecycleOwner, this@JoinRoomFragment::showWrongInvitationCodeDialog)
        }
    }

    private fun setOnClickListener() {
        binding.run {
            santabackgroundJoinroom.setOnBackKeyClickListener {
                findNavController().navigateUp()
            }

            santabottomButtonJoinroom.setOnClickListener {
                joinRoomViewModel.joinRoom(this@JoinRoomFragment::startManittoRoomActivity)
            }
        }
    }

    private fun startManittoRoomActivity(joinRoomResponse: JoinRoomResponse) {
        requireActivity().run {
            //Todo: 마니또 방 액티비티로 이동
        }
    }

    private fun showAlreadyMatchedDialog(isShow: Boolean) {
        if (isShow) {
            showErrorDialog(getString(R.string.joinroom_dialog_already_matched))
        }
    }

    private fun showDuplicatedMemberDialog(isShow: Boolean) {
        if (isShow) {
            showErrorDialog(getString(R.string.joinroom_dialog_duplicated_member))
        }
    }

    private fun showWrongInvitationCodeDialog(isShow: Boolean) {
        if (isShow) {
            showErrorDialog(getString(R.string.joinroom_dialog_wrong_invitation_code))
        }
    }

    private fun showErrorDialog(message: String) {
        Log.d(TAG, "showErrorDialog: $message")
        RoundDialogBuilder()
            .setContentText(message)
            .addHorizontalButton(getString(R.string.dialog_confirm))
            .build()
            .show(parentFragmentManager, "error_dialog")
    }
}