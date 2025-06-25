package org.sopt.santamanitto.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Calendar.DAY_OF_YEAR
import java.util.Calendar.YEAR
import java.util.Date
import java.util.GregorianCalendar
import java.util.Locale
import java.util.TimeZone

/**
 * 클라이언트에서 사용하는 모든 시간 : KST(UTC +9) 기준
 *
 * 서버에서 사용하는 모든 시간 : UTC(UTC +0) 기준
 */

object TimeUtil {

    private const val UTC_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    private const val NO_TIME_FORMAT = "yyyy-MM-dd"
    private const val ONLY_TIME_FORMAT = "HH:mm"
    private const val WRONG_FORMAT = "날짜 형식이 잘못되었습니다."

    private val UTC_TIME_ZONE: TimeZone = TimeZone.getTimeZone("UTC")
    private val KOREA_TIME_ZONE: TimeZone = TimeZone.getTimeZone("Asia/Seoul")

    private val utcFormat = SimpleDateFormat(UTC_DATE_FORMAT, Locale.KOREA).apply {
        timeZone = UTC_TIME_ZONE
    }
    private val kstFormat = SimpleDateFormat(UTC_DATE_FORMAT, Locale.KOREA).apply {
        timeZone = KOREA_TIME_ZONE
    }
    private val noTimeKstFormat = SimpleDateFormat(NO_TIME_FORMAT, Locale.KOREA).apply {
        timeZone = KOREA_TIME_ZONE
    }
    private val onlyTimeKstFormat = SimpleDateFormat(ONLY_TIME_FORMAT, Locale.KOREA).apply {
        timeZone = KOREA_TIME_ZONE
    }

    fun getDayDiff(later: String, early: String): Int {
        return try {
            val laterDate = noTimeKstFormat.parse(later)
            val earlyDate = noTimeKstFormat.parse(early)
            if (laterDate != null && earlyDate != null) {
                val diffInMillis = laterDate.time - earlyDate.time
                val diffInDays = (diffInMillis / (1000 * 60 * 60 * 24)).toInt()
                diffInDays
            } else {
                throw IllegalArgumentException(WRONG_FORMAT)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            -1
        }
    }

    fun getDayDiffFromNow(kstFormatString: String): Int {
        return getDayDiff(kstFormatString, kstFormat.format(Date()))
    }

    fun isExpired(utcFormatString: String): Boolean {
        return try {
            getDayDiffFromNow(convertUtcToKst(utcFormatString)) <= 0
        } catch (e: Exception) {
            e.printStackTrace()
            true
        }
    }

    // GregorianCalendar -> Utc(+0)
    fun convertGregorianCalendarToUtc(calendar: GregorianCalendar): String {
        return utcFormat.format(Date(calendar.timeInMillis))
    }

    // Utc(+0) -> Utc(KST)
    fun convertUtcToKst(utcFormatString: String): String {
        return kstFormat.format(
            utcFormat.parse(utcFormatString) ?: throw IllegalArgumentException(WRONG_FORMAT)
        )
    }

    // Utc(+0) -> Utc(KST) Date
    fun convertUtcToKstDate(utcFormatString: String): String {
        return noTimeKstFormat.format(
            utcFormat.parse(utcFormatString) ?: throw IllegalArgumentException(WRONG_FORMAT)
        )
    }

    // Utc(+0) -> Utc(KST) Time
    fun convertUtcToKstTime(utcFormatString: String): String {
        return onlyTimeKstFormat.format(
            utcFormat.parse(utcFormatString) ?: throw IllegalArgumentException(WRONG_FORMAT)
        )
    }

    // Utc(KST) -> GregorianCalendar
    fun convertKstToGregorianCalendar(kstFormatString: String): GregorianCalendar {
        return GregorianCalendar(KOREA_TIME_ZONE).apply {
            time = kstFormat.parse(kstFormatString) ?: throw IllegalArgumentException(WRONG_FORMAT)
        }
    }

    // Utc(KST) -> Calendar
    fun convertKstToCalendar(kstFormatString: String): Calendar {
        return Calendar.getInstance(KOREA_TIME_ZONE).apply {
            time = kstFormat.parse(kstFormatString) ?: throw IllegalArgumentException(WRONG_FORMAT)
        }
    }

    // Fake 객체로 서버 대체 위한 UTC 포맷 날짜 생성
    fun getDateWithOffsetFromNow(offsetDays: Int): String {
        return utcFormat.format(
            Calendar.getInstance(UTC_TIME_ZONE).apply { add(Calendar.DATE, offsetDays) }.time
        )
    }

    // 현재 시각과의 차이를 지정된 텍스트로 변환
    fun convertToElapsedTime(utcFormatString: String): String {
        val eventDate = utcFormat.parse(utcFormatString) ?: return ""

        val diffSec = (Date().time - eventDate.time) / 1000
        when {
            diffSec < 0 -> return ""
            diffSec < 60 -> return "방금"
            diffSec < 3_600 -> return "${diffSec / 60}분 전"
        }

        val diffHours = diffSec / 3_600
        val eventCal = Calendar.getInstance(KOREA_TIME_ZONE).apply { time = eventDate }
        val nowCal = Calendar.getInstance(KOREA_TIME_ZONE)
        if (eventCal[YEAR] == nowCal[YEAR] && eventCal[DAY_OF_YEAR] == nowCal[DAY_OF_YEAR]) {
            return "${diffHours}시간 전"
        }

        val diffDays = diffHours / 24
        return when {
            diffDays <= 6 -> "${diffDays}일 전"
            diffDays <= 13 -> "1주일 전"
            diffDays <= 29 -> "${diffDays / 7}주 전"
            else -> noTimeKstFormat.format(eventCal.time)
        }
    }
}