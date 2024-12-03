package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.EMAIL_TAG
import com.insurtech.kanguro.designsystem.ui.theme.MobaSubheadRegular
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryMedium

@Composable
fun TrackYourClaimRenters(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    onEmailPressed: () -> Unit
) {
    ExpandableCardStateful(
        modifier = modifier,
        icon = R.drawable.ic_coverage_list,
        backgroundColor = backgroundColor,
        title = stringResource(id = R.string.track_your_claim)
    ) {
        TrackYourClaimContent(onEmailPressed)
    }
}

@Composable
private fun TrackYourClaimContent(
    onEmailPressed: () -> Unit
) {
    val annotatedString = getSupportEmailText(
        R.string.track_your_claim_content,
        R.string.renters_claims_email
    )

    ClickableText(
        modifier = Modifier.padding(top = 16.dp),
        style = MobaSubheadRegular.copy(color = SecondaryMedium),
        text = annotatedString,
        onClick = { offset ->
            annotatedString.getStringAnnotations(tag = EMAIL_TAG, start = offset, end = offset)
                .firstOrNull()?.let {
                    onEmailPressed()
                }
        }
    )
}
