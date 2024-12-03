package com.insurtech.kanguro.common.date

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.concurrent.TimeUnit

object DateUtils {

    const val MONTH_DAY_YEAR_SLASH_FORMAT = "MM/dd/yyyy"

    fun betweenDates(firstDate: Date, secondDate: Date): Long {
        val diffInMillies: Long = Math.abs(secondDate.time - firstDate.time)
        val diff: Long = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS)

        return diff
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun betweenDates(firstDate: Instant, secondDate: Instant): Long {
        return ChronoUnit.DAYS.between(firstDate, secondDate)
    }

    // A number from 1-7, corresponding to the days of the week, starting on Sunday.
    fun getDayOfWeekOfToday(): Int {
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = Date()
        return calendar.get(Calendar.DAY_OF_WEEK)
    }

    fun getFormattedLocalDate(
        date: Date,
        pattern: String,
        timeZone: TimeZone = TimeZone.getDefault()
    ): String {
        val formatter = SimpleDateFormat(pattern, Locale.getDefault())
        formatter.timeZone = timeZone
        return formatter.format(date)
    }

    fun getEnglishFormattedLocalDate(
        date: Date,
        pattern: String,
        timeZone: TimeZone = TimeZone.getDefault()
    ): String {
        val formatter = SimpleDateFormat(pattern, Locale.ENGLISH)
        formatter.timeZone = timeZone
        return formatter.format(date)
    }

    fun Date.toLongFormatDate(): String {
        val formatter = SimpleDateFormat.getDateInstance(SimpleDateFormat.LONG, Locale.getDefault())
        return formatter.format(this)
    }
}
