package com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString

sealed class StepModel(@DrawableRes open val icon: Int) {
    data class Basic(
        @DrawableRes override val icon: Int,
        val instruction: AnnotatedString
    ) : StepModel(icon)

    data class CustomInstruction(
        @DrawableRes override val icon: Int,
        val instruction: @Composable () -> Unit
    ) : StepModel(icon)
}
