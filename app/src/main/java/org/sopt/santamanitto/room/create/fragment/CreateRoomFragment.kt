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
import org.sopt.santamanitto.room.create.setExpirationDiff
import org.sopt.santamanitto.room.create.setExpirationPreview
import org.sopt.santamanitto.room.create.setExpirationTime
import org.sopt.santamanitto.room.create.data.ExpirationLiveData
import org.sopt.santamanitto.room.create.viewmodel.CreateRoomAndMissionViewModel
import org.sopt.santamanitto.view.SantaEditText
import org.sopt.santamanitto.view.santanumberpicker.SantaNumberPicker
import org.sopt.santamanitto.view.setTextColorById

class CreateRoomFragment : Fragment() {

    companion object {
        private const val MAX_ROOM_NAME_LENGTH = 17
    }

    private lateinit var binding: FragmentCreateRoomBinding

    private val viewModel: CreateRoomAndMissionViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateRoomBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@CreateRoomFragment
            viewModel = this@CreateRoomFragment.viewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        initView()

        refreshUI(viewModel.expirationLiveData)

        subscribeUI()

        setOnClickListener()
    }

    private fun initView() {
        binding.textviewCreateroomAlert.text = String.format(getString(R.string.santanameinput_alert), MAX_ROOM_NAME_LENGTH)

        binding.edittextCreateroomRoomname.run {
            addTextChangeListener(SantaEditText.SantaEditLimitLengthWatcher(this, MAX_ROOM_NAME_LENGTH) {
                if (it) {
                    binding.textviewCreateroomAlert.setTextColorById(R.color.red)
                } else {
                    binding.textviewCreateroomAlert.setTextColorById(R.color.gray_3)
                }
            })

        }
    }

    private fun setOnClickListener() {
        binding.run {
            santabottombuttonCreateroom.setOnClickListener {
                findNavController().navigate(CreateRoomFragmentDirections.actionCreateRoomFragmentToCreateMissionsFragment())
            }
            santabackgroundCreateroom.setOnBackKeyClickListener {
                requireActivity().finish()
            }
            santaperiodpickerCreateroomExpiration.setOnPeriodChangedListener { period ->
                this@CreateRoomFragment.viewModel.setDayDiff(period)
            }
            santaswitchCreateroomAmpm.setOnSwitchChangedListener { isAm ->
                this@CreateRoomFragment.viewModel.setAmPm(!isAm)
            }
            textviewCreateroomExpirationpreview.setOnClickListener {
                showTimePicker()
            }
        }
    }

    private fun subscribeUI() {
        viewModel.expirationLiveData.observe(viewLifecycleOwner, this::refreshUI)
    }

    private fun refreshUI(expiration: ExpirationLiveData) {
        binding.run {
            setExpirationDiff(textviewCreateroomPreviewstart, expiration)
            setExpirationPreview(textviewCreateroomPreviewdate, expiration)
            setExpirationTime(textviewCreateroomExpirationpreview, expiration)
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
        return LayoutInflater.from(requireContext())
                .inflate(R.layout.dialog_create_room_time_picker, binding.root as ViewGroup, false)
                .apply {
                    findViewById<SantaNumberPicker>(R.id.santanumberpicker_pickerdialog_hour)
                            .setInitialPosition(viewModel.expirationLiveData.hour - 1)
                    findViewById<SantaNumberPicker>(R.id.santanumberpicker_pickerdialog_minute)
                            .setInitialPosition(viewModel.expirationLiveData.minute)
                }
    }
}