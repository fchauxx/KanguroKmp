package com.insurtech.kanguro.ui.scenes.rentersFaq

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.graphics.Insets
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.base.FullscreenFragment
import com.insurtech.kanguro.core.utils.UiState
import com.insurtech.kanguro.databinding.FragmentRentersFrequentlyAskedQuestionsBinding
import com.insurtech.kanguro.designsystem.ui.composables.rentersFrequentlyAskedQuestions.RentersFrequentlyAskedQuestionsScreenContent
import com.insurtech.kanguro.designsystem.ui.composables.rentersFrequentlyAskedQuestions.model.RentersFaqModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersFrequentlyAskedQuestions.model.RentersFrequentlyAskedQuestionsEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RentersFrequentlyAskedQuestionsFragment :
    FullscreenFragment<FragmentRentersFrequentlyAskedQuestionsBinding>() {

    override val screenName: AnalyticsEnums.Screen =
        AnalyticsEnums.Screen.RentersFrequentlyAskedQuestions

    override val viewModel: RentersFrequentlyAskedQuestionsViewModel by viewModels()

    override fun onCreateBinding(inflater: LayoutInflater): FragmentRentersFrequentlyAskedQuestionsBinding =
        FragmentRentersFrequentlyAskedQuestionsBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
    }

    override fun processWindowInsets(insets: Insets) {}

    private fun setupUi() {
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                RentersFrequentlyAskedQuestionsFragment(
                    viewModel = viewModel,
                    onEvent = ::onEvent
                )
            }
        }
    }

    private fun onEvent(event: RentersFrequentlyAskedQuestionsEvent) {
        when (event) {
            is RentersFrequentlyAskedQuestionsEvent.OnBackPressed -> findNavController().popBackStack()

            is RentersFrequentlyAskedQuestionsEvent.OnPullToRefresh -> viewModel.fetchFaq()

            is RentersFrequentlyAskedQuestionsEvent.OnTryAgainPressed -> viewModel.fetchFaq()
        }
    }
}

@Composable
private fun RentersFrequentlyAskedQuestionsFragment(
    viewModel: RentersFrequentlyAskedQuestionsViewModel,
    onEvent: (RentersFrequentlyAskedQuestionsEvent) -> Unit
) {
    val uiState: UiState<RentersFaqModel> by viewModel.uiState.collectAsState()

    RentersFrequentlyAskedQuestionsScreenContent(
        model = (uiState as? UiState.Success)?.data ?: RentersFaqModel(),
        isLoading = uiState is UiState.Loading,
        isError = uiState is UiState.Error,
        onEvent = onEvent
    )
}
