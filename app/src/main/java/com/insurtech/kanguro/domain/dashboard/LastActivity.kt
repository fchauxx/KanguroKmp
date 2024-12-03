package com.insurtech.kanguro.domain.dashboard

import java.text.SimpleDateFormat
import java.util.*

data class LastActivity(var activityName: String, var petName: String, var date: Date, var rate: Int) {
    fun getNameAndDate(): String {
        val format = SimpleDateFormat("EEE dd MMM")
        val formattedDate = format.format(date)

        return "$petName - $formattedDate"
    }
}
