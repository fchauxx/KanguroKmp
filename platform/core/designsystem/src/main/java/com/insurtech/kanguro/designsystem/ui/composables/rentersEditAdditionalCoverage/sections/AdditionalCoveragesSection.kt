package com.insurtech.kanguro.designsystem.ui.composables.rentersEditAdditionalCoverage.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.AdditionalCoverageItemTypeModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditAdditionalCoverage.AdditionalCoverageCard
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditAdditionalCoverage.domain.AdditionalCoverageCardModel

@Composable
fun AdditionalCoveragesSection(
    modifier: Modifier = Modifier,
    additionalCoverages: List<AdditionalCoverageCardModel>,
    onAdditionalCoveragePressed: (AdditionalCoverageCardModel) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        additionalCoverages.forEach { additionalCoverage ->
            AdditionalCoverageCard(
                model = additionalCoverage,
                onSwitchChanged = onAdditionalCoveragePressed
            )
        }
    }
}

@Composable
@Preview
private fun AdditionalCoveragesSectionPreview() {
    Surface {
        AdditionalCoveragesSection(
            modifier = Modifier.padding(16.dp),
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
            onAdditionalCoveragePressed = {}
        )
    }
}
