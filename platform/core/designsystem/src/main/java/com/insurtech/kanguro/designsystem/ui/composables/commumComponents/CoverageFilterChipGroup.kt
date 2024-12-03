package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.CoverageFilter
import com.insurtech.kanguro.designsystem.ui.theme.MobaCaptionRegular
import com.insurtech.kanguro.designsystem.ui.theme.NegativeDarkest
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground
import com.insurtech.kanguro.designsystem.ui.theme.PositiveDarkest

@Composable
fun CoverageFilterChipGroup(
    modifier: Modifier = Modifier,
    selectedFilter: CoverageFilter,
    onFilterChanged: (CoverageFilter) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CoverageFilter.values().forEach { filter ->
            KanguroFilterChip(
                isActive = filter == selectedFilter,
                onPressed = { onFilterChanged(filter) }
            ) {
                CustomFilterChip(filterType = filter)
            }
        }
    }
}

@Composable
private fun CustomFilterChip(filterType: CoverageFilter) {
    when (filterType) {
        CoverageFilter.All -> CustomChipContent(
            textId = R.string.coverage_all_filter
        )

        CoverageFilter.Active -> CustomChipContent(
            textId = R.string.coverage_active_filter,
            color = PositiveDarkest
        )

        CoverageFilter.Inactive -> CustomChipContent(
            textId = R.string.coverage_inactive_filter,
            color = NegativeDarkest
        )
    }
}

@Composable
private fun CustomChipContent(textId: Int, color: Color? = null) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        color?.let { DotComponent(color = it) }
        Text(
            modifier = Modifier.widthIn(min = 28.dp),
            text = stringResource(id = textId),
            style = MobaCaptionRegular.copy(textAlign = TextAlign.Center)
        )
    }
}

@Composable
private fun DotComponent(color: Color) {
    Surface(
        modifier = Modifier.size(4.dp),
        color = color,
        shape = CircleShape
    ) { }
}

@Preview
@Composable
private fun CoverageFilterChipGroupPreview() {
    Surface(
        color = NeutralBackground
    ) {
        CoverageFilterChipGroup(
            modifier = Modifier.padding(16.dp),
            selectedFilter = CoverageFilter.Active,
            onFilterChanged = {}
        )
    }
}
