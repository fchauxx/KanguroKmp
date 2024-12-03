package com.insurtech.kanguro.ui.scenes.petFaq

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
import com.insurtech.kanguro.databinding.FragmentPetFrequentlyAskedQuestionsBinding
import com.insurtech.kanguro.designsystem.ui.composables.petFrequentlyAskedQuestions.PetFrequentlyAskedQuestionsScreenContent
import com.insurtech.kanguro.designsystem.ui.composables.petFrequentlyAskedQuestions.model.PetFaqModel
import com.insurtech.kanguro.designsystem.ui.composables.petFrequentlyAskedQuestions.model.PetFrequentlyAskedQuestionsEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PetFrequentlyAskedQuestionsFragment :
    FullscreenFragment<FragmentPetFrequentlyAskedQuestionsBinding>() {

    override val screenName: AnalyticsEnums.Screen =
        AnalyticsEnums.Screen.PetFrequentlyAskedQuestions

    override val viewModel: PetFrequentlyAskedQuestionsViewModel by viewModels()

    override fun onCreateBinding(inflater: LayoutInflater): FragmentPetFrequentlyAskedQuestionsBinding =
        FragmentPetFrequentlyAskedQuestionsBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
    }

    override fun processWindowInsets(insets: Insets) {}

    private fun setupUi() {
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                PetFrequentlyAskedQuestionsFragment(
                    viewModel = viewModel,
                    onEvent = ::onEvent
                )
            }
        }
    }

    private fun onEvent(event: PetFrequentlyAskedQuestionsEvent) {
        when (event) {
            is PetFrequentlyAskedQuestionsEvent.OnBackPressed -> findNavController().popBackStack()

            is PetFrequentlyAskedQuestionsEvent.OnPullToRefresh -> viewModel.fetchFaq()

            is PetFrequentlyAskedQuestionsEvent.OnTryAgainPressed -> viewModel.fetchFaq()
        }
    }
}

@Composable
private fun PetFrequentlyAskedQuestionsFragment(
    viewModel: PetFrequentlyAskedQuestionsViewModel,
    onEvent: (PetFrequentlyAskedQuestionsEvent) -> Unit
) {
    val uiState: UiState<PetFaqModel> by viewModel.uiState.collectAsState()

    PetFrequentlyAskedQuestionsScreenContent(
        model = (uiState as? UiState.Success)?.data ?: PetFaqModel(listOf()),
        isLoading = uiState is UiState.Loading,
        isError = uiState is UiState.Error,
        onEvent = onEvent
    )
}
