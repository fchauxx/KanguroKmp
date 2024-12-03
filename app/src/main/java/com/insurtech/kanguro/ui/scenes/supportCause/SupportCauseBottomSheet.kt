package com.insurtech.kanguro.ui.scenes.supportCause

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.insurtech.kanguro.R
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.common.enums.CharityCause
import com.insurtech.kanguro.core.utils.safeNavigate
import com.insurtech.kanguro.core.utils.setEndlessImageGif
import com.insurtech.kanguro.databinding.BottomsheetSupportCauseBinding
import com.insurtech.kanguro.ui.custom.KanguroBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SupportCauseBottomSheet : KanguroBottomSheetFragment<BottomsheetSupportCauseBinding>() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.SupportCause

    override val viewModel: SupportCauseViewModel by activityViewModels()

    override fun onCreateBinding(inflater: LayoutInflater) =
        BottomsheetSupportCauseBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setGif()
        setupListeners()
        setupObservers()

        viewModel.fetchCharityList()
    }

    private fun setGif() {
        binding.heartGif.setEndlessImageGif(R.drawable.gif_heart)
    }

    private fun setupListeners() {
        binding.closeButton.setOnClickListener {
            dismiss()
        }

        binding.buttonsList.animalsButton.setOnClickListener {
            viewModel.onCauseSelected(CharityCause.Animals)
            navigateToCategorySelected()
        }

        binding.buttonsList.globalWarmingButton.setOnClickListener {
            viewModel.onCauseSelected(CharityCause.GlobalWarming)
            navigateToCategorySelected()
        }

        binding.buttonsList.socialCausesButton.setOnClickListener {
            viewModel.onCauseSelected(CharityCause.SocialCauses)
            navigateToCategorySelected()
        }

        binding.buttonsList.latinCommunitiesButton.setOnClickListener {
            viewModel.onCauseSelected(CharityCause.LatinCommunities)
            navigateToCategorySelected()
        }
    }

    private fun navigateToCategorySelected() {
        findNavController().safeNavigate(
            SupportCauseBottomSheetDirections.actionGlobalCategorySelected()
        )
    }

    private fun setupObservers() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.isLoading.isVisible = it
            binding.buttonsList.root.isVisible = !it
        }

        viewModel.donationDone.observe(viewLifecycleOwner) {
            dismiss()
        }
    }
}
