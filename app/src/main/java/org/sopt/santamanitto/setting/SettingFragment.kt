package org.sopt.santamanitto.setting

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.santamanitto.R
import org.sopt.santamanitto.base.BaseFragment
import org.sopt.santamanitto.databinding.FragmentSettingBinding

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.run {
            buttonSettingBack.setOnClickListener { findNavController().navigateUp() }
            settinglistviewSetting
                    .addSetting(getString(R.string.setting_1_edit_name)) {
                        findNavController().navigate(
                                SettingFragmentDirections.actionSettingFragment2ToEditNameFragment()
                        )
                    }
                    .addSetting(getString(R.string.setting_2_tos)) {
                        //Todo: 웹뷰 이동
                    }
                    .addSetting(getString(R.string.setting_3_privacy_policy)) {
                        //Todo: 웹뷰 이동
                    }
                    .commit()
        }
    }
}