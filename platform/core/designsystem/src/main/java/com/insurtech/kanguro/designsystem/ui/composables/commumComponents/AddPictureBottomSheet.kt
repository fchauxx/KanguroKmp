package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

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

@Composable
fun AddPictureBottomSheet(
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
                label = stringResource(id = R.string.take_picture),
                onClick = onTakePicturePressed
            )
            ModalBottomSheetAction(
                modifier = Modifier.padding(vertical = 16.dp),
                icon = R.drawable.ic_gallery,
                label = stringResource(id = R.string.select_picture),
                onClick = onSelectFilePressed
            )
        }
    }
}

@Composable
@Preview
private fun AddPictureBottomSheetPreview() {
    AddPictureBottomSheet(
        onDismiss = {},
        onTakePicturePressed = {},
        onSelectFilePressed = {}
    )
}
