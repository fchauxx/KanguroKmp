package com.insurtech.kanguro.designsystem.ui.composables.chatbot

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyRegular
import com.insurtech.kanguro.designsystem.ui.theme.White

@Composable
fun AddAttachmentButton(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int? = null,
    label: String,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .background(color = White)
            .clickable {
                onClick()
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.5.dp, horizontal = 32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = label
                )
            }
            Text(
                text = label,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
                style = MobaBodyRegular,
                textAlign = TextAlign.Start
            )
        }
    }
}

@Composable
@Preview
private fun VideoRecordingPreview() {
    Surface {
        AddAttachmentButton(
            modifier = Modifier.padding(16.dp),
            icon = R.drawable.ic_camera,
            label = stringResource(id = R.string.record_videos),
            onClick = {}
        )
    }
}

@Composable
@Preview
private fun VideoRecordingNoIconPreview() {
    Surface {
        AddAttachmentButton(
            modifier = Modifier.padding(16.dp),
            label = stringResource(id = R.string.record_videos),
            onClick = {}
        )
    }
}

@Composable
@Preview
private fun FileAttachmentPreview() {
    Surface {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            AddAttachmentButton(
                icon = R.drawable.ic_camera,
                label = stringResource(id = R.string.take_picture),
                onClick = {}
            )
            AddAttachmentButton(
                icon = R.drawable.ic_gallery,
                label = stringResource(id = R.string.select_picture),
                onClick = {}
            )
            AddAttachmentButton(
                icon = R.drawable.ic_document_favorite,
                label = stringResource(id = R.string.select_file),
                onClick = {}
            )
        }
    }
}
