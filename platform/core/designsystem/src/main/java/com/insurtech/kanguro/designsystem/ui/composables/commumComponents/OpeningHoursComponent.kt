package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.theme.OpeningHoursTextStyle
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryLight
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryLightest
import com.insurtech.kanguro.designsystem.ui.theme.spacingNano
import com.insurtech.kanguro.designsystem.ui.theme.spacingXxxs

@Composable
fun OpeningHoursComponent(
    modifier: Modifier = Modifier,
    text: String
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = SecondaryLightest,
        border = BorderStroke(1.dp, SecondaryLight)
    ) {
        Column(
            modifier = modifier.padding(horizontal = spacingXxxs, vertical = spacingNano)
        ) {
            Text(
                text = text,
                style = OpeningHoursTextStyle
            )
        }
    }
}

@Preview
@Composable
private fun OpeningHoursComponentPreview() {
    OpeningHoursComponent(
        text = stringResource(id = R.string.working_hours)
    )
}
