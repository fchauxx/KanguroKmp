package com.insurtech.kanguro.ui.scenes.rentersScheduledItemsSelectedPictures

import android.net.Uri
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
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.databinding.FragmentScheduledItemsBinding
import com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItemsSelectedPictures.ScheduledItemsSelectedPicturesScreenContent
import com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItemsSelectedPictures.model.SelectedPicturesEvent
import com.insurtech.kanguro.domain.model.FilePickerResult
import com.insurtech.kanguro.domain.model.ScheduledItemImageType
import com.insurtech.kanguro.ui.compose.utils.GetPictureComponent
import com.insurtech.kanguro.ui.compose.utils.getFileNameFromUri
import com.insurtech.kanguro.ui.compose.utils.toFile
import com.insurtech.kanguro.ui.custom.KanguroBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScheduledItemsSelectedPicturesFragment :
    KanguroBottomSheetFragment<FragmentScheduledItemsBinding>() {

    override val viewModel: ScheduledItemsSelectedPicturesViewModel by viewModels()

    override val screenName: AnalyticsEnums.Screen =
        AnalyticsEnums.Screen.ScheduledItemsSelectedPictures

    override val isDraggable: Boolean = false

    override fun onCreateBinding(inflater: LayoutInflater): FragmentScheduledItemsBinding =
        FragmentScheduledItemsBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.composeView
            .setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        binding.composeView.setContent {
            val uiState: ScheduledItemsSelectedPicturesViewModel.UiState by viewModel.uiState.collectAsState()

            if (uiState.isInputImagesSent) {
                dismiss()
            }

            GetPictureComponent(
                context = requireContext(),
                showCapturePicture = uiState.showCapturePicture,
                showSelectPicture = uiState.showSelectPicture
            ) { result ->
                handleCapturedImage(
                    result = result,
                    itemType = uiState.pictureType
                )
            }

            ScheduledItemsSelectedPicturesFragment(uiState = uiState) { event ->
                handleEvent(event)
            }
        }
    }

    private fun handleCapturedImage(
        result: FilePickerResult<Uri>,
        itemType: ScheduledItemImageType
    ) {
        when (result) {
            is FilePickerResult.Success -> {
                handleSuccess(result.data, itemType)
            }

            is FilePickerResult.Error -> {
                // TODO Implement when Renters Claims are Implemented
            }
        }
    }

    private fun handleSuccess(
        imageUri: Uri,
        itemType: ScheduledItemImageType
    ) {
        val fileName = imageUri.getFileNameFromUri(requireContext()) ?: "file.jpg"

        val file = imageUri.toFile(
            requireContext(),
            fileName
        )

        if (file == null || file.length() == 0L) {
            viewModel.handleCloseAddPictureBottomSheet()
            return
        }

        viewModel.handleImageCaptured(
            imageFile = file,
            imageType = itemType
        )
    }

    private fun handleEvent(event: SelectedPicturesEvent) {
        when (event) {
            is SelectedPicturesEvent.Back -> dismiss()

            is SelectedPicturesEvent.Done ->
                viewModel.handleOnDonePressed()

            is SelectedPicturesEvent.CapturePicture ->
                viewModel.handleCapturePicture(event.type)

            is SelectedPicturesEvent.SelectPicture ->
                viewModel.handleSelectPicture(event.type)

            is SelectedPicturesEvent.ShowAddPictureBottomSheet ->
                viewModel.handleAddPicture(event.type)

            is SelectedPicturesEvent.CloseAddPictureBottomSheet ->
                viewModel.handleCloseAddPictureBottomSheet()

            is SelectedPicturesEvent.DeletePicture ->
                viewModel.handleOnDeletePicture(
                    id = event.id,
                    type = event.type
                )

            else -> {
                // left empty on purpose
            }
        }
    }
}

@Composable
private fun ScheduledItemsSelectedPicturesFragment(
    uiState: ScheduledItemsSelectedPicturesViewModel.UiState,
    onEvent: (SelectedPicturesEvent) -> Unit
) {
    ScheduledItemsSelectedPicturesScreenContent(
        receiptPictures = uiState.receiptPictures,
        itemPictures = uiState.itemPictures,
        itemOnReceiptPictures = uiState.itemOnReceiptPictures,
        isSubmitButtonEnabled = uiState.isSubmitButtonEnabled,
        showAddPictureBottomSheet = uiState.showAddPictureBottomSheet,
        onEvent = onEvent
    )
}

@Composable
@Preview
private fun ScheduledItemsSelectedPicturesFragmentPreview() {
    Surface {
        ScheduledItemsSelectedPicturesFragment(
            uiState = ScheduledItemsSelectedPicturesViewModel.UiState(),
            onEvent = {}
        )
    }
}
