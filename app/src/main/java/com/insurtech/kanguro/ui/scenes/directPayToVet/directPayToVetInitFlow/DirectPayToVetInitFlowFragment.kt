package com.insurtech.kanguro.ui.scenes.directPayToVet.directPayToVetInitFlow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.utils.safeNavigate
import com.insurtech.kanguro.databinding.FragmentDirectPayToVetInitFlowBinding
import com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.directPayToVetInitFlow.DirectPayToVetInitFlowScreenContent
import com.insurtech.kanguro.ui.custom.KanguroBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DirectPayToVetInitFlowFragment :
    KanguroBottomSheetFragment<FragmentDirectPayToVetInitFlowBinding>() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.DirectPayToVetInitFlow

    override val viewModel: DirectPayToVetInitFlowViewModel by viewModels()

    override val isDraggable: Boolean = false
    override fun onCreateBinding(inflater: LayoutInflater): FragmentDirectPayToVetInitFlowBinding =
        FragmentDirectPayToVetInitFlowBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
    }

    private fun setupUi() {
        binding.composeView
            .setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        binding.composeView.setContent {
            DirectPayToVetInitFlowFragment(
                viewModel = viewModel,
                onClosePressed = { dismiss() },
                onNextButtonPressed = { onNextButtonPressed() }
            )
        }
    }

    private fun onNextButtonPressed() {
        findNavController()
            .safeNavigate(
                DirectPayToVetInitFlowFragmentDirections
                    .actionDirectPayToVetInitFlowFragmentToDirectPayToVetInstructionsFragment(
                        viewModel.getSharedFlow()
                    )
            )
    }
}

@Composable
private fun DirectPayToVetInitFlowFragment(
    viewModel: DirectPayToVetInitFlowViewModel = viewModel(),
    onClosePressed: () -> Unit,
    onNextButtonPressed: () -> Unit
) {
    val uiState: DirectPayToVetInitFlowViewModel.UiState by viewModel.uiState.collectAsState()

    DirectPayToVetInitFlowScreenContent(
        onClosePressed = onClosePressed,
        price = uiState.claimValue,
        onPriceChange = { viewModel.onClaimValueChange(it) },
        onNextPressed = onNextButtonPressed,
        isNextEnable = uiState.isNextEnable
    )
}

@Preview
@Composable
private fun DirectPayToVetInitFlowFragmentPreview() {
    Surface {
        DirectPayToVetInitFlowScreenContent(
            onClosePressed = {},
            price = "",
            onPriceChange = {},
            onNextPressed = {},
            isNextEnable = false
        )
    }
}
