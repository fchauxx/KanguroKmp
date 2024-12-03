package com.insurtech.kanguro.ui.scenes.petDashboard

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.insurtech.kanguro.R
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.base.FullscreenFragment
import com.insurtech.kanguro.core.utils.bottomSheetLikeTransitions
import com.insurtech.kanguro.core.utils.collectLatestIn
import com.insurtech.kanguro.core.utils.safeNavigate
import com.insurtech.kanguro.databinding.FragmentPetDashboardBinding
import com.insurtech.kanguro.designsystem.ui.composables.petDashboard.PetDashboardScreenContent
import com.insurtech.kanguro.designsystem.ui.composables.petDashboard.model.PetDashboardEvent
import com.insurtech.kanguro.ui.scenes.javier.ChatbotType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PetDashboardFragment : FullscreenFragment<FragmentPetDashboardBinding>() {

    override val screenName = AnalyticsEnums.Screen.PetDashboard

    override var lightSystemBarTint: Boolean = true

    override val viewModel: PetDashboardViewModel by viewModels()

    override fun onCreateBinding(inflater: LayoutInflater): FragmentPetDashboardBinding =
        FragmentPetDashboardBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        observeRoamUrl()
        setupStatusBarColor()
    }

    private fun setupStatusBarColor() {
        statusBarColor = ContextCompat.getColor(requireContext(), R.color.neutral_background)

        setupSystemBarTint()
    }

    private fun setupUi() {
        binding.composeView.apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )

            setContent {
                PetDashboardFragment(
                    modifier = Modifier.fillMaxSize(),
                    viewModel = viewModel,
                    onEvent = ::onEvent
                )
            }
        }
    }

    private fun observeRoamUrl() {
        viewModel.onOpenWebsite.collectLatestIn(viewLifecycleOwner) { roamUrl ->
            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse(roamUrl))
            startActivity(intent)
        }
    }

    private fun onEvent(petDashboardEvent: PetDashboardEvent) {
        when (petDashboardEvent) {
            PetDashboardEvent.OnAddPetPressed -> onAddPetPressed()
            is PetDashboardEvent.OnAdvertiserPressed -> onAdvertiserPressed(petDashboardEvent.advertiserId)
            is PetDashboardEvent.OnPetPressed -> onPetPressed(petDashboardEvent.petId)
            PetDashboardEvent.OnDirectPayVetPressed -> onDirectPayVetPressed()
            PetDashboardEvent.OnFileClaimPressed -> onFileClaimPressed()
            PetDashboardEvent.OnFindVetPressed -> onFindVetPressed()
            PetDashboardEvent.OnFrequentlyAskedQuestionsPressed -> onFrequentlyAskedQuestionsPressed()
            PetDashboardEvent.OnPaymentSettingsPressed -> onPaymentSettingsPressed()
            PetDashboardEvent.OnTrackClaimPressed -> onTrackClaimPressed()
            PetDashboardEvent.OnTellMeMorePressed -> onTellMeMorePressed()
            PetDashboardEvent.PullToRefresh -> viewModel.loadData()
            PetDashboardEvent.OnTryAgainPressed -> viewModel.loadData()
            PetDashboardEvent.OnLiveVetPressed -> onLiveVetPressed()
        }
    }

    private fun onAdvertiserPressed(advertiserId: String) {
        viewModel.getAdvertiserWebsiteUrl(
            advertiserId = advertiserId
        )
    }

    private fun onAddPetPressed() {
        findNavController().safeNavigate(
            PetDashboardFragmentDirections.actionGlobalGetQuoteFragment(),
            bottomSheetLikeTransitions
        )
    }

    private fun onPetPressed(petId: String) {
        val petPolicy = viewModel.getPetPolicy(petId)
        if (petPolicy != null) {
            findNavController().safeNavigate(
                PetDashboardFragmentDirections.actionGlobalCoverageDetailFragment(petPolicy)
            )
        }
    }

    private fun onDirectPayVetPressed() {
        findNavController().safeNavigate(
            PetDashboardFragmentDirections.actionPetDashboardFragmentToDirectPayToVetInitFlowFragment()
        )
    }

    private fun onFileClaimPressed() {
        findNavController().safeNavigate(
            PetDashboardFragmentDirections.actionGlobalJavierChatbotFragment(
                ChatbotType.NewClaim(null)
            )
        )
    }

    private fun onFindVetPressed() {
        findNavController().safeNavigate(
            PetDashboardFragmentDirections.actionGlobalChatBotMapFragment()
        )
    }

    private fun onFrequentlyAskedQuestionsPressed() {
        findNavController().safeNavigate(
            PetDashboardFragmentDirections.actionPetDashboardFragmentToPetFrequentlyAskedQuestionsFragment()
        )
    }

    private fun onPaymentSettingsPressed() {
        findNavController().safeNavigate(
            PetDashboardFragmentDirections.actionGlobalPaymentSettingsFragment()
        )
    }

    private fun onTrackClaimPressed() {
        findNavController().safeNavigate(
            PetDashboardFragmentDirections.actionGlobalPetTrackYourClaimsFragment()
        )
    }

    private fun onTellMeMorePressed() {
        findNavController().safeNavigate(
            PetDashboardFragmentDirections.actionGlobalGetQuoteFragment(),
            bottomSheetLikeTransitions
        )
    }

    private fun onLiveVetPressed() {
        findNavController().safeNavigate(
            PetDashboardFragmentDirections.actionGlobalToLiveVetBottomsheet(),
            bottomSheetLikeTransitions
        )
    }
}

@Composable
fun PetDashboardFragment(
    modifier: Modifier = Modifier,
    viewModel: PetDashboardViewModel,
    onEvent: (PetDashboardEvent) -> Unit
) {
    val state: PetDashboardViewModel.PetDashboardUiState by viewModel.state.collectAsState()

    PetDashboardScreenContent(
        modifier = modifier,
        coverages = state.coverages,
        isLoading = state.isLoading,
        isError = state.isError,
        userHasPets = state.userHasPets,
        shouldShowLiveVet = state.shouldShowLiveVet,
        onEvent = onEvent
    )
}
