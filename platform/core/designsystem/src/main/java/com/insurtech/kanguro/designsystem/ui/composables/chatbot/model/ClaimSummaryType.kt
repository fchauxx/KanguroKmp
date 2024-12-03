package com.insurtech.kanguro.designsystem.ui.composables.chatbot.model

import androidx.annotation.StringRes
import com.insurtech.kanguro.designsystem.R

enum class ClaimSummaryType(@StringRes val label: Int) {
    Injury(label = R.string.injury),
    PersonalProperty(label = R.string.personal_property),
    InjuryAndPersonalProperty(label = R.string.injury_and_property)
}
