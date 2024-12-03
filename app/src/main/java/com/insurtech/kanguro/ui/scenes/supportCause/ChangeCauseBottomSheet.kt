package com.insurtech.kanguro.ui.scenes.supportCause

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.insurtech.kanguro.NavDashboardDirections
import com.insurtech.kanguro.R
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.utils.safeNavigate
import com.insurtech.kanguro.core.utils.setEndlessImageGif
import com.insurtech.kanguro.databinding.BottomsheetChangeCauseBinding
import com.insurtech.kanguro.ui.custom.KanguroBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeCauseBottomSheet : KanguroBottomSheetFragment<BottomsheetChangeCauseBinding>() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.SupportCause

    override fun onCreateBinding(inflater: LayoutInflater) =
        BottomsheetChangeCauseBinding.inflate(inflater)

    override val viewModel: ChangeCauseViewModel by viewModels()

    private var itemExpanded = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.heartGif.setEndlessImageGif(R.drawable.gif_heart)

        setupListeners()
        setupObservers()

        viewModel.getCauseInfo()
    }

    private fun setupListeners() {
        binding.closeButton.setOnClickListener {
            dismiss()
        }

        binding.supportedCause.chooseCauseButton.setOnClickListener {
            findNavController().popBackStack()
            findNavController().safeNavigate(
                NavDashboardDirections.actionGlobalSupportCause()
            )
        }

        binding.supportedCause.headerTitle.setOnClickListener {
            itemExpanded = !itemExpanded

            binding.supportedCause.summaryIcon.setImageResource(
                if (itemExpanded) R.drawable.ic_up else R.drawable.ic_down
            )

            binding.supportedCause.charityInfo.isVisible = itemExpanded
        }
    }

    private fun setupObservers() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.isLoading.isVisible = isLoading
            binding.supportedCause.root.isVisible = !isLoading
        }

        viewModel.cause.observe(viewLifecycleOwner) {
            binding.supportedCause.charityInitials.text = it.attributes?.abbreviatedTitle
            binding.supportedCause.charityDescription.text = it.attributes?.description
            binding.supportedCause.charityTitle.text = it.attributes?.title
        }
    }
}
