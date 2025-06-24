package org.sopt.santamanitto.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.sopt.santamanitto.BuildConfig
import org.sopt.santamanitto.R
import org.sopt.santamanitto.analytics.AmplitudeManager
import org.sopt.santamanitto.analytics.EventType
import org.sopt.santamanitto.chat.overview.ChatOverviewActivity
import org.sopt.santamanitto.databinding.FragmentMainBinding
import org.sopt.santamanitto.main.list.MyManittoListAdapter
import org.sopt.santamanitto.room.create.CreateRoomActivity
import org.sopt.santamanitto.room.manittoroom.ManittoRoomActivity
import org.sopt.santamanitto.view.dialog.exit.ExitDialogCreator
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    @Inject
    lateinit var adapter: MyManittoListAdapter

    private val viewModel: MainViewModel by viewModels()

    private var backPressedTime: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            FragmentMainBinding.inflate(inflater, container, false).apply {
                lifecycleOwner = this@MainFragment
                viewModel = this@MainFragment.viewModel
                recyclerviewMainHistory.adapter = adapter
                recyclerviewMainHistory.visibility = View.GONE
                constraintlayoutMainNomymanitto.visibility = View.VISIBLE
            }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AmplitudeManager.trackEvent("home", EventType.PAGE)
        binding.santabackgroundMain.isBackKeyEnabled = false
        subscribeUI()
        setOnClickListener()
        initBackPressedCallback()
        loadAds()
    }

    private fun subscribeUI() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.myManittoModelList
                    .collect { list ->
                        adapter.submitList(list)

                        if (list.isEmpty()) {
                            binding.recyclerviewMainHistory.visibility = View.INVISIBLE
                            binding.constraintlayoutMainNomymanitto.visibility = View.VISIBLE
                        } else {
                            binding.recyclerviewMainHistory.visibility = View.VISIBLE
                            binding.constraintlayoutMainNomymanitto.visibility = View.INVISIBLE
                        }
                    }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isRefreshing.collect { isRefreshing ->
                    binding.progressbarMainJoinedRooms.isVisible = isRefreshing
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        initJoinedRooms()
    }

    private fun setOnClickListener() {
        binding.run {
            santaimageroundbuttonMainMakeroom.setOnClickListener {
                startCreateRoomActivity()
            }
            santaimageroundbuttonMainJoin.setOnClickListener {
                navigateJoinRoomFragment()
            }
            imagebuttonMainSetting.setOnClickListener {
                navigateSettingFragment()
            }
            //TODO: 서버통신 이후, 안읽은 채팅 있으면 XML 이미지 ic_noti_on으로 바꾸기
            imagebuttonMainNoti.setOnClickListener {
                startChatActivity()
            }
        }

        adapter.run {
            setOnItemClickListener { roomId, isMatched, isFinished ->
                requireActivity().run {
                    startActivity(
                        Intent(this, ManittoRoomActivity::class.java).apply {
                            putExtra(ManittoRoomActivity.EXTRA_ROOM_ID, roomId)
                            putExtra(ManittoRoomActivity.EXTRA_IS_MATCHED, isMatched)
                            putExtra(ManittoRoomActivity.EXTRA_IS_FINISHED, isFinished)
                        },
                    )
                }
            }
            setOnExitClickListener { roomId, roomName, isHost ->
                AmplitudeManager.trackEvent(
                    if (isHost) "leader_exit_popup" else "participant_exit_popup",
                    EventType.MODAL
                )
                ExitDialogCreator.create(requireContext(), roomName, isHost) {
                    viewModel.exitRoom(roomId)
                    AmplitudeManager.trackEvent(
                        if (isHost) "leader_exit_popup_exit_btn" else "participant_exit_popup_exit_btn",
                        EventType.BUTTON
                    )
                }.show(childFragmentManager, "exit")
            }
            setOnRemoveClickListener { roomId ->
                viewModel.deleteRoom(roomId)
            }
        }
    }

    private fun initJoinedRooms() {
        viewModel.fetchMyManittoList()
    }

    private fun navigateSettingFragment() {
        findNavController().navigate(MainFragmentDirections.actionMainFragmentToSettingFragment())
    }

    private fun navigateJoinRoomFragment() {
        findNavController().navigate(MainFragmentDirections.actionMainFragmentToJoinRoomFragment())
    }

    private fun startChatActivity() {
        requireActivity().run {
            startActivity(Intent(this, ChatOverviewActivity::class.java))
        }
    }

    private fun startCreateRoomActivity() {
        requireActivity().run {
            startActivity(Intent(this, CreateRoomActivity::class.java))
        }
    }

    private fun initBackPressedCallback() {
        val onBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (System.currentTimeMillis() - backPressedTime >= BACK_PRESSED_INTERVAL) {
                        backPressedTime = System.currentTimeMillis()
                        Snackbar.make(
                            binding.root,
                            getString(R.string.main_back_pressed),
                            Snackbar.LENGTH_SHORT,
                        ).show()
                    } else {
                        requireActivity().finish()
                    }
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback,
        )
    }

    private fun loadAds() {
        binding.adContainer.post {
            val metrics = requireContext().resources.displayMetrics
            val widthPx  = binding.adContainer.width
            val widthDp  = (widthPx / metrics.density).toInt()

            val adSize = AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(
                requireContext(),
                widthDp
            )

            val adView = AdView(requireContext()).apply {
                adUnitId = BuildConfig.ADMOB_CA_APP_PUB
                setAdSize(adSize)
                adListener = object : AdListener() {
                    override fun onAdFailedToLoad(error: LoadAdError) {
                        Log.e("AdMob", "실패 코드=${error.code}, 메시지=${error.message}")
                    }
                    override fun onAdLoaded() {
                        Log.d("AdMob", "광고 로드 성공")
                    }
                }
            }

            binding.adContainer
                .apply {
                    removeAllViews()
                    addView(adView)
                }
            adView.loadAd(AdRequest.Builder().build())
        }
    }

    companion object {
        const val BACK_PRESSED_INTERVAL = 2000
    }
}
