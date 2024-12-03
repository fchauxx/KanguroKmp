package com.insurtech.kanguro.ui.scenes.rentersCoverageDetails

import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.PartyItemModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.PartyItemModelRelation
import com.insurtech.kanguro.domain.model.AdditionalParty
import com.insurtech.kanguro.domain.model.AdditionalPartyType

fun AdditionalParty.toPartyItemModel(): PartyItemModel {
    return PartyItemModel(
        id = id,
        name = fullName,
        relation = when (this.type) {
            AdditionalPartyType.Spouse -> PartyItemModelRelation.Spouse
            AdditionalPartyType.Child -> PartyItemModelRelation.Child
            AdditionalPartyType.Roommate -> PartyItemModelRelation.Roommate
            AdditionalPartyType.Landlord -> PartyItemModelRelation.Landlord
            AdditionalPartyType.PropertyManager -> PartyItemModelRelation.PropertyManager
        }
    )
}
