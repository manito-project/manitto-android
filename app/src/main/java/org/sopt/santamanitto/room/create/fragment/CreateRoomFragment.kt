package org.sopt.santamanitto.room.create.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.sopt.santamanitto.R
import org.sopt.santamanitto.databinding.FragmentCreateRoomBinding
import org.sopt.santamanitto.room.create.data.ExpirationLiveData
import org.sopt.santamanitto.room.create.fragment.CreateRoomFragmentDirections.Companion.actionCreateRoomFragmentToCreateMissionsFragment
import org.sopt.santamanitto.room.create.setExpirationDiff
import org.sopt.santamanitto.room.create.setExpirationPeriod
import org.sopt.santamanitto.room.create.setExpirationPreview
import org.sopt.santamanitto.room.create.setExpirationTime
import org.sopt.santamanitto.room.create.viewmodel.CreateRoomAndMissionViewModel
import org.sopt.santamanitto.util.FragmentUtil.hideKeyboardOnOutsideEditText
import org.sopt.santamanitto.util.base.BaseFragment
import org.sopt.santamanitto.view.SantaEditText
import org.sopt.santamanitto.view.SantaPeriodPicker
import org.sopt.santamanitto.view.dialog.RoundDialogBuilder
import org.sopt.santamanitto.view.santanumberpicker.SantaNumberPicker
import org.sopt.santamanitto.view.setTextColorById

class CreateRoomFragment :
    BaseFragment<FragmentCreateRoomBinding>(R.layout.fragment_create_room, true) {

    companion object {
        private const val MAX_ROOM_NAME_LENGTH = 17
    }

    private val viewModel: CreateRoomAndMissionViewModel by activityViewModels()

    private val args: CreateRoomFragmentArgs by navArgs()

    private var isNewRoom = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        loadDataWhenModifying()

        initView()

        refreshUI(viewModel.expirationLiveData)

        subscribeUI()

        setOnClickListener()

        hideKeyboardOnOutsideEditText()
    }

    private fun loadDataWhenModifying() {
        viewModel.getRoomData(args.roomId)
        isNewRoom = args.roomId == -1
    }

    private fun initView() {
        binding.run {
            textviewCreateroomExpirationdescription.text = String.format(
                getString(R.string.createroom_expiration_description),
                SantaPeriodPicker.MINIMUM_PERIOD,
                SantaPeriodPicker.MAXIMUM_PERIOD
            )

            edittextCreateroomRoomname.hint =
                String.format(getString(R.string.createroom_roomname_hint), MAX_ROOM_NAME_LENGTH)
        }

        if (!isNewRoom) {
            binding.run {
                santabackgroundCreateroom.hideDescription()
                santabottombuttonCreateroom.text = getString(R.string.createroom_modify_done)
                santabackgroundCreateroom.title = getString(R.string.createroom_modify_title)
            }
        }
    }

    private fun setOnClickListener() {
        binding.run {
            santabottombuttonCreateroom.setOnClickListener {
                if (isNewRoom) {
                    findNavController().navigate(actionCreateRoomFragmentToCreateMissionsFragment())
                } else {
                    viewModel.modifyRoom {
                        findNavController().navigateUp()
                    }
                }
            }
            santabackgroundCreateroom.setOnBackKeyClickListener {
                if (isNewRoom) {
                    requireActivity().finish()
                } else {
                    findNavController().navigateUp()
                }
            }
            santaperiodpickerCreateroomExpiration.setOnPeriodChangedListener { period ->
                viewModel.setPeriod(period)
            }
            santaswitchCreateroomAmpm.setOnSwitchChangedListener { isAm ->
                viewModel.setAmPm(!isAm)
            }
            textviewCreateroomExpirationpreview.setOnClickListener {
                showTimePicker()
            }
        }
    }

    private fun subscribeUI() {
        viewModel.run {
            expirationLiveData.observe(viewLifecycleOwner, ::refreshUI)

            hint.observe(viewLifecycleOwner) { previousRoomName ->
                if (!previousRoomName.isNullOrEmpty()) {
                    binding.edittextCreateroomRoomname.hint = previousRoomName
                }
            }
        }
    }

    private fun refreshUI(expiration: ExpirationLiveData) {
        binding.run {
            setExpirationDiff(textviewCreateroomPreviewstart, expiration)
            setExpirationPreview(textviewCreateroomPreviewdate, expiration)
            setExpirationTime(textviewCreateroomExpirationpreview, expiration)
            setExpirationPeriod(santaperiodpickerCreateroomExpiration, expiration)
        }
    }

    private fun showTimePicker() {
        RoundDialogBuilder()
            .setTitle(getString(R.string.createroom_dialog_expiration_time_setting))
            .setContentView(getPickerView())
            .addHorizontalButton(getString(R.string.dialog_cancel))
            .addHorizontalButton(getString(R.string.dialog_confirm)) {
                val hour =
                    it!!.findViewById<SantaNumberPicker>(R.id.santanumberpicker_pickerdialog_hour)
                        .getCurrentNumber()
                val minute =
                    it.findViewById<SantaNumberPicker>(R.id.santanumberpicker_pickerdialog_minute)
                        .getCurrentNumber()
                viewModel.setTime(hour, minute)
            }
            .build()
            .show(parentFragmentManager, "createroom_timepicker")
    }

    private fun getPickerView(): View =
        LayoutInflater.from(requireContext())
            .inflate(R.layout.dialog_create_room_time_picker, binding.root as ViewGroup, false)
            .apply {
                findViewById<SantaNumberPicker>(R.id.santanumberpicker_pickerdialog_hour)
                    .setInitialPosition(viewModel.expirationLiveData.hour - 1)
                findViewById<SantaNumberPicker>(R.id.santanumberpicker_pickerdialog_minute)
                    .setInitialPosition(viewModel.expirationLiveData.minute)
            }
}