package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.theme.MobaSubheadBlack
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDarkest
import com.insurtech.kanguro.designsystem.ui.theme.White

@Composable
fun UploadButton(
    isUploadingDocument: Boolean,
    onUploadFilePressed: () -> Unit,
    content: @Composable () -> Unit
) {
    Button(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
            .height(46.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = SecondaryDarkest),
        shape = CircleShape,
        elevation = ButtonDefaults.elevation(0.dp),
        onClick = { onUploadFilePressed() },
        enabled = !isUploadingDocument
    ) {
        if (isUploadingDocument) {
            ScreenLoader(modifier = Modifier.padding(4.dp))
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                content()
            }
        }
    }
}

@Composable
@Preview
fun UploadButtonFilePreview() {
    UploadButton(false, {}) {
        Text(
            text = stringResource(id = R.string.upload_signed_document),
            style = MobaSubheadBlack.copy(color = White)
        )
        Image(
            painter = painterResource(R.drawable.ic_document_upload_base),
            contentDescription = null,
            colorFilter = ColorFilter.tint(White)
        )
    }
}

@Composable
@Preview
fun UploadButtonVideoPreview() {
    UploadButton(false, {}) {
        androidx.compose.material3.Text(
            text = stringResource(R.string.send_video),
            style = MobaSubheadBlack.copy(color = White)
        )
    }
}

@Composable
@Preview
fun UploadingButtonPreview() {
    UploadButton(true, {}) {
        androidx.compose.material3.Text(
            text = stringResource(R.string.send_video),
            style = MobaSubheadBlack.copy(color = White)
        )
    }
}
