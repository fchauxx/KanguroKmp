package com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.section

import androidx.annotation.DrawableRes

data class SectionHeaderModel(
    val title: String = "",
    val subtitle: String = "",
    val renewDate: String = "",
    val startDate: String = "",
    val endDate: String = "",
    @DrawableRes val icon: Int? = null
)
