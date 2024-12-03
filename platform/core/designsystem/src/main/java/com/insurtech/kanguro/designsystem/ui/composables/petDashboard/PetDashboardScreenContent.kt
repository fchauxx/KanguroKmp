package com.insurtech.kanguro.designsystem.ui.composables.petDashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.ActionCardButtonWhite
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.AdvertiserCardCarousel
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.BottomGradientAlpha5
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.LoadingErrorStateOverlay
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.TopGradientLine
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.actions.ActionCardLiveVet
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.AdvertiserCardModels
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.CoverageStatusUi
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.PetType
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.PetsCoverageSummaryCardModel
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.petsCoverageList.LiveVetPetsCoveragesListComponent
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.petsCoverageList.PetsCoveragesListComponent
import com.insurtech.kanguro.designsystem.ui.composables.petDashboard.model.PetDashboardEvent
import com.insurtech.kanguro.designsystem.ui.composables.upselling.PetUpsellingScreenContent
import com.insurtech.kanguro.designsystem.ui.theme.BKLHeading3
import com.insurtech.kanguro.designsystem.ui.theme.MobaCaptionBold
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground20
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground40
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground5
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground80
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDark
import com.insurtech.kanguro.designsystem.ui.theme.spacingXs
import com.insurtech.kanguro.designsystem.ui.theme.spacingXxs
import com.insurtech.kanguro.designsystem.ui.theme.spacingXxxs
import java.util.Calendar

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PetDashboardScreenContent(
    modifier: Modifier = Modifier,
    coverages: SnapshotStateList<PetsCoverageSummaryCardModel>,
    isLoading: Boolean,
    isError: Boolean,
    userHasPets: Boolean,
    shouldShowLiveVet: Boolean,
    onEvent: (PetDashboardEvent) -> Unit
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = false,
        onRefresh = {
            onEvent(PetDashboardEvent.PullToRefresh)
        }
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        PetDashboardLoadingErrorStateWrapper(
            modifier = Modifier.fillMaxSize(),
            coverages = coverages,
            isLoading = isLoading,
            isError = isError,
            userHasPets = userHasPets,
            shouldShowLiveVet = shouldShowLiveVet,
            onEvent = onEvent
        )

        PullRefreshIndicator(
            refreshing = false,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )

        BottomGradientAlpha5(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }
}

@Composable
private fun PetDashboardLoadingErrorStateWrapper(
    modifier: Modifier = Modifier,
    coverages: SnapshotStateList<PetsCoverageSummaryCardModel>,
    isLoading: Boolean,
    isError: Boolean,
    userHasPets: Boolean,
    shouldShowLiveVet: Boolean,
    onEvent: (PetDashboardEvent) -> Unit
) {
    Box(
        modifier = modifier
            .background(color = NeutralBackground)
            .fillMaxSize()
    ) {
        if (!isLoading && !isError) {
            if (userHasPets) {
                PetDashboard(
                    coverages = coverages,
                    shouldShowLiveVet = shouldShowLiveVet,
                    onEvent = onEvent
                )
            } else {
                PetUpsellingScreenContent(
                    modifier = Modifier.fillMaxSize(),
                    onTellMeMorePressed = { onEvent(PetDashboardEvent.OnTellMeMorePressed) }
                )
            }
        } else {
            PetDashboardLoadingErrorScreen(
                isLoading = isLoading,
                isError = isError,
                onTryAgainPressed = { onEvent(PetDashboardEvent.OnTryAgainPressed) }
            )
        }
    }
}

@Composable
private fun PetDashboard(
    modifier: Modifier = Modifier,
    coverages: SnapshotStateList<PetsCoverageSummaryCardModel>,
    shouldShowLiveVet: Boolean,
    onEvent: (PetDashboardEvent) -> Unit
) {
    Column(
        modifier
            .padding(top = spacingXxxs)
            .fillMaxSize()
    ) {
        PetDashboardTitle(
            modifier = Modifier
                .padding(horizontal = spacingXxs)
                .fillMaxWidth()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Content(
                modifier = Modifier.fillMaxSize(),
                coverages = coverages,
                shouldShowLiveVet = shouldShowLiveVet,
                onEvent = onEvent
            )

            TopGradientLine(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth(),
                colors = listOf(
                    NeutralBackground,
                    NeutralBackground80,
                    NeutralBackground40,
                    NeutralBackground20,
                    NeutralBackground5
                )
            )
        }
    }
}

@Composable
private fun PetDashboardLoadingErrorScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    isError: Boolean,
    onTryAgainPressed: () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        PetDashboardTitle(
            modifier = Modifier
                .padding(top = spacingXxxs, start = spacingXxs, end = spacingXxs)
                .align(Alignment.TopStart)
        )
        LoadingErrorStateOverlay(
            isLoading = isLoading,
            isError = isError,
            onTryAgainPressed = { onTryAgainPressed() }
        )
    }
}

@Composable
private fun PetDashboardTitle(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(id = R.string.pet_upselling_pet_health_plan),
        style = BKLHeading3,
        modifier = modifier
    )
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    coverages: SnapshotStateList<PetsCoverageSummaryCardModel>,
    shouldShowLiveVet: Boolean,
    onEvent: (PetDashboardEvent) -> Unit
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(spacingXxs))

        if (shouldShowLiveVet) {
            LiveVetPetsCoveragesListComponent(
                coverages = coverages,
                onCoveragePressed = { onEvent(PetDashboardEvent.OnPetPressed(it)) },
                onAddPetPressed = { onEvent(PetDashboardEvent.OnAddPetPressed) },
                onLiveVetPressed = { onEvent(PetDashboardEvent.OnLiveVetPressed) }
            )
        } else {
            PetsCoveragesListComponent(
                coverages = coverages,
                onCoveragePressed = { onEvent(PetDashboardEvent.OnPetPressed(it)) },
                onAddPetPressed = { onEvent(PetDashboardEvent.OnAddPetPressed) }
            )
        }

        Spacer(modifier = Modifier.height(spacingXs))

        AdvertiserCardCarousel(
            defaultOnClickCard = { onEvent(PetDashboardEvent.OnAdvertiserPressed(it)) },
            cardList = mutableStateListOf(
                AdvertiserCardModels.Roam,
                AdvertiserCardModels.MissingPets
            )
        )

        Spacer(modifier = Modifier.height(spacingXs))

        MoreActionsSection(
            modifier = Modifier.padding(horizontal = spacingXxs),
            shouldShowLiveVet = shouldShowLiveVet,
            onEvent = onEvent
        )

        Spacer(modifier = Modifier.height(spacingXxs))
    }
}

@Composable
private fun MoreActionsSection(
    modifier: Modifier = Modifier,
    shouldShowLiveVet: Boolean,
    onEvent: (PetDashboardEvent) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = stringResource(id = R.string.more_actions).uppercase(),
            style = MobaCaptionBold.copy(color = SecondaryDark)
        )

        Spacer(modifier = Modifier.height(spacingXxxs))

        if (shouldShowLiveVet) {
            ActionCardLiveVet {
                onEvent(PetDashboardEvent.OnLiveVetPressed)
            }
        }

        ActionCardButtonWhite(
            text = stringResource(id = R.string.file_claim),
            icon = R.drawable.ic_add_square
        ) {
            onEvent(PetDashboardEvent.OnFileClaimPressed)
        }

        ActionCardButtonWhite(
            text = stringResource(id = R.string.track_your_claim),
            icon = R.drawable.ic_coverage_list
        ) {
            onEvent(PetDashboardEvent.OnTrackClaimPressed)
        }

        ActionCardButtonWhite(
            text = stringResource(id = R.string.action_direct_pay_vet),
            icon = R.drawable.ic_dollar_circle_arrow
        ) {
            onEvent(PetDashboardEvent.OnDirectPayVetPressed)
        }

        ActionCardButtonWhite(
            text = stringResource(id = R.string.action_add_a_pet),
            icon = R.drawable.ic_paw
        ) {
            onEvent(PetDashboardEvent.OnAddPetPressed)
        }

        ActionCardButtonWhite(
            text = stringResource(id = R.string.payment_settings),
            icon = R.drawable.ic_dollar_circle
        ) {
            onEvent(PetDashboardEvent.OnPaymentSettingsPressed)
        }

        ActionCardButtonWhite(
            text = stringResource(id = R.string.find_your_vet),
            icon = R.drawable.ic_maps
        ) {
            onEvent(PetDashboardEvent.OnFindVetPressed)
        }

        ActionCardButtonWhite(
            text = stringResource(id = R.string.frequently_asked_questions),
            icon = R.drawable.ic_message_question
        ) {
            onEvent(PetDashboardEvent.OnFrequentlyAskedQuestionsPressed)
        }
    }
}

@Preview
@Composable
private fun PetDashboardScreenContentPreview() {
    Surface {
        PetDashboardScreenContent(
            coverages = mutableStateListOf(
                PetsCoverageSummaryCardModel(
                    id = "1",
                    breed = "Labradoodle",
                    birthDate = Calendar.getInstance().apply {
                        set(Calendar.YEAR, 2019)
                        set(Calendar.MONTH, Calendar.NOVEMBER)
                        set(Calendar.DAY_OF_MONTH, 29)
                    }.time,
                    status = CoverageStatusUi.Active,
                    pictureUrl = "",
                    name = "Oliver",
                    petType = PetType.Cat
                )
            ),
            isLoading = false,
            isError = false,
            userHasPets = true,
            shouldShowLiveVet = true,
            onEvent = {}
        )
    }
}

@Preview
@Composable
private fun PetDashboardScreenContentLoadingPreview() {
    PetDashboardScreenContent(
        coverages = mutableStateListOf(
            PetsCoverageSummaryCardModel(
                id = "1",
                breed = "Labradoodle",
                birthDate = Calendar.getInstance().apply {
                    set(Calendar.YEAR, 2019)
                    set(Calendar.MONTH, Calendar.NOVEMBER)
                    set(Calendar.DAY_OF_MONTH, 29)
                }.time,
                status = CoverageStatusUi.Active,
                pictureUrl = "",
                name = "Oliver",
                petType = PetType.Cat
            )
        ),
        isLoading = true,
        isError = false,
        userHasPets = true,
        shouldShowLiveVet = true,
        onEvent = {}
    )
}

@Preview
@Composable
private fun PetDashboardScreenContentErrorPreview() {
    PetDashboardScreenContent(
        coverages = mutableStateListOf(
            PetsCoverageSummaryCardModel(
                id = "1",
                breed = "Labradoodle",
                birthDate = Calendar.getInstance().apply {
                    set(Calendar.YEAR, 2019)
                    set(Calendar.MONTH, Calendar.NOVEMBER)
                    set(Calendar.DAY_OF_MONTH, 29)
                }.time,
                status = CoverageStatusUi.Active,
                pictureUrl = "",
                name = "Oliver",
                petType = PetType.Cat
            )
        ),
        isLoading = false,
        isError = true,
        userHasPets = true,
        shouldShowLiveVet = true,
        onEvent = {}
    )
}
