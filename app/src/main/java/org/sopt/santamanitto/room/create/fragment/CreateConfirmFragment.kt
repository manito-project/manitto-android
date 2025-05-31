package org.sopt.santamanitto.room.create.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import org.sopt.santamanitto.BuildConfig
import org.sopt.santamanitto.R
import org.sopt.santamanitto.analytics.AmplitudeManager
import org.sopt.santamanitto.analytics.EventType
import org.sopt.santamanitto.databinding.FragmentCreateConfirmBinding
import org.sopt.santamanitto.room.create.adaptor.CreateConfirmAdaptor
import org.sopt.santamanitto.room.create.adaptor.CreateMissionAdaptor
import org.sopt.santamanitto.room.create.data.ExpirationLiveData
import org.sopt.santamanitto.room.create.network.CreateRoomModel
import org.sopt.santamanitto.room.create.setExpirationDiff
import org.sopt.santamanitto.room.create.setExpirationPreview
import org.sopt.santamanitto.room.create.viewmodel.CreateRoomAndMissionViewModel
import org.sopt.santamanitto.room.manittoroom.fragment.WaitingRoomFragment.Companion.INVITATION_CODE_LABEL
import org.sopt.santamanitto.util.ClipBoardUtil
import org.sopt.santamanitto.view.dialog.RoundDialogBuilder

class CreateConfirmFragment :
    Fragment(),
    CreateMissionAdaptor.CreateMissionCallback {
    private lateinit var binding: FragmentCreateConfirmBinding

    private val viewModel: CreateRoomAndMissionViewModel by activityViewModels()

    private val createConfirmAdapter = CreateConfirmAdaptor(this)

    private var interstitialAd: InterstitialAd? = null
    private var isAdLoading = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCreateConfirmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadInterstitialAd()
        AmplitudeManager.trackEvent("make_complete", EventType.PAGE)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
            recyclerviewCreateconfirm.adapter = createConfirmAdapter
        }
        initRecyclerView()
        refreshUI(viewModel.expirationLiveData)
        subscribeUI()
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.run {
            santabottombuttonCreatemconfirm.setOnClickListener {
                AmplitudeManager.trackEvent("make_complete_btn", EventType.BUTTON)
                showInterstitialAdAndCreateRoom()
            }
            santabackgroundCreateconfirm.setOnBackKeyClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun showInvitationCodeDialog(createRoom: CreateRoomModel) {
        AmplitudeManager.trackEvent("make_code_complete_popup", EventType.MODAL)
        if (context != null) {
            RoundDialogBuilder()
                .setContentText(getString(R.string.createconfirm_done_dialog))
                .setInvitationCode(createRoom.invitationCode) {
                    AmplitudeManager.trackEvent("make_code_copy_btn", EventType.BUTTON)
                    ClipBoardUtil.copy(
                        requireContext(),
                        INVITATION_CODE_LABEL,
                        createRoom.invitationCode,
                    )
                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
                        Toast
                            .makeText(
                                requireContext(),
                                getString(R.string.waitingroom_snackbar_invitation_code),
                                Toast.LENGTH_SHORT,
                            ).show()
                    }
                    requireActivity().finish()
                }.enableCancel(false)
                .build()
                .show(parentFragmentManager, "invitation_code_dialog")
        } else {
            requireActivity().finish()
        }
    }

    private fun initRecyclerView() {
        setRecyclerViewHeight()
        createConfirmAdapter.setList(viewModel.missions.getMissions())
    }

    private fun setRecyclerViewHeight() {
        binding.recyclerviewCreateconfirm.run {
            layoutParams =
                layoutParams.apply {
                    height = viewModel.heightOfRecyclerView
                }
        }
    }

    private fun subscribeUI() {
        viewModel.expirationLiveData.observe(viewLifecycleOwner, ::refreshUI)
        viewModel.missions.observe(viewLifecycleOwner) {
            createConfirmAdapter.setList(it.getMissions())
        }
    }

    private fun refreshUI(expiration: ExpirationLiveData) {
        binding.run {
            setExpirationDiff(textviewCreateconfirmExpirationdiff, expiration)
            setExpirationPreview(textviewCreateconfirmExpirationpreview, expiration)
        }
    }

    override fun onMissionInserted(mission: String) {
        viewModel.addMission(mission)
    }

    override fun onMissionDeleted(mission: String) {
        AmplitudeManager.trackEvent("make_complete_mission_minus_btn", EventType.BUTTON)
        viewModel.deleteMission(mission)
    }

    private fun loadInterstitialAd(){
        if (isAdLoading) return

        isAdLoading = true
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            requireContext(),
            BuildConfig.ADMOB_ROOM_CREATE_ID,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    isAdLoading = false
                    interstitialAd = ad
                    setAdCallback()
                    super.onAdLoaded(ad)
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    isAdLoading = false
                    interstitialAd = null
                    super.onAdFailedToLoad(adError)
                }
            }
        )
    }

    private fun setAdCallback() {
        interstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                interstitialAd = null
                viewModel.createRoom(::showInvitationCodeDialog)
                loadInterstitialAd()
                super.onAdDismissedFullScreenContent()
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                interstitialAd = null
                viewModel.createRoom(::showInvitationCodeDialog)
                super.onAdFailedToShowFullScreenContent(adError)
            }

            override fun onAdShowedFullScreenContent() {
                interstitialAd = null
                super.onAdShowedFullScreenContent()
            }
        }
    }

    private fun showInterstitialAdAndCreateRoom() {
        if (interstitialAd != null) {
            interstitialAd?.show(requireActivity())
        } else {
            viewModel.createRoom(::showInvitationCodeDialog)
            loadInterstitialAd()
        }
    }
}
