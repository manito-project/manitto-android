package org.sopt.santamanitto.setting

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.santamanitto.BuildConfig
import org.sopt.santamanitto.R
import org.sopt.santamanitto.databinding.FragmentSettingBinding
import org.sopt.santamanitto.util.base.BaseFragment

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting, false) {
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        binding.run {
            buttonSettingBack.setOnClickListener { findNavController().navigateUp() }
            settinglistviewSetting
                .addSetting(getString(R.string.setting_1_edit_name)) {
                    findNavController().navigate(
                        SettingFragmentDirections.actionSettingFragmentToEditNameFragment(),
                    )
                }
                .addSetting(getString(R.string.setting_2_inquiry)) {
                    goToWebViewFragment(BuildConfig.INQUIRY_URL)
                }
                .addSetting(getString(R.string.setting_3_tos)) {
                    goToWebViewFragment(BuildConfig.TOS_URL)
                }
                .addSetting(getString(R.string.setting_4_privacy_policy)) {
                    goToWebViewFragment(BuildConfig.PRIVACY_POLICY_RUL)
                }
                .commit()
        }
    }

    private fun goToWebViewFragment(url: String) {
        findNavController().navigate(
            SettingFragmentDirections.actionSettingFragmentToWebViewFragmentFromSetting(
                url,
            ),
        )
    }
}
