package org.sopt.santamanitto.room.join

import android.content.Intent
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
import org.sopt.santamanitto.base.BaseFragment
import org.sopt.santamanitto.databinding.FragmentJoinRoomBinding
import org.sopt.santamanitto.dialog.RoundDialogBuilder
import org.sopt.santamanitto.room.join.network.JoinRoomResponse
import org.sopt.santamanitto.room.manittoroom.ManittoRoomActivity

@AndroidEntryPoint
class JoinRoomFragment: BaseFragment<FragmentJoinRoomBinding>(R.layout.fragment_join_room, false) {

    companion object {
        private const val TAG = "JoinRoomFragment"
    }

    private val joinRoomViewModel: JoinRoomViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewModel = joinRoomViewModel
        subscribeUI()
        setOnClickListener()
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
            startActivity(Intent(this, ManittoRoomActivity::class.java).apply {
                putExtra(ManittoRoomActivity.EXTRA_ROOM_ID, joinRoomResponse.room.id)
            })
        }
        findNavController().navigateUp()
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