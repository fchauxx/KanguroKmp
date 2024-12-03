package com.insurtech.kanguro.ui.scenes.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.insurtech.kanguro.R
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.base.FullscreenFragment
import com.insurtech.kanguro.core.base.GenericBroadcastReceiver
import com.insurtech.kanguro.core.utils.IntentUtils
import com.insurtech.kanguro.core.utils.KanguroConstants
import com.insurtech.kanguro.core.utils.bottomSheetLikeTransitions
import com.insurtech.kanguro.core.utils.registerForAction
import com.insurtech.kanguro.core.utils.safeNavigate
import com.insurtech.kanguro.databinding.FragmentHomeBinding
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.CoverageStatusUi
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.PetType
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.PetsCoverageSummaryCardModel
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.RentersCoverageSummaryCardModel
import com.insurtech.kanguro.designsystem.ui.composables.homeDashboard.HomeDashboardScreenContent
import com.insurtech.kanguro.designsystem.ui.composables.homeDashboard.model.HomeDashboardEvent
import com.insurtech.kanguro.designsystem.ui.composables.homeDashboard.model.ItemReminderUiModel
import com.insurtech.kanguro.designsystem.ui.composables.homeDashboard.model.ReminderTypeUiModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.DwellingType
import com.insurtech.kanguro.ui.MainActivity
import com.insurtech.kanguro.ui.scenes.javier.ChatbotType
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class HomeDashboardFragment : FullscreenFragment<FragmentHomeBinding>() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.Home

    override val viewModel: HomeDashboardViewModel by viewModels()

    override var lightSystemBarTint: Boolean = true

    private val refreshPoliciesBroadcastReceiver = GenericBroadcastReceiver {
        viewModel.fetchInformation()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        statusBarColor = ContextCompat.getColor(requireContext(), R.color.neutral_background)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateBinding(inflater: LayoutInflater): FragmentHomeBinding =
        FragmentHomeBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        setupObservers()

        refreshPoliciesBroadcastReceiver.registerForAction(
            requireContext(),
            KanguroConstants.BROADCAST_ACTION_REFRESH_POLICIES
        )
    }

    private fun setupUi() {
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                HomeDashboardFragment(
                    viewModel = viewModel,
                    onEvent = ::onEvent
                )
            }
        }
    }

    private fun setupObservers() {
        viewModel.petsNeedAdditionalInfo.observe(viewLifecycleOwner) { pets ->
            findNavController().safeNavigate(
                HomeDashboardFragmentDirections.actionHomeDashboardFragmentToChatBotFragment(
                    pets.toTypedArray()
                )
            )
        }

        viewModel.userWantsFileClaim.observe(viewLifecycleOwner) { userWantsFileClaim ->
            if (userWantsFileClaim) {
                findNavController().safeNavigate(
                    HomeDashboardFragmentDirections.actionGlobalJavierChatbotFragment(
                        ChatbotType.NewClaim(
                            null
                        )
                    )
                )
            }
        }

        viewModel.rentersOnboardingNeeded.observe(viewLifecycleOwner) { rentersPolicyId ->
            findNavController().safeNavigate(
                HomeDashboardFragmentDirections.actionRentersChatbotFragment(
                    rentersPolicyId
                )
            )
        }

        viewModel.petNeedsMedicalHistory.observe(viewLifecycleOwner) { petId ->
            findNavController().safeNavigate(
                HomeDashboardFragmentDirections.actionGlobalMedicalHistoryAlertDialog(
                    petId
                )
            )
        }

        viewModel.initialLoad.observe(viewLifecycleOwner) { initialLoad ->
            (requireActivity() as? MainActivity)?.let { mainActivity ->
                mainActivity.binding.initialLoader.isVisible = initialLoad
            }
        }
    }

    private fun onEvent(event: HomeDashboardEvent) {
        when (event) {
            is HomeDashboardEvent.OnFileClaimPressed -> {
                val userHasBothProducts =
                    viewModel.userHasPets && viewModel.userHasRenters

                if (userHasBothProducts) {
                    viewModel.handleFileAClaimPressed(showModal = true)
                } else if (viewModel.userHasPets) {
                    navigateToPetFileAClaimChatBot()
                } else if (viewModel.userHasRenters) {
                    viewModel.handleRentersFileAClaimPressed(showDialog = true)
                }
            }

            is HomeDashboardEvent.OnBlogPressed -> {
                findNavController().safeNavigate(
                    HomeDashboardFragmentDirections.actionGlobalBlogFragment(),
                    bottomSheetLikeTransitions
                )
            }

            is HomeDashboardEvent.OnFaqPressed -> {
                findNavController().safeNavigate(
                    HomeDashboardFragmentDirections.actionHomeDashboardFaq()
                )
            }

            is HomeDashboardEvent.OnRentersCoveragePressed -> {
                findNavController().safeNavigate(
                    HomeDashboardFragmentDirections.actionHomeDashboardFragmentToRentersCoverageDetailsFragment(
                        event.policyId
                    )
                )
            }

            is HomeDashboardEvent.OnAddResidencePressed -> {
                findNavController().safeNavigate(
                    HomeDashboardFragmentDirections.actionGlobalGetQuoteRentersFragment(),
                    bottomSheetLikeTransitions
                )
            }

            is HomeDashboardEvent.OnPetsCoveragePressed -> {
                val selectedPetCoverage = viewModel.getSelectedPetCoverage(event.policyId)
                if (selectedPetCoverage != null) {
                    findNavController().safeNavigate(
                        HomeDashboardFragmentDirections.actionGlobalCoverageDetailFragment(
                            selectedPetCoverage
                        )
                    )
                }
            }

            is HomeDashboardEvent.OnAddPetsPressed -> {
                findNavController().safeNavigate(
                    HomeDashboardFragmentDirections.actionGlobalGetQuoteFragment(),
                    bottomSheetLikeTransitions
                )
            }

            is HomeDashboardEvent.OnReminderPressed -> {
                handleReminderPressed(event.model)
            }

            is HomeDashboardEvent.OnSeeAllRemindersPressed -> {
                findNavController().safeNavigate(
                    HomeDashboardFragmentDirections.actionGlobalRemindersFragment()
                )
            }

            is HomeDashboardEvent.OnPetUpsellingBannerPressed -> {
                findNavController().safeNavigate(
                    HomeDashboardFragmentDirections.actionGlobalGetQuoteFragment(),
                    bottomSheetLikeTransitions
                )
            }

            is HomeDashboardEvent.OnRentersUpsellingBannerPressed -> {
                findNavController().safeNavigate(
                    HomeDashboardFragmentDirections.actionGlobalGetQuoteRentersFragment(),
                    bottomSheetLikeTransitions
                )
            }

            is HomeDashboardEvent.OnDonationBannerPressed -> {
                if (viewModel.userAlreadyChoseCause) {
                    findNavController().safeNavigate(HomeDashboardFragmentDirections.actionGlobalChangeCause())
                } else {
                    findNavController().safeNavigate(HomeDashboardFragmentDirections.actionGlobalSupportCause())
                }
            }

            is HomeDashboardEvent.OnNotificationsPressed -> {
                findNavController().safeNavigate(
                    HomeDashboardFragmentDirections.actionHomeDashboardFragmentToNotificationsFragment()
                )
            }

            is HomeDashboardEvent.OnReferAFriendBannerPressed -> {
                findNavController().safeNavigate(
                    HomeDashboardFragmentDirections.actionGlobalReferFriendsDialog()
                )
            }

            is HomeDashboardEvent.OnTryAgainPressed -> viewModel.fetchInformation()

            is HomeDashboardEvent.OnPullToRefresh -> viewModel.fetchInformation()

            is HomeDashboardEvent.OnPetCoverageFilterPressed -> viewModel.handlePetCoverageFilterChanged(
                event.filter
            )

            is HomeDashboardEvent.OnRentersCoverageFilterPressed -> viewModel.handleRentersCoverageFilterChanged(
                event.filter
            )

            HomeDashboardEvent.OnPetFileClaimPressed -> {
                viewModel.handleFileAClaimPressed(showModal = false)
                navigateToPetFileAClaimChatBot()
            }

            HomeDashboardEvent.OnRentersFileClaimPressed -> {
                viewModel.handleFileAClaimPressed(showModal = false)
                viewModel.handleRentersFileAClaimPressed(showDialog = true)
            }

            HomeDashboardEvent.OnEmailPressed -> {
                viewModel.handleRentersFileAClaimPressed(showDialog = false)
                IntentUtils.openMailToKanguro(requireContext())
            }

            HomeDashboardEvent.OnDismissFileAClaimTypeModal -> {
                viewModel.handleFileAClaimPressed(showModal = false)
            }

            HomeDashboardEvent.OnDismissRentersFileAClaimDialog -> {
                viewModel.handleRentersFileAClaimPressed(showDialog = false)
            }

            HomeDashboardEvent.OnCloudPressed -> {
                findNavController().safeNavigate(
                    HomeDashboardFragmentDirections.actionGlobalKanguroCloudFragment()
                )
            }

            HomeDashboardEvent.OnLiveVetPressed -> {
                findNavController().safeNavigate(
                    HomeDashboardFragmentDirections.actionGlobalToLiveVetBottomsheet(),
                    bottomSheetLikeTransitions
                )
            }
        }
    }

    private fun navigateToPetFileAClaimChatBot() {
        findNavController().safeNavigate(
            HomeDashboardFragmentDirections.actionGlobalJavierChatbotFragment(
                ChatbotType.NewClaim(null)
            )
        )
    }

    private fun handleReminderPressed(reminder: ItemReminderUiModel) {
        when (reminder.type) {
            ReminderTypeUiModel.MedicalHistory -> {
                val petId = reminder.petId
                if (petId != null) {
                    findNavController().safeNavigate(
                        HomeDashboardFragmentDirections.actionGlobalMedicalHistoryChatbotFragment(
                            ChatbotType.CompleteMedicalHistory(petId)
                        )
                    )
                }
            }

            ReminderTypeUiModel.Claim -> {
                val claimId = reminder.claimId
                if (claimId != null) {
                    findNavController().safeNavigate(
                        HomeDashboardFragmentDirections.actionGlobalCommunicationChatbotFragment(
                            claimId
                        )
                    )
                }
            }

            ReminderTypeUiModel.AddPet -> {
                findNavController().safeNavigate(
                    HomeDashboardFragmentDirections.actionGlobalGetQuoteFragment(),
                    bottomSheetLikeTransitions
                )
            }

            else -> {
                // TODO Handle Other Kinds of Reminders
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeDashboardFragment(
    viewModel: HomeDashboardViewModel,
    onEvent: (HomeDashboardEvent) -> Unit
) {
    val homeDashboardUiState: HomeDashboardViewModel.HomeDashboardUiState by viewModel.uiState.collectAsState()

    val pullRefreshState = rememberPullRefreshState(
        refreshing = false,
        onRefresh = { onEvent(HomeDashboardEvent.OnPullToRefresh) }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        HomeDashboardScreenContent(
            userName = homeDashboardUiState.userName,
            petsNames = homeDashboardUiState.petsNames,
            rentersCoverages = homeDashboardUiState.rentersCoverages,
            petsCoverages = homeDashboardUiState.petsCoverages,
            reminders = homeDashboardUiState.reminders,
            showRentersUpsellingBanner = homeDashboardUiState.showRentersUpsellingBanner,
            showPetUpsellingBanner = homeDashboardUiState.showPetUpsellingBanner,
            showPetCoveragesFilter = homeDashboardUiState.showPetCoveragesFilter,
            showRentersCoveragesFilter = homeDashboardUiState.showRentersCoveragesFilter,
            selectedPetCoverageFilter = homeDashboardUiState.selectedPetCoverageFilter,
            selectedRentersCoverageFilter = homeDashboardUiState.selectedRentersCoverageFilter,
            showSelectFileAClaimTypeDialog = homeDashboardUiState.showSelectFileAClaimTypeDialog,
            showRentersFileAClaimDialog = homeDashboardUiState.showRentersFileAClaimDialog,
            showLiveVeterinary = homeDashboardUiState.showLiveVeterinary,
            isError = homeDashboardUiState.isError,
            isLoading = homeDashboardUiState.isLoading,
            onEvent = onEvent
        )

        PullRefreshIndicator(
            refreshing = false,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Preview
@Composable
fun HomeDashboardScreenContentPreview() {
    Surface {
        HomeDashboardScreenContent(
            onEvent = {},
            userName = "Laurem",
            petsNames = listOf("Oliver", "Luna"),
            rentersCoverages = listOf(
                RentersCoverageSummaryCardModel(
                    "",
                    "Tampa, FL",
                    DwellingType.SingleFamily,
                    CoverageStatusUi.Active
                )
            ),
            petsCoverages = listOf(
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
                ),
                PetsCoverageSummaryCardModel(
                    id = "1",
                    breed = "Poodle",
                    birthDate = Calendar.getInstance().apply {
                        set(Calendar.YEAR, 2020)
                        set(Calendar.MONTH, Calendar.FEBRUARY)
                        set(Calendar.DAY_OF_MONTH, 15)
                    }.time,
                    status = CoverageStatusUi.Pending,
                    pictureUrl = "",
                    name = "Luna",
                    petType = PetType.Dog
                )
            ),
            reminders = listOf(
                ItemReminderUiModel(
                    id = "",
                    type = ReminderTypeUiModel.AddPet,
                    petId = 0L,
                    petName = "Oliver",
                    petType = PetType.Dog,
                    petPictureUrl = "",
                    claimId = ""
                ),
                ItemReminderUiModel(
                    id = "",
                    type = ReminderTypeUiModel.FleaMedication,
                    petId = 0L,
                    petName = "Oliver",
                    petType = PetType.Dog,
                    petPictureUrl = "",
                    clinicName = "Pet Loves Clinic",
                    claimId = ""
                ),
                ItemReminderUiModel(
                    id = "",
                    type = ReminderTypeUiModel.DirectPay,
                    petId = 0L,
                    petName = "Oliver",
                    petType = PetType.Dog,
                    petPictureUrl = "",
                    claimId = ""
                ),
                ItemReminderUiModel(
                    id = "",
                    type = ReminderTypeUiModel.Claim,
                    petId = 0L,
                    petName = "Oliver",
                    petType = PetType.Dog,
                    petPictureUrl = "",
                    claimId = ""
                ),
                ItemReminderUiModel(
                    id = "",
                    type = ReminderTypeUiModel.MedicalHistory,
                    petId = 0L,
                    petName = "Oliver",
                    petType = PetType.Dog,
                    petPictureUrl = "",
                    claimId = ""
                )
            )
        )
    }
}
