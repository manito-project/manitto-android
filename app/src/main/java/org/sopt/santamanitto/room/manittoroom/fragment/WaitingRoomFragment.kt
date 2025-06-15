package org.sopt.santamanitto.room.manittoroom.fragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.santamanitto.BuildConfig
import org.sopt.santamanitto.R
import org.sopt.santamanitto.admob.AdmobInterstitialAdHelper
import org.sopt.santamanitto.admob.InterstitialAdHelper
import org.sopt.santamanitto.analytics.AmplitudeManager
import org.sopt.santamanitto.analytics.EventType
import org.sopt.santamanitto.databinding.FragmentWaitingRoomBinding
import org.sopt.santamanitto.room.manittoroom.ManittoRoomViewModel
import org.sopt.santamanitto.room.manittoroom.MemberAdapter
import org.sopt.santamanitto.room.manittoroom.fragment.WaitingRoomFragmentDirections.Companion.actionWaitingRoomFragmentToCreateRoomFragmentModify
import org.sopt.santamanitto.room.manittoroom.fragment.WaitingRoomFragmentDirections.Companion.actionWaitingRoomFragmentToFinishFragment
import org.sopt.santamanitto.room.manittoroom.fragment.WaitingRoomFragmentDirections.Companion.actionWaitingRoomFragmentToMatchingFragment
import org.sopt.santamanitto.util.ClipBoardUtil
import org.sopt.santamanitto.util.base.BaseFragment
import org.sopt.santamanitto.view.dialog.RoundDialogBuilder
import javax.inject.Inject

@AndroidEntryPoint
class WaitingRoomFragment :
    BaseFragment<FragmentWaitingRoomBinding>(R.layout.fragment_waiting_room, false) {

    companion object {
        const val INVITATION_CODE_LABEL = "InvitationCode"
    }

    @Inject
    lateinit var adHelper: InterstitialAdHelper

    private val viewModel: ManittoRoomViewModel by activityViewModels()
    private val memberAdapter = MemberAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        adHelper = AdmobInterstitialAdHelper(requireContext()).apply {
            initialize(requireActivity(), BuildConfig.ADMOB_MATCHING_RESULT_ID)
            loadAd()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        if (viewModel.isMatched) {
            if (viewModel.isFinished) {
//                adHelper.showAdIfAvailable {
                    navigateFinishFragment()
//                }
            } else {
                navigateMatchingFragment()
            }
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AmplitudeManager.trackEvent("room_manitto_list", EventType.PAGE)
        initView()
        setOnClickListener()
        subscribeUI()
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshManittoRoomInfo()
    }

    private fun initView() {
        binding.run {
            vm = viewModel
            recyclerviewWaitingroom.adapter = memberAdapter
        }
    }

    private fun setOnClickListener() {
        binding.run {
            santabackgroundWaitingroom.setOnBackKeyClickListener {
                requireActivity().finish()
            }
            textviewWaitingroomInvitationcode.setOnClickListener {
                AmplitudeManager.trackEvent("room_code_copy_btn", EventType.BUTTON)
                ClipBoardUtil.copy(
                    requireContext(),
                    INVITATION_CODE_LABEL,
                    viewModel.invitationCode,
                )
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
                    Snackbar
                        .make(
                            binding.root,
                            getString(R.string.waitingroom_snackbar_invitation_code),
                            Snackbar.LENGTH_SHORT,
                        ).show()
                }
            }
            santabottombuttonWaitingroomModify.setOnClickListener {
                AmplitudeManager.trackEvent("room_edit_btn", EventType.BUTTON)
                navigateModifyFragment()
            }
            santabottombuttonWaitingroomMatch.setOnClickListener {
                AmplitudeManager.trackEvent("room_start_btn", EventType.BUTTON)
                viewModel.match()
                navigateMatchingFragment()
            }
            buttonWaitingroomRefresh.setOnClickListener {
                AmplitudeManager.trackEvent("room_refresh_btn", EventType.BUTTON)
                viewModel.refreshManittoRoomInfo()
            }
        }
    }

    private fun subscribeUI() {
        viewModel.isExpired.observe(viewLifecycleOwner) { isExpired ->
            if (isExpired) {
                RoundDialogBuilder()
                    .setContentText(getString(R.string.mymanitto_epired_dialog))
                    .addHorizontalButton(getString(R.string.mymanitto_epired_dialog_button)) {
                        requireActivity().finish()
                    }.enableCancel(false)
                    .build()
                    .show(childFragmentManager, "expired")
            }
        }
    }

    private fun navigateModifyFragment() {
        findNavController().navigate(actionWaitingRoomFragmentToCreateRoomFragmentModify(viewModel.roomId))
    }

    private fun navigateFinishFragment() {
        findNavController().navigate(actionWaitingRoomFragmentToFinishFragment())
    }

    private fun navigateMatchingFragment() {
        findNavController().navigate(actionWaitingRoomFragmentToMatchingFragment())
    }
}
