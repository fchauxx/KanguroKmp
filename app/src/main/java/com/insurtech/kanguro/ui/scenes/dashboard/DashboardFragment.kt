package com.insurtech.kanguro.ui.scenes.dashboard

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.Insets
import androidx.core.view.updatePadding
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.insurtech.kanguro.NavDashboardDirections
import com.insurtech.kanguro.R
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.common.enums.PolicyStatus
import com.insurtech.kanguro.core.base.FullscreenFragment
import com.insurtech.kanguro.core.base.GenericBroadcastReceiver
import com.insurtech.kanguro.core.session.ISessionManager
import com.insurtech.kanguro.core.utils.*
import com.insurtech.kanguro.databinding.FragmentDashboardBinding
import com.insurtech.kanguro.databinding.LayoutCoveragesItemBinding
import com.insurtech.kanguro.domain.dashboard.*
import com.insurtech.kanguro.domain.model.PetPolicy
import com.insurtech.kanguro.ui.eventHandlers.DashboardListHandler
import com.insurtech.kanguro.ui.scenes.javier.ChatbotType
import com.insurtech.kanguro.ui.scenes.moreActions.MoreFragmentDirections
import com.insurtech.kanguro.ui.scenes.vetAdvice.InformationScreenType
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment : FullscreenFragment<FragmentDashboardBinding>(), DashboardListHandler {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.Dashboard

    @Inject
    lateinit var sessionManager: ISessionManager

    @Inject
    lateinit var preferences: SharedPreferences

    override var lightSystemBarTint: Boolean = true

    private val controller: DashboardEpoxyController by lazy {
        DashboardEpoxyController(requireContext(), this, sessionManager, lifecycleScope)
    }

    override val viewModel: DashboardViewModel by activityViewModels()

    private val refreshRemindersBroadcastReceiver = GenericBroadcastReceiver {
        viewModel.fetchUserReminders()
    }

    private val refreshPoliciesBroadcastReceiver = GenericBroadcastReceiver {
        viewModel.refreshPolicies()
    }

    private val profileClosedBroadcastReceiver = GenericBroadcastReceiver {
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
            enforcePhoneExists()
        }
    }

    override fun onCreateBinding(inflater: LayoutInflater): FragmentDashboardBinding {
        exitTransition = null
        postponeEnterTransition()
        return FragmentDashboardBinding.inflate(inflater).apply {
            recyclerView.setControllerAndBuildModels(controller)
            recyclerView.post { startPostponedEnterTransition() }
            setupObservers()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarLayout.toolbar.setOnMenuItemClickListener {
            onMenuItemPressed(it)
            true
        }

        enforcePhoneExists()

        setupBroadcastReceivers()

        setupSwipeRefresh()
    }

    private fun setupObservers() {
        viewModel.mainNavigationAction.observe(viewLifecycleOwner) {
            findNavController().safeNavigate(it)
        }

        viewModel.petsList.observe(viewLifecycleOwner, controller::setPetNames)

        viewModel.coveragesList.observe(viewLifecycleOwner) {
            controller.setCoveragesList(it, isVisible = isFilterVisible(it))
        }

        viewModel.filteredCoveragesList.observe(viewLifecycleOwner) {
            controller.setCoveragesList(it, isVisible = true)
        }

        viewModel.remindersList.observe(viewLifecycleOwner, controller::setRemindersList)

        viewModel.activityList.observe(viewLifecycleOwner, controller::setLastActivitiesList)

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

    private fun enforcePhoneExists() {
        if (sessionManager.sessionInfo?.phone.isNullOrEmpty()) {
            showMissingPhoneDialog()
        }
    }

    private fun showMissingPhoneDialog() {
        binding.root.post {
            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.update_cellphone_message))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.ok)) { _, _ ->
                    findNavController().safeNavigate(
                        DashboardFragmentDirections.actionGlobalProfileInfoFragment(true)
                    )
                }
                .show()
        }
    }

    private fun setupBroadcastReceivers() {
        refreshRemindersBroadcastReceiver.registerForAction(
            requireContext(),
            KanguroConstants.BROADCAST_ACTION_REFRESH_REMINDERS
        )
        refreshPoliciesBroadcastReceiver.registerForAction(
            requireContext(),
            KanguroConstants.BROADCAST_ACTION_REFRESH_POLICIES
        )
        profileClosedBroadcastReceiver.registerForAction(
            requireContext(),
            KanguroConstants.BROADCAST_ACTION_PROFILE_CLOSED
        )
    }

    private fun setupSwipeRefresh() {
        binding.dashboardSwipeRefresh.setOnRefreshListener {
            viewModel.fetchDashboard()
        }

        viewModel.isRefreshing.observe(viewLifecycleOwner) {
            binding.dashboardSwipeRefresh.isRefreshing = it
        }
    }

    override fun processWindowInsets(insets: Insets) {
        binding.root.updatePadding(top = insets.top)
    }

    override fun onClickPolicyItem(item: PetPolicy, view: LayoutCoveragesItemBinding) {
        findNavController().safeNavigate(
            DashboardFragmentDirections.actionGlobalCoverageDetailFragment(
                item
            )
        )
    }

    override fun onClickFilterCoverages(item: CoveragesFilter) {
        viewModel.filterCoverages(item)
    }

    override fun onClickAdvertising(advertiserId: String) {
        val userId = sessionManager.sessionInfo?.id
        if (userId != null) {
            viewModel.onClickRoamAdvertising(advertiserId, userId)
        }
    }

    override fun onDonationPressed() {
        if (sessionManager.sessionInfo?.donation != null) {
            findNavController().safeNavigate(DashboardFragmentDirections.actionGlobalChangeCause())
        } else {
            findNavController().safeNavigate(DashboardFragmentDirections.actionGlobalSupportCause())
        }
    }

    override fun onClickRemindersItem(item: Reminder) {
        when (item.type) {
            ReminderType.MedicalHistory -> {
                findNavController().safeNavigate(
                    DashboardFragmentDirections.actionGlobalMedicalHistoryChatbotFragment(
                        ChatbotType.CompleteMedicalHistory(item.pet.id ?: return)
                    )
                )
            }

            ReminderType.Claim -> {
                if (item.claimId != null) {
                    findNavController().safeNavigate(
                        DashboardFragmentDirections.actionGlobalCommunicationChatbotFragment(item.claimId)
                    )
                }
            }

            else -> {
                // Left empty on purpose
            }
        }
    }

    override fun onClickSeeAllReminders() {
        findNavController().safeNavigate(
            DashboardFragmentDirections.actionGlobalRemindersFragment()
        )
    }

    override fun onClickLastActivitiesItem(item: LastActivity) {
        Toast.makeText(
            requireContext(),
            "Click LastActivity ${item.activityName}",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onClickMoreActionsItem(item: Action) {
        when (item) {
            Action.NewPetParents -> newPetParents()

            Action.TrackClaims -> trackClaims()

            Action.DirectPayYourVet -> directPayYourVet()

            Action.FileClaim -> fileClaim()

            Action.GetQuote -> openGetAQuote()

            Action.VetAdvice -> vetAdvices()

            Action.FrequentQuestions -> frequentQuestions()

            Action.Blog -> openBlog()

            Action.FindVet -> openFindVet()

            else -> {
                Toast.makeText(
                    requireContext(),
                    "Click MoreActions for ${item.name}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onClickAddCoverageItem() {
        openGetAQuote()
    }

    override fun onClickReferFriends() {
        referFriends()
    }

    private fun referFriends() {
        findNavController().safeNavigate(
            DashboardFragmentDirections.actionGlobalReferFriendsDialog()
        )
    }

    private fun fileClaim() {
        findNavController().safeNavigate(
            DashboardFragmentDirections.actionGlobalJavierChatbotFragment(ChatbotType.NewClaim(null))
        )
    }

    private fun newPetParents() {
        findNavController().safeNavigate(
            DashboardFragmentDirections.actionGlobalInformationTopicsFragment(
                InformationScreenType.NewPetParent
            )
        )
    }

    private fun frequentQuestions() {
        /*
        * This method doesn't do anything. The one that is activating FAQ is located on DashboardFragment. Pretty pretty weird
        * TODO: Find a way to activate this method instead of DashboardFragment
        *
        *  */

        findNavController().safeNavigate(
            DashboardFragmentDirections.actionGlobalInformationTopicsFragment(
                InformationScreenType.FAQ
            )
        )
    }

    private fun vetAdvices() {
        findNavController().safeNavigate(
            NavDashboardDirections.actionGlobalVetAdviceFragment()
        )
    }

    private fun openGetAQuote() {
        findNavController().safeNavigate(
            DashboardFragmentDirections.actionGlobalGetQuoteFragment(),
            bottomSheetLikeTransitions
        )
    }

    private fun openBlog() {
        findNavController().safeNavigate(
            MoreFragmentDirections.actionGlobalBlogFragment(),
            bottomSheetLikeTransitions
        )
    }

    private fun trackClaims() {
        findNavController().safeNavigate(
            DashboardFragmentDirections.actionGlobalPetTrackYourClaimsFragment()
        )
    }

    private fun directPayYourVet() {
        findNavController().safeNavigate(DashboardFragmentDirections.actionDashboardFragmentToDirectPayToVetInitFlowFragment())
    }

    private fun openFindVet() {
        findNavController().safeNavigate(
            DashboardFragmentDirections.actionGlobalChatBotMapFragment()
        )
    }

    private fun onMenuItemPressed(menu: MenuItem) {
        when (menu.itemId) {
            R.id.menu_profile -> {
                findNavController().safeNavigate(
                    NavDashboardDirections.actionGlobalProfileInfoFragment(
                        false
                    )
                )
            }
        }
    }

    private fun isFilterVisible(policies: List<PetPolicy>): Boolean {
        var hasActivePolicy = false
        var hasInactivePolicy = false

        for (policy in policies) {
            if (policy.status == PolicyStatus.ACTIVE) {
                hasActivePolicy = true
            }
            if (policy.status == PolicyStatus.CANCELED || policy.status == PolicyStatus.TERMINATED) {
                hasInactivePolicy = true
            }
        }
        return hasActivePolicy && hasInactivePolicy
    }

    override fun onDestroy() {
        LocalBroadcastManager.getInstance(requireContext()).let {
            it.unregisterReceiver(refreshRemindersBroadcastReceiver)
            it.unregisterReceiver(refreshPoliciesBroadcastReceiver)
        }

        super.onDestroy()
    }

    companion object {
        const val IS_LOGGED = true
    }
}
