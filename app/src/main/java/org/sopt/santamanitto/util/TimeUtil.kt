package org.sopt.santamanitto.util

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

object TimeUtil {

    private const val LOCAL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
    private const val SERVER_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

    fun getDayDiff(later: String, early: String): Int {
        val laterCalendar = getGregorianCalendarFromLocalFormat(later)
        val earlyCalendar = getGregorianCalendarFromLocalFormat(early)
        initHourAndBelow(laterCalendar)
        initHourAndBelow(earlyCalendar)
        return getDayDiff(laterCalendar.timeInMillis, earlyCalendar.timeInMillis)
    }

    private fun getDayDiff(later: Long, early: Long): Int {
        val gap: Long = later - early
        val dayDiff: Float = (gap / (24f * 60 * 60 * 1000))
        return dayDiff.roundToInt()
    }

    private fun getDateInstanceFromLocalFormat(localFormatString: String): Date {
        val inputFormat = SimpleDateFormat(LOCAL_DATE_FORMAT, Locale.KOREA)
        return inputFormat.parse(localFormatString)!!
    }

    fun isLaterThanNow(target: String): Boolean {
        return getDateInstanceFromLocalFormat(target).time >= Date().time
    }

    fun getLocalFormatFromGregorianCalendar(gregorianCalendar: GregorianCalendar): String {
        return SimpleDateFormat(LOCAL_DATE_FORMAT, Locale.KOREA).format(Date(gregorianCalendar.timeInMillis))
    }

    fun getGregorianCalendarFromLocalFormat(localFormatString: String): GregorianCalendar {
        return GregorianCalendar().apply {
            timeInMillis = getDateInstanceFromLocalFormat(localFormatString).time
        }
    }

    fun getDayDiffFromNow(localFormatString: String): Int {
        val later = getGregorianCalendarFromLocalFormat(localFormatString)
        initHourAndBelow(later)
        val early = GregorianCalendar()
        initHourAndBelow(early)
        return getDayDiff(later.timeInMillis, early.timeInMillis)
    }

    private fun initHourAndBelow(gregorianCalendar: GregorianCalendar) {
        gregorianCalendar.apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.MILLISECOND, 0)
        }
    }
}