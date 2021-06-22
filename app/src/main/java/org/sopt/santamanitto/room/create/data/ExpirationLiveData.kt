package org.sopt.santamanitto.room.create.data

import androidx.lifecycle.LiveData
import org.sopt.santamanitto.util.TimeUtil
import org.sopt.santamanitto.view.SantaPeriodPicker
import java.util.*

class ExpirationLiveData : LiveData<ExpirationLiveData>() {

    companion object {
        private const val TAG = "ExpirationLiveData"
        private const val INITIAL_HOUR = 10
        private const val INITIAL_MINUTE = 0
        private const val INITIAL_AM_PM = Calendar.AM
    }


    private var expirationDate = GregorianCalendar().apply {
        add(Calendar.DAY_OF_MONTH, SantaPeriodPicker.DEFAULT_PERIOD)
        set(Calendar.HOUR, INITIAL_HOUR)
        set(Calendar.MINUTE, INITIAL_MINUTE)
        set(Calendar.MILLISECOND, 0)
        set(Calendar.AM_PM, INITIAL_AM_PM)
    }

    val year: Int
        get() = expirationDate.get(Calendar.YEAR)

    val month: Int
        get() = expirationDate.get(Calendar.MONTH) + 1

    val day: Int
        get() = expirationDate.get(Calendar.DAY_OF_MONTH)

    val hour: Int
        get() {
            var convertedHour = expirationDate.get(Calendar.HOUR)
            if (convertedHour == 0) {
                convertedHour = 12
            }
            return convertedHour
        }

    val minute: Int
        get() = expirationDate.get(Calendar.MINUTE)

    val isAm: Boolean
        get() = expirationDate.get(Calendar.AM_PM) == Calendar.AM

    private var _period = SantaPeriodPicker.DEFAULT_PERIOD
    var period: Int
        get() = _period
        set(value) {
            val diff = value - this._period
            expirationDate.add(Calendar.DAY_OF_MONTH, diff)
            this._period = value
            postValue(this)
        }

    fun setTime(hour: Int, minute: Int) {
        expirationDate.run {
            val convertedHour = if (hour == 12) {
                0
            } else {
                hour
            }
            set(Calendar.HOUR, convertedHour)
            set(Calendar.MINUTE, minute)
            postValue(this@ExpirationLiveData)
        }
    }

    fun setAmPm(isAm: Boolean) {
        if (isAm) {
            expirationDate.set(Calendar.AM_PM, Calendar.AM)
        } else {
            expirationDate.set(Calendar.AM_PM, Calendar.PM)
        }
        postValue(this)
    }

    fun init(expiration: String) {
        expirationDate = TimeUtil.getGregorianCalendarFromLocalFormat(expiration)
        _period = TimeUtil.getDayDiffFromNow(expiration)
        postValue(this)
    }

    override fun toString(): String {
        return TimeUtil.getLocalFormatFromGregorianCalendar(expirationDate)
    }
}