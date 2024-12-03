package com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.ActionCardButton
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.PolicyStatus
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.AdditionalCoverageItem
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.CoverageSectionCard
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.SectionHeader
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.AdditionalCoverageItemModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.AdditionalCoverageItemTypeModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.section.AdditionalCoverageSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.section.SectionHeaderModel
import java.math.BigDecimal

@Composable
fun AdditionalCoverageSection(
    modifier: Modifier = Modifier,
    additionalCoverageSectionModel: AdditionalCoverageSectionModel,
    onInfoButtonPressed: (AdditionalCoverageItemTypeModel) -> Unit = {},
    onEditAdditionalCoveragePressed: () -> Unit = {}
) {
    CoverageSectionCard(modifier = modifier) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            SectionHeader(
                model = SectionHeaderModel(
                    title = stringResource(id = R.string.renters_additional_coverage),
                    icon = R.drawable.ic_renters_award
                )
            )

            additionalCoverageSectionModel.additionalCoverages.forEach { additionalCoverageItem ->
                AdditionalCoverageItem(
                    modifier = Modifier.fillMaxWidth(),
                    item = additionalCoverageItem,
                    onInfoPressed = { onInfoButtonPressed(additionalCoverageItem.type) },
                    onAddItemPressed = onEditAdditionalCoveragePressed,
                    policyStatus = additionalCoverageSectionModel.policyStatus
                )
            }

            if (additionalCoverageSectionModel.policyStatus == PolicyStatus.ACTIVE) {
                ActionCardButton(
                    text = stringResource(id = R.string.edit_additional_coverage),
                    icon = R.drawable.ic_award,
                    onClick = onEditAdditionalCoveragePressed
                )
            }
        }
    }
}

@Composable
@Preview
private fun AdditionalCoverageSectionPreview() {
    Surface {
        AdditionalCoverageSection(
            modifier = Modifier.padding(16.dp),
            additionalCoverageSectionModel = AdditionalCoverageSectionModel(
                listOf(
                    AdditionalCoverageItemModel(
                        type = AdditionalCoverageItemTypeModel.REPLACEMENT_COST,
                        coverageLimit = null,
                        deductible = null,
                        intervalTotal = 3.99.toBigDecimal()
                    ),
                    AdditionalCoverageItemModel(
                        type = AdditionalCoverageItemTypeModel.WATER_SEWER_BACKUP,
                        coverageLimit = BigDecimal(2500),
                        deductible = BigDecimal(250),
                        intervalTotal = 3.99.toBigDecimal()
                    ),
                    AdditionalCoverageItemModel(
                        type = AdditionalCoverageItemTypeModel.FRAUD_PROTECTION,
                        coverageLimit = BigDecimal(15000),
                        deductible = BigDecimal(100),
                        intervalTotal = 3.99.toBigDecimal()
                    )
                )
            )
        )
    }
}
