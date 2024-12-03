package com.insurtech.kanguro.ui.scenes.rentersTrackYourClaims

import android.view.LayoutInflater
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.base.FullscreenFragment
import com.insurtech.kanguro.databinding.FragmentRentersTrackYourClaimsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RentersTrackYourClaimsFragment : FullscreenFragment<FragmentRentersTrackYourClaimsBinding>() {
    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.RentersTrackYourClaims

    override fun onCreateBinding(inflater: LayoutInflater): FragmentRentersTrackYourClaimsBinding =
        FragmentRentersTrackYourClaimsBinding.inflate(inflater)
}
