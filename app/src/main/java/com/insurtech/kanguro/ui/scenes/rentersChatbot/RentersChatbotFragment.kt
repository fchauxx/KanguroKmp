package com.insurtech.kanguro.ui.scenes.rentersChatbot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import com.insurtech.kanguro.R
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.base.FullscreenFragment
import com.insurtech.kanguro.core.utils.bottomSheetLikeTransitions
import com.insurtech.kanguro.core.utils.safeNavigate
import com.insurtech.kanguro.databinding.FragmentRentersChatbotBinding
import com.insurtech.kanguro.designsystem.ui.composables.chatbot.model.ActionUi
import com.insurtech.kanguro.designsystem.ui.composables.chatbot.model.ChatbotActionTypeUi
import com.insurtech.kanguro.designsystem.ui.composables.chatbot.model.getChatMessageModelListMock
import com.insurtech.kanguro.designsystem.ui.composables.chatbot.renters.RentersChatbotScreenContent
import com.insurtech.kanguro.designsystem.ui.composables.chatbot.renters.getValue
import com.insurtech.kanguro.designsystem.ui.composables.chatbot.renters.getValueUiFormatted
import com.insurtech.kanguro.ui.scenes.rentersOnboardingVideo.RentersOnboardingVideoFragment
import com.insurtech.kanguro.ui.scenes.rentersScheduledItems.ScheduledItemsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RentersChatbotFragment : FullscreenFragment<FragmentRentersChatbotBinding>() {

    override val viewModel: RentersChatbotViewModel by viewModels()

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.RentersChatbot

    override var lightSystemBarTint: Boolean = true

    override val showBottomNavigation: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListeners()
    }

    override fun onCreateBinding(inflater: LayoutInflater): FragmentRentersChatbotBinding =
        FragmentRentersChatbotBinding.inflate(inflater)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        statusBarColor = ContextCompat.getColor(requireContext(), R.color.secondary_lightest)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()
    }

    private fun setupUi() {
        binding.composeView
            .setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

        binding.composeView.setContent {
            RentersChatbotFragment(
                viewModel = viewModel,
                navigateToScheduledItems = { navigateToScheduledItems() },
                onOnboardingFinish = { onOnboardingFinish() },
                navigateToOnboardingVideo = { navigateToOnboardingVideo() }
            )
        }
    }

    private fun setFragmentResultListeners() {
        ScheduledItemsFragment.setFragmentResultListener(this) { isScheduledItemsClosed ->
            viewModel.onScheduledItemsClosed(isScheduledItemsClosed)
        }

        RentersOnboardingVideoFragment.setFragmentResultListener(this) { id ->
            viewModel.onVideoUploaded(id)
        }
    }

    private fun navigateToScheduledItems() {
        findNavController().safeNavigate(
            RentersChatbotFragmentDirections.actionRentersChatbotFragmentToScheduledItemsFragment(
                viewModel.getPolicyId(),
                false
            )
        )
    }

    private fun navigateToOnboardingVideo() {
        findNavController().safeNavigate(
            RentersChatbotFragmentDirections.actionRentersChatbotFragmentToRentersOnboardingVideoFragment(),
            bottomSheetLikeTransitions
        )
    }

    private fun onOnboardingFinish() {
        val userAlreadyChoseCause = this.viewModel.userAlreadyChoseCause
        findNavController().popBackStack(R.id.homeDashboardFragment, false)
        if (userAlreadyChoseCause) {
            findNavController().safeNavigate(RentersChatbotFragmentDirections.actionGlobalChangeCause())
        } else {
            findNavController().safeNavigate(RentersChatbotFragmentDirections.actionGlobalSupportCause())
        }
    }
}

@Composable
private fun RentersChatbotFragment(
    viewModel: RentersChatbotViewModel = viewModel(),
    navigateToScheduledItems: () -> Unit,
    navigateToOnboardingVideo: () -> Unit,
    onOnboardingFinish: () -> Unit
) {
    val uiState: RentersChatbotViewModel.ChatbotState by viewModel.uiState.collectAsState()

    RentersChatbotScreenContent(
        messages = uiState.messages.map { it.toUi() },
        navigateToScheduledItems = { navigateToScheduledItems() },
        navigateToOnboardingVideo = { navigateToOnboardingVideo() },
        action = uiState.action.toUi(),
        isLoading = uiState.isLoading,
        onKeyboardStateChange = { viewModel.setScrollState(it) },
        shouldScrollToEnd = uiState.shouldScrollToEnd,
        onOnboardingFinish = { onOnboardingFinish() }
    ) {
        viewModel.addUserMessage(it.getValueUiFormatted())
        viewModel.postChatbotSession(it.getValue())
    }
}

@Composable
@Preview
private fun RentersChatbotFragmentPreview() {
    Surface {
        RentersChatbotScreenContent(
            messages = getChatMessageModelListMock(),
            action = ActionUi.Text(ChatbotActionTypeUi.Text),
            navigateToScheduledItems = {},
            onOnboardingFinish = {},
            navigateToOnboardingVideo = {}
        )
    }
}
