package com.insurtech.kanguro.ui.scenes.trackYourClaim

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.core.graphics.Insets
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.insurtech.kanguro.R
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.common.enums.ClaimStatus
import com.insurtech.kanguro.core.base.FullscreenFragment
import com.insurtech.kanguro.core.utils.safeNavigate
import com.insurtech.kanguro.databinding.FragmentTrackYourClaimBinding
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.PetType
import com.insurtech.kanguro.designsystem.ui.composables.petTrackYourClaims.TrackYourClaimsScreenContent
import com.insurtech.kanguro.designsystem.ui.composables.petTrackYourClaims.model.ClaimTrackerCardModel
import com.insurtech.kanguro.designsystem.ui.composables.petTrackYourClaims.model.TrackYourClaimsEvent
import com.insurtech.kanguro.domain.model.ClaimType
import com.insurtech.kanguro.domain.model.ReimbursementProcess
import com.insurtech.kanguro.ui.scenes.javier.ChatbotType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrackYourClaimFragment : FullscreenFragment<FragmentTrackYourClaimBinding>() {

    override val viewModel: TrackYourClaimViewModel by viewModels()

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.Claims

    override var lightSystemBarTint: Boolean = true

    override fun processWindowInsets(insets: Insets) {
        binding.root.updatePadding(top = insets.top)
    }

    override fun onCreateBinding(inflater: LayoutInflater) =
        FragmentTrackYourClaimBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupStatusBarColor()
        setupUi()
    }

    private fun setupStatusBarColor() {
        statusBarColor = ContextCompat.getColor(requireContext(), R.color.neutral_background)
        setupSystemBarTint()
    }

    private fun setupUi() {
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

            setContent {
                TrackYourClaimFragment(
                    viewmodel = viewModel,
                    onEvent = ::handleEvent
                )
            }
        }
    }

    private fun handleEvent(event: TrackYourClaimsEvent) {
        when (event) {
            is TrackYourClaimsEvent.OnClaimDetailPressed -> {
                val selectedClaim = viewModel.getClaimById(event.claimId) ?: return

                findNavController().safeNavigate(
                    TrackYourClaimFragmentDirections.actionTrackYourClaimsFragmentToClaimDetailsBottomSheet(
                        selectedClaim.toOldClaim()
                    )
                )
            }

            is TrackYourClaimsEvent.OnDirectPayToVetPressed -> {
                findNavController().safeNavigate(
                    TrackYourClaimFragmentDirections.actionTrackYourClaimsFragmentToDirectPayToVetAlmostDone(
                        event.claimId
                    )
                )
            }

            TrackYourClaimsEvent.OnBackButtonPressed -> {
                findNavController().popBackStack()
            }

            TrackYourClaimsEvent.OnNewClaimPressed -> {
                findNavController().safeNavigate(
                    TrackYourClaimFragmentDirections.actionGlobalJavierChatbotFragment(
                        ChatbotType.NewClaim(null)
                    )
                )
            }

            TrackYourClaimsEvent.OnPullToRefresh -> {
                viewModel.fetchClaims()
            }

            TrackYourClaimsEvent.OnTryAgainPressed -> {
                viewModel.fetchClaims()
            }
        }
    }
}

@Composable
fun TrackYourClaimFragment(
    viewmodel: TrackYourClaimViewModel,
    onEvent: (TrackYourClaimsEvent) -> Unit
) {
    val uiState: TrackYourClaimViewModel.TrackYourClaimUiState by viewmodel.uiState.collectAsState()

    TrackYourClaimsScreenContent(
        isLoading = uiState.isLoading,
        isError = uiState.isError,
        claimTrackerCardModel = uiState.claims,
        onEvent = onEvent
    )
}

@Preview
@Composable
private fun TrackYourClaimFragmentPreview() {
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
