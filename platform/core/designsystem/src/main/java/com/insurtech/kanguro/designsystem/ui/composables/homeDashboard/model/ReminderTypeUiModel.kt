package com.insurtech.kanguro.designsystem.ui.composables.homeDashboard.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.theme.InformationDark
import com.insurtech.kanguro.designsystem.ui.theme.NegativeDark
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDark
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDarkest
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryMedium
import com.insurtech.kanguro.designsystem.ui.theme.TertiaryDarkest
import com.insurtech.kanguro.designsystem.ui.theme.TertiaryExtraDark
import com.insurtech.kanguro.designsystem.ui.theme.TertiaryLightest
import com.insurtech.kanguro.designsystem.ui.theme.WarningDarkest
import com.insurtech.kanguro.designsystem.ui.theme.WarningExtraDark
import com.insurtech.kanguro.designsystem.ui.theme.WarningLightest
import com.insurtech.kanguro.designsystem.ui.theme.White

enum class ReminderTypeUiModel(
    @StringRes val label: Int,
    val leftColor: Color,
    val backgroundColor: Color,
    val textColor: Color
) {

    MedicalHistory(R.string.medical_history, NegativeDark, White, NegativeDark),
    Claim(R.string.claim, WarningDarkest, WarningLightest, WarningExtraDark),
    PetPicture(R.string.pet_picture, NegativeDark, White, NegativeDark),
    DirectPay(R.string.direct_pay, SecondaryDarkest, White, SecondaryMedium),
    FleaMedication(R.string.flea_medication, InformationDark, White, SecondaryDark),
    AddPet(R.string.add_a_pet_label, TertiaryDarkest, TertiaryExtraDark, TertiaryLightest)
}
