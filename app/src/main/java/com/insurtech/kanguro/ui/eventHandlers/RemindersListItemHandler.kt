package com.insurtech.kanguro.ui.eventHandlers

import com.insurtech.kanguro.domain.dashboard.Reminder

interface RemindersListItemHandler {
    fun onClickRemindersItem(item: Reminder)
    fun onClickSeeAllReminders()
}
