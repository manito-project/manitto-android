package org.sopt.santamanitto.room.create.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import org.sopt.santamanitto.databinding.FragmentCreateConfirmBinding
import org.sopt.santamanitto.room.create.*
import org.sopt.santamanitto.room.create.viewmodel.CreateRoomAndMissionViewModel
import org.sopt.santamanitto.room.data.ExpirationLiveData

class CreateConfirmFragment: Fragment(), CreateMissionAdaptor.CreateMissionCallback {

    private lateinit var binding: FragmentCreateConfirmBinding

    private val createRoomAndMissionViewModel: CreateRoomAndMissionViewModel by activityViewModels()

    private val createConfirmAdapter = CreateConfirmAdapter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCreateConfirmBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = createRoomAndMissionViewModel
            recyclerviewCreateconfirm.adapter = createConfirmAdapter
        }

        //initVisibility()

        initRecyclerView()

        refreshUI(createRoomAndMissionViewModel.expirationLiveData)

        subscribeUI()

        return binding.root
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