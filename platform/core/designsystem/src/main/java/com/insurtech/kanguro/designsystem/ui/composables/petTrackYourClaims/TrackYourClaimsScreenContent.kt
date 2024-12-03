package com.insurtech.kanguro.designsystem.ui.composables.petTrackYourClaims

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.common.enums.ClaimStatus
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.BottomGradientAlpha5
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.LoadingErrorStateOverlay
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.TopGradientLine
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.PetType
import com.insurtech.kanguro.designsystem.ui.composables.petTrackYourClaims.model.ClaimTrackerCardModel
import com.insurtech.kanguro.designsystem.ui.composables.petTrackYourClaims.model.TrackYourClaimsEvent
import com.insurtech.kanguro.designsystem.ui.theme.BKLHeading3
import com.insurtech.kanguro.designsystem.ui.theme.MobaHeadline
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground20
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground40
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground5
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground80
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDarkest
import com.insurtech.kanguro.designsystem.ui.theme.White
import com.insurtech.kanguro.designsystem.ui.theme.spacingLg
import com.insurtech.kanguro.designsystem.ui.theme.spacingXxs
import com.insurtech.kanguro.designsystem.ui.theme.spacingXxxs
import com.insurtech.kanguro.domain.model.ClaimType
import com.insurtech.kanguro.domain.model.ReimbursementProcess

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TrackYourClaimsScreenContent(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    isError: Boolean = false,
    claimTrackerCardModel: List<ClaimTrackerCardModel> = emptyList(),
    onEvent: (TrackYourClaimsEvent) -> Unit
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = false,
        onRefresh = {
            onEvent(TrackYourClaimsEvent.OnPullToRefresh)
        }
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
            .background(color = NeutralBackground)
    ) {
        MainContent(
            claimTrackerCardModels = claimTrackerCardModel,
            isLoading = isLoading,
            isError = isError,
            onEvent = onEvent
        )

        PullRefreshIndicator(
            refreshing = false,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
private fun MainContent(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    isError: Boolean,
    claimTrackerCardModels: List<ClaimTrackerCardModel> = emptyList(),
    onEvent: (TrackYourClaimsEvent) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        TrackYourClaimsBackButton(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp)
        ) {
            onEvent(TrackYourClaimsEvent.OnBackButtonPressed)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            modifier = modifier
                .padding(horizontal = spacingXxs)
                .padding(bottom = spacingXxxs)
                .fillMaxWidth(),
            text = stringResource(id = R.string.track_your_claim),
            style = BKLHeading3
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (isLoading || isError) {
                LoadingErrorStateOverlay(
                    isLoading = isLoading,
                    isError = isError,
                    onTryAgainPressed = { onEvent(TrackYourClaimsEvent.OnTryAgainPressed) }
                )
            } else if (claimTrackerCardModels.isEmpty()) {
                EmptyStateScreen(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                ClaimsList(
                    modifier = Modifier,
                    claimsList = claimTrackerCardModels,
                    onEvent = onEvent
                )
            }

            TopGradientLine(
                colors = listOf(
                    NeutralBackground,
                    NeutralBackground80,
                    NeutralBackground40,
                    NeutralBackground20,
                    NeutralBackground5
                ),
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
            )

            BottomGradientAlpha5(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
            )

            IconButton(
                onClick = {
                    onEvent(TrackYourClaimsEvent.OnNewClaimPressed)
                },
                modifier = Modifier
                    .padding(16.dp)
                    .size(40.dp)
                    .align(Alignment.BottomEnd)
                    .background(color = SecondaryDarkest, shape = CircleShape)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(color = White)
                )
            }
        }
    }
}

@Composable
private fun EmptyStateScreen(
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_lupe),
            contentDescription = ""
        )
        Text(
            modifier = Modifier.padding(top = 24.dp),
            text = stringResource(id = R.string.track_claims_empty_list),
            textAlign = TextAlign.Center,
            style = MobaHeadline
        )
    }
}

@Composable
private fun ClaimsList(
    modifier: Modifier = Modifier,
    claimsList: List<ClaimTrackerCardModel> = emptyList(),
    onEvent: (TrackYourClaimsEvent) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = spacingLg)
    ) {
        items(claimsList) { claim ->
            if (claim.claimStatus == ClaimStatus.Denied) {
                ClaimTrackerDeniedCard(
                    modifier = Modifier.padding(horizontal = spacingXxs),
                    model = claim,
                    onDetailPressed = {
                        onEvent(TrackYourClaimsEvent.OnClaimDetailPressed(claim.id))
                    }
                )
            } else {
                ClaimTrackerCard(
                    modifier = Modifier.padding(horizontal = spacingXxs),
                    model = claim,
                    onDetailPressed = {
                        onEvent(TrackYourClaimsEvent.OnClaimDetailPressed(claim.id))
                    },
                    onDirectPaymentPressed = {
                        onEvent(TrackYourClaimsEvent.OnDirectPayToVetPressed(claim.id))
                    }

                )
            }
        }
    }
}

@Preview
@Composable
private fun TrackYourClaimsScreenContentPreview() {
    Surface {
        TrackYourClaimsScreenContent(
            isLoading = false,
            isError = false,
            claimTrackerCardModel = listOf(
                ClaimTrackerCardModel(
                    id = "1",
                    petName = "Luna",
                    petType = PetType.Cat,
                    claimType = ClaimType.Illness,
                    claimStatus = ClaimStatus.Submitted,
                    claimLastUpdated = "Sep 04, 2021",
                    claimAmount = "$100.00",
                    claimAmountPaid = "$100.00",
                    reimbursementProcess = ReimbursementProcess.VeterinarianReimbursement,
                    claimStatusDescription = null,
                    petPictureUrl = null
                ),
                ClaimTrackerCardModel(
                    id = "1",
                    petName = "Luna",
                    petType = PetType.Cat,
                    claimType = ClaimType.Illness,
                    claimStatus = ClaimStatus.InReview,
                    claimLastUpdated = "Sep 04, 2021",
                    claimAmount = "$100.00",
                    claimAmountPaid = "$100.00",
                    reimbursementProcess = ReimbursementProcess.UserReimbursement,
                    claimStatusDescription = null,
                    petPictureUrl = null
                ),
                ClaimTrackerCardModel(
                    id = "2",
                    petName = "Oliver",
                    petType = PetType.Dog,
                    claimType = ClaimType.Other,
                    claimStatus = ClaimStatus.MedicalHistoryInReview,
                    claimLastUpdated = "Sep 04, 2021",
                    claimAmount = "$100.00",
                    claimAmountPaid = "$100.00",
                    reimbursementProcess = ReimbursementProcess.UserReimbursement,
                    claimStatusDescription = "We are reviewing the Medical records for the claim and confirming coverage.",
                    petPictureUrl = null
                ),
                ClaimTrackerCardModel(
                    id = "2",
                    petName = "Oliver",
                    petType = PetType.Dog,
                    claimType = ClaimType.Other,
                    claimStatus = ClaimStatus.PendingMedicalHistory,
                    claimLastUpdated = "Sep 04, 2021",
                    claimAmount = "$100.00",
                    claimAmountPaid = "$100.00",
                    reimbursementProcess = ReimbursementProcess.UserReimbursement,
                    claimStatusDescription = "We received your claim, however either no Medical Records were provided or they are incomplete. No worries we are hard at work to secure those from your Vet.\n Additionally, if you have them, please click here.",
                    petPictureUrl = null
                ),
                ClaimTrackerCardModel(
                    id = "3",
                    petName = "Meg",
                    petType = PetType.Cat,
                    claimType = ClaimType.Illness,
                    claimStatus = ClaimStatus.Closed,
                    claimLastUpdated = "Sep 04, 2021",
                    claimAmount = "$100.00",
                    claimAmountPaid = "$100.00",
                    reimbursementProcess = ReimbursementProcess.VeterinarianReimbursement,
                    claimStatusDescription = null,
                    petPictureUrl = null
                ),
                ClaimTrackerCardModel(
                    id = "1",
                    petName = "Luna",
                    petType = PetType.Cat,
                    claimType = ClaimType.Illness,
                    claimStatus = ClaimStatus.Denied,
                    claimLastUpdated = "Sep 04, 2021",
                    claimAmount = "$100.00",
                    claimAmountPaid = "$100.00",
                    reimbursementProcess = ReimbursementProcess.UserReimbursement,
                    claimStatusDescription = null,
                    petPictureUrl = null
                )
            ),
            onEvent = {}
        )
    }
}

@Preview
@Composable
private fun TrackYourClaimsScreenContentEmptyPreview() {
    Surface {
        TrackYourClaimsScreenContent(
            isLoading = false,
            isError = false,
            onEvent = {}
        )
    }
}

@Preview
@Composable
private fun TrackYourClaimsScreenContentLoadingPreview() {
    Surface {
        TrackYourClaimsScreenContent(
            isLoading = true,
            isError = false,
            onEvent = {}
        )
    }
}

@Preview
@Composable
private fun TrackYourClaimsScreenContentErrorPreview() {
    Surface {
        TrackYourClaimsScreenContent(
            isLoading = false,
            isError = true,
            onEvent = {}
        )
    }
}
