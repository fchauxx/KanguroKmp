package com.insurtech.kanguro.ui.scenes.rentersChangeMyAddress

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.base.FullscreenFragment
import com.insurtech.kanguro.databinding.FragmentRentersChangeMyAddressBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RentersChangeMyAddressFragment : FullscreenFragment<FragmentRentersChangeMyAddressBinding>() {

    override val screenName = AnalyticsEnums.Screen.RentersChangeMyAddress

    override val viewModel: RentersChangeMyAddressViewModel by viewModels()

    override fun onCreateBinding(inflater: LayoutInflater): FragmentRentersChangeMyAddressBinding =
        FragmentRentersChangeMyAddressBinding.inflate(inflater)
}
