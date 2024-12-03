package com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.direcPayToVetDocumentSent

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.horizontalPayToVetPaddingScreen
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.HeaderBackAndClose
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.KanguroButton
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyRegular
import com.insurtech.kanguro.designsystem.ui.theme.MobaTitle3SemiBold
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDark

@Composable
fun DirectPayToVetDocumentSentScreenContent(
    modifier: Modifier = Modifier,
    onClosePressed: () -> Unit,
    onOkayPressed: () -> Unit
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
            onOkayPressed = onOkayPressed
        )
    }
}

@Composable
fun Content(
    modifier: Modifier = Modifier,
    onOkayPressed: () -> Unit
) {
    Column(
        modifier = modifier
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
            painter = painterResource(id = R.drawable.img_dtp_kanguro_document_sent),
            contentDescription = null
        )
        Text(
            modifier = Modifier.padding(top = 40.dp),
            text = stringResource(id = R.string.document_sent),
            style = MobaTitle3SemiBold
        )

        Text(
            modifier = Modifier.padding(top = 24.dp),
            text = stringResource(id = R.string.document_sent_instruction),
            style = MobaBodyRegular.copy(color = SecondaryDark, textAlign = TextAlign.Center)
        )

        Spacer(modifier = Modifier.weight(1f))

        KanguroButton(
            text = stringResource(id = R.string.okay),
            enabled = true,
            onClick = onOkayPressed
        )
    }
}

@Composable
@Preview
fun DirectPayToVetDocumentSentScreenContentPreview() {
    Surface {
        DirectPayToVetDocumentSentScreenContent(
            onClosePressed = {},
            onOkayPressed = {}
        )
    }
}

@Composable
@Preview(locale = "es")
fun DirectPayToVetDocumentSentScreenContentLocalePreview() {
    Surface {
        DirectPayToVetDocumentSentScreenContent(
            onClosePressed = {},
            onOkayPressed = {}
        )
    }
}
