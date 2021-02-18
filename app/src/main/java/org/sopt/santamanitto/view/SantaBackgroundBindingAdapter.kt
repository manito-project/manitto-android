package org.sopt.santamanitto.view

import androidx.databinding.BindingAdapter
import org.sopt.santamanitto.R
import org.sopt.santamanitto.util.TimeUtil
import java.util.*

@BindingAdapter("setExpirationDescription")
fun setExpirationDescription(view: SantaBackground, expiration: String?) {
    if (expiration == null) {
        return
    }
    val calendar = TimeUtil.getGregorianCalendarFromLocalFormat(expiration)
    val amPm = if (calendar.get(Calendar.AM_PM) == Calendar.AM) {
        view.context.getString(R.string.am)
    } else {
        view.context.getString(R.string.pm)
    }
    view.setNoLogoDescription(
        String.format(
            view.context.getString(R.string.manittoroom_description),
            TimeUtil.getDayDiffFromNow(expiration),
            calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.DAY_OF_MONTH),
            amPm,
            calendar.get(Calendar.HOUR),
            calendar.get(Calendar.MINUTE)
        )
    )
}
