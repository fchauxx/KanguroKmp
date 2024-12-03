package com.insurtech.kanguro.ui.scenes.directPayToVet.directPayToVetShareWithYourVet

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.insurtech.kanguro.R
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.utils.safeNavigate
import com.insurtech.kanguro.databinding.FragmentDirectPayToVetShareWithYourVetBinding
import com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.directPayToVetShareWithYourVet.DirectPayToVetShareWithYourVetScreenContent
import com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.directPayToVetShareWithYourVet.DocumentPicker
import com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.domain.SelectedFileType
import com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.domain.SelectedFileUi
import com.insurtech.kanguro.ui.compose.utils.LaunchCameraPhoto
import com.insurtech.kanguro.ui.compose.utils.LaunchSingleFilePicker
import com.insurtech.kanguro.ui.compose.utils.getFileNameFromUri
import com.insurtech.kanguro.ui.compose.utils.toFile
import com.insurtech.kanguro.ui.custom.KanguroBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DirectPayToVetShareWithYourVetFragment :
    KanguroBottomSheetFragment<FragmentDirectPayToVetShareWithYourVetBinding>() {

    override val screenName: AnalyticsEnums.Screen =
        AnalyticsEnums.Screen.DirectPayToVetShareWithYourVet

    override val viewModel: DirectPayToVetShareWithYourVetViewModel by viewModels()

    override val isDraggable: Boolean = false

    override fun onCreateBinding(inflater: LayoutInflater): FragmentDirectPayToVetShareWithYourVetBinding =
        FragmentDirectPayToVetShareWithYourVetBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
    }

    private fun setupUi() {
        binding.composeView
            .setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        binding.composeView.setContent {
            DirectPayToVetShareWithYourVetFragment(
                viewModel = viewModel,
                onDownloadFilePressed = { viewModel.handleDownloadFile(requireContext()) },
                onUploadFilePressed = { viewModel.handleOnUploadFilePressed(true) },
                onDeleteDocumentPressed = { viewModel.handleDeleteDocument() },
                onImageScanned = { imageUri ->
                    handleDocumentSelected(imageUri, SelectedFileType.Image)
                },
                onFileSelected = { fileUri ->
                    handleDocumentSelected(fileUri, SelectedFileType.File)
                },
                onClosePressed = ::onClosePressed,
                onDonePressed = { viewModel.handleDoneButtonPressed() },
                onCloseErrorDialog = { viewModel.handleOnCloseErrorDialogPressed() },
                onVeterinarianSignatureSent = ::onVeterinarianSignatureSent
            )
        }
    }

    private fun onClosePressed() {
        findNavController().popBackStack()
    }

    private fun onVeterinarianSignatureSent() {
        val navOptions = navOptions {
            popUpTo(R.id.directPayToVetAlmostDone) {
                inclusive = true
            }
        }

        findNavController()
            .safeNavigate(
                DirectPayToVetShareWithYourVetFragmentDirections
                    .actionDirectPayToVetShareWithYourVetFragmentToDirectPayToVetDocumentSentFragment(),
                navOptions
            )
    }

    private fun handleDocumentSelected(uri: Uri, type: SelectedFileType) {
        val fileName = uri.getFileNameFromUri(requireContext())
            ?: if (type == SelectedFileType.File) "file.pdf" else "file.jpg"

        val file = uri.toFile(requireContext(), fileName)

        if (file == null || file.length() == 0L) {
            return
        }

        viewModel.handleOnUploadFilePressed(false)
        viewModel.handleSelectedDocument(file, fileName, type)
    }
}

@Composable
private fun DirectPayToVetShareWithYourVetFragment(
    viewModel: DirectPayToVetShareWithYourVetViewModel,
    onDownloadFilePressed: () -> Unit,
    onUploadFilePressed: () -> Unit,
    onDeleteDocumentPressed: () -> Unit,
    onDonePressed: () -> Unit,
    onImageScanned: (Uri) -> Unit,
    onFileSelected: (Uri) -> Unit,
    onClosePressed: () -> Unit,
    onCloseErrorDialog: () -> Unit,
    onVeterinarianSignatureSent: () -> Unit = {}
) {
    val uiState: DirectPayToVetShareWithYourVetViewModel.UiState by viewModel.uiState.collectAsState()

    if (uiState.showDocumentPicker) {
        DocumentPicker(
            onDismiss = { viewModel.handleOnUploadFilePressed(false) },
            onTakePicturePressed = { viewModel.handleOnCameraLauncherPressed(true) },
            onSelectFilePressed = { viewModel.handleOnFilePickerLauncherPressed(true) }
        )
    }

    if (uiState.showCameraLauncher) {
        LaunchCameraPhoto(context = LocalContext.current) { imageUri ->
            onImageScanned(imageUri)
        }
    }

    if (uiState.showFilePickerLauncher) {
        LaunchSingleFilePicker { fileUri ->
            if (fileUri != null) {
                onFileSelected(fileUri)
            }
        }
    }

    val error = uiState.isError
    if (error != null) {
        val message = LocalContext.current.getString(error.message)
        showErrorMessage(message, LocalContext.current) {
            onCloseErrorDialog()
        }
    }

    if (uiState.isSignatureSent) {
        onVeterinarianSignatureSent()
    }

    DirectPayToVetShareWithYourVetScreenContent(
        isDoneButtonEnabled = uiState.isDoneButtonEnabled,
        isUploadingDocument = uiState.isUploadingDocument,
        isSendingSignature = uiState.isSendingSignature,
        selectedFileUi = uiState.selectedFileUi,
        onClosePressed = onClosePressed,
        onDownloadFilePressed = onDownloadFilePressed,
        onUploadFilePressed = onUploadFilePressed,
        onDeleteDocumentPressed = onDeleteDocumentPressed,
        onDonePressed = onDonePressed
    )
}

private fun showErrorMessage(
    message: String,
    context: Context,
    onCloseErrorDialog: () -> Unit
) {
    val alert = MaterialAlertDialogBuilder(context)
        .setTitle(context.getString(R.string.error_dialog_title))
        .setMessage(message)
        .setPositiveButton(context.getString(R.string.back)) { _, _ ->
            onCloseErrorDialog()
        }
        .setOnDismissListener {
            onCloseErrorDialog()
        }

    alert.show()
}

@Composable
@Preview
fun DirectPayToVetShareWithYourVetFragmentPreview() {
    Surface {
        DirectPayToVetShareWithYourVetScreenContent(
            isDoneButtonEnabled = true,
            isUploadingDocument = false,
            isSendingSignature = false,
            selectedFileUi = SelectedFileUi(
                id = 1,
                fileUrl = "fileUrl",
                fileName = "test.jpg",
                type = SelectedFileType.File
            ),
            onClosePressed = {},
            onDownloadFilePressed = {},
            onUploadFilePressed = {},
            onDeleteDocumentPressed = {},
            onDonePressed = {}
        )
    }
}
