package org.sopt.santamanitto.room.create.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import org.sopt.santamanitto.R
import org.sopt.santamanitto.databinding.FragmentCreateMissionBinding
import org.sopt.santamanitto.dialog.RoundDialogBuilder
import org.sopt.santamanitto.room.create.CreateMissionAdaptor
import org.sopt.santamanitto.room.create.viewmodel.CreateRoomAndMissionViewModel

class CreateMissionsFragment : Fragment(), CreateMissionAdaptor.CreateMissionCallback {

    private lateinit var binding: FragmentCreateMissionBinding

    private val viewModel: CreateRoomAndMissionViewModel by activityViewModels()

    private val createMissionAdaptor = CreateMissionAdaptor(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCreateMissionBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@CreateMissionsFragment.viewModel
            recyclerviewCreatemission.adapter = createMissionAdaptor
        }

        subscribeUI()

        saveMeasuredHeightOfRecyclerView()

        setOnClickListener()

        return binding.root
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