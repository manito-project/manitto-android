package org.sopt.santamanitto.util

import java.text.SimpleDateFormat
import java.util.*

object TimeUtil {

    private const val LOCAL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
    private const val SERVER_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

    fun getCurrentTimeByLocalFormat(): String {
        return SimpleDateFormat(LOCAL_DATE_FORMAT, Locale.KOREA).format(Date())
    }

    fun getCurrentTimeByServerFormat(): String {
        return SimpleDateFormat(SERVER_DATE_FORMAT, Locale.KOREA).format(Date())
    }

    fun getDifferentOfDaysFromNow(to: String): Int {
        return getDifferentOfDays(Date(), getDateInstanceFromLocalFormat(to))
    }

    fun getDifferentOfDays(from: Date, to: Date): Int {
        val gap: Long = from.time - to.time
        return (gap / ( 24 * 60 * 60 * 1000)).toInt()
    }

    fun getDifferentOfDays(from: String, to: String): Int {
        val fromTime = getGregorianCalendarFromLocalFormat(from).timeInMillis
        val toTime = getGregorianCalendarFromLocalFormat(to).timeInMillis
        val gap: Long = fromTime - toTime
        return (gap / ( 24 * 60 * 60 * 1000)).toInt()
    }

    fun getDateInstanceFromLocalFormat(localFormatString: String): Date {
        val inputFormat = SimpleDateFormat(LOCAL_DATE_FORMAT, Locale.KOREA)
        return inputFormat.parse(localFormatString)!!
    }

    fun isLaterThanNow(target: String): Boolean {
        return getDateInstanceFromLocalFormat(target).time >= Date().time
    }

    fun getServerFormatFromGregorianCalendar(gregorianCalendar: GregorianCalendar): String {
        return SimpleDateFormat(SERVER_DATE_FORMAT, Locale.KOREA).format(Date(gregorianCalendar.timeInMillis))
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
        val now = GregorianCalendar()
        initHourAndBelow(now)
        val target = getGregorianCalendarFromLocalFormat(localFormatString)
        initHourAndBelow(target)
        return ((target.timeInMillis - now.timeInMillis) / ( 24 * 60 * 60 * 1000)).toInt() + 1
    }

    private fun initHourAndBelow(gregorianCalendar: GregorianCalendar) {
        gregorianCalendar.apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.MILLISECOND, 0)
        }
    }
}