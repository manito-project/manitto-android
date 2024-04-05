package org.sopt.santamanitto.room.create.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import org.sopt.santamanitto.R
import org.sopt.santamanitto.databinding.FragmentCreateConfirmBinding
import org.sopt.santamanitto.room.create.adaptor.CreateConfirmAdaptor
import org.sopt.santamanitto.room.create.adaptor.CreateMissionAdaptor
import org.sopt.santamanitto.room.create.data.ExpirationLiveData
import org.sopt.santamanitto.room.create.network.CreateRoomModel
import org.sopt.santamanitto.room.create.setExpirationDiff
import org.sopt.santamanitto.room.create.setExpirationPreview
import org.sopt.santamanitto.room.create.viewmodel.CreateRoomAndMissionViewModel
import org.sopt.santamanitto.room.manittoroom.ManittoRoomActivity
import org.sopt.santamanitto.room.manittoroom.ManittoRoomActivity.Companion.EXTRA_ROOM_ID
import org.sopt.santamanitto.room.manittoroom.fragment.WaitingRoomFragment
import org.sopt.santamanitto.room.manittoroom.fragment.WaitingRoomFragment.Companion.INVITATION_CODE_LABEL
import org.sopt.santamanitto.util.ClipBoardUtil
import org.sopt.santamanitto.view.dialog.RoundDialogBuilder

class CreateConfirmFragment : Fragment(), CreateMissionAdaptor.CreateMissionCallback {

    private lateinit var binding: FragmentCreateConfirmBinding

    private val viewModel: CreateRoomAndMissionViewModel by activityViewModels()

    private val createConfirmAdapter = CreateConfirmAdaptor(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateConfirmBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
            recyclerviewCreateconfirm.adapter = createConfirmAdapter
        }

        initRecyclerView()

        refreshUI(viewModel.expirationLiveData)

        subscribeUI()

        setOnClickListener()

        return binding.root
    }

    private fun setOnClickListener() {
        binding.run {
            santabottombuttonCreatemconfirm.setOnClickListener {
                viewModel.createRoom(::showInvitationCodeDialog)
            }
            santabackgroundCreateconfirm.setOnBackKeyClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun showInvitationCodeDialog(createRoom: CreateRoomModel) {
        RoundDialogBuilder().setContentText(getString(R.string.createconfirm_done_dialog))
            .setInvitationCode(createRoom.invitationCode) {
                ClipBoardUtil.copy(
                    requireContext(),
                    INVITATION_CODE_LABEL,
                    createRoom.invitationCode
                )
                startMatchingRoomActivity(createRoom.id)
            }.enableCancel(false).build().show(parentFragmentManager, "invitation_code_dialog")
    }

    private fun startMatchingRoomActivity(createdRoomId: Int) {
        requireActivity().run {
            startActivity(Intent(this, ManittoRoomActivity::class.java).apply {
                putExtra(EXTRA_ROOM_ID, createdRoomId)
            })
            finish()
        }
    }

    private fun initRecyclerView() {
        setRecyclerViewHeight()
        createConfirmAdapter.setList(viewModel.missions.getMissions())
    }

    private fun setRecyclerViewHeight() {
        binding.recyclerviewCreateconfirm.run {
            layoutParams = layoutParams.apply {
                height = viewModel.heightOfRecyclerView
            }
        }
    }

    private fun subscribeUI() {
        viewModel.expirationLiveData.observe(viewLifecycleOwner, ::refreshUI)
        viewModel.missions.observe(viewLifecycleOwner) {
            createConfirmAdapter.setList(it.getMissions())
        }
    }

    private fun refreshUI(expiration: ExpirationLiveData) {
        binding.run {
            setExpirationDiff(textviewCreateconfirmExpirationdiff, expiration)
            setExpirationPreview(textviewCreateconfirmExpirationpreview, expiration)
        }
    }

    override fun onMissionInserted(mission: String) {
        viewModel.addMission(mission)
    }

    override fun onMissionDeleted(mission: String) {
        viewModel.deleteMission(mission)
    }
}