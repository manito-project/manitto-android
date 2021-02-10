package org.sopt.santamanitto.room.create.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import org.sopt.santamanitto.databinding.FragmentCreateConfirmBinding
import org.sopt.santamanitto.room.create.CreateConfirmAdapter
import org.sopt.santamanitto.room.create.CreateMissionAdaptor
import org.sopt.santamanitto.room.create.viewmodel.CreateRoomAndMissionViewModel

class CreateConfirmFragment: Fragment(), CreateMissionAdaptor.CreateMissionCallback {

    private lateinit var binding: FragmentCreateConfirmBinding

    private val viewModel: CreateRoomAndMissionViewModel by activityViewModels()

    private val createConfirmAdapter = CreateConfirmAdapter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCreateConfirmBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@CreateConfirmFragment.viewModel
            recyclerviewCreateconfirm.adapter = createConfirmAdapter

        }

        setRecyclerViewHeight()

        subscribeUI()

        return binding.root
    }

    private fun setRecyclerViewHeight() {
        binding.recyclerviewCreateconfirm.run {
            layoutParams = layoutParams.apply {
                height = viewModel.heightOfRecyclerView
            }
        }
    }

    private fun subscribeUI() {
        viewModel.missions.observe(viewLifecycleOwner) {
            createConfirmAdapter.setList(it.getMissions())
        }
    }

    //Todo: 직전 프래그먼트와 중복코드. 액티비티로 옮길 수 있는지 고려
    override fun onMissionInserted(mission: String) {
        viewModel.addMission(mission)
    }

    override fun onMissionDeleted(mission: String) {
        viewModel.deleteMission(mission)
    }
}