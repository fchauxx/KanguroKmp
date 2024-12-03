package com.insurtech.kanguro.designsystem.ui.composables.petTrackYourClaims

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.common.enums.ClaimStatus
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.PetType
import com.insurtech.kanguro.designsystem.ui.composables.petTrackYourClaims.model.ClaimTrackerCardModel
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyBold
import com.insurtech.kanguro.designsystem.ui.theme.MobaFootnoteBlack
import com.insurtech.kanguro.designsystem.ui.theme.MobaFootnoteRegular
import com.insurtech.kanguro.designsystem.ui.theme.MobaSubheadRegular
import com.insurtech.kanguro.designsystem.ui.theme.NegativeDark
import com.insurtech.kanguro.designsystem.ui.theme.NegativeDarkest
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground
import com.insurtech.kanguro.designsystem.ui.theme.NeutralMedium
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDark
import com.insurtech.kanguro.designsystem.ui.theme.White
import com.insurtech.kanguro.domain.model.ClaimType
import com.insurtech.kanguro.domain.model.ReimbursementProcess

@Composable
fun ClaimTrackerDeniedCard(
    modifier: Modifier = Modifier,
    model: ClaimTrackerCardModel,
    onDetailPressed: () -> Unit
) {
    Surface(
        modifier = modifier.height(IntrinsicSize.Min),
        shape = RoundedCornerShape(8.dp),
        color = White,
        elevation = 1.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .width(8.dp)
                    .fillMaxHeight()
                    .background(color = NegativeDark)
            )

            Spacer(modifier = Modifier.width(8.dp))

            ClaimTrackerPetImage(
                modifier = Modifier.padding(top = 16.dp),
                pictureUrl = model.petPictureUrl,
                placeHolder = model.petType.placeHolder
            )

            Spacer(modifier = Modifier.width(8.dp))

            PetInfo(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .weight(1f),
                petName = model.petName,
                paidAmount = model.claimAmountPaid,
                claimType = model.claimType,
                closedIn = model.claimLastUpdated
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.padding(top = 16.dp, end = 16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                horizontalAlignment = Alignment.End
            ) {
                ClaimTrackerDetailButton(
                    onClick = onDetailPressed
                )

                DeniedLabel()
            }
        }
    }
}

@Composable
private fun PetInfo(
    modifier: Modifier = Modifier,
    petName: String,
    paidAmount: String,
    claimType: ClaimType,
    closedIn: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(end = 4.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = petName,
            style = MobaBodyBold
        )

        Text(
            text = stringResource(id = R.string.claim_card_paid, paidAmount),
            style = MobaSubheadRegular.copy(color = SecondaryDark)
        )

        Text(
            text = when (claimType) {
                ClaimType.Illness -> stringResource(id = R.string.illness)
                ClaimType.Accident -> stringResource(id = R.string.accident)
                ClaimType.Other -> stringResource(id = R.string.other)
            },
            style = MobaFootnoteRegular.copy(color = NeutralMedium)
        )

        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = stringResource(id = R.string.claim_card_closed_in),
                style = MobaFootnoteRegular.copy(color = NeutralMedium)
            )
            Text(
                text = closedIn,
                style = MobaFootnoteBlack.copy(color = NeutralMedium)
            )
        }
    }
}

@Composable
private fun DeniedLabel() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = stringResource(id = R.string.claim_status_denied),
            style = MobaSubheadRegular.copy(color = NegativeDarkest)
        )

        Image(
            painter = painterResource(id = R.drawable.ic_circle_error),
            colorFilter = ColorFilter.tint(NegativeDark),
            contentDescription = null
        )
    }
}

@Preview
@Composable
private fun ClaimTrackerDeniedCardPreview() {
    Surface(
        modifier = Modifier
            .background(color = NeutralBackground)
            .fillMaxWidth()
            .padding(16.dp),
        color = NeutralBackground
    ) {
        ClaimTrackerDeniedCard(
            model = ClaimTrackerCardModel(
                id = "1",
                petName = "Luna",
                petType = PetType.Dog,
                claimType = ClaimType.Other,
                claimStatus = ClaimStatus.Submitted,
                claimLastUpdated = "Sep 04, 2021",
                claimAmount = "$100.00",
                claimAmountPaid = "$100.00",
                reimbursementProcess = ReimbursementProcess.UserReimbursement,
                claimStatusDescription = null,
                petPictureUrl = null
            ),
            onDetailPressed = {}
        )
    }
}
