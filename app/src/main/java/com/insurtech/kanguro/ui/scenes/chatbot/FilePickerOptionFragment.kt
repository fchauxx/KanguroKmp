package com.insurtech.kanguro.ui.scenes.chatbot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.Keep
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.base.BaseBottomSheetDialogFragment
import com.insurtech.kanguro.databinding.FragmentFileActionsBinding
import com.insurtech.kanguro.ui.scenes.javier.ChatbotActionPressedListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilePickerOptionFragment : BaseBottomSheetDialogFragment<FragmentFileActionsBinding>() {

    override val screenName: AnalyticsEnums.Screen = AnalyticsEnums.Screen.FilePickerOption

    private val action by lazy {
        requireArguments().getString(ARG_ACTION)
    }

    override fun onCreateBinding(inflater: LayoutInflater) =
        FragmentFileActionsBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.selectFileOption.setOnClickListener { setResultPicker(Selection.FILE) }
        binding.selectPicOption.setOnClickListener { setResultPicker(Selection.IMAGE) }
        binding.takePicOption.setOnClickListener { setResultPicker(Selection.CAMERA) }
    }

    private fun setResultPicker(selection: Selection) {
        setFragmentResult(REQUEST_KEY, bundleOf(ARG_TYPE to selection))
        dismiss()
    }

    companion object {

        private const val REQUEST_KEY = "file_picker_result"
        private const val ARG_ACTION = "action"
        private const val ARG_TYPE = "type"

        @Keep
        private enum class Selection { FILE, IMAGE, CAMERA }

        fun newInstance(action: String) = FilePickerOptionFragment().apply {
            arguments = bundleOf(ARG_ACTION to action)
        }

        fun show(target: Fragment, action: String, listener: ChatbotActionPressedListener) {
            newInstance(action).show(target.parentFragmentManager, null)
            target.setFragmentResultListener(REQUEST_KEY) { _, bundle ->
                when (bundle.get(ARG_TYPE) as Selection) {
                    Selection.FILE -> listener.onSelectFilesPressed(action)
                    Selection.IMAGE -> listener.onSelectImagesPressed(action)
                    Selection.CAMERA -> listener.onOpenCameraPressed(action)
                }
            }
        }
    }
}
