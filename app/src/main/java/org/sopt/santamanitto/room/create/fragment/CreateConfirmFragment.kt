package org.sopt.santamanitto.room.create.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import org.sopt.santamanitto.R
import org.sopt.santamanitto.analytics.AmplitudeManager
import org.sopt.santamanitto.analytics.EventType
import org.sopt.santamanitto.databinding.FragmentCreateConfirmBinding
import org.sopt.santamanitto.room.create.adaptor.CreateConfirmAdaptor
import org.sopt.santamanitto.room.create.adaptor.CreateMissionAdaptor
import org.sopt.santamanitto.room.create.data.ExpirationLiveData
import org.sopt.santamanitto.room.create.network.CreateRoomModel
import org.sopt.santamanitto.room.create.setExpirationDiff
import org.sopt.santamanitto.room.create.setExpirationPreview
import org.sopt.santamanitto.room.create.viewmodel.CreateRoomAndMissionViewModel
import org.sopt.santamanitto.room.manittoroom.fragment.WaitingRoomFragment.Companion.INVITATION_CODE_LABEL
import org.sopt.santamanitto.util.ClipBoardUtil
import org.sopt.santamanitto.view.dialog.RoundDialogBuilder

class CreateConfirmFragment :
    Fragment(),
    CreateMissionAdaptor.CreateMissionCallback {
    private lateinit var binding: FragmentCreateConfirmBinding

    private val viewModel: CreateRoomAndMissionViewModel by activityViewModels()

    private val createConfirmAdapter = CreateConfirmAdaptor(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCreateConfirmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AmplitudeManager.trackEvent("make_complete", EventType.PAGE)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
            recyclerviewCreateconfirm.adapter = createConfirmAdapter
        }
        initRecyclerView()
        refreshUI(viewModel.expirationLiveData)
        subscribeUI()
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.run {
            santabottombuttonCreatemconfirm.setOnClickListener {
                AmplitudeManager.trackEvent("make_complete_btn", EventType.BUTTON)
                viewModel.createRoom(::showInvitationCodeDialog)
            }
            santabackgroundCreateconfirm.setOnBackKeyClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun showInvitationCodeDialog(createRoom: CreateRoomModel) {
        AmplitudeManager.trackEvent("make_code_complete_popup", EventType.MODAL)
        if (context != null) {
            RoundDialogBuilder()
                .setContentText(getString(R.string.createconfirm_done_dialog))
                .setInvitationCode(createRoom.invitationCode) {
                    AmplitudeManager.trackEvent("make_code_copy_btn", EventType.BUTTON)
                    ClipBoardUtil.copy(
                        requireContext(),
                        INVITATION_CODE_LABEL,
                        createRoom.invitationCode,
                    )
                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
                        Toast
                            .makeText(
                                requireContext(),
                                getString(R.string.waitingroom_snackbar_invitation_code),
                                Toast.LENGTH_SHORT,
                            ).show()
                    }
                    requireActivity().finish()
                }.enableCancel(false)
                .build()
                .show(parentFragmentManager, "invitation_code_dialog")
        } else {
            requireActivity().finish()
        }
    }

    private fun initRecyclerView() {
        setRecyclerViewHeight()
        createConfirmAdapter.setList(viewModel.missions.getMissions())
    }

    private fun setRecyclerViewHeight() {
        binding.recyclerviewCreateconfirm.run {
            layoutParams =
                layoutParams.apply {
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
        AmplitudeManager.trackEvent("make_complete_mission_minus_btn", EventType.BUTTON)
        viewModel.deleteMission(mission)
    }
}
