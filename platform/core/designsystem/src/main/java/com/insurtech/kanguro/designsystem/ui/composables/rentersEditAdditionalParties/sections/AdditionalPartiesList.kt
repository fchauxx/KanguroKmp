package com.insurtech.kanguro.designsystem.ui.composables.rentersEditAdditionalParties.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.PartyItemModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.PartyItemModelRelation

@Composable
fun AdditionalPartiesList(
    modifier: Modifier = Modifier,
    parties: List<PartyItemModel>,
    onEditPressed: (String) -> Unit,
    onDeletePressed: (String) -> Unit
) {
    LazyColumn(
        modifier = modifier.padding(top = 12.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 12.dp, start = 32.dp, end = 32.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(parties) { item ->
            AdditionalPartyCard(
                name = item.name,
                relation = item.relation,
                onEditPressed = { onEditPressed(item.id) },
                onDeletePressed = { onDeletePressed(item.id) }
            )
        }
    }
}

@Composable
@Preview
private fun AdditionalPartiesListPreview() {
    Surface {
        AdditionalPartiesList(
            parties = listOf(
                PartyItemModel(
                    id = "1",
                    name = "Benjamin Thompson",
                    relation = PartyItemModelRelation.Spouse
                ),
                PartyItemModel(
                    id = "1",
                    name = "Emily Walker Thompson",
                    relation = PartyItemModelRelation.Child
                ),
                PartyItemModel(
                    id = "1",
                    name = "Jonathan Walker Thompson",
                    relation = PartyItemModelRelation.Landlord
                ),
                PartyItemModel(
                    id = "1",
                    name = "Olivia Roberts",
                    relation = PartyItemModelRelation.Roommate
                ),
                PartyItemModel(
                    id = "1",
                    name = "Matthew Anderson",
                    relation = PartyItemModelRelation.PropertyManager
                )
            ),
            onEditPressed = {},
            onDeletePressed = {}
        )
    }
}
