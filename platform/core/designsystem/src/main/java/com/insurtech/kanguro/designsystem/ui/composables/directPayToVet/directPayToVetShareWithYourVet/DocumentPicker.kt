package com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.directPayToVetShareWithYourVet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.KanguroModalBottomSheet
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.ModalBottomSheetAction

@Composable
fun DocumentPicker(
    onDismiss: () -> Unit,
    onTakePicturePressed: () -> Unit,
    onSelectFilePressed: () -> Unit
) {
    KanguroModalBottomSheet(onDismiss = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ModalBottomSheetAction(
                modifier = Modifier.padding(vertical = 16.dp),
                icon = R.drawable.ic_camera,
                label = stringResource(id = R.string.scan_document),
                onClick = onTakePicturePressed
            )
            ModalBottomSheetAction(
                modifier = Modifier.padding(vertical = 16.dp),
                icon = R.drawable.ic_document_text,
                label = stringResource(id = R.string.select_single_file),
                onClick = onSelectFilePressed
            )
        }
    }
}

@Composable
@Preview
fun DocumentPickerPreview() {
    DocumentPicker(
        onDismiss = {},
        onTakePicturePressed = {},
        onSelectFilePressed = {}
    )
}
