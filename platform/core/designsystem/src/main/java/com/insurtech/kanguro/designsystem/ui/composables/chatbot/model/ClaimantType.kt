package com.insurtech.kanguro.designsystem.ui.composables.chatbot.model

import androidx.annotation.StringRes
import com.insurtech.kanguro.designsystem.R

enum class ClaimantType(@StringRes val label: Int) {
    PolicyHolder(label = R.string.policy_holder),
    AnotherResident(label = R.string.another_resident),
    SomeoneElse(label = R.string.someone_else)
}
