package com.insurtech.kanguro.core.utils

import android.Manifest
import android.app.Activity
import android.content.ClipData
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.insurtech.kanguro.domain.model.FilePickerErrorType
import com.insurtech.kanguro.domain.model.FilePickerResult

typealias KanguroFilesCallback = (FilePickerResult<List<KanguroFile>>) -> Unit

class FilePickerHandler(
    private val fragment: Fragment
) {

    private var selectedDataType = ""
    private var onFileSelected: KanguroFilesCallback? = null

    private val contentResolver by lazy {
        fragment.requireActivity().contentResolver
    }

    private var isMultipleSelection = true

    private val permissionResult = fragment.registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { result ->
        if (result) {
            accessExternalStorage()
        }
    }

    private val handleSelectedFile = fragment.registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        if (activityResult.resultCode == Activity.RESULT_OK) {
            activityResult.data?.data?.let { uri ->
                val result = filePickerResult(uri)
                onFileSelected?.invoke(result)
            }

            activityResult.data?.clipData?.let { clipData ->
                val result = filePickerResult(clipData)
                onFileSelected?.invoke(result)
            }
        }
    }

    private fun filePickerResult(uri: Uri): FilePickerResult<List<KanguroFile>> {
        val mimeType = contentResolver.getType(uri).orEmpty()

        return if (mimeType.isSupportedMimeType()) {
            val kanguroFile = getKanguroFile(uri)
            FilePickerResult.Success(data = listOf(kanguroFile))
        } else {
            FilePickerResult.Error(
                errorType = FilePickerErrorType.FormatNotSupported(
                    mimeType.substringAfterLast(
                        "/"
                    )
                )
            )
        }
    }

    private fun filePickerResult(clipData: ClipData): FilePickerResult<ArrayList<KanguroFile>> {
        val result: FilePickerResult<ArrayList<KanguroFile>> = run {
            val kanguroFiles = arrayListOf<KanguroFile>()
            for (i in 0 until clipData.itemCount) {
                val item = clipData.getItemAt(i)
                val uri = item.uri

                val mimeType = contentResolver.getType(uri).orEmpty()

                if (mimeType.isSupportedMimeType().not()) {
                    return@run FilePickerResult.Error(
                        errorType = FilePickerErrorType.FormatNotSupported(
                            mimeType
                        )
                    )
                }

                val kanguroFile = getKanguroFile(uri)
                kanguroFiles.add(kanguroFile)
            }
            FilePickerResult.Success(data = kanguroFiles)
        }

        return result
    }

    private fun getKanguroFile(uri: Uri): KanguroFile {
        val extension = uri.getFileExtension(contentResolver)
        val openInputStream = contentResolver.openInputStream(uri)
        val availableSize = openInputStream?.available() ?: 0
        val fileBytes = ByteArray(availableSize)
        openInputStream?.use {
            it.read(fileBytes, 0, availableSize)
        }

        return KanguroFile(uri, fileBytes, extension)
    }

    private fun accessExternalStorage() {
        val data = Intent(Intent.ACTION_OPEN_DOCUMENT)
        if (isMultipleSelection) {
            data.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }

        data.type = selectedDataType

        val intentToLaunch = Intent.createChooser(data, "Choose a file")
        handleSelectedFile.launch(intentToLaunch)
    }

    fun openFilePicker(
        dataType: String,
        isMultipleSelection: Boolean = true,
        onFileSelected: KanguroFilesCallback
    ) {
        this.selectedDataType = dataType
        this.isMultipleSelection = isMultipleSelection
        this.onFileSelected = onFileSelected

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            accessExternalStorage()
            return
        }

        attemptToOpenFile(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    fun openImagePicker(
        dataType: String,
        isMultipleSelection: Boolean = true,
        onFileSelected: KanguroFilesCallback
    ) {
        this.selectedDataType = dataType
        this.isMultipleSelection = isMultipleSelection
        this.onFileSelected = onFileSelected

        val permissionToCheck = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        attemptToOpenFile(permissionToCheck)
    }

    private fun attemptToOpenFile(permission: String) {
        when {
            ContextCompat.checkSelfPermission(
                fragment.requireContext(),
                permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                accessExternalStorage()
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                fragment.requireActivity(),
                permission
            ) -> {
                onFileSelected?.invoke(
                    FilePickerResult.Error(errorType = FilePickerErrorType.PermissionDenied)
                )
            }

            else -> {
                permissionResult.launch(permission)
            }
        }
    }
}
