package com.insurtech.kanguro.designsystem.ui.composables.petTrackYourClaims

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.common.enums.ClaimStatus
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.PetType
import com.insurtech.kanguro.designsystem.ui.composables.petTrackYourClaims.model.ClaimTrackerCardModel
import com.insurtech.kanguro.designsystem.ui.theme.InformationLightest
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyBold
import com.insurtech.kanguro.designsystem.ui.theme.MobaCaptionBold
import com.insurtech.kanguro.designsystem.ui.theme.MobaCaptionRegular
import com.insurtech.kanguro.designsystem.ui.theme.MobaFootnoteBlack
import com.insurtech.kanguro.designsystem.ui.theme.MobaFootnoteRegular
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDark
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDarkest
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryMedium
import com.insurtech.kanguro.designsystem.ui.theme.WarningDark
import com.insurtech.kanguro.designsystem.ui.theme.WarningLightest
import com.insurtech.kanguro.designsystem.ui.theme.White
import com.insurtech.kanguro.designsystem.ui.theme.borderRadiusSm
import com.insurtech.kanguro.designsystem.ui.theme.spacingNano
import com.insurtech.kanguro.designsystem.ui.theme.spacingQuark
import com.insurtech.kanguro.designsystem.ui.theme.spacingXxs
import com.insurtech.kanguro.designsystem.ui.theme.spacingXxxs
import com.insurtech.kanguro.domain.model.ClaimType
import com.insurtech.kanguro.domain.model.ReimbursementProcess

@Composable
fun ClaimTrackerCard(
    modifier: Modifier = Modifier,
    model: ClaimTrackerCardModel,
    onDetailPressed: () -> Unit,
    onDirectPaymentPressed: () -> Unit
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(borderRadiusSm),
        color = White,
        elevation = 1.dp
    ) {
        Column(
            modifier = Modifier.padding(top = spacingXxxs),
            verticalArrangement = Arrangement.spacedBy(spacingXxs)
        ) {
            Header(
                model = model,
                onDetailPressed = onDetailPressed
            )

            ClaimTracker(
                modifier = Modifier
                    .padding(horizontal = spacingXxxs),
                claimStatus = model.claimStatus
            )

            val claimStatusDescription = model.claimStatusDescription.orEmpty()
            if (claimStatusDescription.isNotEmpty()) {
                ClaimStatusDescription(
                    text = claimStatusDescription,
                    isWarning = model.claimStatus == ClaimStatus.PendingMedicalHistory,
                    onClick = onDetailPressed
                )
            }

            if (model.reimbursementProcess == ReimbursementProcess.VeterinarianReimbursement) {
                DirectPayToVetButton(onClick = onDirectPaymentPressed)
            } else {
                Spacer(modifier = Modifier.height(spacingNano))
            }
        }
    }
}

@Composable
private fun Header(
    model: ClaimTrackerCardModel,
    onDetailPressed: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = spacingXxxs)
            .fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        ClaimTrackerPetImage(
            pictureUrl = model.petPictureUrl,
            placeHolder = model.petType.placeHolder
        )

        Spacer(modifier = Modifier.width(spacingNano))

        PetInfo(
            modifier = Modifier
                .weight(1f)
                .padding(end = spacingQuark),
            petName = model.petName,
            claimAmount = model.claimAmount,
            lastUpdate = model.claimLastUpdated
        )

        Spacer(modifier = Modifier.width(spacingNano))

        ClaimTrackerDetailButton(onClick = onDetailPressed)
    }
}

@Composable
private fun PetInfo(
    modifier: Modifier = Modifier,
    petName: String,
    claimAmount: String,
    lastUpdate: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(end = spacingNano),
        verticalArrangement = Arrangement.spacedBy(spacingQuark)
    ) {
        Text(text = petName, style = MobaBodyBold)

        LabelValue(
            label = stringResource(id = R.string.claim_card_claim_amount),
            value = claimAmount
        )

        LabelValue(
            label = stringResource(id = R.string.last_update_in),
            value = lastUpdate
        )
    }
}

@Composable
private fun LabelValue(
    modifier: Modifier = Modifier,
    label: String,
    value: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(spacingQuark)
    ) {
        Text(
            text = label,
            style = MobaFootnoteRegular.copy(color = SecondaryDark)
        )
        Text(
            text = value,
            style = MobaFootnoteBlack.copy(color = SecondaryDark)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ClaimStatusDescription(
    text: String,
    isWarning: Boolean = false,
    onClick: () -> Unit
) {
    val backgroundColor = if (isWarning) WarningLightest else InformationLightest
    val strokeColor = if (isWarning) WarningDark else SecondaryMedium

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        color = backgroundColor,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, strokeColor),
        onClick = onClick
    ) {
        Text(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            text = text,
            style = MobaCaptionRegular.copy(color = SecondaryDark),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun DirectPayToVetButton(
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .background(color = SecondaryDarkest)
            .clickable {
                onClick()
            }
            .fillMaxWidth()
            .height(32.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.action_direct_pay_vet),
            style = MobaCaptionBold.copy(color = White),
            textDecoration = TextDecoration.Underline
        )

        Spacer(modifier = Modifier.width(8.dp))

        Image(
            painter = painterResource(id = R.drawable.ic_share_outline),
            colorFilter = ColorFilter.tint(White),
            contentDescription = null
        )
    }
}

@Composable
@Preview
private fun ClaimTrackerCardPreview() {
    Column(
        modifier = Modifier
            .background(color = NeutralBackground)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(spacingNano)
    ) {
        ClaimTrackerCard(
            model = ClaimTrackerCardModel(
                id = "1",
                petName = "Luna",
                petType = PetType.Dog,
                claimType = ClaimType.Illness,
                claimStatus = ClaimStatus.Submitted,
                claimLastUpdated = "Sep 04, 2021",
                claimAmount = "$100.00",
                claimAmountPaid = "$100.00",
                reimbursementProcess = ReimbursementProcess.UserReimbursement,
                claimStatusDescription = null,
                petPictureUrl = null
            ),
            onDetailPressed = {},
            onDirectPaymentPressed = {}
        )

        ClaimTrackerCard(
            model = ClaimTrackerCardModel(
                id = "1",
                petName = "Luna",
                petType = PetType.Cat,
                claimType = ClaimType.Illness,
                claimStatus = ClaimStatus.InReview,
                claimLastUpdated = "Sep 04, 2021",
                claimAmount = "$100.00",
                claimAmountPaid = "$100.00",
                reimbursementProcess = ReimbursementProcess.VeterinarianReimbursement,
                claimStatusDescription = null,
                petPictureUrl = null
            ),
            onDetailPressed = {},
            onDirectPaymentPressed = {}
        )

        ClaimTrackerCard(
            model = ClaimTrackerCardModel(
                id = "1",
                petName = "Luna",
                petType = PetType.Cat,
                claimType = ClaimType.Illness,
                claimStatus = ClaimStatus.PendingMedicalHistory,
                claimLastUpdated = "Sep 04, 2021",
                claimAmount = "$100.00",
                claimAmountPaid = "$100.00",
                reimbursementProcess = ReimbursementProcess.UserReimbursement,
                claimStatusDescription = "We received your claim, however either no Medical Records were provided or they are incomplete. No worries we are hard at work to secure those from your Vet.\n Additionally, if you have them, please click here.",
                petPictureUrl = null
            ),
            onDetailPressed = {},
            onDirectPaymentPressed = {}
        )

        ClaimTrackerCard(
            model = ClaimTrackerCardModel(
                id = "1",
                petName = "Luna",
                petType = PetType.Cat,
                claimType = ClaimType.Illness,
                claimStatus = ClaimStatus.MedicalHistoryInReview,
                claimLastUpdated = "Sep 04, 2021",
                claimAmount = "$100.00",
                claimAmountPaid = "$100.00",
                reimbursementProcess = ReimbursementProcess.VeterinarianReimbursement,
                claimStatusDescription = "We are reviewing the Medical records for the claim and confirming coverage.",
                petPictureUrl = null
            ),
            onDetailPressed = {},
            onDirectPaymentPressed = {}
        )

        ClaimTrackerCard(
            model = ClaimTrackerCardModel(
                id = "1",
                petName = "Luna",
                petType = PetType.Cat,
                claimType = ClaimType.Illness,
                claimStatus = ClaimStatus.Closed,
                claimLastUpdated = "Sep 04, 2021",
                claimAmount = "$100.00",
                claimAmountPaid = "$100.00",
                reimbursementProcess = ReimbursementProcess.UserReimbursement,
                claimStatusDescription = null,
                petPictureUrl = null
            ),
            onDetailPressed = {},
            onDirectPaymentPressed = {}
        )

        ClaimTrackerDeniedCard(
            model = ClaimTrackerCardModel(
                id = "1",
                petName = "Luna",
                petType = PetType.Cat,
                claimType = ClaimType.Illness,
                claimStatus = ClaimStatus.Closed,
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
