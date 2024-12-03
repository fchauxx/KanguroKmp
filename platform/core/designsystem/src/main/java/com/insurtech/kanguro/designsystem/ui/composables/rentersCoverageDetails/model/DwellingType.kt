package com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.insurtech.kanguro.designsystem.R

enum class DwellingType(
    @DrawableRes val icon: Int,
    @DrawableRes val summaryIcon: Int,
    @StringRes val text: Int
) {

    SingleFamily(
        R.drawable.single_family,
        R.drawable.ic_single_family,
        R.string.single_family
    ),

    MultiFamily(
        R.drawable.multi_family,
        R.drawable.ic_multi_family,
        R.string.multi_family
    ),

    Townhouse(
        R.drawable.townhouse,
        R.drawable.ic_townhouse,
        R.string.townhouse
    ),

    Apartment(
        R.drawable.apartment,
        R.drawable.ic_apartment,
        R.string.apartment
    ),

    StudentHousing(
        R.drawable.student_housing,
        R.drawable.ic_student_housing,
        R.string.student_housing
    )
}
