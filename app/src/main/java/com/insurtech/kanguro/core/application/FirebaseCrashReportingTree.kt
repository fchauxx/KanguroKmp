package com.insurtech.kanguro.core.application

import android.util.Log
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import timber.log.Timber

class FirebaseCrashReportingTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.ERROR || priority == Log.WARN) {
            if (t != null) {
                Firebase.crashlytics.recordException(t)
            } else {
                Firebase.crashlytics.log("$tag: $message")
            }
        } else {
            return
        }
    }
}
