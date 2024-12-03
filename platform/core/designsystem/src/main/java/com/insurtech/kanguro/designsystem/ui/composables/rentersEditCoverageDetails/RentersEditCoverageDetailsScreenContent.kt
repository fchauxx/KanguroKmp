package com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Surface
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
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.RightButtonClose
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.ScreenLoader
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.FooterSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.InvoiceInterval
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.model.ChipItemModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.model.DeductibleItemsSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.model.DeductibleSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.model.LiabilitySectionModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.model.LossOfUseSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.model.RentersEditCoverageDetailsEvent
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.model.YourBelongingsUiModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.sections.DeductibleItemsSection
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.sections.DeductibleSection
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.sections.LiabilitySection
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.sections.LossOfUseSection
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.sections.YourBelongingsSection
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground

@Composable
fun RentersEditCoverageDetailsScreenContent(
    modifier: Modifier = Modifier,
    isLoadingState: Boolean = false,
    isErrorState: Boolean = false,
    liabilitySectionModel: LiabilitySectionModel?,
    yourBelongingsUiModel: YourBelongingsUiModel?,
    lossOfUseSectionModel: LossOfUseSectionModel?,
    deductibleSectionModel: DeductibleSectionModel?,
    deductibleItemsSectionModel: DeductibleItemsSectionModel?,
    footerSectionModel: FooterSectionModel?,
    onEvent: (RentersEditCoverageDetailsEvent) -> Unit
) {
    if (isLoadingState) {
        LoadingState(onEvent = onEvent)
    } else if (isErrorState) {
        ErrorState(onEvent = onEvent)
    } else {
        Content(
            modifier = modifier,
            liabilitySectionModel = liabilitySectionModel,
            yourBelongingsUiModel = yourBelongingsUiModel,
            lossOfUseSectionModel = lossOfUseSectionModel,
            deductibleSectionModel = deductibleSectionModel,
            deductibleItemsSectionModel = deductibleItemsSectionModel,
            footerSectionModel = footerSectionModel,
            onEvent = onEvent
        )
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    liabilitySectionModel: LiabilitySectionModel?,
    yourBelongingsUiModel: YourBelongingsUiModel?,
    lossOfUseSectionModel: LossOfUseSectionModel?,
    deductibleSectionModel: DeductibleSectionModel?,
    deductibleItemsSectionModel: DeductibleItemsSectionModel?,
    footerSectionModel: FooterSectionModel?,
    onEvent: (RentersEditCoverageDetailsEvent) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        JavierTitleXClose(title = stringResource(id = R.string.main_numbers_of_policy)) {
            onEvent(RentersEditCoverageDetailsEvent.OnClosePressed)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            Liability(liabilitySectionModel = liabilitySectionModel, onEvent = onEvent)

            YourBelongings(
                yourBelongingsUiModel = yourBelongingsUiModel,
                onEvent = onEvent
            )

            LossOfUse(lossOfUseSectionModel = lossOfUseSectionModel)

            Deductible(sectionModel = deductibleSectionModel, onEvent = onEvent)

            DeductibleItems(sectionModel = deductibleItemsSectionModel)

            Footer(footerSectionModel = footerSectionModel) {
                onEvent(RentersEditCoverageDetailsEvent.OnSubmitPressed)
            }
        }
    }
}

@Composable
private fun ErrorState(
    onEvent: (RentersEditCoverageDetailsEvent) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (closeButton, loader) = createRefs()

        RightButtonClose(
            onClosePressed = { onEvent(RentersEditCoverageDetailsEvent.OnClosePressed) },
            modifier = Modifier
                .padding(
                    top = 8.dp,
                    end = 8.dp
                )
                .constrainAs(closeButton) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        ErrorComponent(
            onTryAgainPressed = {
                onEvent(RentersEditCoverageDetailsEvent.OnTryAgainPressed)
            },
            modifier = Modifier
                .constrainAs(loader) {
                    linkTo(top = parent.top, bottom = parent.bottom, bias = 0.5f)
                    linkTo(start = parent.start, end = parent.end)
                }
                .padding(horizontal = 22.dp)
        )
    }
}

@Composable
private fun LoadingState(
    modifier: Modifier = Modifier,
    onEvent: (RentersEditCoverageDetailsEvent) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        JavierTitleXClose(title = stringResource(id = R.string.main_numbers_of_policy)) {
            onEvent(RentersEditCoverageDetailsEvent.OnClosePressed)
        }

        Loader()
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
private fun Liability(
    liabilitySectionModel: LiabilitySectionModel?,
    onEvent: (RentersEditCoverageDetailsEvent) -> Unit
) {
    if (liabilitySectionModel != null) {
        Spacer(modifier = Modifier.height(24.dp))
        LiabilitySection(
            liabilitySectionModel = liabilitySectionModel,
            onSelectedLiability = {
                onEvent(RentersEditCoverageDetailsEvent.OnLiabilitySelected(it))
            },
            onInformationPressed = {
                onEvent(RentersEditCoverageDetailsEvent.OnLiabilityInformationPressed)
            }
        )
    }
}

@Composable
private fun YourBelongings(
    yourBelongingsUiModel: YourBelongingsUiModel?,
    onEvent: (RentersEditCoverageDetailsEvent) -> Unit
) {
    if (yourBelongingsUiModel != null) {
        Spacer(modifier = Modifier.height(24.dp))

        Divider(
            color = NeutralBackground,
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(horizontal = 32.dp)
        )

        YourBelongingsSection(
            modifier = Modifier.padding(top = 32.dp),
            minValue = yourBelongingsUiModel.minValue,
            maxValue = yourBelongingsUiModel.maxValue,
            isEnabled = yourBelongingsUiModel.isInputEnabled,
            selectedValue = yourBelongingsUiModel.selectedValue,
            onValueChange = {
                onEvent(RentersEditCoverageDetailsEvent.OnYourBelongingsValueChange(it))
            }
        )
    }
}

@Composable
private fun LossOfUse(
    lossOfUseSectionModel: LossOfUseSectionModel?
) {
    if (lossOfUseSectionModel != null) {
        Spacer(modifier = Modifier.height(24.dp))
        LossOfUseSection(model = lossOfUseSectionModel)
    }
}

@Composable
private fun Deductible(
    sectionModel: DeductibleSectionModel?,
    onEvent: (RentersEditCoverageDetailsEvent) -> Unit
) {
    if (sectionModel != null) {
        Spacer(modifier = Modifier.height(24.dp))
        Divider(
            color = NeutralBackground,
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(horizontal = 32.dp)
        )

        DeductibleSection(
            modifier = Modifier.padding(top = 32.dp),
            sectionModel = sectionModel,
            onSelected = {
                if (sectionModel.isInputEnabled) {
                    onEvent(RentersEditCoverageDetailsEvent.OnDeductibleSelected(it))
                }
            },
            onInformationPressed = {
                onEvent(RentersEditCoverageDetailsEvent.OnDeductibleInformationPressed)
            }
        )
    }
}

@Composable
private fun DeductibleItems(
    sectionModel: DeductibleItemsSectionModel?
) {
    DeductibleItemsSection(sectionModel = sectionModel)
}

@Composable
private fun Footer(
    footerSectionModel: FooterSectionModel?,
    onSubmitPressed: () -> Unit
) {
    Spacer(modifier = Modifier.height(24.dp))
    FooterSection(
        modifier = Modifier.padding(horizontal = 32.dp),
        model = footerSectionModel,
        onSubmitPressed = onSubmitPressed
    )
}

@Preview
@Composable
private fun RentersEditCoverageDetailsScreenContentPreview() {
    Surface {
        RentersEditCoverageDetailsScreenContent(
            yourBelongingsUiModel =
            YourBelongingsUiModel(
                5000f,
                5000f,
                50000f
            ),
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
            onEvent = {},
            lossOfUseSectionModel = LossOfUseSectionModel(1000.toBigDecimal()),
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
            )
        )
    }
}
