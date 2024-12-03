package com.insurtech.kanguro.ui.scenes.rentersWhatIsCovered

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.viewModels
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.databinding.FragmentRentersWhatIsCoveredBinding
import com.insurtech.kanguro.designsystem.ui.composables.rentersWhatIsCovered.RentersWhatIsCoveredScreenContent
import com.insurtech.kanguro.ui.custom.KanguroBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RentersWhatIsCoveredBottomSheet :
    KanguroBottomSheetFragment<FragmentRentersWhatIsCoveredBinding>() {

    override val screenName = AnalyticsEnums.Screen.RentersWhatIsCovered

    override val viewModel: RentersWhatIsCoveredViewModel by viewModels()

    override val isDraggable: Boolean = false

    override fun onCreateBinding(inflater: LayoutInflater): FragmentRentersWhatIsCoveredBinding =
        FragmentRentersWhatIsCoveredBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
    }

    private fun setupUi() {
        binding.composeView
            .setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        binding.composeView.setContent {
            RentersWhatIsCoveredBottomSheet(
                onClosePressed = { dismiss() }
            )
        }
    }
}

@Composable
private fun RentersWhatIsCoveredBottomSheet(
    onClosePressed: () -> Unit
) {
    RentersWhatIsCoveredScreenContent {
        onClosePressed()
    }
}

@Composable
@Preview(showBackground = true, device = Devices.PIXEL_5)
private fun RentersWhatIsCoveredBottomSheetPreview() {
    RentersWhatIsCoveredBottomSheet {}
}
