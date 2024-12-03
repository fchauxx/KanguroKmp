package com.insurtech.kanguro.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import io.sentry.Sentry
import timber.log.Timber

class ReferrerReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val referrer = intent?.getStringExtra("referrer")
        if (referrer != null) {
            Sentry.captureMessage("Referrer is $referrer\"")
            Timber.tag("Referrer").d("Referrer is $referrer")
        }
    }
}
