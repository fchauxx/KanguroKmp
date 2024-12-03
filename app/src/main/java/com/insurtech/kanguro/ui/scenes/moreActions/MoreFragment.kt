package com.insurtech.kanguro.ui.scenes.moreActions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.snapshots.Snapshot.Companion.observe
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.firebase.messaging.FirebaseMessaging
import com.insurtech.kanguro.NavDashboardDirections
import com.insurtech.kanguro.R
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.base.FullscreenFragment
import com.insurtech.kanguro.core.session.ISessionManager
import com.insurtech.kanguro.core.utils.IntentUtils
import com.insurtech.kanguro.core.utils.bottomSheetLikeTransitions
import com.insurtech.kanguro.core.utils.openPdf
import com.insurtech.kanguro.core.utils.safeNavigate
import com.insurtech.kanguro.databinding.FragmentMoreBinding
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.ScreenLoader
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground
import com.insurtech.kanguro.designsystem.ui.theme.White
import com.insurtech.kanguro.domain.dashboard.Action
import com.insurtech.kanguro.ui.MainActivity
import com.insurtech.kanguro.ui.eventHandlers.MoreActionsListItemHandler
import com.insurtech.kanguro.ui.eventHandlers.ReferFriendsHandler
import com.insurtech.kanguro.ui.scenes.vetAdvice.InformationScreenType
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MoreFragment :
    FullscreenFragment<FragmentMoreBinding>(),
    MoreActionsListItemHandler,
    ReferFriendsHandler {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.More

    override val viewModel: MoreViewModel by viewModels()

    override var lightSystemBarTint: Boolean = true

    private val controller: MoreActionsEpoxyController by lazy {
        MoreActionsEpoxyController(requireContext(), this, this)
    }

    @Inject
    lateinit var sessionManager: ISessionManager

    override fun onCreateBinding(inflater: LayoutInflater) = FragmentMoreBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        composeLoading()
    }

    override fun onClickMoreActionsItem(item: Action) {
        when (item) {
            Action.Phone -> {
                val phoneNumber = getString(R.string.phone_number_support)
                IntentUtils.openDialIntent(requireContext(), phoneNumber)
            }

            Action.NewPetParents -> {
                newPetParents()
            }

            Action.FrequentQuestions -> {
                frequentQuestions()
            }

            Action.PaymentSettings -> {
                paymentSettingsPressed()
            }

            Action.VetAdvice -> {
                vetAdvices()
            }

            Action.Profile -> {
                findNavController().safeNavigate(
                    NavDashboardDirections.actionGlobalProfileInfoFragment(
                        false
                    )
                )
            }

            Action.ReferAFriend -> {
                referFriends()
            }

            Action.Reminders -> {
                findNavController().safeNavigate(
                    MoreFragmentDirections.actionGlobalRemindersFragment()
                )
            }

            Action.PrivacyPolicy -> {
                findNavController().safeNavigate(
                    MoreFragmentDirections.actionGlobalPrivacyPolicyFragment(),
                    bottomSheetLikeTransitions
                )
            }

            Action.Settings -> {
                findNavController().safeNavigate(MoreFragmentDirections.actionGlobalSettingsFragment())
            }

            Action.TermsOfUse -> {
                viewModel.openUseTermsPressed()
            }

            Action.Logout -> {
                onLogoffPressed()
            }

            Action.SupportCause -> {
                supportCause()
            }

            Action.Communication -> {
                // TODO Navigate to Communications screen to be implemented when the button is shown
            }

            Action.ContactUs -> {
                val navOptions = navOptions {
                    popUpTo(R.id.moreFragment) {
                        inclusive = true
                    }
                }
                findNavController().safeNavigate(
                    MoreFragmentDirections.actionGlobalChatFragment(),
                    navOptions
                )
            }

            Action.LiveVet -> {
                findNavController().safeNavigate(MoreFragmentDirections.actionGlobalToLiveVetBottomsheet())
            }

            else -> {
                // left empty on purpose
            }
        }
    }

    private fun newPetParents() {
        findNavController().safeNavigate(
            MoreFragmentDirections.actionGlobalInformationTopicsFragment(
                InformationScreenType.NewPetParent
            )
        )
    }

    private fun frequentQuestions() {
        findNavController().safeNavigate(
            MoreFragmentDirections.actionGlobalInformationTopicsFragment(
                InformationScreenType.FAQ
            )
        )
    }

    private fun paymentSettingsPressed() {
        findNavController().safeNavigate(
            MoreFragmentDirections.actionGlobalPaymentSettingsFragment()
        )
    }

    private fun vetAdvices() {
        findNavController().safeNavigate(
            MoreFragmentDirections.actionGlobalVetAdviceFragment()
        )
    }

    override fun onClickReferFriends() {
        referFriends()
    }

    private fun setupObservers() {
        viewModel.openUseTermsEvent.observe(viewLifecycleOwner) {
            it?.let { requireContext().openPdf(it) }
        }

        viewModel.fetchingUseTerms.observe(viewLifecycleOwner) {
            controller.termsOfUseLoading = it
        }

        viewModel.shouldShowRenters.observe(viewLifecycleOwner) {
            controller.shouldShowRenters = it
            binding.recyclerView.setControllerAndBuildModels(controller)
        }

        viewModel.userHasPets.observe(viewLifecycleOwner) {
            controller.userHasPets = it
        }

        viewModel.shouldShowLiveVet.observe(viewLifecycleOwner) {
            controller.shouldShowLiveVet = it
        }
    }

    private fun composeLoading() {
        binding.composeViewLoading
            .setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        binding.composeViewLoading.setContent {
            val isLoadingState = viewModel.isLoading.collectAsState()

            if (isLoadingState.value) {
                ScreenLoader(
                    color = White,
                    modifier = Modifier
                        .background(color = NeutralBackground)
                        .fillMaxSize()
                )
            }
        }
    }

    private fun onLogoffPressed() {
        FirebaseMessaging.getInstance().deleteToken()
        sessionManager.sessionInfo = null
        requireActivity().finish()
        startActivity(MainActivity.newInstance(requireContext()))
    }

    private fun referFriends() {
        findNavController().safeNavigate(
            MoreFragmentDirections.actionGlobalReferFriendsDialog()
        )
    }

    private fun supportCause() {
        if (sessionManager.sessionInfo?.donation != null) {
            findNavController().safeNavigate(MoreFragmentDirections.actionGlobalChangeCause())
        } else {
            findNavController().safeNavigate(MoreFragmentDirections.actionGlobalSupportCause())
        }
    }
}
