package com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.directPayToVetAlmostDone

import android.view.Gravity
import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.horizontalPayToVetPaddingScreen
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.HeaderBackAndClose
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.KanguroButton
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.StyledClickableText
import com.insurtech.kanguro.designsystem.ui.theme.MobaSubheadRegular
import com.insurtech.kanguro.designsystem.ui.theme.MobaTitle3SemiBold
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDark

@Composable
fun DirectPayToVetAlmostDoneScreenContent(
    modifier: Modifier = Modifier,
    onClosePressed: () -> Unit,
    onOkayPressed: () -> Unit,
    onTapHerePressed: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        HeaderBackAndClose(
            modifier = Modifier
                .padding(16.dp),
            onClosePressed = onClosePressed
        )
        Content(
            onOkayPressed = onOkayPressed,
            onTapHerePressed = onTapHerePressed
        )
    }
}

@Composable
private fun Content(
    onOkayPressed: () -> Unit,
    onTapHerePressed: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .horizontalPayToVetPaddingScreen()
            .padding(bottom = 31.dp)
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.img_dtp_kanguro_almost_done),
            contentDescription = null
        )
        Text(
            modifier = Modifier.padding(top = 40.dp),
            text = stringResource(id = R.string.direct_pay_to_vet_almost_done),
            style = MobaTitle3SemiBold
        )
        StyledText(
            modifier = Modifier.padding(top = 24.dp),
            text = LocalContext.current.resources.getText(R.string.direct_pay_to_vet_almost_done_instruction)
        )

        StyledClickableText(
            modifier = Modifier.padding(top = 24.dp),
            text = stringResource(id = R.string.share_with_vet_hint),
            styledText = stringResource(id = R.string.tap_here),
            baseStyle = MobaSubheadRegular.copy(color = SecondaryDark, textAlign = TextAlign.Center),
            onClick = onTapHerePressed
        )
        Spacer(modifier = Modifier.weight(1f))
        Footer(onOkayPressed)
    }
}

@Composable
private fun StyledText(
    modifier: Modifier = Modifier,
    text: CharSequence
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            TextView(context).apply {
                setTextAppearance(R.style.MobaBodyRegular)
                gravity = Gravity.CENTER_HORIZONTAL
            }
        },
        update = {
            it.text = text
        }
    )
}

@Composable
private fun Footer(
    onOkayPressed: () -> Unit
) {
    KanguroButton(
        modifier = Modifier.padding(top = 24.dp),
        text = stringResource(id = R.string.okay),
        enabled = true,
        onClick = onOkayPressed
    )
}

@Composable
@Preview
private fun DirectPayToVetAlmostDoneScreenContentPreview() {
    Surface {
        DirectPayToVetAlmostDoneScreenContent(
            onClosePressed = {},
            onOkayPressed = {},
            onTapHerePressed = {}
        )
    }
}
