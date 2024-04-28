package org.sopt.santamanitto.user.mypage

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.santamanitto.R
import org.sopt.santamanitto.databinding.FragmentEditNameBinding
import org.sopt.santamanitto.util.base.BaseFragment

@AndroidEntryPoint
class EditNameFragment : BaseFragment<FragmentEditNameBinding>(R.layout.fragment_edit_name, true) {
    private val viewModel: EditNameViewModel by viewModels()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel

        setOnClickListener()
        observeChangeRequest()
    }

    private fun setOnClickListener() {
        binding.run {
            santabackgroundEditname.setOnBackKeyClickListener {
                findNavController().navigateUp()
            }
            //  buttonSettingBack.setOnClickListener { findNavController().navigateUp() }
        }
    }

    private fun observeChangeRequest() {
        viewModel.requestDone.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                findNavController().popBackStack(R.id.mainFragment, false)
                Toast.makeText(context, R.string.editname_finish, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
