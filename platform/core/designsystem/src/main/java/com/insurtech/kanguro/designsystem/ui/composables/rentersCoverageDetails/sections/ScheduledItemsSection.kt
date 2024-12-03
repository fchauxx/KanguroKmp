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
import com.insurtech.kanguro.designsystem.toDollarFormat
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.ActionCardButton
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.StyledText
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.CoverageSectionCard
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.SectionHeader
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.section.ScheduledItemsSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.section.SectionHeaderModel

@Composable
fun ScheduledItemsSection(
    modifier: Modifier = Modifier,
    scheduledItemsSectionModel: ScheduledItemsSectionModel,
    onMyScheduledItemsPressed: () -> Unit
) {
    CoverageSectionCard(modifier = modifier) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            SectionHeader(
                model = SectionHeaderModel(
                    title = stringResource(id = R.string.scheduled_items),
                    icon = R.drawable.ic_scheduled_items_section
                )
            )

            StyledText(
                text = stringResource(
                    id = R.string.scheduled_item_section_text,
                    stringResource(id = R.string.dollar) + scheduledItemsSectionModel.totalValue.toDollarFormat()
                ),
                styledText = stringResource(id = R.string.dollar) + scheduledItemsSectionModel.totalValue.toDollarFormat()
            )

            ActionCardButton(
                text = stringResource(id = R.string.my_scheduled_items),
                icon = R.drawable.ic_document_favorite,
                onClick = onMyScheduledItemsPressed
            )
        }
    }
}

@Composable
@Preview
fun ScheduledItemsSectionPreview() {
    Surface {
        ScheduledItemsSection(
            modifier = Modifier.padding(16.dp),
            scheduledItemsSectionModel = ScheduledItemsSectionModel(10203.toBigDecimal()),
            onMyScheduledItemsPressed = {}
        )
    }
}
