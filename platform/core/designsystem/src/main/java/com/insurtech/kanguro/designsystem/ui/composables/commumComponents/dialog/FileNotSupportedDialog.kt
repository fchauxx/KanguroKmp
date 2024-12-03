package com.insurtech.kanguro.designsystem.ui.composables.commumComponents.dialog

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.InformationDialog
import com.insurtech.kanguro.designsystem.ui.theme.BKSParagraphRegular

@Composable
fun FileFormatNotSupportedDialog(
    fileType: String,
    onDismiss: () -> Unit
) {
    InformationDialog(
        title = stringResource(
            id = R.string.file_not_supported_title
        ),
        content = {
            Text(
                text = stringResource(
                    id = R.string.file_not_supported_message,
                    fileType.removePrefix(".")
                ),
                style = BKSParagraphRegular
            )
        },
        onDismiss = {
            onDismiss()
        }
    )
}

@Composable
@Preview
private fun EditPolicyDialogPreview() {
    FileFormatNotSupportedDialog(
        fileType = "heic",
        onDismiss = {}
    )
}

@Composable
@Preview(locale = "es")
private fun EditPolicyDialogPreviewLocalized() {
    FileFormatNotSupportedDialog(
        fileType = "heic",
        onDismiss = {}
    )
}
