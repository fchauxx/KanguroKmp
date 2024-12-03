package com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.ActionCardButton
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.StyledCustomizableClicableText
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.TrackYourClaimRenters
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.PolicyStatus
import com.insurtech.kanguro.designsystem.ui.theme.MobaCaptionBold
import com.insurtech.kanguro.designsystem.ui.theme.MobaSubheadRegularSecondaryMedium
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDark
import com.insurtech.kanguro.designsystem.ui.theme.TertiaryDarkest

@Composable
fun MoreActionsSection(
    modifier: Modifier = Modifier,
    policyStatus: PolicyStatus,
    onFileClaimPressed: () -> Unit,
    onTrackYourClaimPressed: () -> Unit,
    onEditPolicyDetailsPressed: () -> Unit,
    onChangeMyAddressPressed: () -> Unit,
    onFrequentlyAskedQuestionsPressed: () -> Unit,
    onPhonePressed: () -> Unit,
    onEmailPressed: () -> Unit

) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            modifier = Modifier,
            text = stringResource(id = R.string.more_actions).uppercase(),
            style = MobaCaptionBold.copy(color = SecondaryDark)
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (policyStatus == PolicyStatus.ACTIVE) {
            ActionCardButton(
                text = stringResource(id = R.string.file_claim),
                icon = R.drawable.ic_add_square,
                iconTint = SecondaryDark
            ) {
                onFileClaimPressed()
            }

            TrackYourClaimRenters(backgroundColor = NeutralBackground) {
                onTrackYourClaimPressed()
            }

            ActionCardButton(
                text = stringResource(id = R.string.edit_policy_details),
                icon = R.drawable.ic_edit_underline,
                iconTint = SecondaryDark
            ) {
                onEditPolicyDetailsPressed()
            }

            ActionCardButton(
                text = stringResource(id = R.string.change_my_address),
                icon = R.drawable.ic_home_2,
                iconTint = SecondaryDark
            ) {
                onChangeMyAddressPressed()
            }
        }

        ActionCardButton(
            text = stringResource(id = R.string.frequently_asked_questions),
            icon = R.drawable.ic_message_question,
            iconTint = SecondaryDark
        ) {
            onFrequentlyAskedQuestionsPressed()
        }
    }
}

@Composable
private fun CancelPolicyContent(
    onPhonePressed: () -> Unit,
    onEmailPressed: () -> Unit
) {
    Column(
        modifier = Modifier.padding(top = 16.dp)
    ) {
        val phoneNumber = stringResource(id = R.string.phone_number_support)
        val fullText = stringResource(id = R.string.cancel_or_change_coverage_information_0)
            .substringBefore("%") + " " + phoneNumber + ".\n"

        StyledCustomizableClicableText(
            text = fullText,
            highlightText = stringResource(id = R.string.phone_number_support),
            defaultStyle = MobaSubheadRegularSecondaryMedium,
            highlightColor = TertiaryDarkest,
            highlightTextDecoration = TextDecoration.Underline,
            onClick = onPhonePressed
        )

        StyledCustomizableClicableText(
            text = stringResource(id = R.string.cancel_or_change_coverage_information_1),
            highlightText = stringResource(id = R.string.email_us_label),
            defaultStyle = MobaSubheadRegularSecondaryMedium,
            highlightColor = TertiaryDarkest,
            highlightTextDecoration = TextDecoration.Underline,
            onClick = onEmailPressed
        )
    }
}

@Preview
@Composable
private fun MoreActionsSectionsPreview() {
    Surface {
        MoreActionsSection(
            onFileClaimPressed = { },
            policyStatus = PolicyStatus.ACTIVE,
            onTrackYourClaimPressed = { },
            onEditPolicyDetailsPressed = { },
            onChangeMyAddressPressed = { },
            onPhonePressed = {},
            onEmailPressed = {},
            onFrequentlyAskedQuestionsPressed = {}
        )
    }
}
