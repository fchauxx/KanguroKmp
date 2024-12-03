package com.insurtech.kanguro.designsystem.ui.composables.rentersDashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.ActionCardButtonWhite
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.BottomGradientAlpha5
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.DonationBanner
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.ErrorComponent
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.RentersCoveragesListComponent
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.ScreenLoader
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.TopGradientLine
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.TrackYourClaimRenters
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.dialog.FileClaimDialog
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.CoverageStatusUi
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.RentersCoverageSummaryCardModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.DwellingType
import com.insurtech.kanguro.designsystem.ui.composables.rentersDashboard.model.RentersDashboardEvent
import com.insurtech.kanguro.designsystem.ui.composables.upselling.RentersUpsellingScreenContent
import com.insurtech.kanguro.designsystem.ui.theme.BKLHeading3
import com.insurtech.kanguro.designsystem.ui.theme.MobaCaptionBold
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground20
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground40
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground5
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground80
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDark
import com.insurtech.kanguro.designsystem.ui.theme.White

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RentersDashboardScreenContent(
    modifier: Modifier = Modifier,
    coverages: List<RentersCoverageSummaryCardModel>,
    isLoading: Boolean,
    isError: Boolean,
    showFileClaimDialog: Boolean = false,
    userHasRenters: Boolean,
    onEvent: (RentersDashboardEvent) -> Unit
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = false,
        onRefresh = {
            onEvent(
                RentersDashboardEvent.PullToRefresh
            )
        }
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        if (userHasRenters) {
            RentersDashboard(
                modifier = modifier,
                coverages = coverages,
                isLoading = isLoading,
                isError = isError,
                onEvent = onEvent
            )
        } else {
            RentersUpsellingScreenContent(
                modifier = modifier.fillMaxSize(),
                onTellMeMorePressed = {
                    onEvent(RentersDashboardEvent.OnTellMeMorePressed)
                }
            )
        }

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

        FileClaimDialog(
            showFileClaimDialog = showFileClaimDialog,
            onClick = {
                onEvent(RentersDashboardEvent.OnEmailPressed)
            },
            onDismiss = {
                onEvent(RentersDashboardEvent.OnFileClaimDismissed)
            }
        )
    }
}

@Composable
private fun RentersDashboard(
    modifier: Modifier = Modifier,
    coverages: List<RentersCoverageSummaryCardModel>,
    isLoading: Boolean,
    isError: Boolean,
    onEvent: (RentersDashboardEvent) -> Unit
) {
    Box(
        modifier = modifier
            .background(color = NeutralBackground)
            .fillMaxSize()
    ) {
        ParentContent(
            isLoading = isLoading,
            isError = isError,
            coverages = coverages,
            onEvent = onEvent
        )

        LoadingErrorStates(
            isLoading = isLoading,
            isError = isError,
            onEvent = onEvent
        )
    }
}

@Composable
private fun Title(
    modifier: Modifier
) {
    Text(
        text = stringResource(id = R.string.renters_insurance),
        style = BKLHeading3,
        modifier = modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
    )
}

@Composable
private fun ParentContent(
    isLoading: Boolean,
    isError: Boolean,
    coverages: List<RentersCoverageSummaryCardModel>,
    onEvent: (RentersDashboardEvent) -> Unit
) {
    ConstraintLayout(
        Modifier
            .padding(top = 16.dp)
            .fillMaxSize()
    ) {
        val (title, gradient, content) = createRefs()

        Title(
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Content(
            isLoading = isLoading,
            isError = isError,
            coverages = coverages,
            onEvent = onEvent,
            modifier = Modifier
                .constrainAs(content) {
                    top.linkTo(title.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                }
        )

        TopGradientLine(
            colors = listOf(
                NeutralBackground,
                NeutralBackground80,
                NeutralBackground40,
                NeutralBackground20,
                NeutralBackground5
            ),
            modifier = Modifier.constrainAs(gradient) {
                top.linkTo(title.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
    }
}

@Composable
private fun Content(
    modifier: Modifier,
    isLoading: Boolean,
    isError: Boolean,
    coverages: List<RentersCoverageSummaryCardModel>,
    onEvent: (RentersDashboardEvent) -> Unit
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        if (!isLoading && !isError) {
            Spacer(modifier = Modifier.height(32.dp))

            RentersCoveragesListComponent(
                coverages = coverages,
                onCoveragePressed = {
                    onEvent(RentersDashboardEvent.OnCoveragePressed(it))
                },
                onAddResidencePressed = {
                    onEvent(RentersDashboardEvent.OnAddResidencePressed)
                }
            )

            Spacer(modifier = Modifier.heightIn(32.dp))

            DonationBanner(
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                onEvent(RentersDashboardEvent.OnDonationPressed)
            }

            Spacer(modifier = Modifier.heightIn(32.dp))

            MoreActionsSection(
                modifier = Modifier.padding(horizontal = 24.dp),
                onEvent = onEvent
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun LoadingErrorStates(
    isLoading: Boolean,
    isError: Boolean,
    onEvent: (RentersDashboardEvent) -> Unit
) {
    if (isLoading) {
        Loader()
    } else if (isError) {
        Error {
            onEvent(RentersDashboardEvent.OnTryAgainPressed)
        }
    }
}

@Composable
private fun MoreActionsSection(
    modifier: Modifier = Modifier,
    onEvent: (RentersDashboardEvent) -> Unit
) {
    Text(
        modifier = modifier,
        text = stringResource(id = R.string.more_actions).uppercase(),
        style = MobaCaptionBold.copy(color = SecondaryDark)
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        ActionCardButtonWhite(
            text = stringResource(id = R.string.file_claim),
            icon = R.drawable.ic_add_square
        ) {
            onEvent(RentersDashboardEvent.OnFileClaimPressed)
        }

        TrackYourClaimRenters(backgroundColor = White) {
            onEvent(RentersDashboardEvent.OnEmailPressed)
        }

        ActionCardButtonWhite(
            text = stringResource(id = R.string.add_residence),
            icon = R.drawable.ic_house
        ) {
            onEvent(RentersDashboardEvent.OnAddResidencePressed)
        }

        ActionCardButtonWhite(
            text = stringResource(id = R.string.payment_settings),
            icon = R.drawable.ic_dollar_circle
        ) {
            onEvent(RentersDashboardEvent.OnPaymentSettingsPressed)
        }

        ActionCardButtonWhite(
            text = stringResource(id = R.string.frequently_asked_questions),
            icon = R.drawable.ic_message_question
        ) {
            onEvent(RentersDashboardEvent.OnFrequentlyAskedQuestionsPressed)
        }
    }
}

@Composable
private fun Loader() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (loader) = createRefs()

        ScreenLoader(
            color = White,
            modifier = Modifier
                .size(width = 87.dp, height = 84.dp)
                .constrainAs(loader) {
                    linkTo(top = parent.top, bottom = parent.bottom, bias = 0.45f)
                    linkTo(start = parent.start, end = parent.end)
                }
        )
    }
}

@Composable
private fun Error(
    onTryAgainPressed: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 22.dp)
    ) {
        val (errorComponent) = createRefs()

        ErrorComponent(
            onTryAgainPressed = onTryAgainPressed,
            modifier = Modifier
                .constrainAs(errorComponent) {
                    linkTo(top = parent.top, bottom = parent.bottom, bias = 0.5f)
                    linkTo(start = parent.start, end = parent.end)
                }
        )
    }
}

@Composable
@Preview
private fun RentersDashboardScreenContentPreview() {
    Surface {
        RentersDashboardScreenContent(
            coverages = listOf(
                RentersCoverageSummaryCardModel(
                    id = "1",
                    address = "Miami, FL",
                    type = DwellingType.SingleFamily,
                    status = CoverageStatusUi.Active
                )
            ),
            onEvent = {},
            isLoading = false,
            isError = false,
            userHasRenters = true
        )
    }
}

@Composable
@Preview
private fun RentersDashboardScreenLoaderPreview() {
    Surface {
        RentersDashboardScreenContent(
            coverages = listOf(
                RentersCoverageSummaryCardModel(
                    id = "1",
                    address = "Miami, FL",
                    type = DwellingType.SingleFamily,
                    status = CoverageStatusUi.Active
                )
            ),
            onEvent = {},
            isLoading = true,
            isError = false,
            userHasRenters = true
        )
    }
}

@Composable
@Preview
private fun RentersDashboardScreenErrorPreview() {
    Surface {
        RentersDashboardScreenContent(
            coverages = listOf(
                RentersCoverageSummaryCardModel(
                    id = "1",
                    address = "Miami, FL",
                    type = DwellingType.SingleFamily,
                    status = CoverageStatusUi.Active
                )
            ),
            onEvent = {},
            isLoading = false,
            isError = true,
            userHasRenters = true
        )
    }
}

@Composable
@Preview
private fun RentersDashboardScreenNoRentersPreview() {
    Surface {
        RentersDashboardScreenContent(
            coverages = listOf(
                RentersCoverageSummaryCardModel(
                    id = "1",
                    address = "Miami, FL",
                    type = DwellingType.SingleFamily,
                    status = CoverageStatusUi.Active
                )
            ),
            onEvent = {},
            isLoading = false,
            isError = true,
            userHasRenters = false
        )
    }
}

@Composable
@Preview
private fun RentersDashboardScreenFileClaimPreview() {
    Surface {
        RentersDashboardScreenContent(
            coverages = listOf(
                RentersCoverageSummaryCardModel(
                    id = "1",
                    address = "Miami, FL",
                    type = DwellingType.SingleFamily,
                    status = CoverageStatusUi.Active
                )
            ),
            onEvent = {},
            showFileClaimDialog = true,
            isLoading = false,
            isError = false,
            userHasRenters = true
        )
    }
}
