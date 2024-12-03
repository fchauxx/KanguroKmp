package com.insurtech.kanguro.ui.scenes.rentersChatbot.scheduledItems

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.utils.UiState
import com.insurtech.kanguro.databinding.FragmentRentersChatbotScheduledItemsBinding
import com.insurtech.kanguro.designsystem.ui.composables.chatbot.scheduledItems.YourScheduledItemsScreenContent
import com.insurtech.kanguro.designsystem.ui.composables.chatbot.scheduledItems.YourScheduledItemsScreenEvent
import com.insurtech.kanguro.designsystem.ui.composables.chatbot.scheduledItems.model.ChatbotScheduledItem
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.ErrorComponent
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.HeaderBackAndClose
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.ScreenLoader
import com.insurtech.kanguro.ui.custom.KanguroBottomSheetFragment

class YourScheduledItemsFragment :
    KanguroBottomSheetFragment<FragmentRentersChatbotScheduledItemsBinding>() {

    override val screenName: AnalyticsEnums.Screen =
        AnalyticsEnums.Screen.RentersChatbotScheduledItems

    override val viewModel: YourScheduledItemsViewModel by viewModels()

    override fun onCreateBinding(inflater: LayoutInflater): FragmentRentersChatbotScheduledItemsBinding =
        FragmentRentersChatbotScheduledItemsBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
    }

    private fun setupUi() {
        binding.composeView.apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )

            setContent {
                YourScheduledItemsFragment(
                    viewModel = viewModel,
                    onEvent = { event ->
                        when (event) {
                            is YourScheduledItemsScreenEvent.OnClosePressed -> {
                                dismiss()
                            }

                            is YourScheduledItemsScreenEvent.OnTryAgainPressed -> TODO()

                            is YourScheduledItemsScreenEvent.OnDonePressed -> TODO()

                            is YourScheduledItemsScreenEvent.OnItemPressed -> TODO()
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun YourScheduledItemsFragment(
    viewModel: YourScheduledItemsViewModel,
    onEvent: (YourScheduledItemsScreenEvent) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is UiState.Loading -> YourScheduledItemsLoadingScreen {
            onEvent(YourScheduledItemsScreenEvent.OnClosePressed)
        }

        is UiState.Error -> YourScheduledItemsErrorScreen(
            onClosePressed = { onEvent(YourScheduledItemsScreenEvent.OnClosePressed) },
            onTryAgainPressed = { onEvent(YourScheduledItemsScreenEvent.OnTryAgainPressed) }
        )

        is UiState.Success -> YourScheduledItemsScreenContent(
            scheduledItems = (uiState as UiState.Success<List<ChatbotScheduledItem>>).data,
            onEvent = onEvent
        )
    }
}

@Composable
private fun YourScheduledItemsLoadingScreen(
    onClosePressed: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HeaderBackAndClose(
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp),
            onClosePressed = onClosePressed
        )

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ScreenLoader()
        }
    }
}

@Composable
private fun YourScheduledItemsErrorScreen(
    onClosePressed: () -> Unit,
    onTryAgainPressed: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HeaderBackAndClose(
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp),
            onClosePressed = onClosePressed
        )

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ErrorComponent(onTryAgainPressed = onTryAgainPressed)
        }
    }
}

@Composable
@Preview(device = Devices.NEXUS_5)
private fun YourScheduledItemsFragmentPreview() {
    Surface {
        YourScheduledItemsScreenContent(
            modifier = Modifier.fillMaxSize(),
            scheduledItems = listOf(
                ChatbotScheduledItem(id = "1", name = "Item 1", value = 100.toBigDecimal()),
                ChatbotScheduledItem(id = "2", name = "Item 2", value = 200.toBigDecimal()),
                ChatbotScheduledItem(id = "3", name = "Item 3", value = 300.toBigDecimal())
            ),
            onEvent = {}
        )
    }
}

@Composable
@Preview(device = Devices.NEXUS_5)
private fun YourScheduledItemsLoadingScreenPreview() {
    Surface {
        YourScheduledItemsLoadingScreen {}
    }
}

@Composable
@Preview(device = Devices.NEXUS_5)
private fun YourScheduledItemsErrorScreenPreview() {
    Surface {
        YourScheduledItemsErrorScreen(
            onClosePressed = {},
            onTryAgainPressed = {}
        )
    }
}
