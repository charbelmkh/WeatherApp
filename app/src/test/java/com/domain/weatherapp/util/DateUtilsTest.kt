package com.domain.weatherapp.util

import com.domain.weatherapp.core.utils.DateUtils
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DateUtilsTest {




    @Test
    fun dateConversion_dateUtils_convertToDay() {
        //const val DATE_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss"
        val result=DateUtils.dateToWeekDay("2020-01-21 13:13:13")
        assertNotNull(result)
        assertThat(result, `is`("TUESDAY"))
    }

    @Test
    fun dateConversion_dateUtils_returnEmptyString() {
        //const val DATE_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss"
        val result=DateUtils.dateToWeekDay("aaaaaaaaaaaaaa")
        assertNotNull(result)
        assertTrue(result.isEmpty());

    }

    @Test
    fun dateConversion_dateUtils_convertToTime() {
        //const val DATE_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss"
        val result=DateUtils.dateToTime("2020-01-21 13:13:13")
        assertNotNull(result)
        assertThat(result, `is`("13:13 PM"))
    }


}
