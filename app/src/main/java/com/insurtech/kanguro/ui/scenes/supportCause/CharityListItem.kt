package com.insurtech.kanguro.ui.scenes.supportCause

import com.insurtech.kanguro.domain.model.Charity

data class CharityListItem(
    val charityResponse: Charity,
    var isLoading: Boolean = false,
    var isExpanded: Boolean = false
)
