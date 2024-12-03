package com.insurtech.kanguro.analytics

import com.insurtech.kanguro.analytics.firebase.KanguroFirebaseAnalytics
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class AnalyticsServiceTest {

    private lateinit var kanguroFirebaseAnalyticsMock: KanguroFirebaseAnalytics

    private lateinit var analyticsService: AnalyticsService

    @Before
    fun setUp() {
        kanguroFirebaseAnalyticsMock = mockk()

        analyticsService = AnalyticsService(kanguroFirebaseAnalyticsMock)
    }

    @Test
    fun `Analytics log screen, when it is requested to log a new analytics event, then verify log event command is called`() {
        val screenName = AnalyticsEnums.Screen.Welcome

        every { kanguroFirebaseAnalyticsMock.logEvent(screenName) } returns Unit

        // ACT

        analyticsService.analyticsLogScreen(screenName)

        // ASSERT

        verify(exactly = 1) { kanguroFirebaseAnalyticsMock.logEvent(screenName) }
    }
}
