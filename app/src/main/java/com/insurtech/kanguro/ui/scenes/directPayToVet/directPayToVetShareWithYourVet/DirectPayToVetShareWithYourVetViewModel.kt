package com.insurtech.kanguro.ui.scenes.directPayToVet.directPayToVetShareWithYourVet

import android.content.Context
import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.insurtech.kanguro.R
import com.insurtech.kanguro.core.base.BaseViewModel
import com.insurtech.kanguro.core.utils.downloader.AndroidDownloader
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.repository.IRefactoredClaimRepository
import com.insurtech.kanguro.data.repository.ITemporaryFileRepository
import com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.domain.ErrorState
import com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.domain.SelectedFileType
import com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.domain.SelectedFileUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class DirectPayToVetShareWithYourVetViewModel(
    private val args: DirectPayToVetShareWithYourVetFragmentArgs,
    private val temporaryFileRepository: ITemporaryFileRepository,
    private val claimRepository: IRefactoredClaimRepository
) : BaseViewModel() {

    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        temporaryFileRepository: ITemporaryFileRepository,
        claimRepository: IRefactoredClaimRepository
    ) : this(
        DirectPayToVetShareWithYourVetFragmentArgs.fromSavedStateHandle(savedStateHandle),
        temporaryFileRepository,
        claimRepository
    )

    data class UiState(
        val showDocumentPicker: Boolean = false,
        val showCameraLauncher: Boolean = false,
        val showFilePickerLauncher: Boolean = false,
        val selectedFileUi: SelectedFileUi? = null,
        val isUploadingDocument: Boolean = false,
        val isSendingSignature: Boolean = false,
        val isError: ErrorState? = null,
        val isDoneButtonEnabled: Boolean = false,
        val isSignatureSent: Boolean = false
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun handleDownloadFile(context: Context) {
        viewModelScope.launch {
            val result = claimRepository.getDirectPaymentPreSignedFileUrl(args.claimId)

            if (result is Result.Success) {
                val downloader = AndroidDownloader(context)
                downloader.downloadFile(
                    result.data,
                    context.getString(R.string.direct_pay_to_vet_document)
                )
            } else {
                handleError(R.string.error_dialog_generic_message)
            }
        }
    }

    fun handleOnUploadFilePressed(showDocumentPicker: Boolean) {
        _uiState.value = _uiState.value.copy(
            showDocumentPicker = showDocumentPicker,
            showCameraLauncher = false,
            showFilePickerLauncher = false
        )
    }

    fun handleOnCameraLauncherPressed(showCameraLauncher: Boolean) {
        _uiState.value = _uiState.value.copy(
            showDocumentPicker = false,
            showCameraLauncher = showCameraLauncher,
            showFilePickerLauncher = false
        )
    }

    fun handleSelectedDocument(file: File, fileName: String, type: SelectedFileType) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isUploadingDocument = true,
                isError = null,
                isDoneButtonEnabled = false
            )

            val result = temporaryFileRepository.postTemporaryFile(file)
            if (result is Result.Success) {
                val imageId = result.data.id
                if (imageId != null) {
                    handleUploadSuccess(
                        imageId = imageId,
                        fileName = fileName,
                        fileUrl = result.data.url,
                        type = type
                    )
                } else {
                    handleError(R.string.error_dialog_generic_message)
                }
            } else {
                handleError(R.string.upload_document_error)
            }
        }
    }

    private fun handleUploadSuccess(
        imageId: Int,
        fileName: String,
        fileUrl: String?,
        type: SelectedFileType
    ) {
        val selectedDocument = SelectedFileUi(
            id = imageId,
            fileName = fileName,
            fileUrl = fileUrl,
            type = type
        )

        _uiState.value = _uiState.value.copy(
            selectedFileUi = selectedDocument,
            isUploadingDocument = false,
            isError = null,
            isDoneButtonEnabled = true
        )
    }

    private fun handleError(@StringRes messageRes: Int) {
        _uiState.value = _uiState.value.copy(
            selectedFileUi = null,
            isSendingSignature = false,
            isUploadingDocument = false,
            isError = ErrorState(
                message = messageRes
            ),
            isDoneButtonEnabled = false
        )
    }

    fun handleOnFilePickerLauncherPressed(showFilePickerLauncher: Boolean) {
        _uiState.value = _uiState.value.copy(
            showDocumentPicker = false,
            showCameraLauncher = false,
            showFilePickerLauncher = showFilePickerLauncher
        )
    }

    fun handleDeleteDocument() {
        _uiState.value = _uiState.value.copy(
            selectedFileUi = null,
            isDoneButtonEnabled = false
        )
    }

    fun handleOnCloseErrorDialogPressed() {
        _uiState.value = _uiState.value.copy(
            isError = null
        )
    }

    fun handleDoneButtonPressed() {
        _uiState.value = _uiState.value.copy(
            isSendingSignature = true,
            isDoneButtonEnabled = false
        )

        val documentId = _uiState.value.selectedFileUi?.id

        if (documentId != null) {
            viewModelScope.launch {
                val result = claimRepository.postDirectPaymentVeterinarianSignature(
                    claimId = args.claimId,
                    fileIds = listOf(documentId)
                )

                if (result is Result.Success && result.data) {
                    _uiState.value = _uiState.value.copy(
                        isSendingSignature = false,
                        isSignatureSent = true
                    )
                } else {
                    handleError(R.string.send_document_error)
                }
            }
        }
    }
}
