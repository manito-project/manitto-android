package org.sopt.santamanitto.room.manittoroom.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.santamanitto.R
import org.sopt.santamanitto.databinding.FragmentMatchedBinding
import org.sopt.santamanitto.room.manittoroom.ManittoRoomViewModel
import org.sopt.santamanitto.view.setLayoutHeight

@AndroidEntryPoint
class MatchedFragment : Fragment() {

    private lateinit var binding: FragmentMatchedBinding

    private val manittoRoomViewModel: ManittoRoomViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        manittoRoomViewModel.run {
            refreshManittoRoomInfo()
            getPersonalRelationInfo()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMatchedBinding.inflate(inflater, container, false).apply {
            viewModel = manittoRoomViewModel
            lifecycleOwner = viewLifecycleOwner

            root.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
                override fun onLayoutChange(v: View, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int,
                                            oldTop: Int, oldRight: Int, oldBottom: Int) {
                    binding.root.removeOnLayoutChangeListener(this)

                    initMissionText()
                }
            })
        }
        initManittoTitle()

        setOnClickListener()

        return binding.root
    }

    private fun initMissionText() {
        binding.textviewMatchedMission.run {
            setLayoutHeight(this, this.height)
            manittoRoomViewModel.myMission.observe(viewLifecycleOwner) {
                text = if (it.isNullOrEmpty()) {
                    getString(R.string.matched_no_mission)
                } else {
                    it
                }
            }
        }
    }

    private fun initManittoTitle() {
        binding.textviewMatchedTitle.text = String.format(getString(R.string.matched_manitto_title), manittoRoomViewModel.myName)
    }

    private fun setOnClickListener() {
        binding.run {
            santabackgroundMatched.setOnBackKeyClickListener {
                requireActivity().finish()
            }
            santabottombuttonMatched.setOnClickListener {
                requireActivity().finish()
            }
        }
    }
}