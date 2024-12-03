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
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.ActionCardButton
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.PolicyStatus
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.CoverageSectionCard
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.PartiesList
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.SectionHeader
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.PartyItemModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.PartyItemModelRelation
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.section.AdditionalPartiesSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.section.SectionHeaderModel

@Composable
fun AdditionalPartiesSection(
    modifier: Modifier = Modifier,
    additionalPartiesSectionModel: AdditionalPartiesSectionModel,
    onEditAdditionalPartiesPressed: () -> Unit
) {
    CoverageSectionCard(modifier = modifier) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            SectionHeader(
                model = SectionHeaderModel(
                    title = stringResource(id = R.string.renters_additional_parties),
                    icon = R.drawable.ic_additional_parties_section
                )
            )

            PartiesList(parties = additionalPartiesSectionModel.additionalParties)

            if (additionalPartiesSectionModel.policyStatus == PolicyStatus.ACTIVE) {
                ActionCardButton(
                    text = stringResource(id = R.string.renters_edit_additional_parties),
                    icon = R.drawable.ic_coverage_list,
                    onClick = onEditAdditionalPartiesPressed
                )
            }
        }
    }
}

@Composable
@Preview
private fun AdditionalPartiesSectionPreview() {
    Surface {
        AdditionalPartiesSection(
            modifier = Modifier.padding(16.dp),
            additionalPartiesSectionModel = AdditionalPartiesSectionModel(
                listOf(
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
            ),
            onEditAdditionalPartiesPressed = {}
        )
    }
}
