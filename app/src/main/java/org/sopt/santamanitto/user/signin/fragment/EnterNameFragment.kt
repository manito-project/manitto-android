package org.sopt.santamanitto.user.signin.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.santamanitto.databinding.FragmentEnterNameBinding
import org.sopt.santamanitto.user.signin.viewmodel.EnterNameViewModel

@AndroidEntryPoint
class EnterNameFragment : Fragment() {

    private lateinit var binding: FragmentEnterNameBinding
    private val viewModel: EnterNameViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentEnterNameBinding.inflate(inflater, container, false).apply {
            viewModel = this@EnterNameFragment.viewModel
            lifecycleOwner = this@EnterNameFragment
        }

        binding.santabottombuttonEntername.setOnClickListener {
            val userName = viewModel.userName.value
            if (userName != null) {
                val direction = EnterNameFragmentDirections.actionEnterNameFragmentToConditionFragment(userName)
                findNavController().navigate(direction)
            }
        }

        return binding.root
    }
}