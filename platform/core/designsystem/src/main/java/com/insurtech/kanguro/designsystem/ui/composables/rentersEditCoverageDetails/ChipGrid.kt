package com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.model.ChipItemModel
import kotlin.math.ceil

@Composable
fun ChipGrid(
    modifier: Modifier = Modifier,
    chipItems: List<ChipItemModel>,
    onSelected: (ChipItemModel) -> Unit
) {
    val itemHeight = 80
    val lines = ceil(chipItems.size / 2.0)
    val gridHeight = (lines * itemHeight + (8 * (lines - 1))).dp

    LazyVerticalGrid(
        modifier = modifier.height(gridHeight),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        userScrollEnabled = false
    ) {
        items(chipItems) { chipItem ->
            ChipItem(
                chipItemModel = chipItem,
                onPressed = {
                    onSelected(chipItem)
                }
            )
        }
    }
}

@Composable
@Preview
private fun LiabilityChipGridPreview() {
    Surface {
        ChipGrid(
            modifier = Modifier.padding(16.dp),
            chipItems = listOf(
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
            ),
            onSelected = { }
        )
    }
}
