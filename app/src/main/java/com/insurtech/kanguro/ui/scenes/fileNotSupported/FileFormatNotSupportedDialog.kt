package com.insurtech.kanguro.ui.scenes.fileNotSupported

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.fragment.navArgs
import com.insurtech.kanguro.core.base.BaseDialog
import com.insurtech.kanguro.databinding.FragmentComposeContainerBinding
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.dialog.FileFormatNotSupportedDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FileFormatNotSupportedDialog : BaseDialog<FragmentComposeContainerBinding>() {
    override fun onCreateBinding(inflater: LayoutInflater): FragmentComposeContainerBinding =
        FragmentComposeContainerBinding.inflate(inflater)

    private val args: FileFormatNotSupportedDialogArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
    }

    private fun setupUi() {
        binding.composeView
            .setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

        binding.composeView.setContent {
            FileFormatNotSupportedDialog(
                fileType = args.fileType
            ) {
                dismiss()
            }
        }
    }
}

@Composable
@Preview
private fun FileNotSupportedFragmentPreview() {
    FileFormatNotSupportedDialog(
        fileType = ".heic",
        onDismiss = {}
    )
}
