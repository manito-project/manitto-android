package org.sopt.santamanitto.room.create.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import org.sopt.santamanitto.R
import org.sopt.santamanitto.databinding.FragmentCreateRoomBinding
import org.sopt.santamanitto.dialog.RoundDialogBuilder
import org.sopt.santamanitto.room.data.ExpirationLiveData
import org.sopt.santamanitto.room.create.viewmodel.CreateRoomAndMissionViewModel
import org.sopt.santamanitto.view.santanumberpicker.SantaNumberPicker

class CreateRoomFragment : Fragment() {

    private lateinit var binding: FragmentCreateRoomBinding

    private val viewModel: CreateRoomAndMissionViewModel by activityViewModels()

    private var cachedPickerView: View? = null

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
        binding.textviewCreateroomExpirationpreview.setOnClickListener {
            showTimePicker()
        }

        refreshUI(viewModel.expirationLiveData)

        subscribeUI()

        setOnClickListener()

        return binding.root
    }

    private fun setOnClickListener() {
        binding.santabottombuttonCreateroom.setOnClickListener {
            findNavController().navigate(CreateRoomFragmentDirections.actionCreateRoomFragmentToCreateMissionsFragment())
        }
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
                            expiration.year, expiration.month, expiration.day,
                            amPmStr, expiration.hour, expiration.minute)
            textviewCreateroomPreviewstart.text =
                    String.format(context?.getText(R.string.createroom_preview_start).toString(), expiration.dayDiff)
            textviewCreateroomExpirationpreview.text =
                    String.format(context?.getText(R.string.createroom_expiration_time).toString(),
                            expiration.hour, expiration.minute)
        }
    }

    private fun showTimePicker() {
        RoundDialogBuilder()
                .setTitle(getString(R.string.createroom_dialog_expiration_time_setting))
                .setContentView(getPickerView())
                .addHorizontalButton(getString(R.string.dialog_cancel))
                .addHorizontalButton(getString(R.string.dialog_confirm)) {
                    val hour = it!!.findViewById<SantaNumberPicker>(R.id.santanumberpicker_pickerdialog_hour)
                            .getCurrentNumber()
                    val minute = it.findViewById<SantaNumberPicker>(R.id.santanumberpicker_pickerdialog_minute)
                            .getCurrentNumber()
                    viewModel.setTime(hour, minute)
                }
                .build()
                .show(parentFragmentManager, "createroom_timepicker")
    }

    private fun getPickerView(): View {
        if (cachedPickerView == null) {
            cachedPickerView = LayoutInflater.from(requireContext())
                    .inflate(R.layout.dialog_create_room_time_picker, binding.root as ViewGroup, false)
                    .apply {
                        findViewById<SantaNumberPicker>(R.id.santanumberpicker_pickerdialog_hour)
                                .setInitialPosition(viewModel.expirationLiveData.hour - 1)
                        findViewById<SantaNumberPicker>(R.id.santanumberpicker_pickerdialog_minute)
                                .setInitialPosition(viewModel.expirationLiveData.minute)
                    }
        }
        return cachedPickerView!!
    }
}