package org.sopt.santamanitto.user.signin.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.santamanitto.BuildConfig
import org.sopt.santamanitto.databinding.FragmentConditionBinding
import org.sopt.santamanitto.main.MainActivity
import org.sopt.santamanitto.user.signin.viewmodel.ConditionViewModel


@AndroidEntryPoint
class ConditionFragment : Fragment() {

    private lateinit var binding: FragmentConditionBinding

    private val args: ConditionFragmentArgs by navArgs()

    private val viewModel: ConditionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 요청 대기 중에 뒤로 가기 버튼을 눌러 화면을 벗어나는 것을 방지
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {

                override fun handleOnBackPressed() {
                    if (!viewModel.isWaitingForResponse) {
                        findNavController().navigateUp()
                    }
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
                navigateToMain()
            }
        }

        viewModel.userSaveFail.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(binding.root.context, "회원가입에 실패했어요!", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.isUserExist.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(binding.root.context, "이미 가입된 계정이야!", Toast.LENGTH_SHORT).show()
                navigateToMain()
            }
        }
    }

    private fun navigateToMain() {
        requireActivity().run {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
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