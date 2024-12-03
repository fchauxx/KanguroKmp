package com.insurtech.kanguro.ui.scenes.reminders

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.insurtech.kanguro.R
import com.insurtech.kanguro.domain.dashboard.Reminder
import com.insurtech.kanguro.domain.dashboard.ReminderType

object ReminderListItemUtils {
    @DrawableRes
    fun getBackground(reminderType: ReminderType?): Int {
        return when (reminderType) {
            ReminderType.MedicalHistory -> R.drawable.reminder_background
            ReminderType.Claim -> R.drawable.reminder_claim_background
            ReminderType.PetPicture -> R.drawable.reminder_background
            else -> R.drawable.reminder_background
        }
    }

    @DrawableRes
    fun getBackgroundStroke(reminderType: ReminderType?): Int {
        return when (reminderType) {
            ReminderType.MedicalHistory -> R.drawable.reminder_background_stroke
            ReminderType.Claim -> R.drawable.reminder_claim_background_stroke
            ReminderType.PetPicture -> R.drawable.reminder_background_stroke
            else -> R.drawable.reminder_background_stroke
        }
    }

    fun getSubtitle(context: Context, reminder: Reminder): String? {
        return when (reminder.type) {
            ReminderType.MedicalHistory -> reminder.formatDueDate(context)
            ReminderType.Claim -> context.getString(R.string.reminder_missing_document)
            ReminderType.PetPicture -> reminder.formatDueDate(context)
        }
    }

    @ColorRes
    fun getSubtitleColor(reminderType: ReminderType): Int {
        return when (reminderType) {
            ReminderType.MedicalHistory -> R.color.negative_dark
            ReminderType.Claim -> R.color.warning_extra_dark
            ReminderType.PetPicture -> R.color.negative_dark
        }
    }
}
