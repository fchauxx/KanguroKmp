package com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItems

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.theme.MobaHeadline
import com.insurtech.kanguro.designsystem.ui.theme.MobaSubheadRegular
import com.insurtech.kanguro.designsystem.ui.theme.NegativeDarkest
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDarkest
import com.insurtech.kanguro.designsystem.ui.theme.White

@Composable
fun DeleteItemAlertDialog(
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
                modifier = Modifier
                    .fillMaxWidth(),
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
        modifier = Modifier
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_trash),
            contentDescription = stringResource(id = R.string.trash)
        )
        Text(
            modifier = Modifier.padding(top = 24.dp),
            text = stringResource(id = R.string.delete_item_alert_message),
            textAlign = TextAlign.Center,
            style = MobaHeadline
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
        CancelButton(modifier = Modifier.weight(1f), onCancelPressed)
        ConfirmButton(modifier = Modifier.weight(1f), onConfirmPressed)
    }
}

@Composable
private fun CancelButton(modifier: Modifier, onCancelPressed: () -> Unit) {
    OutlinedButton(
        onClick = { onCancelPressed() },
        modifier = modifier
            .fillMaxWidth()
            .height(46.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = White),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(width = 1.dp, color = SecondaryDarkest),
        elevation = ButtonDefaults.elevation(0.dp)
    ) {
        Text(
            text = stringResource(id = R.string.cancel),
            style = MobaSubheadRegular.copy(
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Black
            )
        )
    }
}

@Composable
private fun ConfirmButton(modifier: Modifier, onConfirmPressed: () -> Unit) {
    Button(
        onClick = { onConfirmPressed() },
        modifier = modifier
            .height(46.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = NegativeDarkest),
        shape = RoundedCornerShape(8.dp),
        elevation = ButtonDefaults.elevation(0.dp)
    ) {
        Text(
            text = stringResource(id = R.string.yes_delete),
            style = MobaSubheadRegular.copy(
                color = White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Black
            )
        )
    }
}

@Composable
@Preview
private fun DeleteItemAlertDialogPreview() {
    Surface {
        DeleteItemAlertDialog(
            modifier = Modifier,
            onClosePressed = {},
            onCancelPressed = {},
            onConfirmPressed = {}
        )
    }
}
