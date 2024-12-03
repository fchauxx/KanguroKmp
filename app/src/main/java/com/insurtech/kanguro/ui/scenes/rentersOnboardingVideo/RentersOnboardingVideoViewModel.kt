package com.insurtech.kanguro.ui.scenes.rentersOnboardingVideo

import androidx.lifecycle.viewModelScope
import com.insurtech.kanguro.core.base.BaseViewModel
import com.insurtech.kanguro.core.utils.update
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.repository.ITemporaryFileRepository
import com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.domain.SelectedFileType
import com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.domain.SelectedFileUi
import com.insurtech.kanguro.domain.model.TemporaryUploadModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class RentersOnboardingVideoViewModel @Inject constructor(
    private val temporaryFileRepository: ITemporaryFileRepository
) : BaseViewModel() {

    private var _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    lateinit var temporaryUploadModel: TemporaryUploadModel
        private set

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                copy(isLoading = true)
            }

            when (val result = temporaryFileRepository.getTemporaryUploadModel()) {
                is Result.Success -> {
                    temporaryUploadModel = result.data

                    _uiState.update {
                        copy(isLoading = false, isError = false)
                    }
                }

                is Result.Error -> {
                    _uiState.update {
                        copy(isLoading = false, isError = true)
                    }
                }
            }
        }
    }

    fun onSendPressed() {
        _uiState.update {
            copy(
                showVideoOptionsBottomSheet = true,
                showCamera = false,
                showGallery = false,
                isUploadingVideoError = false
            )
        }
    }

    fun onRecordVideoPressed() {
        _uiState.update {
            copy(showCamera = true, showVideoOptionsBottomSheet = false)
        }
    }

    fun onSelectFilePressed() {
        _uiState.update {
            copy(showGallery = true, showVideoOptionsBottomSheet = false)
        }
    }

    fun closeBottomSheet() {
        _uiState.update {
            copy(showVideoOptionsBottomSheet = false)
        }
    }

    fun handleVideoCaptured(videoFile: File) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                copy(
                    isUploadingVideo = true,
                    showCamera = false,
                    showGallery = false,
                    showVideoOptionsBottomSheet = false
                )
            }

            when (
                temporaryFileRepository.putOnboardingVideoFile(
                    temporaryUploadModel.url,
                    temporaryUploadModel.blobType.orEmpty(),
                    videoFile
                )
            ) {
                is Result.Success -> {
                    val selectedVideoFile = SelectedFileUi(
                        type = SelectedFileType.Video,
                        id = temporaryUploadModel.id,
                        fileName = videoFile.name
                    )

                    _uiState.update {
                        copy(
                            isUploadingVideo = false,
                            doneButtonEnabled = true,
                            selectedVideoFile = selectedVideoFile
                        )
                    }
                }

                is Result.Error -> {
                    _uiState.update {
                        copy(
                            isUploadingVideo = false,
                            isUploadingVideoError = true,
                            doneButtonEnabled = false,
                            selectedVideoFile = null
                        )
                    }
                }
            }
        }
    }

    fun handleDeleteCapturedVideoPressed() {
        _uiState.update {
            copy(
                selectedVideoFile = null,
                doneButtonEnabled = false
            )
        }
    }

    fun handleCaptureVideoError() {
        _uiState.update {
            copy(
                isUploadingVideoError = true,
                isError = false,
                isLoading = false,
                isUploadingVideo = false,
                doneButtonEnabled = false,
                selectedVideoFile = null,
                showCamera = false,
                showGallery = false,
                showVideoOptionsBottomSheet = false
            )
        }
    }

    fun handleOnClosePressed(onClosePressedNoVideoSent: () -> Unit) {
        if (uiState.value.selectedVideoFile == null) {
            onClosePressedNoVideoSent()
        } else {
            _uiState.update {
                copy(
                    showCloseWindowDialog = true
                )
            }
        }
    }

    fun closeDialog() {
        _uiState.update {
            copy(
                showCloseWindowDialog = false
            )
        }
    }

    data class UiState(
        val isLoading: Boolean = false,
        val isError: Boolean = false,
        val isUploadingVideo: Boolean = false,
        val isUploadingVideoError: Boolean = false,
        val doneButtonEnabled: Boolean = false,
        val selectedVideoFile: SelectedFileUi? = null,
        val showCamera: Boolean = false,
        val showGallery: Boolean = false,
        val showVideoOptionsBottomSheet: Boolean = false,
        val showCloseWindowDialog: Boolean = false
    )
}
