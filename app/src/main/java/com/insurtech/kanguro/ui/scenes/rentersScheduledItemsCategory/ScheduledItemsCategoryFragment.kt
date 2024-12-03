package com.insurtech.kanguro.ui.scenes.rentersScheduledItemsCategory

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
import com.insurtech.kanguro.core.utils.UiState
import com.insurtech.kanguro.core.utils.safeNavigate
import com.insurtech.kanguro.databinding.FragmentScheduledItemsCategoryBinding
import com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItemsCategory.ScheduledItemsCategoryScreenContent
import com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItemsCategory.ScheduledItemsCategoryScreenLoaderState
import com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItemsCategory.domain.ScheduledItemsCategoryItemModelUi
import com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItemsCategory.domain.getScheduledItemsCategoryItemModelUiMockList
import com.insurtech.kanguro.domain.model.ScheduledItemTypeModel
import com.insurtech.kanguro.ui.custom.KanguroBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScheduledItemsCategoryFragment :
    KanguroBottomSheetFragment<FragmentScheduledItemsCategoryBinding>() {

    override val viewModel: ScheduledItemsCategoryViewModel by viewModels()

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.ScheduledItemsCategory

    override val isDraggable: Boolean = false
    override fun onCreateBinding(inflater: LayoutInflater): FragmentScheduledItemsCategoryBinding =
        FragmentScheduledItemsCategoryBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()
    }

    private fun setupUi() {
        binding.composeView
            .setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        binding.composeView.setContent {
            ScheduledItemsCategoryFragment(
                viewModel = viewModel,
                onBackPressed = { dismiss() },
                onCategoryPressed = { onCategoryPressed(it) }
            )
        }
    }

    private fun onCategoryPressed(item: ScheduledItemsCategoryItemModelUi) {
        findNavController().safeNavigate(
            ScheduledItemsCategoryFragmentDirections
                .actionScheduledItemsCategoryFragmentToScheduledItemsAddItemFragment(
                    item.toDomain(),
                    viewModel.getPolicyId()
                )
        )
    }
}

@Composable
private fun ScheduledItemsCategoryFragment(
    viewModel: ScheduledItemsCategoryViewModel = viewModel(),
    onBackPressed: () -> Unit,
    onCategoryPressed: (ScheduledItemsCategoryItemModelUi) -> Unit
) {
    val uiState: UiState<List<ScheduledItemTypeModel>> by viewModel.uiState.collectAsState()

    when (uiState) {
        is UiState.Success -> {
            ScheduledItemsCategoryScreenContent(
                onBackPressed = { onBackPressed() },
                onCategoryPressed = { onCategoryPressed(it) },
                list = (uiState as UiState.Success<List<ScheduledItemTypeModel>>).data.scheduledItemTypeModelListToUi()
            )
        }

        UiState.Loading.ScreenLoader -> {
            ScheduledItemsCategoryScreenLoaderState(onBackPressed = { onBackPressed() })
        }

        else -> {}
    }
}

@Preview
@Composable
private fun ScheduledItemsFragmentContentPreview() {
    Surface {
        ScheduledItemsCategoryScreenContent(
            onBackPressed = {},
            onCategoryPressed = {},
            list = getScheduledItemsCategoryItemModelUiMockList()
        )
    }
}
