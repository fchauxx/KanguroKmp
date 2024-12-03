package com.insurtech.kanguro.designsystem.ui.composables.directPayToVet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.HeaderBackAndClose
import com.insurtech.kanguro.designsystem.ui.theme.MobaTitle3SemiBold

@Composable
fun DirectPayToVetTitleComponent(
    modifier: Modifier = Modifier,
    onBackButtonPressed: (() -> Unit)? = null,
    onClosePressed: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        HeaderBackAndClose(
            onBackButtonPressed = onBackButtonPressed,
            onClosePressed = onClosePressed
        )
        Text(
            text = stringResource(id = R.string.direct_pay_to_vet_title),
            modifier = Modifier.padding(start = 16.dp, top = 12.dp),
            style = MobaTitle3SemiBold
        )
    }
}

@Composable
@Preview
private fun DirectPayToVetTitleComponentPreview() {
    Surface {
        DirectPayToVetTitleComponent(
            modifier = Modifier.padding(16.dp),
            onClosePressed = {}
        )
    }
}

@Composable
@Preview
private fun DirectPayToVetTitleComponentBackButtonPreview() {
    Surface {
        DirectPayToVetTitleComponent(
            modifier = Modifier.padding(16.dp),
            onBackButtonPressed = {},
            onClosePressed = {}
        )
    }
}
