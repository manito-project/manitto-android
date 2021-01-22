package org.sopt.santamanitto.util

import java.text.SimpleDateFormat
import java.util.*


object TimeUtil {

    fun getCurrentTime(): String {
        return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.KOREA).format(Date())
    }

}