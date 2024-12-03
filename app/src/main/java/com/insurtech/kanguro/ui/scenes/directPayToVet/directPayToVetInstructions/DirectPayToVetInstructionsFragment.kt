package com.insurtech.kanguro.ui.scenes.directPayToVet.directPayToVetInstructions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.insurtech.kanguro.R
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.utils.safeNavigate
import com.insurtech.kanguro.databinding.FragmentDirectPayToVetInstructionsBinding
import com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.directPayToVetInstructions.DirectPayToVetInstructionsScreenContent
import com.insurtech.kanguro.ui.custom.KanguroBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DirectPayToVetInstructionsFragment :
    KanguroBottomSheetFragment<FragmentDirectPayToVetInstructionsBinding>() {

    override val screenName: AnalyticsEnums.Screen =
        AnalyticsEnums.Screen.DirectPayToVetInstructions

    override val viewModel: DirectPayToVetInstructionsViewModel by viewModels()

    override val isDraggable: Boolean = false
    override fun onCreateBinding(inflater: LayoutInflater): FragmentDirectPayToVetInstructionsBinding =
        FragmentDirectPayToVetInstructionsBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()
    }

    private fun setupUi() {
        binding.composeView
            .setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

        binding.composeView.setContent {
            DirectPayVetInstructionsFragment(
                onBackPressed = { dismiss() },
                onClosePressed = { onCloseButtonPressed() },
                onNextPressed = { onNextPressed() }
            )
        }
    }

    private fun onNextPressed() {
        findNavController()
            .safeNavigate(
                DirectPayToVetInstructionsFragmentDirections
                    .actionDirectPayToVetInstructionsFragmentToDirectPayToVetFormFragment(
                        viewModel.getSharedFlow()
                    )
            )
    }

    private fun onCloseButtonPressed() {
        findNavController().popBackStack(R.id.directPayToVetInitFlowFragment, true)
    }
}

@Composable
private fun DirectPayVetInstructionsFragment(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    onClosePressed: () -> Unit,
    onNextPressed: () -> Unit
) {
    DirectPayToVetInstructionsScreenContent(
        modifier = modifier,
        onBackPressed = onBackPressed,
        onClosePressed = onClosePressed,
        onNextPressed = onNextPressed
    )
}

@Composable
@Preview
private fun DirectPayVetInstructionsFragmentPreview() {
    Surface {
        DirectPayToVetInstructionsScreenContent(
            onBackPressed = { },
            onClosePressed = { },
            onNextPressed = { }
        )
    }
}
