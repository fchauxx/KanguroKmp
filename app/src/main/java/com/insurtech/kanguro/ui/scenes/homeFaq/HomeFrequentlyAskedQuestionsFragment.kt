package com.insurtech.kanguro.ui.scenes.homeFaq

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
import com.insurtech.kanguro.databinding.FragmentHomeFrequentlyAskedQuestionsBinding
import com.insurtech.kanguro.designsystem.ui.composables.homeFrequentlyAskedQuestions.HomeFrequentlyAskedQuestionsScreenContent
import com.insurtech.kanguro.designsystem.ui.composables.homeFrequentlyAskedQuestions.model.HomeDashboardFaqModel
import com.insurtech.kanguro.designsystem.ui.composables.homeFrequentlyAskedQuestions.model.HomeFrequentlyAskedQuestionsEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFrequentlyAskedQuestionsFragment :
    FullscreenFragment<FragmentHomeFrequentlyAskedQuestionsBinding>() {

    override val screenName: AnalyticsEnums.Screen =
        AnalyticsEnums.Screen.FrequentlyAskedQuestions

    override val viewModel: HomeFrequentlyAskedQuestionsViewModel by viewModels()

    override fun onCreateBinding(inflater: LayoutInflater): FragmentHomeFrequentlyAskedQuestionsBinding =
        FragmentHomeFrequentlyAskedQuestionsBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
    }

    override fun processWindowInsets(insets: Insets) {}

    private fun setupUi() {
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                HomeFrequentlyAskedQuestionsFragment(
                    viewModel = viewModel,
                    onEvent = ::onEvent
                )
            }
        }
    }

    private fun onEvent(event: HomeFrequentlyAskedQuestionsEvent) {
        when (event) {
            is HomeFrequentlyAskedQuestionsEvent.OnBackPressed -> findNavController().popBackStack()

            is HomeFrequentlyAskedQuestionsEvent.OnPullToRefresh -> viewModel.fetchFaq()

            is HomeFrequentlyAskedQuestionsEvent.OnTryAgainPressed -> viewModel.fetchFaq()
        }
    }
}

@Composable
private fun HomeFrequentlyAskedQuestionsFragment(
    viewModel: HomeFrequentlyAskedQuestionsViewModel,
    onEvent: (HomeFrequentlyAskedQuestionsEvent) -> Unit
) {
    val uiState: UiState<HomeDashboardFaqModel> by viewModel.uiState.collectAsState()

    HomeFrequentlyAskedQuestionsScreenContent(
        model = (uiState as? UiState.Success)?.data ?: HomeDashboardFaqModel(),
        isLoading = uiState is UiState.Loading,
        isError = uiState is UiState.Error,
        onEvent = onEvent
    )
}
