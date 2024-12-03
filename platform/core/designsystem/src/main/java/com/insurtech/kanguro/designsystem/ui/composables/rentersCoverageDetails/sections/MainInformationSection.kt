package com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.ActionCardButton
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.StyledText
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.PolicyStatus
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.CoverageSectionCard
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.DocumentationCard
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.PlanSummaryCard
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.SectionHeader
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.PlanSummaryCardModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.section.MainInformationSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.section.SectionHeaderModel
import com.insurtech.kanguro.designsystem.ui.theme.PrimaryDarkest
import com.insurtech.kanguro.designsystem.ui.theme.TertiaryDarkest
import com.insurtech.kanguro.domain.model.PolicyDocument
import java.math.BigDecimal

@Composable
fun MainInformationSection(
    modifier: Modifier = Modifier,
    model: MainInformationSectionModel,
    onWhatIsCoveredPressed: () -> Unit = {},
    onDocumentsPressed: (PolicyDocument) -> Unit = {},
    onEditPolicyDetailPressed: () -> Unit = {}
) {
    CoverageSectionCard(modifier = modifier) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            SectionHeader(
                model = SectionHeaderModel(
                    title = stringResource(id = R.string.renters_insurance),
                    subtitle = stringResource(id = R.string.renters_insurance).uppercase(),
                    renewDate = model.renewDate,
                    startDate = model.startDate,
                    endDate = model.endDate,
                    icon = R.drawable.ic_renters_coverage_home
                )
            )
            StyledText(
                text = stringResource(
                    id = R.string.renters_insurance_status,
                    stringResource(model.policyStatus.titleRes)
                ),
                highlightColor = if (model.policyStatus == PolicyStatus.ACTIVE) {
                    TertiaryDarkest
                } else {
                    PrimaryDarkest
                },
                styledText = stringResource(model.policyStatus.titleRes)
            )
            CardButtons(
                model,
                onWhatIsCoveredPressed,
                onDocumentsPressed,
                onEditPolicyDetailPressed
            )
        }
    }
}

@Composable
private fun CardButtons(
    model: MainInformationSectionModel,
    onWhatIsCoveredPressed: () -> Unit,
    onDocumentsPressed: (PolicyDocument) -> Unit,
    onEditPolicyDetailPressed: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        PlanSummaryCard(planSummaryCardModel = model.planSummary)
        ActionCardButton(
            text = stringResource(id = R.string.what_is_covered),
            icon = R.drawable.ic_coverage_list
        ) {
            onWhatIsCoveredPressed()
        }

        DocumentationCard(documents = model.documents) {
            onDocumentsPressed(it)
        }
        if (model.policyStatus == PolicyStatus.ACTIVE) {
            ActionCardButton(
                text = stringResource(id = R.string.edit_policy_detail),
                icon = R.drawable.ic_edit
            ) {
                onEditPolicyDetailPressed()
            }
        }
    }
}

@Composable
@Preview
private fun MainInformationSectionPreview() {
    Surface {
        MainInformationSection(
            modifier = Modifier.padding(16.dp),
            model = MainInformationSectionModel(
                planSummary = PlanSummaryCardModel(
                    liability = BigDecimal(100),
                    deductible = BigDecimal(100),
                    personalProperty = BigDecimal(100),
                    lossOfUse = BigDecimal(100)
                ),
                documents = listOf(
                    PolicyDocument(
                        id = 0,
                        filename = "Document File Name"
                    )
                ),
                renewDate = "10/30/2023",
                startDate = "10/30/2022",
                endDate = "10/30/2023",
                policyStatus = PolicyStatus.ACTIVE
            )
        )
    }
}
