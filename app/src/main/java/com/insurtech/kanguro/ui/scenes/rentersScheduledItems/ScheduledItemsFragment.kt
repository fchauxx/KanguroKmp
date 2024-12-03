package com.insurtech.kanguro.ui.scenes.rentersScheduledItems

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.utils.UiState
import com.insurtech.kanguro.core.utils.safeNavigate
import com.insurtech.kanguro.databinding.FragmentScheduledItemsBinding
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.ErrorScreenContent
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.ScreenLoader
import com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItems.DeleteItemAlertDialog
import com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItems.ScheduledItemsEmptyStateScreenContent
import com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItems.ScheduledItemsScreenContent
import com.insurtech.kanguro.domain.model.ScheduledItemModel
import com.insurtech.kanguro.domain.model.getScheduledItemModelMock
import com.insurtech.kanguro.ui.custom.KanguroBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScheduledItemsFragment : KanguroBottomSheetFragment<FragmentScheduledItemsBinding>() {

    override val viewModel: ScheduledItemsViewModel by viewModels()

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.ScheduledItems

    override val isDraggable: Boolean = false
    override fun onCreateBinding(inflater: LayoutInflater): FragmentScheduledItemsBinding =
        FragmentScheduledItemsBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.composeView
            .setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        binding.composeView.setContent {
            ScheduledItemsFragment(
                viewModel = viewModel,
                onClosePressed = { onClosePressed() },
                onDocumentPressed = { scheduledItemId ->
                    navigateToScheduledItemsSelectedPictures(scheduledItemId)
                },
                onAddMoreItemsPressed = { onAddMoreItemsPressed() },
                onDoneButtonPressed = { onClosePressed() }
            )
        }
    }

    private fun onAddMoreItemsPressed() {
        findNavController().safeNavigate(
            ScheduledItemsFragmentDirections
                .actionScheduledItemsFragmentToScheduledItemsCategoryFragment(
                    viewModel.getPolicyId()
                )
        )
    }

    private fun navigateToScheduledItemsSelectedPictures(scheduleItemId: String) {
        findNavController().safeNavigate(
            ScheduledItemsFragmentDirections.actionScheduledItemsFragmentToScheduledItemsSelectedPicturesFragment(
                scheduleItemId
            )
        )
    }

    private fun onClosePressed() {
        val isClosePressed = true
        setFragmentResult(
            REQUEST_KEY,
            bundleOf(
                REQUEST_KEY to isClosePressed
            )
        )
        dismiss()
    }

    companion object {
        const val REQUEST_KEY = "KEY_SCHEDULED_ITEMS"
        fun setFragmentResultListener(
            fragment: Fragment,
            listener: (Boolean) -> Unit
        ) {
            fragment.setFragmentResultListener(REQUEST_KEY) { _, bundle ->
                val result = bundle.getBoolean(REQUEST_KEY, false)
                listener(result)
            }
        }
    }
}

@Composable
private fun ScheduledItemsFragment(
    modifier: Modifier = Modifier,
    viewModel: ScheduledItemsViewModel,
    onClosePressed: () -> Unit,
    onDocumentPressed: (String) -> Unit,
    onAddMoreItemsPressed: () -> Unit,
    onDoneButtonPressed: () -> Unit
) {
    var openAlertDialog by remember { mutableStateOf(false) }

    val uiState: UiState<List<ScheduledItemModel>> by viewModel.uiState.collectAsState()
    when (uiState) {
        is UiState.Loading -> {
            ScreenLoader(modifier = modifier.fillMaxSize())
        }

        is UiState.Success -> {
            ScheduledItemsSuccessScreenContent(
                modifier = modifier,
                uiState = uiState,
                onClosePressed = onClosePressed,
                onAddMoreItemsPressed = onAddMoreItemsPressed,
                onDoneButtonPressed = onDoneButtonPressed,
                onDocumentPressed = onDocumentPressed,
                onTrashPressed = { scheduledItemId ->
                    openAlertDialog = true
                    viewModel.handleOnDeleteScheduledItem(scheduledItemId)
                },
                isReadyOnly = viewModel.isReadyOnly()
            )
        }

        is UiState.Error -> {
            ErrorScreenContent(
                modifier = modifier,
                onClosePressed = onClosePressed,
                onTryAgainPressed = (uiState as UiState.Error).onRetry
            )
        }
    }

    if (openAlertDialog) {
        DeleteItemAlertDialog(
            onClosePressed = { openAlertDialog = false },
            onCancelPressed = { openAlertDialog = false }
        ) {
            openAlertDialog = false
            viewModel.handleOnDeleteScheduledItemConfirmed()
        }
    }
}

@Composable
private fun ScheduledItemsSuccessScreenContent(
    modifier: Modifier,
    uiState: UiState<List<ScheduledItemModel>>,
    onClosePressed: () -> Unit,
    onAddMoreItemsPressed: () -> Unit,
    onDoneButtonPressed: () -> Unit,
    onDocumentPressed: (String) -> Unit,
    onTrashPressed: (String) -> Unit,
    isReadyOnly: Boolean
) {
    val items = (uiState as UiState.Success<List<ScheduledItemModel>>).data

    if (items.isEmpty()) {
        ScheduledItemsEmptyStateScreenContent(
            modifier = modifier,
            onClosePressed = onClosePressed,
            isReadyOnly = isReadyOnly,
            onAddMoreItemsPressed = onAddMoreItemsPressed,
            onDoneButtonPressed = onDoneButtonPressed
        )
    } else {
        ScheduledItemsScreenContent(
            modifier = modifier,
            itemsList = items,
            isReadyOnly = isReadyOnly,
            onClosePressed = onClosePressed,
            onDocumentPressed = onDocumentPressed,
            onTrashPressed = onTrashPressed,
            onAddMoreItemsPressed = onAddMoreItemsPressed,
            onDoneButtonPressed = onDoneButtonPressed
        )
    }
}

@Preview
@Composable
fun ScheduledItemsFragmentContentPreview() {
    Surface {
        ScheduledItemsSuccessScreenContent(
            modifier = Modifier.height(900.dp),
            uiState = UiState.Success(getScheduledItemModelMock() + getScheduledItemModelMock() + getScheduledItemModelMock()),
            onClosePressed = {},
            onAddMoreItemsPressed = {},
            onDoneButtonPressed = {},
            onDocumentPressed = {},
            onTrashPressed = {},
            isReadyOnly = false
        )
    }
}

@Composable
@Preview
fun ScheduledItemsEmptyStateScreenContentPreview() {
    Surface {
        ScheduledItemsSuccessScreenContent(
            modifier = Modifier.height(900.dp),
            uiState = UiState.Success(emptyList()),
            onClosePressed = {},
            onAddMoreItemsPressed = {},
            onDoneButtonPressed = {},
            onDocumentPressed = {},
            onTrashPressed = {},
            isReadyOnly = false
        )
    }
}
