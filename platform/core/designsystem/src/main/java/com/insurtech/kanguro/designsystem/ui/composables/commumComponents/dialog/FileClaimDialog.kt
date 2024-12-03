package com.insurtech.kanguro.designsystem.ui.composables.commumComponents.dialog

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.EMAIL_TAG
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.getSupportEmailText
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.InformationDialog
import com.insurtech.kanguro.designsystem.ui.theme.BKSParagraphRegular

@Composable
fun FileClaimDialog(
    showFileClaimDialog: Boolean,
    onClick: () -> Unit,
    onDismiss: () -> Unit
) {
    if (showFileClaimDialog) {
        InformationDialog(
            title = stringResource(id = R.string.file_claim),
            content = {
                val annotatedString =
                    getSupportEmailText(
                        messageId = R.string.renters_file_claim_instructions,
                        emailId = R.string.renters_claims_email
                    )

                ClickableText(
                    text = annotatedString,
                    style = BKSParagraphRegular,
                    onClick = { offset ->
                        annotatedString.getStringAnnotations(
                            tag = EMAIL_TAG,
                            start = offset,
                            end = offset
                        )
                            .firstOrNull()?.let {
                                onClick()
                            }
                    }
                )
            },
            onDismiss = {
                onDismiss()
            }
        )
    }
}

@Preview
@Composable
private fun FileClaimDialogPreview() {
    FileClaimDialog(
        showFileClaimDialog = true,
        onClick = {},
        onDismiss = {}
    )
}
