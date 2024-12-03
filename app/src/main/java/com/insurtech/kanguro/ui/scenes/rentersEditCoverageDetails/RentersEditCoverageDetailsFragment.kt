package com.insurtech.kanguro.ui.scenes.rentersEditCoverageDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.insurtech.kanguro.R
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.utils.collectLatestIn
import com.insurtech.kanguro.databinding.FragmentRentersEditCoverageDetailsBinding
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.FooterSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.InvoiceInterval
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.InformationDialog
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.RentersEditCoverageDetailsScreenContent
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.model.ChipItemModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.model.DeductibleItemsSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.model.DeductibleSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.model.LiabilitySectionModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.model.LossOfUseSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.model.RentersEditCoverageDetailsEvent
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.model.YourBelongingsUiModel
import com.insurtech.kanguro.designsystem.ui.theme.BKSParagraphRegular
import com.insurtech.kanguro.ui.custom.KanguroBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RentersEditCoverageDetailsFragment :
    KanguroBottomSheetFragment<FragmentRentersEditCoverageDetailsBinding>() {

    override val screenName = AnalyticsEnums.Screen.RentersEditCoverageDetails

    override val viewModel: RentersEditCoverageDetailsViewModel by viewModels()
    override fun onCreateBinding(inflater: LayoutInflater): FragmentRentersEditCoverageDetailsBinding =
        FragmentRentersEditCoverageDetailsBinding.inflate(inflater)

    override val isDraggable: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        observeViewModel()
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
                viewModel.clearIsSubmitSuccess()
            }
        }
    }

    private fun setupUi() {
        binding.composeView.apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
            setContent {
                RentersEditCoverageDetailsFragment(
                    viewModel = viewModel,
                    onEvent = ::onEvent
                )
            }
        }
    }

    private fun onEvent(event: RentersEditCoverageDetailsEvent) {
        when (event) {
            is RentersEditCoverageDetailsEvent.OnClosePressed -> findNavController().popBackStack()

            is RentersEditCoverageDetailsEvent.OnYourBelongingsValueChange -> onYourBelongingsValueChange(
                event.value
            )

            is RentersEditCoverageDetailsEvent.OnLiabilityInformationPressed -> viewModel.handleLiabilityInformationPressed(
                show = true
            )

            is RentersEditCoverageDetailsEvent.OnLiabilitySelected -> {
                viewModel.handleLiabilitySelected(event.liabilityItemModel.id)
            }

            is RentersEditCoverageDetailsEvent.OnDeductibleSelected -> {
                viewModel.handleDeductibleSelected(event.itemModel.id)
            }

            is RentersEditCoverageDetailsEvent.OnDeductibleInformationPressed ->
                viewModel.handleDeductibleInformationPressed(true)

            is RentersEditCoverageDetailsEvent.OnSubmitPressed -> viewModel.onSubmitPressed()

            is RentersEditCoverageDetailsEvent.OnTryAgainPressed -> viewModel.refresh()
        }
    }

    private fun onYourBelongingsValueChange(value: Float) {
        viewModel.updateYourBelongingsSelectedValue(value)
    }
}

@Composable
private fun RentersEditCoverageDetailsFragment(
    viewModel: RentersEditCoverageDetailsViewModel,
    onEvent: (RentersEditCoverageDetailsEvent) -> Unit
) {
    val uiState: RentersEditCoverageDetailsViewModel.UiState by viewModel.uiState.collectAsState()

    val isLoadingState =
        uiState.isLiabilityLoading || uiState.isDeductibleSectionLoading || uiState.isDeductibleSectionItemsLoading || uiState.isLossOfUseSectionLoading || uiState.isYourBelongingsLoading

    if (uiState.showLiabilityInformation) {
        InformationDialog(
            title = stringResource(id = R.string.liability),
            content = {
                Text(
                    text = stringResource(id = R.string.liability_dialog_information),
                    style = BKSParagraphRegular
                )
            },
            onDismiss = { viewModel.handleLiabilityInformationPressed(show = false) }
        )
    }

    if (uiState.showDeductibleInformation) {
        InformationDialog(
            title = stringResource(id = R.string.deductible),
            content = {
                Text(
                    text = stringResource(id = R.string.deductible_explanation),
                    style = BKSParagraphRegular
                )
            },
            onDismiss = { viewModel.handleDeductibleInformationPressed(false) }
        )
    }

    RentersEditCoverageDetailsScreenContent(
        isLoadingState = isLoadingState,
        isErrorState = uiState.isErrorState,
        yourBelongingsUiModel = uiState.yourBelongingsUiModel,
        liabilitySectionModel = uiState.liabilityModel,
        onEvent = onEvent,
        lossOfUseSectionModel = uiState.lossOfUseSectionModel,
        deductibleSectionModel = uiState.deductibleSectionModel,
        footerSectionModel = uiState.footerSectionModel,
        deductibleItemsSectionModel = uiState.deductibleItemsSectionModel
    )
}

@Preview
@Composable
private fun RentersEditCoverageDetailsFragmentPreview() {
    Surface {
        RentersEditCoverageDetailsScreenContent(
            yourBelongingsUiModel =
            YourBelongingsUiModel(
                5000f,
                5000f,
                50000f
            ),
            onEvent = {},
            liabilitySectionModel = LiabilitySectionModel(
                liabilityItems = listOf(
                    ChipItemModel(
                        id = "1",
                        value = 1000.toBigDecimal(),
                        isMostPopular = false,
                        isSelected = false
                    ),
                    ChipItemModel(
                        id = "2",
                        value = 2000.toBigDecimal(),
                        isMostPopular = false,
                        isSelected = false
                    ),
                    ChipItemModel(
                        id = "3",
                        value = 3000.toBigDecimal(),
                        isMostPopular = true,
                        isSelected = true
                    ),
                    ChipItemModel(
                        id = "4",
                        value = 4000.toBigDecimal(),
                        isMostPopular = false,
                        isSelected = false
                    )
                )
            ),
            deductibleSectionModel = DeductibleSectionModel(
                items = listOf(
                    ChipItemModel(
                        id = "1",
                        value = 1000.toBigDecimal(),
                        isMostPopular = false,
                        isSelected = false
                    ),
                    ChipItemModel(
                        id = "2",
                        value = 2000.toBigDecimal(),
                        isMostPopular = false,
                        isSelected = false
                    ),
                    ChipItemModel(
                        id = "3",
                        value = 3000.toBigDecimal(),
                        isMostPopular = true,
                        isSelected = true
                    ),
                    ChipItemModel(
                        id = "4",
                        value = 4000.toBigDecimal(),
                        isMostPopular = false,
                        isSelected = false
                    )
                )
            ),
            footerSectionModel = FooterSectionModel(
                buttonPrice = 10.00.toBigDecimal(),
                totalPrice = 100.00.toBigDecimal(),
                invoiceInterval = InvoiceInterval.MONTHLY
            ),
            deductibleItemsSectionModel = DeductibleItemsSectionModel(
                1000.toBigDecimal(),
                5000.toBigDecimal()
            ),
            lossOfUseSectionModel = LossOfUseSectionModel(1000.toBigDecimal())
        )
    }
}
