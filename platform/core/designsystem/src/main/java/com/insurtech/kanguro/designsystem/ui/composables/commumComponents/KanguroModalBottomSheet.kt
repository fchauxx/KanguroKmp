package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KanguroModalBottomSheet(
    modifier: Modifier = Modifier,
    skipPartiallyExpanded: Boolean = false,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    val modalBottomSheetState =
        rememberModalBottomSheetState(skipPartiallyExpanded = skipPartiallyExpanded)

    ModalBottomSheet(
        modifier = modifier,
        containerColor = White,
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            content()
            Spacer(
                modifier = Modifier
                    .navigationBarsPadding()
            )
        }
    }
}

@Composable
@Preview
fun KanguroModalBottomSheetPreview() {
    Surface {
        KanguroModalBottomSheet(onDismiss = {}) {
            Text(text = "This is the modal content")
        }
    }
}
