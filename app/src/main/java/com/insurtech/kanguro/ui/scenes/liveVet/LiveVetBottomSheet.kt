package com.insurtech.kanguro.ui.scenes.liveVet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.utils.IntentUtils
import com.insurtech.kanguro.databinding.BottomsheetLiveVetBinding
import com.insurtech.kanguro.designsystem.ui.composables.liveVet.LiveVetBottomSheetScreenContent
import com.insurtech.kanguro.designsystem.ui.composables.liveVet.model.LiveVetEvent
import com.insurtech.kanguro.domain.model.AirvetUserDetails
import com.insurtech.kanguro.ui.custom.KanguroBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LiveVetBottomSheet : KanguroBottomSheetFragment<BottomsheetLiveVetBinding>() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.LiveVet

    override val viewModel: LiveVetViewModel by viewModels()

    override fun onCreateBinding(inflater: LayoutInflater): BottomsheetLiveVetBinding {
        return BottomsheetLiveVetBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
    }

    private fun setupUi() {
        binding.composeView.setContent {
            LiveVetBottomSheet(
                modifier = Modifier.fillMaxSize(),
                viewModel = viewModel,
                onEvent = ::onEvent
            )
        }
    }

    private fun onEvent(liveVetEvent: LiveVetEvent) {
        when (liveVetEvent) {
            LiveVetEvent.OnClosePressed -> onClosePressed()
            is LiveVetEvent.OnDownloadPressed -> onDownloadPressed(
                liveVetEvent.airvetUserDetails
            )

            LiveVetEvent.OnTryAgainPressed -> viewModel.loadData()
        }
    }

    private fun onClosePressed() {
        findNavController().navigateUp()
    }

    private fun onDownloadPressed(
        airvetUserDetails: AirvetUserDetails
    ) {
        IntentUtils.openAirvetIntent(requireContext(), airvetUserDetails)
    }
}

@Composable
private fun LiveVetBottomSheet(
    modifier: Modifier = Modifier,
    viewModel: LiveVetViewModel,
    onEvent: (LiveVetEvent) -> Unit
) {
    val liveVetUiState by viewModel.state.collectAsState()

    LiveVetBottomSheetScreenContent(
        modifier = modifier,
        isLoading = liveVetUiState.isLoading,
        isError = liveVetUiState.isError,
        airvetUserDetails = liveVetUiState.airvetUserDetails,
        onEvent = onEvent
    )
}
