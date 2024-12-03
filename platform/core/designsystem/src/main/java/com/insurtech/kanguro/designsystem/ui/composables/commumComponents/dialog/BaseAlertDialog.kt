package com.insurtech.kanguro.designsystem.ui.composables.commumComponents.dialog

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.HorizontalDivider
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyRegular
import com.insurtech.kanguro.designsystem.ui.theme.MobaSubheadRegular
import com.insurtech.kanguro.designsystem.ui.theme.MobaTitle3SemiBold
import com.insurtech.kanguro.designsystem.ui.theme.NeutralDark
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDarkest
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryLightest
import com.insurtech.kanguro.designsystem.ui.theme.White

@Composable
fun BaseAlertDialog(
    modifier: Modifier = Modifier,
    @DrawableRes image: Int,
    @StringRes title: Int,
    @StringRes description: Int,
    @StringRes positiveLabel: Int,
    @StringRes negativeLabel: Int = R.string.maybe_later,
    onClosePressed: () -> Unit,
    onNegativePressed: (() -> Unit)? = null,
    onPositivePressed: () -> Unit
) {
    Dialog(onDismissRequest = { onClosePressed() }) {
        Card(
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            backgroundColor = White
        ) {
            Column(
                modifier = Modifier
                    .padding(32.dp)
            ) {
                CloseButton(onClosePressed)

                Content(
                    image = image,
                    title = title,
                    description = description,
                    negativeLabel = negativeLabel,
                    positiveLabel = positiveLabel,
                    onNegativePressed = onNegativePressed,
                    onPositivePressed = onPositivePressed
                )
            }
        }
    }
}

@Composable
private fun Content(
    image: Int,
    title: Int,
    description: Int,
    negativeLabel: Int,
    positiveLabel: Int,
    onNegativePressed: (() -> Unit)?,
    onPositivePressed: () -> Unit
) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Image(painter = painterResource(id = image), contentDescription = "")

        Spacer(modifier = Modifier.height(26.dp))

        Text(
            text = stringResource(id = title),
            style = MobaTitle3SemiBold.copy(
                textAlign = TextAlign.Center
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = description),
            style = MobaBodyRegular.copy(
                color = NeutralDark,
                textAlign = TextAlign.Center
            )
        )

        Spacer(modifier = Modifier.height(40.dp))

        HorizontalDivider(thicknessDp = 1.0, color = SecondaryLightest)

        Spacer(modifier = Modifier.height(40.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(align = Alignment.Top),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            val modifier = Modifier
                .weight(1f)
                .heightIn(min = 46.dp)

            if (onNegativePressed != null) {
                CustomButton(
                    text = stringResource(id = negativeLabel),
                    modifier = modifier,
                    onClicked = onNegativePressed
                )
            }

            CustomButton(
                text = stringResource(id = positiveLabel),
                isConfirmedButton = true,
                modifier = modifier,
                onClicked = onPositivePressed
            )
        }
    }
}

@Composable
private fun CloseButton(
    onNegativePressed: () -> Unit
) {
    Row {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            modifier = Modifier.clickable { onNegativePressed() },
            painter = painterResource(id = R.drawable.ic_information_close),
            contentDescription = stringResource(id = R.string.close)
        )
    }
}

@Composable
private fun CustomButton(
    text: String,
    isConfirmedButton: Boolean = false,
    modifier: Modifier,
    onClicked: () -> Unit
) {
    val backgroundColor = if (isConfirmedButton) SecondaryDarkest else White
    val textColor = if (isConfirmedButton) White else SecondaryDarkest

    Button(
        onClick = onClicked,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(width = 1.dp, color = SecondaryDarkest),
        elevation = ButtonDefaults.elevation(0.dp)
    ) {
        Text(
            text = text,
            style = MobaSubheadRegular.copy(
                color = textColor,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Black
            )
        )
    }
}

@Composable
@Preview(device = Devices.NEXUS_5)
private fun BaseAlertDialogPreview() {
    BaseAlertDialog(
        image = R.drawable.img_camera_permission,
        title = R.string.enable_camera,
        description = R.string.enable_camera_message,
        positiveLabel = R.string.allow_access,
        onClosePressed = {},
        onNegativePressed = { },
        onPositivePressed = { }
    )
}
