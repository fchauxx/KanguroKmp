package com.insurtech.kanguro.ui.scenes.rentersOnboardingVideo

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.addCallback
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.databinding.FragmentRentersOnboardingVideoBinding
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.chatbot.AddAttachmentButton
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.KanguroModalBottomSheet
import com.insurtech.kanguro.designsystem.ui.composables.rentersOnboardingVideo.content.RentersOnboardingVideoScreenContent
import com.insurtech.kanguro.designsystem.ui.composables.rentersOnboardingVideo.model.RentersOnboardingVideoEvent
import com.insurtech.kanguro.ui.compose.utils.GetVideoComponent
import com.insurtech.kanguro.ui.compose.utils.getFileNameFromUri
import com.insurtech.kanguro.ui.compose.utils.toFile
import com.insurtech.kanguro.ui.custom.KanguroBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RentersOnboardingVideoFragment :
    KanguroBottomSheetFragment<FragmentRentersOnboardingVideoBinding>() {

    override val screenName = AnalyticsEnums.Screen.RentersEditAdditionalParties

    override val viewModel: RentersOnboardingVideoViewModel by viewModels()

    override fun onCreateBinding(inflater: LayoutInflater): FragmentRentersOnboardingVideoBinding =
        FragmentRentersOnboardingVideoBinding.inflate(inflater)

    override val isDraggable: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            setResult(id = 0)
            this.remove()
        }

        setupUi()
    }

    private fun setupUi() {
        binding.composeView.apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
            setContent {
                RentersOnboardingVideoFragment(
                    viewModel = viewModel,
                    onEvent = ::handleEvent
                )
            }
        }
    }

    private fun handleEvent(event: RentersOnboardingVideoEvent) {
        when (event) {
            is RentersOnboardingVideoEvent.OnClosePressed -> {
                viewModel.handleOnClosePressed {
                    setResult(id = 0) // id == 0 indicates that the user canceled de process
                }
            }

            is RentersOnboardingVideoEvent.OnCloseConfirmationPressed -> {
                setResult(id = 0) // id == 0 indicates that the user canceled de process
            }

            is RentersOnboardingVideoEvent.OnSendPressed -> {
                viewModel.onSendPressed()
            }

            is RentersOnboardingVideoEvent.OnDonePressed -> {
                setResult(id = viewModel.temporaryUploadModel.id)
            }

            is RentersOnboardingVideoEvent.OnBackPressed -> {
                viewModel.handleOnClosePressed {
                    setResult(id = 0) // id == 0 indicates that the user canceled de process
                }
            }

            is RentersOnboardingVideoEvent.OnRecordVideoPressed -> {
                viewModel.onRecordVideoPressed()
            }

            is RentersOnboardingVideoEvent.OnSelectFilePressed -> {
                viewModel.onSelectFilePressed()
            }

            is RentersOnboardingVideoEvent.OnDismissBottomSheet -> {
                viewModel.closeBottomSheet()
            }

            is RentersOnboardingVideoEvent.OnVideoRecorded -> {
                handleCapturedVideo(videoUri = event.videoUri)
            }

            is RentersOnboardingVideoEvent.OnDeleteCapturedVideoPressed -> {
                viewModel.handleDeleteCapturedVideoPressed()
            }

            is RentersOnboardingVideoEvent.OnTryAgainPressed -> {
                viewModel.fetchData()
            }
        }
    }

    private fun setResult(id: Int) {
        setFragmentResult(
            REQUEST_KEY,
            bundleOf(
                REQUEST_KEY to id
            )
        )
        dismiss()
    }

    private fun handleCapturedVideo(
        videoUri: Uri
    ) {
        val fileName = videoUri.getFileNameFromUri(requireContext()) ?: "video_file"

        val file = videoUri.toFile(
            requireContext(),
            fileName
        )

        if (file == null || file.length() == 0L) {
            viewModel.handleCaptureVideoError()
            return
        }

        viewModel.handleVideoCaptured(videoFile = file)
    }

    companion object {
        private const val REQUEST_KEY = "KEY_ONBOARDING_VIDEO_ID"

        fun setFragmentResultListener(
            fragment: Fragment,
            listener: (Int) -> Unit
        ) {
            fragment.setFragmentResultListener(REQUEST_KEY) { _, bundle ->
                val result = bundle.getInt(REQUEST_KEY, 0)
                listener(result)
            }
        }
    }
}

@Composable
private fun RentersOnboardingVideoFragment(
    viewModel: RentersOnboardingVideoViewModel,
    onEvent: (RentersOnboardingVideoEvent) -> Unit
) {
    val uiState: RentersOnboardingVideoViewModel.UiState by viewModel.uiState.collectAsState()

    GetVideoComponent(
        showCapturePicture = uiState.showCamera,
        showSelectPicture = uiState.showGallery,
        context = LocalContext.current
    ) { videoUri ->
        onEvent(RentersOnboardingVideoEvent.OnVideoRecorded(videoUri))
    }

    RentersOnboardingVideoScreenContent(
        isLoading = uiState.isLoading,
        isError = uiState.isError,
        isUploadingVideo = uiState.isUploadingVideo,
        isUploadingVideoError = uiState.isUploadingVideoError,
        doneButtonEnabled = uiState.doneButtonEnabled,
        selectedFileUi = uiState.selectedVideoFile,
        onEvent = onEvent
    )

    RentersOnboardingVideoSBottomSheet(
        onDismiss = { onEvent(RentersOnboardingVideoEvent.OnDismissBottomSheet) },
        onRecordVideoPressed = { onEvent(RentersOnboardingVideoEvent.OnRecordVideoPressed) },
        onSelectFilePressed = { onEvent(RentersOnboardingVideoEvent.OnSelectFilePressed) },
        showBottomSheet = uiState.showVideoOptionsBottomSheet
    )

    if (uiState.showCloseWindowDialog) {
        CloseWindowConfirmationDialog(
            onClosePressed = { viewModel.closeDialog() },
            onCancelPressed = { viewModel.closeDialog() },
            onConfirmPressed = { onEvent(RentersOnboardingVideoEvent.OnCloseConfirmationPressed) }
        )
    }
}

@Composable
private fun RentersOnboardingVideoSBottomSheet(
    onDismiss: () -> Unit,
    onRecordVideoPressed: () -> Unit,
    onSelectFilePressed: () -> Unit,
    showBottomSheet: Boolean
) {
    if (showBottomSheet) {
        KanguroModalBottomSheet(onDismiss = { onDismiss() }) {
            AddAttachmentButton(
                icon = R.drawable.ic_camera,
                label = stringResource(id = R.string.record_video),
                onClick = { onRecordVideoPressed() }
            )

            AddAttachmentButton(
                icon = R.drawable.ic_document_text,
                label = stringResource(id = R.string.select_single_file),
                onClick = { onSelectFilePressed() }
            )
        }
    }
}

@Composable
@Preview(device = Devices.NEXUS_5)
fun RentersOnboardingVideoScreenContentPreview() {
    Surface {
        RentersOnboardingVideoScreenContent(
            isUploadingVideo = false,
            doneButtonEnabled = true,
            onEvent = {}
        )
    }
}
