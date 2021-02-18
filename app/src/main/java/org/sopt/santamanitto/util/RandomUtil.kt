package org.sopt.santamanitto.util

import java.util.*


object RandomUtil {

    fun getRandomValue(maxValue: Int): Int {
        return Random().nextInt(maxValue)
    }
}