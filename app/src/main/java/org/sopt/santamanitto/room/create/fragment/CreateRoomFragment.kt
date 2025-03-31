package org.sopt.santamanitto.room.create.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.sopt.santamanitto.R
import org.sopt.santamanitto.analytics.AmplitudeManager
import org.sopt.santamanitto.analytics.EventType
import org.sopt.santamanitto.databinding.FragmentCreateRoomBinding
import org.sopt.santamanitto.room.create.data.ExpirationLiveData
import org.sopt.santamanitto.room.create.fragment.CreateRoomFragmentDirections.Companion.actionCreateRoomFragmentToCreateConfirmFragment
import org.sopt.santamanitto.room.create.fragment.CreateRoomFragmentDirections.Companion.actionCreateRoomFragmentToCreateMissionsFragment
import org.sopt.santamanitto.room.create.setExpirationDiff
import org.sopt.santamanitto.room.create.setExpirationPeriod
import org.sopt.santamanitto.room.create.setExpirationPreview
import org.sopt.santamanitto.room.create.setExpirationTime
import org.sopt.santamanitto.room.create.viewmodel.CreateRoomAndMissionViewModel
import org.sopt.santamanitto.util.FragmentUtil.hideKeyboardOnOutsideEditText
import org.sopt.santamanitto.util.base.BaseFragment
import org.sopt.santamanitto.view.SantaPeriodPicker
import org.sopt.santamanitto.view.dialog.RoundDialogBuilder
import org.sopt.santamanitto.view.santanumberpicker.SantaNumberPicker

class CreateRoomFragment :
    BaseFragment<FragmentCreateRoomBinding>(R.layout.fragment_create_room, true) {
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
        isNewRoom = args.roomId == ""
    }

    private fun initView() {
        binding.run {

            textviewCreateroomExpirationdescription.text =
                String.format(
                    getString(R.string.createroom_expiration_description),
                    SantaPeriodPicker.MINIMUM_PERIOD,
                    SantaPeriodPicker.MAXIMUM_PERIOD,
                )
        }

        if (isNewRoom) {
            AmplitudeManager.trackEvent("make_room_information", EventType.PAGE)
        } else {
            AmplitudeManager.trackEvent("room_edit", EventType.PAGE)
            binding.run {
                santabackgroundCreateroom.hideDescription()
                santabottombuttonSkiproom.visibility = View.GONE
                santabottombuttonCreateroom.text = getString(R.string.createroom_modify_done)
                santabackgroundCreateroom.title = getString(R.string.createroom_modify_title)
            }
        }
    }

    private fun setOnClickListener() {
        binding.run {
            santabottombuttonCreateroom.setOnClickListener {
                if (isNewRoom) {
                    AmplitudeManager.trackEvent("make_room_mission_btn", EventType.BUTTON)
                    findNavController().navigate(actionCreateRoomFragmentToCreateMissionsFragment())
                } else {
                    AmplitudeManager.trackEvent("room_edit_complete_btn", EventType.BUTTON)
                    viewModel.modifyRoom {
                        findNavController().navigateUp()
                    }
                }
            }
            santabottombuttonSkiproom.setOnClickListener {
                AmplitudeManager.trackEvent(
                    "make_room_information_no_mission_btn",
                    EventType.BUTTON
                )
                showNoMissionDialog()
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
                    it!!
                        .findViewById<SantaNumberPicker>(R.id.santanumberpicker_pickerdialog_hour)
                        .getCurrentNumber()
                val minute =
                    it
                        .findViewById<SantaNumberPicker>(R.id.santanumberpicker_pickerdialog_minute)
                        .getCurrentNumber()
                viewModel.setTime(hour, minute)
            }.build()
            .show(parentFragmentManager, "createroom_timepicker")
    }

    private fun getPickerView(): View =
        LayoutInflater
            .from(requireContext())
            .inflate(R.layout.dialog_create_room_time_picker, binding.root as ViewGroup, false)
            .apply {
                findViewById<SantaNumberPicker>(R.id.santanumberpicker_pickerdialog_hour).apply {
                    setInitialPosition(viewModel.expirationLiveData.hour - 1)
                    setRange(1, 12)
                }
                findViewById<SantaNumberPicker>(R.id.santanumberpicker_pickerdialog_minute).apply {
                    setInitialPosition(viewModel.expirationLiveData.minute)
                    setRange(0, 59)
                }
            }

    private fun showNoMissionDialog() {
        AmplitudeManager.trackEvent("mission_no_popup", EventType.MODAL)
        RoundDialogBuilder()
            .setContentText(
                getString(R.string.createmission_dialog_no_mission),
                true,
            ).addHorizontalButton(getString(R.string.createmission_skip_bottom_button)) {
                AmplitudeManager.trackEvent("mission_no_popup_skip_btn", EventType.BUTTON)
                findNavController().navigate(actionCreateRoomFragmentToCreateConfirmFragment())
            }.addHorizontalButton(getString(R.string.createroom_btn_next)) {
                AmplitudeManager.trackEvent("mission_no_popup_mission_btn", EventType.BUTTON)
                findNavController().navigate(actionCreateRoomFragmentToCreateMissionsFragment())
            }.build()
            .show(parentFragmentManager, "done_dialog")
    }
}
