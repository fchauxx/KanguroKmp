package com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.PartyItemModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.PartyItemModelRelation
import com.insurtech.kanguro.designsystem.ui.theme.BKSParagraphBold
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDark
import com.insurtech.kanguro.designsystem.ui.theme.TertiaryDark

@Composable
fun PartiesList(
    modifier: Modifier = Modifier,
    parties: List<PartyItemModel>
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        parties.forEach { party ->
            Column {
                Text(
                    text = party.name,
                    style = BKSParagraphBold.copy(color = SecondaryDark)
                )
                Text(
                    text = stringResource(id = party.relation.label),
                    style = BKSParagraphBold.copy(color = TertiaryDark)
                )
            }
        }
    }
}

@Composable
@Preview
private fun PartiesListPreview() {
    Surface {
        PartiesList(
            modifier = Modifier.padding(16.dp),
            parties = listOf(
                PartyItemModel(
                    id = "1",
                    name = "Benjamin Thompson",
                    relation = PartyItemModelRelation.Spouse
                ),
                PartyItemModel(
                    id = "2",
                    name = "Emily Walker Thompson",
                    relation = PartyItemModelRelation.Child
                ),
                PartyItemModel(
                    id = "3",
                    name = "Jonathan Walker Thompson",
                    relation = PartyItemModelRelation.Landlord
                ),
                PartyItemModel(
                    id = "4",
                    name = "Olivia Roberts",
                    relation = PartyItemModelRelation.Roommate
                ),
                PartyItemModel(
                    id = "5",
                    name = "Matthew Anderson",
                    relation = PartyItemModelRelation.PropertyManager
                )
            )
        )
    }
}
