package com.insurtech.kanguro.ui.scenes.coverages

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.content.ContextCompat
import androidx.core.graphics.Insets
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.insurtech.kanguro.R
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.base.FullscreenFragment
import com.insurtech.kanguro.core.session.ISessionManager
import com.insurtech.kanguro.core.utils.bottomSheetLikeTransitions
import com.insurtech.kanguro.core.utils.safeNavigate
import com.insurtech.kanguro.databinding.FragmentCoveragesBinding
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.ErrorComponent
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.ScreenLoader
import com.insurtech.kanguro.designsystem.ui.composables.upselling.PetUpsellingScreenContent
import com.insurtech.kanguro.designsystem.ui.theme.White
import com.insurtech.kanguro.domain.dashboard.Action
import com.insurtech.kanguro.ui.eventHandlers.AdvertisingHandler
import com.insurtech.kanguro.ui.eventHandlers.MoreActionsListItemHandler
import com.insurtech.kanguro.ui.scenes.dashboard.DashboardFragmentDirections
import com.insurtech.kanguro.ui.scenes.dashboard.DashboardViewModel
import com.insurtech.kanguro.ui.scenes.home.HomeDashboardFragmentDirections
import com.insurtech.kanguro.ui.scenes.javier.ChatbotType
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CoveragesFragment :
    FullscreenFragment<FragmentCoveragesBinding>(),
    AdvertisingHandler,
    MoreActionsListItemHandler {

    @Inject
    lateinit var sessionManager: ISessionManager

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.Coverages

    override var lightSystemBarTint: Boolean = true

    private val controller: CoveragesEpoxyController by lazy {
        CoveragesEpoxyController(requireContext(), this, this, ::onClickAddCoverageItem, ::onClickPetCard)
    }

    override val viewModel: DashboardViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        statusBarColor = ContextCompat.getColor(requireContext(), R.color.neutral_background)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateBinding(inflater: LayoutInflater): FragmentCoveragesBinding {
        /*
        We have to reset this animation, otherwise all other transitions wont animate
        correctly after popping back to this screen.
         */
        exitTransition = null
        postponeEnterTransition()
        return FragmentCoveragesBinding.inflate(inflater).apply {
            recyclerView.setControllerAndBuildModels(controller)
            recyclerView.post {
                startPostponedEnterTransition()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.currentScreen = screenName
        setupObservers()
        configureComposeView()
        composeLoading()
        composeErrorState()
    }

    override fun onDestroyView() {
        viewModel.currentScreen = null
        super.onDestroyView()
    }

    override fun processWindowInsets(insets: Insets) {
        binding.root.updatePadding(top = insets.top)
    }

    override fun onClickAdvertising(advertiserId: String) {
        val userId = sessionManager.sessionInfo?.id
        if (userId != null) {
            viewModel.onClickRoamAdvertising(advertiserId, userId)
        }
    }

    override fun onClickMoreActionsItem(item: Action) {
        when (item) {
            Action.TrackClaims -> openTrackClaims()

            Action.GetQuote -> openGetQuote()

            Action.DirectPayYourVet -> openDirectPayYourVeterinarian()

            Action.FrequentQuestions -> openFrequentQuestions()

            Action.PaymentSettings -> openPaymentSettings()

            Action.FileClaim -> openFileClaim()

            Action.Blog -> openBlog()

            Action.FindVet -> openFindVet()

            Action.LiveVet -> openLiveVet()

            else -> {
                Toast.makeText(
                    requireContext(),
                    "Click MoreActions for ${item.name}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setupObservers() {
        viewModel.coveragesState.observe(viewLifecycleOwner) { state ->
            binding.title.isVisible =
                state is DashboardViewModel.State.Loading || state is DashboardViewModel.State.Data || state is DashboardViewModel.State.Error
            binding.recyclerView.isVisible = state is DashboardViewModel.State.Data
            binding.composeView.isVisible = state is DashboardViewModel.State.Upselling
            binding.composeErrorState.isVisible = state is DashboardViewModel.State.Error
        }

        viewModel.getShouldShowRenters().observe(viewLifecycleOwner) { shouldShow ->
            if (shouldShow) {
                binding.title.text = getString(R.string.pet_upselling_pet_health_plan)
                viewModel.coveragesRefresh()
            }
            setupRefresh(shouldShow)
            setupGradiente(shouldShow)
        }

        viewModel.getShouldShowLiveVetLiveData().observe(viewLifecycleOwner) {
            controller.shouldShowLiveVet = it
        }

        viewModel.petsCardList.observe(viewLifecycleOwner) {
            controller.setCoveragesList(it)
        }
    }

    private fun setupGradiente(isRenters: Boolean) {
        binding.gradient.isVisible = isRenters
    }

    private fun setupRefresh(isRenters: Boolean) {
        binding.swipeRefresh.apply {
            isEnabled = isRenters
            setOnRefreshListener {
                viewModel.coveragesRefresh()
                isRefreshing = false
            }
        }
    }

    private fun composeLoading() {
        binding.composeLoader.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val state = viewModel.isCoveragesLoading.collectAsState()

                if (state.value) {
                    ScreenLoader(
                        color = White
                    )
                }
            }
        }

        viewModel.onOpenRoamWebsite.observe(viewLifecycleOwner) { url ->
            if (url != null) {
                val intent =
                    Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            } else {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle(requireContext().getString(R.string.error_dialog_title))
                    .setMessage(requireContext().getString(R.string.error_opening_website))
                    .setPositiveButton(requireContext().getString(R.string.back), null)
                    .show()
            }
        }
    }

    private fun configureComposeView() {
        binding.composeView.apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )

            setContent {
                PetUpsellingScreenContent(modifier = Modifier.fillMaxSize()) {
                    openGetQuote()
                }
            }
        }
    }

    private fun composeErrorState() {
        binding.composeErrorState.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

            setContent {
                ErrorComponent(
                    modifier = Modifier.fillMaxSize(),
                    onTryAgainPressed = {
                        viewModel.coveragesRefresh()
                    }
                )
            }
        }
    }

    private fun openTrackClaims() {
        findNavController().safeNavigate(CoveragesFragmentDirections.actionGlobalPetTrackYourClaimsFragment())
    }

    private fun openDirectPayYourVeterinarian() {
        findNavController().safeNavigate(CoveragesFragmentDirections.actionCoveragesFragmentToDirectPayToVetInitFlowFragment())
    }

    private fun openGetQuote() {
        findNavController().safeNavigate(
            CoveragesFragmentDirections.actionGlobalGetQuoteFragment(),
            bottomSheetLikeTransitions
        )
    }

    private fun openFrequentQuestions() {
        findNavController().safeNavigate(
            CoveragesFragmentDirections.actionCoveragesFragmentToPetFrequentlyAskedQuestionsFragment()
        )
    }

    private fun openPaymentSettings() {
        findNavController().safeNavigate(CoveragesFragmentDirections.actionGlobalPaymentSettingsFragment())
    }

    private fun openFileClaim() {
        findNavController().safeNavigate(
            CoveragesFragmentDirections.actionGlobalJavierChatbotFragment(
                ChatbotType.NewClaim(
                    null
                )
            )
        )
    }

    private fun openBlog() {
        findNavController().safeNavigate(
            CoveragesFragmentDirections.actionGlobalBlogFragment(),
            bottomSheetLikeTransitions
        )
    }

    private fun openFindVet() {
        findNavController().safeNavigate(
            DashboardFragmentDirections.actionGlobalChatBotMapFragment()
        )
    }

    private fun openLiveVet() {
        findNavController().safeNavigate(
            DashboardFragmentDirections.actionGlobalToLiveVetBottomsheet(),
            bottomSheetLikeTransitions
        )
    }

    private fun onClickPetCard(id: String) {
        val selectedPetCoverage = viewModel.getSelectedPetCoverage(id)
        if (selectedPetCoverage != null) {
            findNavController().safeNavigate(
                HomeDashboardFragmentDirections.actionGlobalCoverageDetailFragment(
                    selectedPetCoverage
                )
            )
        }
    }

    private fun onClickAddCoverageItem() {
        findNavController()
            .safeNavigate(
                CoveragesFragmentDirections.actionGlobalGetQuoteFragment(),
                bottomSheetLikeTransitions
            )
    }
}
