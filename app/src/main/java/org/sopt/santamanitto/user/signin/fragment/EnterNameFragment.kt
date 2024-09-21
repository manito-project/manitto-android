package org.sopt.santamanitto.user.signin.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.sopt.santamanitto.R
import org.sopt.santamanitto.databinding.FragmentEnterNameBinding
import org.sopt.santamanitto.user.signin.viewmodel.EnterNameViewModel
import org.sopt.santamanitto.util.FragmentUtil.hideKeyboardOnOutsideEditText
import org.sopt.santamanitto.util.base.BaseFragment

@AndroidEntryPoint
class EnterNameFragment :
    BaseFragment<FragmentEnterNameBinding>(R.layout.fragment_enter_name, true) {

    private val viewModel: EnterNameViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeUserName()
        observeUserNameValidation()
        setupButtonClickListener()
        hideKeyboardOnOutsideEditText()
    }

    private fun observeUserName() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userName.collect { userName ->
                    if (binding.santanameinputEntername.getText() != userName) {
                        binding.santanameinputEntername.setText(userName)
                    }
                }
            }
        }

        binding.santanameinputEntername.observeTextChanges { text ->
            viewModel.setUserName(text)
        }
    }

    private fun observeUserNameValidation() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isUserNameValid.collect { isValid ->
                    binding.santabottombuttonEntername.isEnabled = isValid
                }
            }
        }
    }

    private fun setupButtonClickListener() {
        binding.santabottombuttonEntername.setOnClickListener {
            val userName = viewModel.userName.value
            if (userName.isNotBlank()) {
                val direction =
                    EnterNameFragmentDirections.actionEnterNameFragmentToConditionFragment(userName)
                findNavController().navigate(direction)
            }
        }
    }
}