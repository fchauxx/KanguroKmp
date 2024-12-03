package com.insurtech.kanguro.ui.scenes.rentersOnboardingVideo

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.KanguroButton
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.OutlinedButton
import com.insurtech.kanguro.designsystem.ui.theme.BKSParagraphRegular
import com.insurtech.kanguro.designsystem.ui.theme.MobaHeadline
import com.insurtech.kanguro.designsystem.ui.theme.White

@Composable
fun CloseWindowConfirmationDialog(
    modifier: Modifier = Modifier,
    onClosePressed: () -> Unit,
    onCancelPressed: () -> Unit,
    onConfirmPressed: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Dialog(onDismissRequest = { onClosePressed() }) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                backgroundColor = White
            ) {
                Box {
                    CloseButton(modifier = Modifier.align(Alignment.TopEnd), onClosePressed)
                    MainContent(onCancelPressed, onConfirmPressed)
                }
            }
        }
    }
}

@Composable
private fun CloseButton(modifier: Modifier, onClosePressed: () -> Unit) {
    Image(
        modifier = modifier
            .clickable { onClosePressed() }
            .padding(24.dp),
        painter = painterResource(id = R.drawable.ic_information_close),
        contentDescription = stringResource(id = R.string.close)
    )
}

@Composable
private fun MainContent(onCancelPressed: () -> Unit, onConfirmPressed: () -> Unit) {
    Column(
        modifier = Modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_warning_dialog),
            contentDescription = stringResource(id = R.string.trash)
        )
        Text(
            modifier = Modifier.padding(top = 24.dp),
            text = stringResource(id = R.string.close_window_alert_message),
            textAlign = TextAlign.Center,
            style = MobaHeadline
        )

        Text(
            modifier = Modifier.padding(top = 24.dp),
            text = stringResource(id = R.string.all_will_be_lost),
            textAlign = TextAlign.Center,
            style = BKSParagraphRegular
        )

        ActionButtons(onCancelPressed, onConfirmPressed)
    }
}

@Composable
private fun ActionButtons(onCancelPressed: () -> Unit, onConfirmPressed: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedButton(
            modifier = Modifier
                .weight(1f)
                .height(46.dp),
            onClick = onCancelPressed,
            fontSize = 14.sp,
            text = R.string.cancel
        )

        KanguroButton(
            modifier = Modifier
                .weight(1f)
                .height(46.dp),
            enabled = true,
            text = stringResource(R.string.yes_close),
            fontSize = 14.sp
        ) {
            onConfirmPressed()
        }
    }
}

@Composable
@Preview
private fun CloseWindowConfirmationDialogPreview() {
    Surface {
        CloseWindowConfirmationDialog(
            modifier = Modifier,
            onClosePressed = {},
            onCancelPressed = {},
            onConfirmPressed = {}
        )
    }
}
