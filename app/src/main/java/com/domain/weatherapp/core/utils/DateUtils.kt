package com.domain.weatherapp.core.utils

import java.text.SimpleDateFormat
import java.util.*

class DateUtils {


    companion object {
        const val DATE_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss"
        const val DATE_FORMAT_HH_MM_SS_AA = "HH:mm a"
        @JvmStatic
        fun dateToWeekDay(date: String): String {
            try {
                val sdf = SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD_HH_MM_SS, Locale.getDefault())
                val cal = Calendar.getInstance()
                cal.time = sdf.parse(date)
                when (cal.get(Calendar.DAY_OF_WEEK)) {
                    Calendar.SUNDAY -> return "SUNDAY"
                    Calendar.MONDAY -> return "MONDAY"
                    Calendar.TUESDAY -> return "TUESDAY"
                    Calendar.WEDNESDAY -> return "WEDNESDAY"
                    Calendar.THURSDAY -> return "THURSDAY"
                    Calendar.FRIDAY -> return "FRIDAY"
                    Calendar.SATURDAY -> return "SATURDAY"

                }
            } catch (e: Exception) {
            }

            return ""
        }

        @JvmStatic
        fun dateToTime(date: String): String {

            try {
                val sdf2 = SimpleDateFormat(DATE_FORMAT_HH_MM_SS_AA, Locale.getDefault())
                val sdf = SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD_HH_MM_SS, Locale.getDefault())
                val time = sdf.parse(date)
                return sdf2.format(time)
            } catch (e: Exception) {
            }
            return ""
        }
    }


}