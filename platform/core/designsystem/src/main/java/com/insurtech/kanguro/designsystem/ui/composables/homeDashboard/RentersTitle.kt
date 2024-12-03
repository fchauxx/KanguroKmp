package com.insurtech.kanguro.designsystem.ui.composables.homeDashboard

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.theme.MobaTitle1
import com.insurtech.kanguro.designsystem.ui.theme.PrimaryDarkest

@Composable
fun RentersTitle(modifier: Modifier = Modifier, userName: String) {
    Text(
        modifier = modifier,
        style = MobaTitle1,
        text = buildAnnotatedString {
            append(stringResource(id = R.string.hello_comma))

            withStyle(style = SpanStyle(color = PrimaryDarkest)) {
                append(text = " $userName")
            }
        }
    )
}

@Preview
@Composable
fun RentersTitlePreview() {
    Surface {
        RentersTitle(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            userName = "Lauren"
        )
    }
}
