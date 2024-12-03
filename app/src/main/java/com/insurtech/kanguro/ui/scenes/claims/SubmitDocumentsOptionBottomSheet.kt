package com.insurtech.kanguro.ui.scenes.claims

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.base.BaseBottomSheetDialogFragment
import com.insurtech.kanguro.core.utils.CameraHelper
import com.insurtech.kanguro.core.utils.FilePickerHandler
import com.insurtech.kanguro.core.utils.KanguroFile
import com.insurtech.kanguro.databinding.BottomsheetSubmitDocumentsOptionBinding
import com.insurtech.kanguro.domain.model.FilePickerResult
import com.insurtech.kanguro.ui.scenes.fileNotSupported.FilePickerErrorHandler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubmitDocumentsOptionBottomSheet :
    BaseBottomSheetDialogFragment<BottomsheetSubmitDocumentsOptionBinding>() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.FilePickerOption

    private val filePickerHandler = FilePickerHandler(this)
    private val cameraHelper = CameraHelper(this)

    override fun onCreateBinding(inflater: LayoutInflater): BottomsheetSubmitDocumentsOptionBinding =
        BottomsheetSubmitDocumentsOptionBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.selectPicOption.setOnClickListener {
            filePickerHandler.openImagePicker("image/*") { result ->
                when (result) {
                    is FilePickerResult.Success -> {
                        setFragmentResultSuccess(result.data)
                    }

                    is FilePickerResult.Error -> {
                        FilePickerErrorHandler.handleFilePickerError(findNavController(), result)
                    }
                }
            }
        }

        binding.takePicOption.setOnClickListener {
            cameraHelper.takePicture { _, uri ->
                setFragmentResult(REQUEST_KEY, bundleOf(REQUEST_KEY to arrayListOf(uri)))
                dismiss()
            }
        }

        binding.selectFileOption.setOnClickListener {
            filePickerHandler.openFilePicker("application/pdf") { result ->
                when (result) {
                    is FilePickerResult.Success -> {
                        setFragmentResultSuccess(result.data)
                    }

                    is FilePickerResult.Error -> {
                        FilePickerErrorHandler.handleFilePickerError(findNavController(), result)
                    }
                }
            }
        }
    }

    private fun setFragmentResultSuccess(result: List<KanguroFile>) {
        val uris = result.map { it.uri }
        setFragmentResult(REQUEST_KEY, bundleOf(REQUEST_KEY to uris))
        dismiss()
    }

    companion object {
        private const val TAG = "SubmitDocumentsOptionBottomSheet"
        private const val REQUEST_KEY = "SubmitDocumentsOptionBottomSheetKey"

        fun show(
            target: Fragment,
            onResultReceived: (uri: List<Uri?>?) -> Unit
        ) {
            target.setFragmentResultListener(REQUEST_KEY) { _, bundle ->
                onResultReceived(bundle.getParcelableArrayList(REQUEST_KEY))
            }

            SubmitDocumentsOptionBottomSheet().show(target.parentFragmentManager, TAG)
        }
    }
}
