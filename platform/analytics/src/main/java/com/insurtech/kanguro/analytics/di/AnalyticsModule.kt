package com.insurtech.kanguro.analytics.di

import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.insurtech.kanguro.analytics.AnalyticsService
import com.insurtech.kanguro.analytics.IAnalyticsService
import com.insurtech.kanguro.analytics.firebase.KanguroFirebaseAnalytics
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AnalyticsModule {

    @Provides
    @Singleton
    fun providesAnalyticsService(): IAnalyticsService =
        AnalyticsService(KanguroFirebaseAnalytics(Firebase.analytics))
}
