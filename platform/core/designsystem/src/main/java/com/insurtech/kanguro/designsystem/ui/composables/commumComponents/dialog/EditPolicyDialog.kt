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
fun EditPolicyDialog(
    showEditPolicyInfoDialog: Boolean,
    onClick: () -> Unit,
    onDismiss: () -> Unit
) {
    if (showEditPolicyInfoDialog) {
        InformationDialog(
            title = stringResource(id = R.string.edit_policy_dialog_title),
            content = {
                val annotatedString =
                    getSupportEmailText(
                        messageId = R.string.edit_policy_dialog_instructions,
                        emailId = R.string.renters_endorsments_email
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
private fun EditPolicyDialogPreview() {
    EditPolicyDialog(
        showEditPolicyInfoDialog = true,
        onClick = {},
        onDismiss = {}
    )
}
