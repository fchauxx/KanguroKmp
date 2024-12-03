package com.insurtech.kanguro.ui.scenes.rentersScheduledItemsAddItem

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
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.utils.collectLatestIn
import com.insurtech.kanguro.databinding.FragmentScheduledItemsAddItemBinding
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.FooterSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.InvoiceInterval
import com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItemsAddItem.ScheduledItemsAddItemScreenContent
import com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItemsCategory.domain.ScheduledItemsCategoryItemModelUi
import com.insurtech.kanguro.ui.custom.KanguroBottomSheetFragment
import com.insurtech.kanguro.ui.scenes.rentersScheduledItemsCategory.toUi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScheduledItemsAddItemFragment :
    KanguroBottomSheetFragment<FragmentScheduledItemsAddItemBinding>() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.ScheduledItemsAddItem

    override val viewModel: ScheduledItemsAddItemViewModel by viewModels()
    override fun onCreateBinding(inflater: LayoutInflater): FragmentScheduledItemsAddItemBinding =
        FragmentScheduledItemsAddItemBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.uiState.collectLatestIn(viewLifecycleOwner) {
            if (it.isDone) {
                dismiss()
            }
        }
    }

    private fun setupUi() {
        binding.composeView
            .setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        binding.composeView.setContent {
            ScheduledItemsAddItemFragment(
                viewModel = viewModel,
                onBackPressed = { dismiss() }
            )
        }
    }
}

@Composable
private fun ScheduledItemsAddItemFragment(
    viewModel: ScheduledItemsAddItemViewModel = viewModel(),
    onBackPressed: () -> Unit
) {
    val uiState: ScheduledItemsAddItemViewModel.UiState by viewModel.uiState.collectAsState()

    ScheduledItemsAddItemScreenContent(
        selectedCategory = uiState.selectedCategory.toUi(),
        onBackPressed = onBackPressed,
        newItemName = uiState.newItemName,
        onNewItemNameChange = { viewModel.onNewItemNameChange(it) },
        newItemPrice = uiState.newItemPrice,
        onNewItemPriceChange = { viewModel.onNewItemPriceChange(it) },
        footerSection = uiState.footerSection,
        onSubmitPressed = { viewModel.postScheduledItem() }
    )
}

@Preview
@Composable
private fun ScheduledItemsAddItemFragmentPreview() {
    Surface {
        ScheduledItemsAddItemScreenContent(
            selectedCategory = ScheduledItemsCategoryItemModelUi("Jewelry", "Jewelry"),
            onBackPressed = {},
            newItemName = "",
            onNewItemNameChange = {},
            onNewItemPriceChange = {},
            newItemPrice = "",
            footerSection = FooterSectionModel(
                100.00.toBigDecimal(),
                1000.00.toBigDecimal(),
                false,
                InvoiceInterval.MONTHLY
            ),
            onSubmitPressed = {}
        )
    }
}
