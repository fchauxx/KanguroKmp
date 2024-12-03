package com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain

import java.util.Calendar
import java.util.Date
import kotlin.math.abs

data class PetsCoverageSummaryCardModel(
    val id: String,
    val breed: String,
    val birthDate: Date? = null,
    val status: CoverageStatusUi,
    val pictureUrl: String,
    val name: String,
    val petType: PetType
) {

    fun getAge(): Int? {
        if (birthDate == null) {
            return null
        }

        val petDate = Calendar.getInstance().apply { time = birthDate }

        val currentDate = Calendar.getInstance()

        var difference = currentDate[Calendar.YEAR] - petDate[Calendar.YEAR]

        val isNotBirthMonthYet = currentDate[Calendar.MONTH] < petDate[Calendar.MONTH]

        val isSameMonth = currentDate[Calendar.MONTH] == petDate[Calendar.MONTH]

        val isNotBirthDayYet =
            isSameMonth && currentDate[Calendar.DAY_OF_MONTH] < petDate[Calendar.DAY_OF_MONTH]

        if (isNotBirthMonthYet || isNotBirthDayYet) {
            difference--
        }

        return abs(difference)
    }
}
