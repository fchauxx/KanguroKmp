package com.insurtech.kanguro.designsystem.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    // Using Secondary Color as Primary Color for the DatePicker
    primary = SecondaryDarkest,
    onPrimary = White,
    primaryContainer = SecondaryLightest,
    onPrimaryContainer = Black,
    secondary = PrimaryDarkest,
    onSecondary = White,
    secondaryContainer = PrimaryLightest,
    onSecondaryContainer = Black,
    tertiary = TertiaryDarkest,
    onTertiary = White,
    tertiaryContainer = TertiaryLightest,
    onTertiaryContainer = Black,
    error = NegativeDarkest,
    onError = White,
    errorContainer = NegativeLightest,
    onErrorContainer = Black,
    background = NeutralBackground,
    onBackground = Black
)

@Composable
fun KanguroDatePickerTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColors,
        content = content
    )
}
