package com.insurtech.kanguro.ui.scenes.rentersScheduledItemsSelectedPictures

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.insurtech.kanguro.core.base.BaseViewModel
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.repository.IRentersScheduledItemsRepository
import com.insurtech.kanguro.data.repository.ITemporaryFileRepository
import com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItemsSelectedPictures.model.SelectedPictureUi
import com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItemsSelectedPictures.model.toScheduledItemInputImage
import com.insurtech.kanguro.domain.model.ScheduledItemImageType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ScheduledItemsSelectedPicturesViewModel(
    private val temporaryFileRepository: ITemporaryFileRepository,
    private val rentersScheduledItemsRepository: IRentersScheduledItemsRepository,
    private val args: ScheduledItemsSelectedPicturesFragmentArgs
) : BaseViewModel() {

    @Inject
    constructor(
        temporaryFileRepository: ITemporaryFileRepository,
        rentersScheduledItemsRepository: IRentersScheduledItemsRepository,
        savedStateHandle: SavedStateHandle
    ) : this(
        temporaryFileRepository,
        rentersScheduledItemsRepository,
        ScheduledItemsSelectedPicturesFragmentArgs.fromSavedStateHandle(savedStateHandle)
    )

    data class UiState(
        val receiptPictures: List<SelectedPictureUi> = emptyList(),
        val itemPictures: List<SelectedPictureUi> = emptyList(),
        val itemOnReceiptPictures: List<SelectedPictureUi> = emptyList(),
        val showCapturePicture: Boolean = false,
        val showSelectPicture: Boolean = false,
        val showAddPictureBottomSheet: Boolean = false,
        val pictureType: ScheduledItemImageType = ScheduledItemImageType.ReceiptOrAppraisal,
        val isInputImagesSent: Boolean = false
    ) {
        val isSubmitButtonEnabled: Boolean
            get() = receiptPictures.isNotEmpty() && itemPictures.isNotEmpty() && itemOnReceiptPictures.isNotEmpty()
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun handleImageCaptured(
        imageFile: File,
        imageType: ScheduledItemImageType
    ) {
        viewModelScope.launch {
            val imageResult = temporaryFileRepository.postTemporaryFile(imageFile)

            if (imageResult is Result.Success) {
                val imageId = imageResult.data.id

                if (imageId != null) {
                    val selectedPicture = SelectedPictureUi(
                        id = imageId,
                        type = imageType,
                        url = imageResult.data.url
                    )
                    updateStateForSelectedPicture(selectedPicture)
                } else {
                    // TODO Handle Id null Error
                }
            } else {
                // TODO Handle Error
            }
        }
    }

    private fun updateStateForSelectedPicture(
        selectedPicture: SelectedPictureUi
    ) {
        val updatedUiState = _uiState.value.copy(
            showCapturePicture = false,
            showSelectPicture = false,
            showAddPictureBottomSheet = false
        )

        _uiState.value = when (selectedPicture.type) {
            ScheduledItemImageType.ReceiptOrAppraisal ->
                updatedUiState.copy(receiptPictures = updatedUiState.receiptPictures + selectedPicture)

            ScheduledItemImageType.Item ->
                updatedUiState.copy(itemPictures = updatedUiState.itemPictures + selectedPicture)

            ScheduledItemImageType.ItemWithReceiptOrAppraisal ->
                updatedUiState.copy(itemOnReceiptPictures = updatedUiState.itemOnReceiptPictures + selectedPicture)

            else -> updatedUiState
        }
    }

    fun handleCapturePicture(type: ScheduledItemImageType) {
        _uiState.value =
            _uiState.value.copy(
                showCapturePicture = true,
                showSelectPicture = false,
                showAddPictureBottomSheet = false,
                pictureType = type
            )
    }

    fun handleSelectPicture(type: ScheduledItemImageType) {
        _uiState.value =
            _uiState.value.copy(
                showCapturePicture = false,
                showSelectPicture = true,
                showAddPictureBottomSheet = false,
                pictureType = type
            )
    }

    fun handleAddPicture(type: ScheduledItemImageType) {
        _uiState.value =
            _uiState.value.copy(
                showCapturePicture = false,
                showSelectPicture = false,
                showAddPictureBottomSheet = true,
                pictureType = type
            )
    }

    fun handleCloseAddPictureBottomSheet() {
        _uiState.value =
            _uiState.value.copy(
                showCapturePicture = false,
                showSelectPicture = false,
                showAddPictureBottomSheet = false
            )
    }

    fun handleOnDonePressed() {
        val allSelectedPictures = _uiState.value.receiptPictures +
            _uiState.value.itemPictures +
            _uiState.value.itemOnReceiptPictures

        val inputImages = allSelectedPictures.map { it.toScheduledItemInputImage() }

        viewModelScope.launch {
            val result =
                rentersScheduledItemsRepository.putImages(args.scheduledItemId, inputImages)

            if (result is Result.Success) {
                _uiState.value = _uiState.value.copy(isInputImagesSent = true)
            } else {
                // TODO Handle Error
            }
        }
    }

    fun handleOnDeletePicture(id: Int, type: ScheduledItemImageType) {
        val updatedUiState = _uiState.value.copy(
            showCapturePicture = false,
            showSelectPicture = false,
            showAddPictureBottomSheet = false
        )

        _uiState.value = when (type) {
            ScheduledItemImageType.ReceiptOrAppraisal ->
                updatedUiState.copy(
                    receiptPictures = _uiState.value.receiptPictures.deletePictureFromList(
                        id
                    )
                )

            ScheduledItemImageType.Item ->
                updatedUiState.copy(
                    itemPictures = _uiState.value.itemPictures.deletePictureFromList(
                        id
                    )
                )

            ScheduledItemImageType.ItemWithReceiptOrAppraisal ->
                updatedUiState.copy(
                    itemOnReceiptPictures = _uiState.value.itemOnReceiptPictures.deletePictureFromList(
                        id
                    )
                )
        }
    }

    private fun List<SelectedPictureUi>.deletePictureFromList(id: Int): List<SelectedPictureUi> =
        this.filterNot { it.id == id }
}
