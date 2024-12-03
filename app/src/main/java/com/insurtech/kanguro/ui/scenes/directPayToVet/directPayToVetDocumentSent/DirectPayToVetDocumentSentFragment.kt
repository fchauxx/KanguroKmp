package com.insurtech.kanguro.ui.scenes.directPayToVet.directPayToVetDocumentSent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.viewModels
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.databinding.FragmentDirectPayToVetDocumentSentBinding
import com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.direcPayToVetDocumentSent.DirectPayToVetDocumentSentScreenContent
import com.insurtech.kanguro.ui.custom.KanguroBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DirectPayToVetDocumentSentFragment :
    KanguroBottomSheetFragment<FragmentDirectPayToVetDocumentSentBinding>() {

    override val screenName: AnalyticsEnums.Screen =
        AnalyticsEnums.Screen.DirectPayToVetDocumentSent

    override val isDraggable = false

    override val viewModel: DirectPayToVetDocumentSentViewModel by viewModels()
    override fun onCreateBinding(inflater: LayoutInflater): FragmentDirectPayToVetDocumentSentBinding =
        FragmentDirectPayToVetDocumentSentBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
    }

    private fun setupUi() {
        binding.composeView
            .setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

        binding.composeView.setContent {
            DirectPayToVetDocumentSentFragment(
                onClosePressed = ::onClosePressed,
                onOkPressed = ::onClosePressed
            )
        }
    }

    private fun onClosePressed() {
        dismiss()
    }
}

@Composable
fun DirectPayToVetDocumentSentFragment(
    modifier: Modifier = Modifier,
    onClosePressed: () -> Unit,
    onOkPressed: () -> Unit
) {
    DirectPayToVetDocumentSentScreenContent(
        modifier = modifier,
        onClosePressed = onClosePressed,
        onOkayPressed = onOkPressed
    )
}

@Composable
@Preview
fun DirectPayToVetDocumentSentFragmentPreview() {
    Surface {
        DirectPayToVetDocumentSentScreenContent(
            onClosePressed = {},
            onOkayPressed = {}
        )
    }
}
