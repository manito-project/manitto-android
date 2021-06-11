package org.sopt.santamanitto.setting

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.santamanitto.R
import org.sopt.santamanitto.base.BaseFragment
import org.sopt.santamanitto.databinding.FragmentSettingBinding

@AndroidEntryPoint
class SettingFragment: BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //Todo: 세팅 내용 구현
        binding.buttonSettingBack.setOnClickListener { findNavController().navigateUp() }
    }
}