package com.insurtech.kanguro.ui.scenes.coverageDetails

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
import com.insurtech.kanguro.databinding.BottomsheetPicturePickerOptionBinding
import com.insurtech.kanguro.domain.model.FilePickerResult
import com.insurtech.kanguro.ui.scenes.fileNotSupported.FilePickerErrorHandler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PicturePickerOptionBottomSheet :
    BaseBottomSheetDialogFragment<BottomsheetPicturePickerOptionBinding>() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.FilePickerOption

    private val filePickerHandler = FilePickerHandler(this)
    private val cameraHelper = CameraHelper(this)

    override fun onCreateBinding(inflater: LayoutInflater): BottomsheetPicturePickerOptionBinding =
        BottomsheetPicturePickerOptionBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.selectPicOption.setOnClickListener {
            filePickerHandler.openImagePicker("image/*", isMultipleSelection = false) { result ->
                when (result) {
                    is FilePickerResult.Success -> {
                        val uri = result.data.first().uri
                        setFragmentResult(REQUEST_KEY, bundleOf(REQUEST_KEY to uri))
                        dismiss()
                    }

                    is FilePickerResult.Error -> {
                        FilePickerErrorHandler.handleFilePickerError(findNavController(), result)
                    }
                }
            }
        }

        binding.takePicOption.setOnClickListener {
            cameraHelper.takePicture { _, uri ->
                setFragmentResult(REQUEST_KEY, bundleOf(REQUEST_KEY to uri))
                dismiss()
            }
        }
    }

    companion object {
        private const val TAG = "PicturePickerOptionBottomSheet"
        private const val REQUEST_KEY = "PicturePickerOptionRequestKey"

        fun show(
            target: Fragment,
            onResultReceived: (uri: Uri?) -> Unit
        ) {
            target.setFragmentResultListener(REQUEST_KEY) { _, bundle ->
                onResultReceived(bundle.getParcelable(REQUEST_KEY))
            }

            PicturePickerOptionBottomSheet().show(target.parentFragmentManager, TAG)
        }
    }
}
