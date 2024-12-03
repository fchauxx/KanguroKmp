package com.insurtech.kanguro.ui.scenes.notification

import android.view.LayoutInflater
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.databinding.FragmentNotificationsBinding
import com.insurtech.kanguro.ui.custom.KanguroBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationsFragment : KanguroBottomSheetFragment<FragmentNotificationsBinding>() {
    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.Notifications

    override fun onCreateBinding(inflater: LayoutInflater): FragmentNotificationsBinding =
        FragmentNotificationsBinding.inflate(inflater)
}
