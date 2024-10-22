package org.sopt.santamanitto.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.Locale
import java.util.TimeZone
import kotlin.math.roundToInt

object TimeUtil {

    private const val LOCAL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
    private const val SERVER_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    private const val WRONG_FORMAT = "날짜 형식이 잘못되었습니다."

    private val UTC_TIME_ZONE: TimeZone = TimeZone.getTimeZone("UTC")
    private val KOREA_TIME_ZONE: TimeZone = TimeZone.getTimeZone("Asia/Seoul")

    // UTC -> Local
    fun convertUtcToLocal(utcTime: String): String {
        return try {
            val utcFormat = SimpleDateFormat(SERVER_DATE_FORMAT, Locale.KOREA).apply {
                timeZone = UTC_TIME_ZONE
            }
            val localFormat = SimpleDateFormat(LOCAL_DATE_FORMAT, Locale.KOREA).apply {
                timeZone = KOREA_TIME_ZONE
            }
            val date = utcFormat.parse(utcTime)
            date?.let { localFormat.format(it) } ?: throw IllegalArgumentException(WRONG_FORMAT)
        } catch (e: Exception) {
            e.printStackTrace()
            WRONG_FORMAT
        }
    }

    // Local -> UTC
    fun convertLocalToUtc(localTime: String): String {
        return try {
            val localFormat = SimpleDateFormat(LOCAL_DATE_FORMAT, Locale.KOREA).apply {
                timeZone = KOREA_TIME_ZONE
            }
            val utcFormat = SimpleDateFormat(SERVER_DATE_FORMAT, Locale.KOREA).apply {
                timeZone = UTC_TIME_ZONE
            }
            val date = localFormat.parse(localTime)
            date?.let { utcFormat.format(it) } ?: throw IllegalArgumentException(WRONG_FORMAT)
        } catch (e: Exception) {
            e.printStackTrace()
            WRONG_FORMAT
        }
    }

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

    fun isExpired(expirationDate: String): Boolean {
        return try {
            val parsedExpirationDate = SimpleDateFormat(SERVER_DATE_FORMAT, Locale.KOREA).apply {
                timeZone = UTC_TIME_ZONE
            }.parse(expirationDate)
            parsedExpirationDate?.before(Date()) ?: true
        } catch (e: Exception) {
            e.printStackTrace()
            true
        }
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

    // 임시 처리
    fun String.changeTempServerToLocalFormat(pattern: String): String {
        return try {
            val formattedInput = if (this.length == 19) {
                "$this.000"
            } else {
                this
            }
            val serverDateFormat = SimpleDateFormat(pattern, Locale.KOREA)
            val localDateFormat = SimpleDateFormat(LOCAL_DATE_FORMAT, Locale.KOREA)
            val parsedDate = serverDateFormat.parse(formattedInput)
            parsedDate?.let {
                localDateFormat.format(it)
            } ?: ""
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

}