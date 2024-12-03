package com.insurtech.kanguro.designsystem.ui.composables.commumComponents.petsCoverageList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.AddCoverageButton
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.LiveVetListCard
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.PetsCoverageSummaryCard
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.CoverageStatusUi
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.PetType
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.PetsCoverageSummaryCardModel
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground
import java.util.Calendar

@Composable
fun LiveVetPetsCoveragesListComponent(
    modifier: Modifier = Modifier,
    coverages: List<PetsCoverageSummaryCardModel>,
    onCoveragePressed: (String) -> Unit,
    onAddPetPressed: () -> Unit,
    onLiveVetPressed: () -> Unit
) {
    if (coverages.isEmpty()) {
        EmptyCoveragesComponent(modifier, onAddPetPressed)
    } else {
        LazyRow(
            modifier = modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                LiveVetListCard {
                    onLiveVetPressed()
                }
            }
            items(coverages) { coverage ->
                PetsCoverageSummaryCard(coverage = coverage) {
                    onCoveragePressed(coverage.id)
                }
            }

            item {
                AddCoverageButton(onAddPetPressed = onAddPetPressed)
            }
        }
    }
}

@Preview(device = "spec:width=2000px,height=2340px,dpi=440")
@Composable
private fun LiveVetPetsCoveragesListComponentPreview() {
    Surface(
        color = NeutralBackground
    ) {
        LiveVetPetsCoveragesListComponent(
            modifier = Modifier
                .padding(vertical = 16.dp),
            coverages = listOf(
                PetsCoverageSummaryCardModel(
                    id = "1",
                    breed = "Labradoodle - Small (11-30 lbs)",
                    birthDate = Calendar.getInstance().apply {
                        set(Calendar.YEAR, 2019)
                        set(Calendar.MONTH, Calendar.NOVEMBER)
                        set(Calendar.DAY_OF_MONTH, 29)
                    }.time,
                    status = CoverageStatusUi.Active,
                    pictureUrl = "",
                    name = "Oliver",
                    petType = PetType.Dog
                ),
                PetsCoverageSummaryCardModel(
                    id = "1",
                    breed = "Labradoodle - Small (11-30 lbs)",
                    birthDate = Calendar.getInstance().apply {
                        set(Calendar.YEAR, 2019)
                        set(Calendar.MONTH, Calendar.NOVEMBER)
                        set(Calendar.DAY_OF_MONTH, 29)
                    }.time,
                    status = CoverageStatusUi.Active,
                    pictureUrl = "",
                    name = "A Big Name For a Pet to Have",
                    petType = PetType.Dog
                )
            ),
            onCoveragePressed = {},
            onAddPetPressed = {},
            onLiveVetPressed = {}
        )
    }
}

@Preview(device = "spec:width=2000px,height=2340px,dpi=440", locale = "es")
@Composable
private fun LiveVetPetsCoveragesListComponentSpanishPreview() {
    Surface(
        color = NeutralBackground
    ) {
        LiveVetPetsCoveragesListComponent(
            modifier = Modifier
                .padding(vertical = 16.dp),
            coverages = listOf(
                PetsCoverageSummaryCardModel(
                    id = "1",
                    breed = "Labradoodle - Small (11-30 lbs)",
                    birthDate = Calendar.getInstance().apply {
                        set(Calendar.YEAR, 2019)
                        set(Calendar.MONTH, Calendar.NOVEMBER)
                        set(Calendar.DAY_OF_MONTH, 29)
                    }.time,
                    status = CoverageStatusUi.Active,
                    pictureUrl = "",
                    name = "Oliver",
                    petType = PetType.Dog
                ),
                PetsCoverageSummaryCardModel(
                    id = "1",
                    breed = "Labradoodle - Small (11-30 lbs)",
                    birthDate = Calendar.getInstance().apply {
                        set(Calendar.YEAR, 2019)
                        set(Calendar.MONTH, Calendar.NOVEMBER)
                        set(Calendar.DAY_OF_MONTH, 29)
                    }.time,
                    status = CoverageStatusUi.Active,
                    pictureUrl = "",
                    name = "A Big Name For a Pet to Have",
                    petType = PetType.Dog
                )
            ),
            onCoveragePressed = {},
            onAddPetPressed = {},
            onLiveVetPressed = {}
        )
    }
}

@Preview
@Composable
private fun LiveVetPetsCoveragesListComponentEmptyPreview() {
    Surface(
        color = NeutralBackground
    ) {
        LiveVetPetsCoveragesListComponent(
            modifier = Modifier
                .padding(vertical = 16.dp),
            coverages = listOf(),
            onCoveragePressed = {},
            onAddPetPressed = {},
            onLiveVetPressed = {}
        )
    }
}
