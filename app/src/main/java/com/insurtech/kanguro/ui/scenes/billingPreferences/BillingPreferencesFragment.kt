package com.insurtech.kanguro.ui.scenes.billingPreferences

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.databinding.FragmentBillingPreferencesBinding
import com.insurtech.kanguro.domain.billingPreferences.BillingHistoryItem
import com.insurtech.kanguro.ui.custom.KanguroBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BillingPreferencesFragment : KanguroBottomSheetFragment<FragmentBillingPreferencesBinding>() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.BillingPreferences

    private val controller: BillingPreferencesEpoxyController by lazy {
        BillingPreferencesEpoxyController(requireContext(), ::onAccordionClickHandler)
    }

    override fun onCreateBinding(inflater: LayoutInflater) =
        FragmentBillingPreferencesBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            recyclerView.setControllerAndBuildModels(controller)
            closeButton.setOnClickListener {
                dismiss()
            }
        }
    }

    private fun onAccordionClickHandler(item: BillingHistoryItem) {
    }
}
