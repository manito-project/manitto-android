package org.sopt.santamanitto.util

import java.text.SimpleDateFormat
import java.util.*


object TimeUtil {

    fun getCurrentTime(): String {
        return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.KOREA).format(Date())
    }

    fun getDifferentOfDaysFromNow(to: String): Int {
        return getDifferentOfDays(Date(), getDateFromString(to))
    }

    fun getDifferentOfDays(from: Date, to: Date): Int {
        val gap: Long = from.time - to.time
        return (gap / ( 24 * 60 * 60 * 1000)).toInt()
    }

    fun getDateFromString(date: String): Date {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA)
        return inputFormat.parse(date)!!
    }

    fun isLaterThanNow(target: String): Boolean {
        return getDateFromString(target).time >= Date().time
    }
}