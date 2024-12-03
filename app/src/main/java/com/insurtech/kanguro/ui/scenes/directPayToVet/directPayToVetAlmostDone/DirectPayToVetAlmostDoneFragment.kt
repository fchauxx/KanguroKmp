package com.insurtech.kanguro.ui.scenes.directPayToVet.directPayToVetAlmostDone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.databinding.FragmentDirectPayToVetAlmostDoneBinding
import com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.directPayToVetAlmostDone.DirectPayToVetAlmostDoneScreenContent
import com.insurtech.kanguro.ui.custom.KanguroBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DirectPayToVetAlmostDoneFragment :
    KanguroBottomSheetFragment<FragmentDirectPayToVetAlmostDoneBinding>() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.DirectPayVetAlmostDone

    override val viewModel: DirectPayToVetAlmostDoneViewModel by viewModels()

    override val isDraggable: Boolean = false

    override fun onCreateBinding(inflater: LayoutInflater): FragmentDirectPayToVetAlmostDoneBinding =
        FragmentDirectPayToVetAlmostDoneBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
    }

    private fun setupUi() {
        binding.composeView
            .setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

        binding.composeView.setContent {
            DirectPayToVetAlmostDoneFragment(
                viewModel = viewModel,
                onClosePressed = { onClosePressed() },
                onOkPressed = { onClosePressed() },
                onTapHerePressed = { navigateToShareWithYourVet() }
            )
        }
    }

    private fun onClosePressed() {
        dismiss()
    }

    private fun navigateToShareWithYourVet() {
        findNavController().navigate(
            DirectPayToVetAlmostDoneFragmentDirections
                .actionDirectPayToVetAlmostDoneToDirectPayToVetShareWithYourVetFragment(
                    viewModel.getClaimId()
                )
        )
    }
}

@Composable
private fun DirectPayToVetAlmostDoneFragment(
    modifier: Modifier = Modifier,
    viewModel: DirectPayToVetAlmostDoneViewModel = viewModel(),
    onClosePressed: () -> Unit,
    onOkPressed: () -> Unit,
    onTapHerePressed: () -> Unit
) {
    DirectPayToVetAlmostDoneScreenContent(
        modifier = modifier,
        onClosePressed = onClosePressed,
        onOkayPressed = onOkPressed,
        onTapHerePressed = onTapHerePressed
    )
}

@Composable
@Preview
private fun DirectPayToVetAlmostDoneFragmentPreview() {
    Surface {
        DirectPayToVetAlmostDoneScreenContent(
            onClosePressed = {},
            onOkayPressed = {},
            onTapHerePressed = {}
        )
    }
}
