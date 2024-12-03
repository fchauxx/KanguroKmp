package com.insurtech.kanguro.ui.scenes.rentersEditAdditionalCoverage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.insurtech.kanguro.R
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.utils.collectLatestIn
import com.insurtech.kanguro.databinding.BottomsheetRentersEditAdditionalCoverageBinding
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.FooterSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.InvoiceInterval
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.AdditionalCoverageItemTypeModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditAdditionalCoverage.RentersEditAdditionalCoverageScreenContent
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditAdditionalCoverage.domain.AdditionalCoverageCardModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditAdditionalCoverage.domain.RentersEditAdditionalCoverageEvent
import com.insurtech.kanguro.ui.custom.KanguroBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RentersEditAdditionalCoverageBottomSheet :
    KanguroBottomSheetFragment<BottomsheetRentersEditAdditionalCoverageBinding>() {

    override val screenName = AnalyticsEnums.Screen.RentersEditAdditionalCoverage

    override val isDraggable: Boolean = false

    override val viewModel: RentersEditAdditionalCoverageViewModel by viewModels()

    override fun onCreateBinding(inflater: LayoutInflater): BottomsheetRentersEditAdditionalCoverageBinding =
        BottomsheetRentersEditAdditionalCoverageBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        observeViewModel()
    }

    private fun setupUi() {
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                RentersEditAdditionalCoverageBottomSheet(
                    viewModel = viewModel,
                    onEvent = ::onEvent
                )
            }
        }
    }

    private fun observeViewModel() {
        viewModel.isSubmitSuccess.collectLatestIn(viewLifecycleOwner) { isSuccess ->
            if (isSuccess != null) {
                if (isSuccess) {
                    findNavController().popBackStack()
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.error_dialog_generic_message),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        viewModel.isCalculatePricingError.collectLatestIn(viewLifecycleOwner) { isError ->
            if (isError != null && isError) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.error_dialog_generic_message),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun onEvent(event: RentersEditAdditionalCoverageEvent) {
        when (event) {
            is RentersEditAdditionalCoverageEvent.OnClosePressed -> findNavController().popBackStack()

            is RentersEditAdditionalCoverageEvent.OnSubmitPressed -> {
                viewModel.putAdditionalCoverage()
            }

            is RentersEditAdditionalCoverageEvent.OnAdditionalCoveragePressed -> viewModel.handleAdditionalCoverageSwitch(
                event.additionalCoverage
            )

            RentersEditAdditionalCoverageEvent.OnTryAgainPressed -> viewModel.getAdditionalCoverages()
        }
    }
}

@Composable
private fun RentersEditAdditionalCoverageBottomSheet(
    viewModel: RentersEditAdditionalCoverageViewModel,
    onEvent: (RentersEditAdditionalCoverageEvent) -> Unit
) {
    val uiState: RentersEditAdditionalCoverageViewModel.UiState by viewModel.uiState.collectAsState()

    RentersEditAdditionalCoverageScreenContent(
        onEvent = onEvent,
        additionalCoverages = uiState.additionalCoverageModels,
        footerSectionModel = uiState.footerSectionModel,
        isLoading = uiState.isLoading,
        isError = uiState.isError
    )
}

@Preview
@Composable
fun RentersEditAdditionalCoverageBottomSheetPreview() {
    Surface {
        RentersEditAdditionalCoverageScreenContent(
            footerSectionModel = FooterSectionModel(
                buttonPrice = 1000.00.toBigDecimal(),
                totalPrice = 2000.00.toBigDecimal(),
                invoiceInterval = InvoiceInterval.MONTHLY
            ),
            additionalCoverages = listOf(
                AdditionalCoverageCardModel(
                    type = AdditionalCoverageItemTypeModel.FRAUD_PROTECTION,
                    description = "Should you need to use your insurance, we'll cover the genuine costs of repairing or replacing items.",
                    monthlyPrice = "3.99",
                    isPreviouslySelected = false,
                    isSelected = false
                ),
                AdditionalCoverageCardModel(
                    type = AdditionalCoverageItemTypeModel.FRAUD_PROTECTION,
                    description = "Should you need to use your insurance, we'll cover the genuine costs of repairing or replacing items.",
                    monthlyPrice = "3.99",
                    isPreviouslySelected = false,
                    isSelected = true
                ),
                AdditionalCoverageCardModel(
                    type = AdditionalCoverageItemTypeModel.FRAUD_PROTECTION,
                    description = "Should you need to use your insurance, we'll cover the genuine costs of repairing or replacing items.",
                    monthlyPrice = "3.99",
                    isPreviouslySelected = true,
                    isSelected = true
                )
            ),
            isLoading = false,
            isError = false,
            onEvent = {}
        )
    }
}
