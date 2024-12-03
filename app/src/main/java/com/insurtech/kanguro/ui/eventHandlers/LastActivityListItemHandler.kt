package com.insurtech.kanguro.ui.eventHandlers

import com.insurtech.kanguro.domain.dashboard.LastActivity

interface LastActivityListItemHandler {
    fun onClickLastActivitiesItem(item: LastActivity)
}
