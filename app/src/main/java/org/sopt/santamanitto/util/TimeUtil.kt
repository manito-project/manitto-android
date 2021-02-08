package org.sopt.santamanitto.util

import java.text.SimpleDateFormat
import java.util.*

object TimeUtil {

    private const val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"

    fun getCurrentTime(): String {
        return SimpleDateFormat(DATE_FORMAT, Locale.KOREA).format(Date())
    }

    fun getDifferentOfDaysFromNow(to: String): Int {
        return getDifferentOfDays(Date(), getDateFromString(to))
    }

    fun getDifferentOfDays(from: Date, to: Date): Int {
        val gap: Long = from.time - to.time
        return (gap / ( 24 * 60 * 60 * 1000)).toInt()
    }

    fun getDateFromString(date: String): Date {
        val inputFormat = SimpleDateFormat(DATE_FORMAT, Locale.KOREA)
        return inputFormat.parse(date)!!
    }

    fun isLaterThanNow(target: String): Boolean {
        return getDateFromString(target).time >= Date().time
    }

    fun addDateToNow(days: Int): String {
        return SimpleDateFormat(DATE_FORMAT, Locale.KOREA).format(
                GregorianCalendar().apply {
                    add(Calendar.DAY_OF_MONTH, days)
                }.time
        )
    }

    fun getTimeToString(expirationDate: GregorianCalendar): String {
        return "${expirationDate.get(Calendar.HOUR)} : ${expirationDate.get(Calendar.MINUTE)}"
    }
}