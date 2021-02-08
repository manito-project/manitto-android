package org.sopt.santamanitto.room.create.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import org.sopt.santamanitto.databinding.FragmentCreateMissionBinding
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

        viewModel.missions.observe(viewLifecycleOwner) {
            createMissionAdaptor.setList(it.getMissions())
            binding.recyclerviewCreatemission.scrollToPosition(createMissionAdaptor.itemCount - 1)
        }

        return binding.root
    }

    override fun onMissionInserted(mission: String) {
        viewModel.addMission(mission)
    }

    override fun onMissionDeleted(mission: String) {
        viewModel.deleteMission(mission)
    }
}