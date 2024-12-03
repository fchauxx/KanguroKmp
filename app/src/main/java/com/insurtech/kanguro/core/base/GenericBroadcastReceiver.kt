package com.insurtech.kanguro.core.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class GenericBroadcastReceiver(val onReceive: (Intent) -> Unit) : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        p1?.let { onReceive(it) }
    }
}
