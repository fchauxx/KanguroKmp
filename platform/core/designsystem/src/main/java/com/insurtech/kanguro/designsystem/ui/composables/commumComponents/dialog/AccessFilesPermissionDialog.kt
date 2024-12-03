package com.insurtech.kanguro.designsystem.ui.composables.commumComponents.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.InformationDialog
import com.insurtech.kanguro.designsystem.ui.theme.BKSParagraphBold
import com.insurtech.kanguro.designsystem.ui.theme.BKSParagraphRegular
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryMedium

@Composable
fun AccessFilesPermissionDialog(
    onDismiss: () -> Unit
) {
    InformationDialog(
        title = stringResource(
            id = R.string.access_to_file_title
        ),
        content = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(
                        id = R.string.access_to_file_message_1
                    ),
                    style = BKSParagraphRegular
                )
                Text(
                    text = stringResource(
                        id = R.string.access_to_file_message_2
                    ),
                    style = BKSParagraphRegular
                )
                Text(
                    text = stringResource(
                        id = R.string.access_to_file_message_highlighted
                    ),
                    style = BKSParagraphBold.copy(color = SecondaryMedium)
                )
            }
        },
        onDismiss = {
            onDismiss()
        }
    )
}

@Composable
@Preview
private fun AccessFilesPermissionDialogPreview() {
    AccessFilesPermissionDialog(
        onDismiss = {}
    )
}

@Composable
@Preview(locale = "es")
private fun AccessFilesPermissionDialogLocalizedPreview() {
    AccessFilesPermissionDialog(
        onDismiss = {}
    )
}
