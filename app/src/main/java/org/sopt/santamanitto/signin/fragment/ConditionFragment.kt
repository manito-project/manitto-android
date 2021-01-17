package org.sopt.santamanitto.signin.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import org.sopt.santamanitto.R
import org.sopt.santamanitto.databinding.FragmentConditionBinding
import org.sopt.santamanitto.signin.viewmodel.ConditionViewModel

class ConditionFragment: Fragment() {

    private lateinit var binding: FragmentConditionBinding

    private val args: ConditionFragmentArgs by navArgs()

    private val viewModel: ConditionViewModel by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentConditionBinding.inflate(inflater, container, false).apply {
            viewModel = this@ConditionFragment.viewModel
            lifecycleOwner = this@ConditionFragment
        }

        initView()

        subscribeUi()

        return binding.root
    }

    private fun initView() {
        context?.let {
            binding.santabackgroundCondition.text =
                String.format(it.resources.getString(R.string.condition_background_text), args.userName)
        }

        binding.santacheckboxConditionAllagree.run {
            addChildSantaCheckBox(binding.santacheckboxCondition1)
            addChildSantaCheckBox(binding.santacheckboxCondition2)
        }
    }

    private fun subscribeUi() {

    }
}