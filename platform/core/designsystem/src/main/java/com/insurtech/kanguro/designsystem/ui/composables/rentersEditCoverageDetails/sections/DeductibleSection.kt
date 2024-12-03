package com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.sections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.ChipGrid
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.EditCoverageDetailSectionTitle
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.model.ChipItemModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.model.DeductibleSectionModel

@Composable
fun DeductibleSection(
    modifier: Modifier = Modifier,
    sectionModel: DeductibleSectionModel,
    onSelected: (ChipItemModel) -> Unit,
    onInformationPressed: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EditCoverageDetailSectionTitle(
            title = stringResource(id = R.string.deductible),
            contentDescription = stringResource(id = R.string.deductible_information),
            onInformationPressed = onInformationPressed
        )

        ChipGrid(
            modifier = Modifier.fillMaxWidth(),
            chipItems = sectionModel.items,
            onSelected = onSelected
        )
    }
}

@Composable
@Preview
fun DeductibleSectionPreview() {
    Surface {
        DeductibleSection(
            modifier = Modifier.padding(16.dp),
            sectionModel = DeductibleSectionModel(
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
            onSelected = { },
            onInformationPressed = { }
        )
    }
}
