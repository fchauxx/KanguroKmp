package com.insurtech.kanguro.designsystem.ui.composables.homeDashboard.model

import android.content.Context
import com.insurtech.kanguro.common.date.DateUtils
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.PetType
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

data class ItemReminderUiModel(
    val id: String,
    val type: ReminderTypeUiModel,
    val petId: Long?,
    val petName: String,
    val petType: PetType,
    val petPictureUrl: String,
    val clinicName: String? = null,
    val dueDate: Date? = null,
    val medicationDate: Date? = null,
    val claimId: String?
) {
    fun getFormattedMessage(context: Context): String {
        return when (type) {
            ReminderTypeUiModel.PetPicture -> {
                formatDueDate(context)
            }

            ReminderTypeUiModel.MedicalHistory -> {
                formatDueDate(context)
            }

            ReminderTypeUiModel.Claim -> {
                context.getString(R.string.reminder_missing_document)
            }

            ReminderTypeUiModel.FleaMedication -> {
                formatDate(medicationDate)
            }

            ReminderTypeUiModel.DirectPay -> {
                context.getString(R.string.processing)
            }

            else -> ""
        }
    }

    private fun formatDate(date: Date?): String {
        if (date == null) {
            return ""
        }
        val format = SimpleDateFormat("EEE dd MMM", Locale.getDefault())
        return format.format(date)
    }

    private fun formatDueDate(context: Context): String {
        if (dueDate == null) return ""

        val format = SimpleDateFormat("EEE dd MMM", Locale.getDefault())
        val today = Calendar.getInstance().time

        return if (dueDate > today) {
            val oneMonthInDays = 30
            val startOfToday: Date = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.time
            val diff = DateUtils.betweenDates(startOfToday, dueDate)
            if (diff < oneMonthInDays) {
                context.resources.getQuantityString(R.plurals.due_in, diff.toInt(), diff)
            } else {
                format.format(dueDate)
            }
        } else {
            format.format(dueDate)
        }
    }
}
