package org.sopt.santamanitto.room.create.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import org.sopt.santamanitto.NetworkViewModel
import org.sopt.santamanitto.room.ExpirationLiveData
import org.sopt.santamanitto.util.TimeUtil
import org.sopt.santamanitto.view.SantaPeriodPicker

class CreateRoomViewModel : NetworkViewModel() {

    val expirationLiveData = ExpirationLiveData()

    var roomName = MutableLiveData<String?>(null)

    var nameIsNullOrEmpty = Transformations.map(roomName) {
        it.isNullOrBlank()
    }

    fun setDayDiff(dayDIff: Int) {
        expirationLiveData.dayDiff = dayDIff
    }

    fun setAmPm(isAm: Boolean) {
        expirationLiveData.setAmPm(isAm)
    }
}