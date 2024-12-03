package com.insurtech.kanguro.designsystem.ui.composables.rentersEditAdditionalCoverage

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.ErrorComponent
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.FooterSection
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.JavierTitleXClose
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.ScreenLoader
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.FooterSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.InvoiceInterval
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.AdditionalCoverageItemTypeModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditAdditionalCoverage.domain.AdditionalCoverageCardModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditAdditionalCoverage.domain.RentersEditAdditionalCoverageEvent
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditAdditionalCoverage.sections.AdditionalCoveragesSection
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground

@Composable
fun RentersEditAdditionalCoverageScreenContent(
    modifier: Modifier = Modifier,
    footerSectionModel: FooterSectionModel?,
    additionalCoverages: List<AdditionalCoverageCardModel> = emptyList(),
    isLoading: Boolean,
    isError: Boolean,
    onEvent: (RentersEditAdditionalCoverageEvent) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        JavierTitleXClose(title = stringResource(id = R.string.additional_coverage_options)) {
            onEvent(RentersEditAdditionalCoverageEvent.OnClosePressed)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Box {
            if (!isLoading && !isError) {
                AdditionalCoverages(additionalCoverages, onEvent, footerSectionModel)
            }

            LoadingErrorStates(isLoading = isLoading, isError = isError) {
                onEvent(RentersEditAdditionalCoverageEvent.OnTryAgainPressed)
            }
        }
    }
}

@Composable
private fun AdditionalCoverages(
    additionalCoverages: List<AdditionalCoverageCardModel>,
    onEvent: (RentersEditAdditionalCoverageEvent) -> Unit,
    footerSectionModel: FooterSectionModel?
) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        AdditionalCoveragesSection(additionalCoverages) {
            onEvent(RentersEditAdditionalCoverageEvent.OnAdditionalCoveragePressed(it))
        }

        Footer(footerSectionModel = footerSectionModel) {
            onEvent(RentersEditAdditionalCoverageEvent.OnSubmitPressed)
        }
    }
}

@Composable
private fun AdditionalCoveragesSection(
    additionalCoverages: List<AdditionalCoverageCardModel>,
    onAdditionalCoveragePressed: (AdditionalCoverageCardModel) -> Unit
) {
    Spacer(modifier = Modifier.height(12.dp))

    AdditionalCoveragesSection(
        modifier = Modifier.padding(horizontal = 32.dp),
        additionalCoverages = additionalCoverages,
        onAdditionalCoveragePressed = onAdditionalCoveragePressed
    )
}

@Composable
private fun Footer(
    footerSectionModel: FooterSectionModel?,
    onSubmitPressed: () -> Unit
) {
    Spacer(modifier = Modifier.height(17.dp))
    FooterSection(
        modifier = Modifier.padding(horizontal = 32.dp),
        model = footerSectionModel,
        onSubmitPressed = onSubmitPressed
    )
}

@Composable
private fun LoadingErrorStates(
    isLoading: Boolean,
    isError: Boolean,
    onTryAgainPressed: () -> Unit
) {
    if (isLoading) {
        Loader()
    } else if (isError) {
        Error(onTryAgainPressed = onTryAgainPressed)
    }
}

@Composable
private fun Loader() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (loader) = createRefs()
        ScreenLoader(
            color = NeutralBackground,
            modifier = Modifier
                .size(width = 87.dp, height = 84.dp)
                .constrainAs(loader) {
                    linkTo(top = parent.top, bottom = parent.bottom, bias = 0.45f)
                    linkTo(start = parent.start, end = parent.end)
                }
        )
    }
}

@Composable
private fun Error(
    onTryAgainPressed: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 22.dp)
    ) {
        val (loader) = createRefs()
        ErrorComponent(
            onTryAgainPressed = onTryAgainPressed,
            modifier = Modifier
                .constrainAs(loader) {
                    linkTo(top = parent.top, bottom = parent.bottom, bias = 0.5f)
                    linkTo(start = parent.start, end = parent.end)
                }
        )
    }
}

@Preview
@Composable
fun RentersEdiAdditionalCoverageScreenContentPreview() {
    Surface {
        RentersEditAdditionalCoverageScreenContent(
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
            footerSectionModel = FooterSectionModel(
                buttonPrice = 1000.00.toBigDecimal(),
                totalPrice = 2000.00.toBigDecimal(),
                invoiceInterval = InvoiceInterval.MONTHLY
            ),
            isLoading = false,
            isError = false,
            onEvent = {}
        )
    }
}

@Preview
@Composable
fun RentersEditAdditionalCoverageScreenContentLoadingPreview() {
    Surface {
        RentersEditAdditionalCoverageScreenContent(
            additionalCoverages = emptyList(),
            footerSectionModel = FooterSectionModel(
                buttonPrice = 1000.00.toBigDecimal(),
                totalPrice = 2000.00.toBigDecimal(),
                invoiceInterval = InvoiceInterval.MONTHLY
            ),
            isLoading = true,
            isError = false,
            onEvent = {}
        )
    }
}

@Preview
@Composable
fun RentersEdiAdditionalCoverageScreenContentErrorPreview() {
    Surface {
        RentersEditAdditionalCoverageScreenContent(
            additionalCoverages = emptyList(),
            footerSectionModel = FooterSectionModel(
                buttonPrice = 1000.00.toBigDecimal(),
                totalPrice = 2000.00.toBigDecimal(),
                invoiceInterval = InvoiceInterval.MONTHLY
            ),
            isLoading = false,
            isError = true,
            onEvent = {}
        )
    }
}
