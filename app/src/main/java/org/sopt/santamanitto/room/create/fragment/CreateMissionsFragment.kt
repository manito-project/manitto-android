package org.sopt.santamanitto.room.create.fragment

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import org.sopt.santamanitto.R
import org.sopt.santamanitto.databinding.FragmentCreateMissionBinding
import org.sopt.santamanitto.room.create.adaptor.CreateMissionAdaptor
import org.sopt.santamanitto.room.create.fragment.CreateMissionsFragmentDirections.Companion.actionCreateMissionsFragmentToCreateConfirmFragment
import org.sopt.santamanitto.room.create.viewmodel.CreateRoomAndMissionViewModel
import org.sopt.santamanitto.util.FragmentUtil.hideKeyboardOnOutsideEditText
import org.sopt.santamanitto.util.base.BaseFragment
import org.sopt.santamanitto.view.dialog.RoundDialogBuilder

class CreateMissionsFragment :
    BaseFragment<FragmentCreateMissionBinding>(
        R.layout.fragment_create_mission,
        false,
    ),
    CreateMissionAdaptor.CreateMissionCallback {
    private val viewModel: CreateRoomAndMissionViewModel by activityViewModels()

    private val createMissionAdaptor = CreateMissionAdaptor(this)

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        binding.recyclerviewCreatemission.adapter = createMissionAdaptor

        subscribeUI()

        saveMeasuredHeightOfRecyclerView()

        setOnClickListener()

        hideKeyboardOnOutsideEditText()
    }

    override fun onMissionInserted(mission: String) {
        viewModel.addMission(mission)
        binding.santabottombuttonCreatemissionDone.isEnabled = true
    }

    override fun onMissionDeleted(mission: String) {
        viewModel.deleteMission(mission)
        if (!viewModel.hasMissions()) binding.santabottombuttonCreatemissionDone.isEnabled = false
    }

    private fun setOnClickListener() {
        binding.run {
            santabottombuttonCreatemissionSkip.setOnClickListener {
                showSkipDialog()
            }
            santabottombuttonCreatemissionDone.setOnClickListener {
                if (viewModel.hasMissions()) {
                    navigateConfirmFragment()
                } else {
                    showNoMissionDialog()
                }
            }
            santabackgroundCreatemission.setOnBackKeyClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun subscribeUI() {
        viewModel.missions.observe(viewLifecycleOwner) { missionList ->
            createMissionAdaptor.setList(missionList.getMissions())
            binding.recyclerviewCreatemission.scrollToPosition(createMissionAdaptor.itemCount - 1)
        }
    }

    private fun saveMeasuredHeightOfRecyclerView() {
        binding.recyclerviewCreatemission.run {
            viewTreeObserver.addOnGlobalLayoutListener {
                viewModel.heightOfRecyclerView =
                    height - (100 * Resources.getSystem().displayMetrics.density).toInt()
            }
        }
    }

    private fun showSkipDialog() {
        if (viewModel.hasMissions()) {
            RoundDialogBuilder()
                .setContentText(
                    getString(R.string.createmission_dialog_skip_has_mission),
                    true,
                ).addHorizontalButton(getString(R.string.createmission_skip_bottom_button)) {
                    viewModel.clearMission()
                    navigateConfirmFragment()
                }.addHorizontalButton(getString(R.string.createroom_btn_next))
                .build()
                .show(parentFragmentManager, "skip_dialog")
        } else {
            showNoMissionDialog()
        }
    }

    private fun showNoMissionDialog() {
        RoundDialogBuilder()
            .setContentText(
                getString(R.string.createmission_dialog_no_mission),
                true,
            ).addHorizontalButton(getString(R.string.createmission_skip_bottom_button)) {
                navigateConfirmFragment()
            }.addHorizontalButton(getString(R.string.createroom_btn_next))
            .build()
            .show(parentFragmentManager, "done_dialog")
    }

    private fun navigateConfirmFragment() {
        findNavController().navigate(actionCreateMissionsFragmentToCreateConfirmFragment())
    }
}
