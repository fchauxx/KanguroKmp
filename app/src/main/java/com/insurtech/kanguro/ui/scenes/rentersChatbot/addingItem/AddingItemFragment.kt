package com.insurtech.kanguro.ui.scenes.rentersChatbot.addingItem

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.viewModels
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.databinding.FragmentRentersChatbotAddingItemBinding
import com.insurtech.kanguro.designsystem.ui.composables.chatbot.addingItem.AddingItemScreenContent
import com.insurtech.kanguro.designsystem.ui.composables.chatbot.addingItem.AddingItemScreenEvent
import com.insurtech.kanguro.designsystem.ui.composables.chatbot.addingItem.model.AddedItemModel
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.AddPictureBottomSheet
import com.insurtech.kanguro.domain.model.FilePickerResult
import com.insurtech.kanguro.ui.compose.utils.GetPictureComponent
import com.insurtech.kanguro.ui.compose.utils.getFileNameFromUri
import com.insurtech.kanguro.ui.compose.utils.toFile
import com.insurtech.kanguro.ui.custom.KanguroBottomSheetFragment

class AddingItemFragment : KanguroBottomSheetFragment<FragmentRentersChatbotAddingItemBinding>() {

    override val screenName: AnalyticsEnums.Screen =
        AnalyticsEnums.Screen.RentersChatbotScheduledItems

    override val viewModel: AddingItemViewModel by viewModels()

    override fun onCreateBinding(inflater: LayoutInflater): FragmentRentersChatbotAddingItemBinding =
        FragmentRentersChatbotAddingItemBinding.inflate(inflater)

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
                AddingItemFragment(
                    viewModel = viewModel,
                    context = requireContext(),
                    onEvent = { addingItemScreenEvent ->
                        handleEvent(addingItemScreenEvent)
                    }
                )
            }
        }
    }

    private fun handleEvent(event: AddingItemScreenEvent) {
        when (event) {
            is AddingItemScreenEvent.OnClosePressed -> dismiss()

            is AddingItemScreenEvent.OnDonePressed -> TODO()

            is AddingItemScreenEvent.OnAddItemPicturePressed -> viewModel.handleAddPictureType(
                AddingItemViewModel.PictureType.ITEM
            )

            is AddingItemScreenEvent.OnAddReceiptPicturePressed -> viewModel.handleAddPictureType(
                AddingItemViewModel.PictureType.RECEIPT
            )

            is AddingItemScreenEvent.OnDeleteItemPicturePressed -> viewModel.handleDeletePicture(
                AddingItemViewModel.PictureType.ITEM
            )

            is AddingItemScreenEvent.OnDeleteReceiptPicturePressed -> viewModel.handleDeletePicture(
                AddingItemViewModel.PictureType.RECEIPT
            )

            is AddingItemScreenEvent.OnItemChanged -> viewModel.handleItemChanged(event.item)

            is AddingItemScreenEvent.OnValueChanged -> viewModel.handleValueChanged(event.value)

            is AddingItemScreenEvent.OnPictureSelected -> {
                handleCapturedImage(event.result)
            }
        }
    }

    private fun handleCapturedImage(result: FilePickerResult<Uri>) {
        when (result) {
            is FilePickerResult.Success -> {
                handleSuccess(result)
            }

            is FilePickerResult.Error -> {
                // TODO Implement when Renters Claims are implemented
            }
        }
    }

    private fun handleSuccess(result: FilePickerResult.Success<Uri>) {
        val fileName =
            result.data.getFileNameFromUri(requireContext())
                ?: when (viewModel.uiState.value.pictureType) {
                    AddingItemViewModel.PictureType.ITEM -> "item_picture.jpg"
                    AddingItemViewModel.PictureType.RECEIPT -> "receipt_picture.jpg"
                }

        val file = result.data.toFile(requireContext(), fileName)
        viewModel.handleFileSelected(file, fileName)
    }
}

@Composable
private fun AddingItemFragment(
    viewModel: AddingItemViewModel,
    context: Context,
    onEvent: (AddingItemScreenEvent) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    GetPictureComponent(
        context = context,
        showCapturePicture = uiState.showCapturePicture,
        showSelectPicture = uiState.showSelectPicture
    ) { result ->
        onEvent(AddingItemScreenEvent.OnPictureSelected(result))
    }

    if (uiState.showAddPictureBottomSheet) {
        AddPictureBottomSheet(
            onDismiss = { viewModel.handleAddPicture(false) },
            onTakePicturePressed = { viewModel.handleCapturePicture(true) },
            onSelectFilePressed = { viewModel.handleSelectPicture(true) }
        )
    }

    AddingItemScreenContent(
        modifier = Modifier.fillMaxSize(),
        addedItemModel = uiState.addedItemModel,
        onEvent = onEvent
    )
}

@Composable
@Preview(device = Devices.NEXUS_5)
private fun AddingItemFragmentPreview() {
    Surface {
        AddingItemScreenContent(
            modifier = Modifier.fillMaxSize(),
            addedItemModel = AddedItemModel(),
            onEvent = {}
        )
    }
}
