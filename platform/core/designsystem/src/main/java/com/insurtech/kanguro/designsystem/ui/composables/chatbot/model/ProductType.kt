package com.insurtech.kanguro.designsystem.ui.composables.chatbot.model

import androidx.annotation.StringRes
import com.insurtech.kanguro.designsystem.R

enum class ProductType(@StringRes val label: Int) {
    Pet(label = R.string.pet_insurance_summary_label),
    Renters(label = R.string.renters_insurance_summary_label)
}
