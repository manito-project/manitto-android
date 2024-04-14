package org.sopt.santamanitto.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.Locale
import kotlin.math.roundToInt

object TimeUtil {

    private const val LOCAL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
    private const val SERVER_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    private const val WRONG_FORMAT = "날짜 형식이 잘못되었습니다."

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
        return inputFormat.parse(localFormatString)
            ?: throw IllegalArgumentException(WRONG_FORMAT)
    }

    fun isLaterThanNow(target: String): Boolean {
        val targetCal = getGregorianCalendarFromLocalFormat(target)
        val nowCal = GregorianCalendar(Locale.KOREA)
        return targetCal.timeInMillis >= nowCal.timeInMillis
    }

    fun getLocalFormatFromGregorianCalendar(gregorianCalendar: GregorianCalendar): String {
        return SimpleDateFormat(
            LOCAL_DATE_FORMAT,
            Locale.KOREA
        ).format(Date(gregorianCalendar.timeInMillis))
    }

    fun getGregorianCalendarFromLocalFormat(localFormatString: String): GregorianCalendar {
        return GregorianCalendar(Locale.KOREA).apply {
            time = getDateInstanceFromLocalFormat(localFormatString)
            add(Calendar.HOUR_OF_DAY, -9)
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

    //For Fake
    fun getCurrentTimeByServerFormat(): String =
        SimpleDateFormat(SERVER_DATE_FORMAT, Locale.KOREA).format(Date())


}