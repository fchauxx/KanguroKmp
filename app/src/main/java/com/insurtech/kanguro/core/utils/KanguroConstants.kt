package com.insurtech.kanguro.core.utils

import com.insurtech.kanguro.BuildConfig

object KanguroConstants {

    const val DB_NAME = "kanguro_db"
    const val TRANSITION_ANIMATION_DURATION = 600L

    const val BROADCAST_ACTION_REFRESH_REMINDERS = "${BuildConfig.APPLICATION_ID}.broadcast.refresh_reminders"
    const val BROADCAST_ACTION_REFRESH_POLICIES = "${BuildConfig.APPLICATION_ID}.broadcast.refresh_policies"
    const val BROADCAST_ACTION_PROFILE_CLOSED = "${BuildConfig.APPLICATION_ID}.broadcast.profile_closed"

    const val BROADCAST_USER_LOGGED_OFF = "${BuildConfig.APPLICATION_ID}.broadcast.user_logged_off"

    const val BROADCAST_ON_NEW_FIREBASE_TOKEN = "${BuildConfig.APPLICATION_ID}.broadcast.on_new_firebase_token"
}
