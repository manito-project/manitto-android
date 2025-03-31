package org.sopt.santamanitto.room.manittoroom.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.santamanitto.R
import org.sopt.santamanitto.analytics.AmplitudeManager
import org.sopt.santamanitto.analytics.EventType
import org.sopt.santamanitto.databinding.FragmentMatchedBinding
import org.sopt.santamanitto.room.manittoroom.ManittoRoomViewModel
import org.sopt.santamanitto.util.BindingAdapters.setLayoutHeight

@AndroidEntryPoint
class MatchedFragment : Fragment() {

    private lateinit var binding: FragmentMatchedBinding

    private val viewModel: ManittoRoomViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMatchedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AmplitudeManager.trackEvent("room_manitto_list", EventType.PAGE)
        binding.apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
            root.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
                override fun onLayoutChange(
                    v: View, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int,
                    oldTop: Int, oldRight: Int, oldBottom: Int
                ) {
                    binding.root.removeOnLayoutChangeListener(this)
                    initMissionText()
                }
            })
        }
        viewModel.run {
            refreshManittoRoomInfo()
            getPersonalRelationInfo()
        }
        initManittoTitle()
        setOnClickListener()
    }

    private fun initMissionText() {
        binding.textviewMatchedMission.run {
            setLayoutHeight(this, this.height)
            viewModel.myMission.observe(viewLifecycleOwner) { myMission ->
                text =
                    if (myMission.isNullOrEmpty()) getString(R.string.matched_no_mission) else myMission
            }
        }
    }

    private fun initManittoTitle() {
        binding.textviewMatchedTitle.text =
            String.format(getString(R.string.matched_manitto_title), viewModel.myName)
        binding.textviewMatchedMissiontitle.text =
            String.format(getString(R.string.matched_mission_title), viewModel.myName)
    }

    private fun setOnClickListener() {
        binding.run {
            santabackgroundMatched.setOnBackKeyClickListener {
                requireActivity().finish()
            }
            santabottombuttonMatched.setOnClickListener {
                AmplitudeManager.trackEvent("manitto_home_btn", EventType.BUTTON)
                requireActivity().finish()
            }
        }
    }
}