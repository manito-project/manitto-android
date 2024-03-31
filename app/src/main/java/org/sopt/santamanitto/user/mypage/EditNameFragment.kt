package org.sopt.santamanitto.user.mypage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.santamanitto.R
import org.sopt.santamanitto.util.base.BaseFragment
import org.sopt.santamanitto.databinding.FragmentEditNameBinding
import org.sopt.santamanitto.databinding.FragmentEnterNameBinding

@AndroidEntryPoint
class EditNameFragment: BaseFragment<FragmentEditNameBinding>(R.layout.fragment_edit_name, true) {

    private val editNameViewModel: EditNameViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = editNameViewModel
    }
}