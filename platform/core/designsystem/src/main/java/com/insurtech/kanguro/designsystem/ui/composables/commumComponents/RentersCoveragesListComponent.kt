package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.CoverageStatusUi
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.RentersCoverageSummaryCardModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.DwellingType
import com.insurtech.kanguro.designsystem.ui.theme.MobaSubheadRegular
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryMedium

@Composable
fun RentersCoveragesListComponent(
    modifier: Modifier = Modifier,
    coverages: List<RentersCoverageSummaryCardModel>,
    onCoveragePressed: (String) -> Unit,
    onAddResidencePressed: () -> Unit
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(coverages) { coverage ->
            RentersCoverageSummaryCard(coverage = coverage) {
                onCoveragePressed(coverage.id)
            }
        }

        if (coverages.isEmpty()) {
            item {
                Text(
                    modifier = Modifier.widthIn(max = 240.dp),
                    text = stringResource(id = R.string.no_policies_matching_selected_filter),
                    style = MobaSubheadRegular.copy(color = SecondaryMedium)
                )
            }
        }

        item {
            IconButton(
                onClick = onAddResidencePressed,
                modifier = Modifier.padding(8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_add_policy),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
@Preview
private fun RentersCoveragesListComponentPreview() {
    Surface(
        color = NeutralBackground
    ) {
        RentersCoveragesListComponent(
            modifier = Modifier.padding(16.dp),
            coverages = listOf(
                RentersCoverageSummaryCardModel(
                    id = "1",
                    address = "Miami, FL",
                    type = DwellingType.SingleFamily,
                    status = CoverageStatusUi.Active
                ),
                RentersCoverageSummaryCardModel(
                    id = "2",
                    address = "New Jersey, NY",
                    type = DwellingType.StudentHousing,
                    status = CoverageStatusUi.Cancelled
                )
            ),
            onCoveragePressed = {},
            onAddResidencePressed = {}
        )
    }
}

@Composable
@Preview
private fun RentersCoveragesListComponentEmptyPreview() {
    Surface(
        color = NeutralBackground
    ) {
        RentersCoveragesListComponent(
            modifier = Modifier.padding(16.dp),
            coverages = listOf(),
            onCoveragePressed = {},
            onAddResidencePressed = {}
        )
    }
}

@Composable
@Preview(locale = "es")
private fun RentersCoveragesListComponentEmptyLocalizedPreview() {
    Surface(
        color = NeutralBackground
    ) {
        RentersCoveragesListComponent(
            modifier = Modifier.padding(16.dp),
            coverages = listOf(),
            onCoveragePressed = {},
            onAddResidencePressed = {}
        )
    }
}
