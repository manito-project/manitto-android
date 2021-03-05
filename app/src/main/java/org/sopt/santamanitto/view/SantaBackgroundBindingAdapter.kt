package org.sopt.santamanitto.view

import androidx.databinding.BindingAdapter
import org.sopt.santamanitto.R
import org.sopt.santamanitto.util.TimeUtil
import java.lang.StringBuilder
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
    val dayDiff = TimeUtil.getDayDiffFromNow(expiration)
    val description = StringBuilder()
    if (dayDiff == 0) {
        description.append(view.context.getString(R.string.manittoroom_description_today_prefix))
    } else {
        description.append(String.format(
                view.context.getString(R.string.manittoroom_description_not_today_prefix), dayDiff)
        )
    }
    description.append(" ").append(
            String.format(view.context.getString(R.string.manittoroom_description_suffix),
                    calendar.get(Calendar.MONTH) + 1,
                    calendar.get(Calendar.DAY_OF_MONTH),
                    amPm,
                    calendar.get(Calendar.HOUR),
                    calendar.get(Calendar.MINUTE)
            )
    )

    view.setNoLogoDescription(description.toString())
}

@BindingAdapter("setFinishDescription")
fun setFinishDescription(view: SantaBackground, period: Int?) {
    if (period == null) {
        return
    }
    view.setNoLogoDescription(
            String.format(view.context.getString(R.string.finish_manitto_title), period)
    )
}
