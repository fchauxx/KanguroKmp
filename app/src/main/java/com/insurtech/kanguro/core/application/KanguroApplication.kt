package com.insurtech.kanguro.core.application

import android.app.Application
import android.webkit.WebView
import com.airbnb.epoxy.Carousel
import com.insurtech.kanguro.BuildConfig
import com.insurtech.kanguro.remoteconfigdata.repository.IRemoteConfigManager
import com.insurtech.kanguro.ui.custom.ReviewManagerWrapper
import dagger.hilt.android.HiltAndroidApp
import io.sentry.SentryLevel
import io.sentry.android.core.SentryAndroid
import io.sentry.android.timber.SentryTimberIntegration
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class KanguroApplication : Application() {

    @Inject
    lateinit var reviewManager: ReviewManagerWrapper

    @Inject
    lateinit var remoteConfigManager: IRemoteConfigManager

    override fun onCreate() {
        super.onCreate()
        Carousel.setDefaultGlobalSnapHelperFactory(null)

        // Unfortunate fix for the locale bug, where opening a webview resets the app locale.
        WebView(this).destroy()

        if (!BuildConfig.DEBUG) {
            SentryAndroid.init(this) { options ->
                options.addIntegration(
                    SentryTimberIntegration(
                        minEventLevel = SentryLevel.ERROR,
                        minBreadcrumbLevel = SentryLevel.INFO
                    )
                )
            }
            Timber.plant(FirebaseCrashReportingTree())
        } else {
            Timber.plant(Timber.DebugTree())
        }

        reviewManager.setReviews(applicationContext)
    }
}
