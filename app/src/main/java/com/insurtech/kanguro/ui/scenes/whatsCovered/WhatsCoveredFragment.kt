package com.insurtech.kanguro.ui.scenes.whatsCovered

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.navArgs
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.databinding.FragmentWhatsCoveredBinding
import com.insurtech.kanguro.domain.policy.getPlanName
import com.insurtech.kanguro.ui.custom.KanguroBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WhatsCoveredFragment : KanguroBottomSheetFragment<FragmentWhatsCoveredBinding>() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.CoverageWhatsCovered

    private val args: WhatsCoveredFragmentArgs by navArgs()

    override fun onCreateBinding(inflater: LayoutInflater) =
        FragmentWhatsCoveredBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.closeButton.setOnClickListener { dismiss() }
        args.policy.getPlanName()?.let { binding.planName.setText(it) }
    }
}
