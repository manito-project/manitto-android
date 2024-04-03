package org.sopt.santamanitto.room.create.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import org.sopt.santamanitto.R
import org.sopt.santamanitto.databinding.FragmentCreateMissionBinding
import org.sopt.santamanitto.room.create.adaptor.CreateMissionAdaptor
import org.sopt.santamanitto.room.create.viewmodel.CreateRoomAndMissionViewModel
import org.sopt.santamanitto.util.FragmentUtil.hideKeyboardOnOutsideEditText
import org.sopt.santamanitto.util.base.BaseFragment
import org.sopt.santamanitto.view.dialog.RoundDialogBuilder

class CreateMissionsFragment : BaseFragment<FragmentCreateMissionBinding>(
        R.layout.fragment_create_mission, true
), CreateMissionAdaptor.CreateMissionCallback {

    private val viewModel: CreateRoomAndMissionViewModel by activityViewModels()

    private val createMissionAdaptor = CreateMissionAdaptor(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewModel = viewModel
        binding.recyclerviewCreatemission.adapter = createMissionAdaptor

        subscribeUI()

        saveMeasuredHeightOfRecyclerView()

        setOnClickListener()

        hideKeyboardOnOutsideEditText()
    }

    override fun onMissionInserted(mission: String) {
        viewModel.addMission(mission)
    }

    override fun onMissionDeleted(mission: String) {
        viewModel.deleteMission(mission)
    }

    private fun setOnClickListener() {
        binding.run {
            santabottombuttonCreatemissionSkip.setOnClickListener {
                showSkipDialog()
            }
            santabottombuttonCreatemissionDone.setOnClickListener {
                if (this@CreateMissionsFragment.viewModel.hasMissions()) {
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
        viewModel.missions.observe(viewLifecycleOwner) {
            createMissionAdaptor.setList(it.getMissions())
            binding.recyclerviewCreatemission.scrollToPosition(createMissionAdaptor.itemCount - 1)
        }
    }

    private fun saveMeasuredHeightOfRecyclerView() {
        binding.recyclerviewCreatemission.run {
            viewTreeObserver.addOnGlobalLayoutListener {
                viewModel.heightOfRecyclerView = height
            }
        }
    }

    private fun showSkipDialog() {
        if (viewModel.hasMissions()) {
            RoundDialogBuilder()
                    .setContentText(getString(R.string.createmission_dialog_skip_has_mission), true)
                    .addHorizontalButton(getString(R.string.dialog_cancel))
                    .addHorizontalButton(getString(R.string.dialog_confirm)) {
                        viewModel.clearMission()
                        navigateConfirmFragment()
                    }
                    .build()
                    .show(parentFragmentManager, "skip_dialog")
        } else {
            showNoMissionDialog()
        }
    }

    private fun showNoMissionDialog() {
        RoundDialogBuilder()
                .setContentText(getString(R.string.createmission_dialog_no_mission), true)
                .addHorizontalButton(getString(R.string.dialog_cancel))
                .addHorizontalButton(getString(R.string.dialog_confirm)) {
                    navigateConfirmFragment()
                }
                .build()
                .show(parentFragmentManager, "done_dialog")
    }

    private fun navigateConfirmFragment() {
        findNavController()
                .navigate(CreateMissionsFragmentDirections.actionCreateMissionsFragmentToCreateConfirmFragment())
    }
}