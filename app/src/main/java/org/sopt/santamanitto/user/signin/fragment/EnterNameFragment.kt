package org.sopt.santamanitto.user.signin.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
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
        binding.viewModel = viewModel
        binding.santabottombuttonEntername.setOnClickListener {
            val userName = viewModel.userName.value
            if (userName != null) {
                val direction =
                    EnterNameFragmentDirections.actionEnterNameFragmentToConditionFragment(userName)
                findNavController().navigate(direction)
            }
        }
        hideKeyboardOnOutsideEditText()
    }
}