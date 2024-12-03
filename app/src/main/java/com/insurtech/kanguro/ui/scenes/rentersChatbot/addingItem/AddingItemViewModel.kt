package com.insurtech.kanguro.ui.scenes.rentersChatbot.addingItem

import com.insurtech.kanguro.core.base.BaseViewModel
import com.insurtech.kanguro.designsystem.ui.composables.chatbot.addingItem.model.AddedItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AddingItemViewModel @Inject constructor() : BaseViewModel() {

    data class UiState(
        val showCapturePicture: Boolean = false,
        val showSelectPicture: Boolean = false,
        val showAddPictureBottomSheet: Boolean = false,
        val pictureType: PictureType = PictureType.ITEM,
        val addedItemModel: AddedItemModel = AddedItemModel()
    )

    enum class PictureType {
        ITEM,
        RECEIPT
    }

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun handleItemChanged(item: String) {
        _uiState.value =
            _uiState.value.copy(addedItemModel = _uiState.value.addedItemModel.copy(item = item))
    }

    fun handleValueChanged(value: String) {
        _uiState.value =
            _uiState.value.copy(addedItemModel = _uiState.value.addedItemModel.copy(value = value))
    }

    fun handleAddPictureType(type: PictureType) {
        _uiState.value = _uiState.value.copy(
            pictureType = type,
            showAddPictureBottomSheet = true,
            showCapturePicture = false,
            showSelectPicture = false
        )
    }

    fun handleAddPicture(showAddPictureBottomSheet: Boolean) {
        _uiState.value = _uiState.value.copy(
            showAddPictureBottomSheet = showAddPictureBottomSheet,
            showCapturePicture = false,
            showSelectPicture = false
        )
    }

    fun handleCapturePicture(showCapturePicture: Boolean) {
        _uiState.value = _uiState.value.copy(
            showAddPictureBottomSheet = false,
            showCapturePicture = showCapturePicture,
            showSelectPicture = false
        )
    }

    fun handleSelectPicture(showSelectPicture: Boolean) {
        _uiState.value = _uiState.value.copy(
            showAddPictureBottomSheet = false,
            showCapturePicture = false,
            showSelectPicture = showSelectPicture
        )
    }

    fun handleFileSelected(file: File?, name: String) {
        if (file == null) return

        // TODO: Handle file check the type and update viewModel

        when (_uiState.value.pictureType) {
            PictureType.ITEM -> {
                _uiState.value = _uiState.value.copy(
                    addedItemModel = _uiState.value.addedItemModel.copy(
                        // TODO: itemSelectedPicture =
                    )
                )
            }

            PictureType.RECEIPT -> {
                _uiState.value = _uiState.value.copy(
                    addedItemModel = _uiState.value.addedItemModel.copy(
                        // TODO: itemReceiptPicture =
                    )
                )
            }
        }
    }

    fun handleDeletePicture(type: PictureType) {
        when (type) {
            PictureType.ITEM -> _uiState.value = _uiState.value.copy(
                addedItemModel = _uiState.value.addedItemModel.copy(
                    itemSelectedPicture = null
                )
            )

            PictureType.RECEIPT -> _uiState.value = _uiState.value.copy(
                addedItemModel = _uiState.value.addedItemModel.copy(
                    itemReceiptPicture = null
                )
            )
        }
    }
}
