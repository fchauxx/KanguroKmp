package com.insurtech.kanguro.designsystem.ui.composables.homeDashboard.sections

import androidx.annotation.StringRes
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.theme.MobaCaptionBoldSecondaryDark

@Composable
fun UpperCaseCaseSectionTitle(
    @StringRes title: Int,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = stringResource(id = title).uppercase(),
        style = MobaCaptionBoldSecondaryDark
    )
}

@Preview
@Composable
private fun UpperCaseSectionTitlePreview() {
    Surface {
        UpperCaseCaseSectionTitle(
            R.string.more_actions
        )
    }
}
