package com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.sections

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
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.ChipGrid
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.EditCoverageDetailSectionTitle
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.model.ChipItemModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.model.LiabilitySectionModel

@Composable
fun LiabilitySection(
    modifier: Modifier = Modifier,
    liabilitySectionModel: LiabilitySectionModel,
    onSelectedLiability: (ChipItemModel) -> Unit,
    onInformationPressed: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
    ) {
        EditCoverageDetailSectionTitle(
            title = stringResource(id = R.string.your_liability_section_title),
            contentDescription = stringResource(id = R.string.liability_information),
            onInformationPressed = onInformationPressed
        )

        ChipGrid(
            modifier = Modifier.fillMaxWidth(),
            chipItems = liabilitySectionModel.liabilityItems,
            onSelected = {
                if (liabilitySectionModel.isInputEnabled) {
                    onSelectedLiability(it)
                }
            }
        )
    }
}

@Composable
@Preview
fun LiabilitySectionPreview() {
    Surface {
        LiabilitySection(
            modifier = Modifier.padding(16.dp),
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
            onSelectedLiability = { },
            onInformationPressed = { }
        )
    }
}
