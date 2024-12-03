package com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.directPayToVetShareWithYourVet

import android.widget.TextView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.HeaderBackAndClose
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.KanguroButton
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.SelectedFileCard
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.UploadButton
import com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.domain.SelectedFileType
import com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.domain.SelectedFileUi
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyBold
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyRegular
import com.insurtech.kanguro.designsystem.ui.theme.MobaSubheadBlack
import com.insurtech.kanguro.designsystem.ui.theme.MobaTitle3SemiBold
import com.insurtech.kanguro.designsystem.ui.theme.PrimaryDarkest
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDark
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDarkest
import com.insurtech.kanguro.designsystem.ui.theme.White

@Composable
fun DirectPayToVetShareWithYourVetScreenContent(
    modifier: Modifier = Modifier,
    isDoneButtonEnabled: Boolean,
    isUploadingDocument: Boolean,
    isSendingSignature: Boolean,
    selectedFileUi: SelectedFileUi? = null,
    onClosePressed: () -> Unit,
    onDownloadFilePressed: () -> Unit,
    onUploadFilePressed: () -> Unit,
    onDeleteDocumentPressed: () -> Unit,
    onDonePressed: () -> Unit
) {
    Column(
        modifier = modifier.padding(16.dp)
    ) {
        HeaderBackAndClose(
            onClosePressed = onClosePressed
        )

        Spacer(modifier = Modifier.height(16.dp))

        TitleSection(
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        StepsSection(
            modifier = Modifier.padding(horizontal = 16.dp),
            isUploadingDocument = isUploadingDocument,
            onDownloadFilePressed = onDownloadFilePressed,
            onUploadFilePressed = onUploadFilePressed
        )

        if (selectedFileUi?.type == SelectedFileType.Image && !isUploadingDocument) {
            SelectedFileCard(
                modifier = Modifier.padding(16.dp),
                selectedFileUi = selectedFileUi,
                onDeletedPressed = onDeleteDocumentPressed

            )
        }

        Spacer(modifier = Modifier.weight(1f))

        KanguroButton(
            modifier = Modifier.padding(16.dp),
            text = stringResource(id = R.string.done),
            enabled = isDoneButtonEnabled,
            isLoading = isSendingSignature,
            onClick = onDonePressed
        )
    }
}

@Composable
private fun TitleSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.share_with_your_vet),
            style = MobaTitle3SemiBold
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = stringResource(id = R.string.share_with_your_vet_instruction),
            style = MobaBodyRegular.copy(color = SecondaryDark)
        )
    }
}

@Composable
private fun StepsSection(
    modifier: Modifier = Modifier,
    isUploadingDocument: Boolean,
    onDownloadFilePressed: () -> Unit,
    onUploadFilePressed: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.step, 1),
            style = MobaSubheadBlack.copy(color = PrimaryDarkest)
        )

        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = stringResource(id = R.string.share_with_your_vet_step_1),
            style = MobaBodyBold.copy(color = SecondaryDark)
        )

        DownloadButton(onDownloadFilePressed)

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = stringResource(id = R.string.step, 2),
            style = MobaSubheadBlack.copy(color = PrimaryDarkest)
        )

        StyledText(
            modifier = Modifier.padding(top = 8.dp),
            text = LocalContext.current.resources.getText(R.string.share_with_your_vet_step_2)
        )

        UploadButton(
            isUploadingDocument = isUploadingDocument,
            onUploadFilePressed = onUploadFilePressed
        ) {
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
            }
        },
        update = {
            it.text = text
        }
    )
}

@Composable
private fun DownloadButton(onDownloadFilePressed: () -> Unit) {
    OutlinedButton(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
            .height(46.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = White),
        shape = CircleShape,
        border = BorderStroke(width = 1.dp, color = SecondaryDarkest),
        elevation = ButtonDefaults.elevation(0.dp),
        onClick = { onDownloadFilePressed() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.download_document),
                style = MobaSubheadBlack
            )
            Image(
                painter = painterResource(R.drawable.ic_download_square),
                contentDescription = null
            )
        }
    }
}

@Composable
@Preview
private fun DirectPayToVetShareWithYourVetScreenContentPreview() {
    Surface {
        DirectPayToVetShareWithYourVetScreenContent(
            isDoneButtonEnabled = true,
            isUploadingDocument = false,
            isSendingSignature = false,
            selectedFileUi = SelectedFileUi(
                id = 1,
                fileUrl = "fileUrl",
                fileName = "test.jpg"
            ),
            onClosePressed = {},
            onDownloadFilePressed = {},
            onUploadFilePressed = {},
            onDeleteDocumentPressed = {},
            onDonePressed = {}
        )
    }
}

@Composable
@Preview
private fun DirectPayToVetShareWithYourVetScreenContentUploadingPreview() {
    Surface {
        DirectPayToVetShareWithYourVetScreenContent(
            onClosePressed = {},
            isDoneButtonEnabled = false,
            isUploadingDocument = true,
            isSendingSignature = false,
            onDownloadFilePressed = {},
            onUploadFilePressed = {},
            onDeleteDocumentPressed = {},
            onDonePressed = {}
        )
    }
}

@Composable
@Preview
private fun DirectPayToVetShareWithYourVetScreenContentSendingSignaturePreview() {
    Surface {
        DirectPayToVetShareWithYourVetScreenContent(
            onClosePressed = {},
            isDoneButtonEnabled = true,
            isUploadingDocument = false,
            isSendingSignature = true,
            selectedFileUi = SelectedFileUi(
                id = 1,
                fileUrl = "fileUrl",
                fileName = "test.jpg"
            ),
            onDownloadFilePressed = {},
            onUploadFilePressed = {},
            onDeleteDocumentPressed = {},
            onDonePressed = {}
        )
    }
}
