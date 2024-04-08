package org.sopt.santamanitto.user.signin.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.santamanitto.BuildConfig
import org.sopt.santamanitto.main.MainActivity
import org.sopt.santamanitto.R
import org.sopt.santamanitto.databinding.FragmentConditionBinding
import org.sopt.santamanitto.user.signin.viewmodel.ConditionViewModel
import androidx.activity.OnBackPressedCallback


@AndroidEntryPoint
class ConditionFragment : Fragment() {

    private lateinit var binding: FragmentConditionBinding

    private val args: ConditionFragmentArgs by navArgs()

    private val viewModel: ConditionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 요청 대기 중에 뒤로 가기 버튼을 눌러 화면을 벗어나는 것을 방지
        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {

            override fun handleOnBackPressed() {
                if (!viewModel.isWaitingForResponse) {
                    findNavController().navigateUp()
                }
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentConditionBinding.inflate(inflater, container, false).apply {
            viewModel = this@ConditionFragment.viewModel
            lifecycleOwner = this@ConditionFragment
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.userName = args.userName
        initView()
        subscribeUi()
    }

    private fun subscribeUi() {
        viewModel.userSaveSuccess.observe(viewLifecycleOwner) {
            if (it) {
                requireActivity().run {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        }

        viewModel.userSaveFail.observe(viewLifecycleOwner) {
            if (it) {
                //Todo: 계정 생성에 실패했다는 다이얼로그 띄우기
                Log.e("ConditionFragment", "Fail to create new account")
            }
        }
    }

    private fun initView() {
//        context?.let {
//            binding.santabackgroundCondition.bigTitle =
//                String.format(it.resources.getString(R.string.condition_background_text), args.userName)
//        }

        binding.santacheckboxConditionAllagree.run {
            addChildSantaCheckBox(binding.santacheckboxCondition1)
            addChildSantaCheckBox(binding.santacheckboxCondition2)
        }

        binding.santacheckboxCondition1.setOnClickListener {
            if (viewModel.isWaitingForResponse) {
                return@setOnClickListener
            }
            val directions = ConditionFragmentDirections
                .actionConditionFragmentToWebViewFragment(BuildConfig.TOS_URL)
            findNavController().navigate(directions)
        }

        binding.santacheckboxCondition2.setOnClickListener {
            if (viewModel.isWaitingForResponse) {
                return@setOnClickListener
            }
            val directions = ConditionFragmentDirections
                .actionConditionFragmentToWebViewFragment(BuildConfig.PRIVACY_POLICY_RUL)
            findNavController().navigate(directions)
        }
    }
}