package com.insurtech.kanguro.ui.scenes.rentersDashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.ColorRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.insurtech.kanguro.R
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.base.FullscreenFragment
import com.insurtech.kanguro.core.utils.IntentUtils
import com.insurtech.kanguro.core.utils.UiState
import com.insurtech.kanguro.core.utils.bottomSheetLikeTransitions
import com.insurtech.kanguro.core.utils.collectLatestIn
import com.insurtech.kanguro.core.utils.safeNavigate
import com.insurtech.kanguro.databinding.FragmentRentersDashboardBinding
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.CoverageStatusUi
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.RentersCoverageSummaryCardModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.DwellingType
import com.insurtech.kanguro.designsystem.ui.composables.rentersDashboard.RentersDashboardScreenContent
import com.insurtech.kanguro.designsystem.ui.composables.rentersDashboard.model.RentersDashboardEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RentersDashboardFragment : FullscreenFragment<FragmentRentersDashboardBinding>() {

    override var lightSystemBarTint: Boolean = true

    override val screenName = AnalyticsEnums.Screen.RentersDashboard

    override val viewModel: RentersDashboardViewModel by viewModels()

    override fun onCreateBinding(inflater: LayoutInflater): FragmentRentersDashboardBinding =
        FragmentRentersDashboardBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        onObserveViewModel()
    }

    private fun onObserveViewModel() {
        viewModel.uiState.collectLatestIn(viewLifecycleOwner) {
            @ColorRes val color: Int = when {
                it is UiState.Loading -> R.color.neutral_background

                it is UiState.Error -> R.color.neutral_background

                it is UiState.Success && it.data.isNotEmpty() -> R.color.neutral_background

                else -> R.color.white
            }

            statusBarColor = ContextCompat.getColor(requireContext(), color)

            setupSystemBarTint()
        }
    }

    private fun setupUi() {
        binding.composeView.apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )

            setContent {
                RentersDashboardFragment(
                    viewModel = viewModel,
                    onEvent = ::handleEvent
                )
            }
        }
    }

    private fun handleEvent(event: RentersDashboardEvent) {
        when (event) {
            is RentersDashboardEvent.OnAddResidencePressed -> onAddResidencePressed()

            is RentersDashboardEvent.OnDonationPressed -> onDonationPressed()

            is RentersDashboardEvent.OnFileClaimPressed -> viewModel.setShowFileClaimDialog(true)

            is RentersDashboardEvent.OnPaymentSettingsPressed -> onPaymentSettingsPressed()

            is RentersDashboardEvent.OnFrequentlyAskedQuestionsPressed -> onFrequentlyAskedQuestionsPressed()

            is RentersDashboardEvent.OnCoveragePressed -> onCoveragePressed(event.coverageId)

            is RentersDashboardEvent.OnTryAgainPressed -> viewModel.fetchPolicies()

            is RentersDashboardEvent.PullToRefresh -> viewModel.fetchPolicies()

            is RentersDashboardEvent.OnTellMeMorePressed -> onTellMeMorePressed()

            is RentersDashboardEvent.OnEmailPressed -> onEmailPressed()

            is RentersDashboardEvent.OnFileClaimDismissed -> viewModel.setShowFileClaimDialog(false)
        }
    }

    private fun onEmailPressed() {
        IntentUtils.openMailToKanguroRentersClaims(requireContext())
    }

    private fun onCoveragePressed(coverageId: String) {
        findNavController().safeNavigate(
            RentersDashboardFragmentDirections
                .actionRentersDashboardFragmentToRentersCoverageDetailsFragment(coverageId)
        )
    }

    private fun onAddResidencePressed() {
        findNavController().safeNavigate(
            RentersDashboardFragmentDirections.actionGlobalGetQuoteRentersFragment(),
            bottomSheetLikeTransitions
        )
    }

    private fun onDonationPressed() {
        if (viewModel.userAlreadyChoseCause) {
            findNavController().safeNavigate(RentersDashboardFragmentDirections.actionGlobalChangeCause())
        } else {
            findNavController().safeNavigate(RentersDashboardFragmentDirections.actionGlobalSupportCause())
        }
    }

    private fun onPaymentSettingsPressed() {
        findNavController().safeNavigate(RentersDashboardFragmentDirections.actionGlobalPaymentSettingsFragment())
    }

    private fun onFrequentlyAskedQuestionsPressed() {
        findNavController().safeNavigate(
            RentersDashboardFragmentDirections.actionRentersDashboardFragmentToRentersFrequentlyAskedQuestionsFragment()
        )
    }

    private fun onTellMeMorePressed() {
        findNavController().safeNavigate(
            RentersDashboardFragmentDirections.actionGlobalGetQuoteRentersFragment(),
            bottomSheetLikeTransitions
        )
    }
}

@Composable
private fun RentersDashboardFragment(
    viewModel: RentersDashboardViewModel,
    onEvent: (RentersDashboardEvent) -> Unit
) {
    val uiState: UiState<List<RentersCoverageSummaryCardModel>> by viewModel.uiState.collectAsState()

    val showFileClaimDialog: Boolean by viewModel.showFileClaim.collectAsState()

    RentersDashboardScreenContent(
        coverages = try {
            (uiState as UiState.Success).data
        } catch (e: Exception) {
            emptyList()
        },
        onEvent = onEvent,
        showFileClaimDialog = showFileClaimDialog,
        isLoading = uiState is UiState.Loading,
        isError = uiState is UiState.Error,
        userHasRenters = when {
            uiState is UiState.Loading -> true
            uiState is UiState.Error -> true
            uiState is UiState.Success && (uiState as UiState.Success).data.isNotEmpty() -> true
            else -> false
        }
    )
}

@Composable
@Preview(device = "id:pixel_4_xl", showBackground = true)
private fun RentersDashboardFragmentPreview() {
    RentersDashboardScreenContent(
        modifier = Modifier.fillMaxSize(),
        coverages = listOf(
            RentersCoverageSummaryCardModel(
                id = "1",
                address = "Miami, FL",
                type = DwellingType.SingleFamily,
                status = CoverageStatusUi.Active
            ),
            RentersCoverageSummaryCardModel(
                id = "1",
                address = "Miami, FL",
                type = DwellingType.SingleFamily,
                status = CoverageStatusUi.Active
            ),
            RentersCoverageSummaryCardModel(
                id = "1",
                address = "Miami, FL",
                type = DwellingType.SingleFamily,
                status = CoverageStatusUi.Active
            ),
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
