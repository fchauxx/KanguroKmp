package com.insurtech.kanguro.domain.dashboard

import android.content.Context
import android.os.Parcelable
import com.insurtech.kanguro.R
import com.insurtech.kanguro.common.date.DateUtils
import com.insurtech.kanguro.domain.model.Pet
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

@Parcelize
data class Reminder(
    val id: String,
    val type: ReminderType,
    val pet: Pet,
    val dueDate: Date?,
    val claimId: String?
) : Parcelable {

    fun formatDueDate(context: Context): String? {
        val format = SimpleDateFormat("EEE dd MMM", Locale.US)
        val today = Calendar.getInstance().time

        if (dueDate == null) return null

        return if (dueDate > today) {
            val oneMonthInDays = 30
            val startOfToday = LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()
            val diff = DateUtils.betweenDates(startOfToday, dueDate.toInstant()).toInt()
            if (diff < oneMonthInDays) {
                context.resources.getQuantityString(R.plurals.due_in, diff, diff)
            } else {
                format.format(dueDate)
            }
        } else {
            format.format(dueDate)
        }
    }
}

data class ReminderResponse(
    val id: String? = null,
    val userId: String? = null,
    val petId: Long? = null,
    val dueDate: Date? = null,
    val createdAt: Date? = null,
    val type: ReminderType? = null,
    val claimId: String? = null
)

fun ReminderResponse.toReminder(pet: Pet): Reminder? {
    return Reminder(
        id ?: return null,
        type ?: return null,
        pet,
        dueDate,
        claimId
    )
}
