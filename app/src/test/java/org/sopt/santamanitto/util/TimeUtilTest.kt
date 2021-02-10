package org.sopt.santamanitto.util

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Test

class TimeUtilTest {

    @Test
    fun getCurrentTime() {
        println(TimeUtil.getCurrentTimeByLocalFormat())
    }

    @Test
    fun getDateFromStringTest() {
        val testCase1 = "2020-10-13 12:44:22"
        val date = TimeUtil.getDateInstanceFromLocalFormat(testCase1)
        print(date)
    }

    @Test
    fun getDifferentOfDays() {
        assertThat(getDifferentOfDaysTestCase("2020-10-13 12:44:22", "2020-10-15 12:44:22"), `is`(2))
        assertThat(getDifferentOfDaysTestCase("2020-12-31 12:44:22", "2021-01-10 12:44:22"), `is`(10))
        assertThat(getDifferentOfDaysTestCase("2020-04-30 12:44:22", "2020-05-01 12:44:22"), `is`(1))
    }

    private fun getDifferentOfDaysTestCase(from: String, to: String): Int {
        val fromDate = TimeUtil.getDateInstanceFromLocalFormat(from)
        val toDate = TimeUtil.getDateInstanceFromLocalFormat(to)
        return TimeUtil.getDifferentOfDays(fromDate, toDate)
    }
}