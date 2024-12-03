package com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model

import androidx.annotation.StringRes
import com.insurtech.kanguro.designsystem.R

enum class PartyItemModelRelation(@StringRes val label: Int) {
    Spouse(label = R.string.additional_party_spouse),
    Child(label = R.string.additional_party_child),
    Roommate(label = R.string.additional_party_roommate),
    Landlord(label = R.string.additional_party_landlord),
    PropertyManager(label = R.string.additional_party_property_manager)
}
