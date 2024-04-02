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
import org.sopt.santamanitto.view.dialog.RoundDialogBuilder
import org.sopt.santamanitto.room.create.adaptor.CreateConfirmAdaptor
import org.sopt.santamanitto.room.create.adaptor.CreateMissionAdaptor
import org.sopt.santamanitto.room.create.setExpirationDiff
import org.sopt.santamanitto.room.create.setExpirationPreview
import org.sopt.santamanitto.room.create.viewmodel.CreateRoomAndMissionViewModel
import org.sopt.santamanitto.room.create.data.ExpirationLiveData
import org.sopt.santamanitto.room.create.network.CreateRoomModel
import org.sopt.santamanitto.room.manittoroom.ManittoRoomActivity
import org.sopt.santamanitto.room.manittoroom.fragment.WaitingRoomFragment
import org.sopt.santamanitto.util.ClipBoardUtil

class CreateConfirmFragment: Fragment(), CreateMissionAdaptor.CreateMissionCallback{

    private lateinit var binding: FragmentCreateConfirmBinding

    private val createRoomAndMissionViewModel: CreateRoomAndMissionViewModel by activityViewModels()

    private val createConfirmAdapter = CreateConfirmAdaptor(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCreateConfirmBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = createRoomAndMissionViewModel
            recyclerviewCreateconfirm.adapter = createConfirmAdapter
        }

        initRecyclerView()

        refreshUI(createRoomAndMissionViewModel.expirationLiveData)

        subscribeUI()

        setOnClickListener()

        return binding.root
    }

    private fun setOnClickListener() {
        binding.run {
            santabottombuttonCreatemconfirm.setOnClickListener {
                createRoomAndMissionViewModel.createRoom(this@CreateConfirmFragment::showInvitationCodeDialog)
            }
            santabackgroundCreateconfirm.setOnBackKeyClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun showInvitationCodeDialog(createRoomModel: CreateRoomModel) {
        RoundDialogBuilder()
                .setContentText(getString(R.string.createconfirm_done_dialog))
                .setInvitationCode(createRoomModel.invitationCode) {
                    ClipBoardUtil.copy(requireContext(),
                            WaitingRoomFragment.INVITATION_CODE_LABEL, createRoomModel.invitationCode)
                    startMatchingRoomActivity(createRoomModel.id)
                }
                .enableCancel(false)
                .build()
                .show(parentFragmentManager, "invitation_code_dialog")
    }

    private fun startMatchingRoomActivity(createdRoomId: Int) {
        requireActivity().run {
            startActivity(Intent(this, ManittoRoomActivity::class.java).apply {
                putExtra(ManittoRoomActivity.EXTRA_ROOM_ID, createdRoomId)
            })
            finish()
        }
    }

    private fun initRecyclerView() {
        setRecyclerViewHeight()
        createConfirmAdapter.setList(createRoomAndMissionViewModel.missions.getMissions())
    }

    private fun setRecyclerViewHeight() {
        binding.recyclerviewCreateconfirm.run {
            layoutParams = layoutParams.apply {
                height = createRoomAndMissionViewModel.heightOfRecyclerView
            }
        }
    }

    private fun subscribeUI() {
        createRoomAndMissionViewModel.expirationLiveData.observe(viewLifecycleOwner, this::refreshUI)
        createRoomAndMissionViewModel.missions.observe(viewLifecycleOwner) {
            createConfirmAdapter.setList(it.getMissions())
        }
    }

    private fun refreshUI(expiration: ExpirationLiveData) {
        binding.run {
            setExpirationDiff(textviewCreateconfirmExpirationdiff, expiration)
            setExpirationPreview(textviewCreateconfirmExpirationpreview, expiration)
        }
    }

    //Todo: 직전 프래그먼트와 중복코드. 액티비티로 옮길 수 있는지 고려
    override fun onMissionInserted(mission: String) {
        createRoomAndMissionViewModel.addMission(mission)
    }

    override fun onMissionDeleted(mission: String) {
        createRoomAndMissionViewModel.deleteMission(mission)
    }
}