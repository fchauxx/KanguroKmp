package com.insurtech.kanguro.ui.scenes.accessFilesPermission

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import com.insurtech.kanguro.core.base.BaseDialog
import com.insurtech.kanguro.databinding.FragmentComposeContainerBinding
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.dialog.AccessFilesPermissionDialog

class AccessFilesPermissionDialog : BaseDialog<FragmentComposeContainerBinding>() {

    override fun onCreateBinding(inflater: LayoutInflater): FragmentComposeContainerBinding =
        FragmentComposeContainerBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
    }

    private fun setupUi() {
        binding.composeView
            .setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

        binding.composeView.setContent {
            AccessFilesPermissionDialog {
                dismiss()
            }
        }
    }
}

@Composable
@Preview
private fun FileNotSupportedFragmentPreview() {
    AccessFilesPermissionDialog {
    }
}
