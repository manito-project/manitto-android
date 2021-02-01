package org.sopt.santamanitto.room.create.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import org.sopt.santamanitto.R
import org.sopt.santamanitto.databinding.FragmentCreateRoomBinding
import org.sopt.santamanitto.room.ExpirationLiveData
import org.sopt.santamanitto.room.create.viewmodel.CreateRoomViewModel

class CreateRoomFragment : Fragment() {

    private lateinit var binding: FragmentCreateRoomBinding

    private val viewModel: CreateRoomViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateRoomBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@CreateRoomFragment
            viewModel = this@CreateRoomFragment.viewModel
        }

        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        binding.santabackgroundCreateroom.setOnBackKeyClickListener {
            findNavController().navigateUp()
        }
        binding.santaperiodpickerCreateroomExpiration.setOnPeriodChangedListener { period ->
            viewModel.setDayDiff(period)
        }
        binding.santaswitchCreateroomAmpm.setOnSwitchChangedListener { isAm ->
            viewModel.setAmPm(!isAm)
        }

        refreshUI(viewModel.expirationLiveData)

        subscribeUI()

        return binding.root
    }

    private fun subscribeUI() {
        viewModel.expirationLiveData.observe(viewLifecycleOwner, this::refreshUI)
    }

    private fun refreshUI(expiration: ExpirationLiveData) {
        val amPmStr = if (expiration.isAm) {
            context?.getString(R.string.createroom_am)
        } else {
            context?.getString(R.string.createroom_pm)
        }
        binding.run {
            textviewCreateroomPreviewdate.text =
                    String.format(context?.getText(R.string.createroom_preview).toString(),
                            expiration.year, expiration.month, expiration.day, amPmStr, expiration.hour, expiration.minute)
            textviewCreateroomPreviewstart.text =
                    String.format(context?.getText(R.string.createroom_preview_start).toString(), expiration.dayDiff)
            textviewCreateroomExpirationpreview.text =
                    String.format(context?.getText(R.string.createroom_expiration_time).toString(), expiration.hour, expiration.minute)
        }
    }
}