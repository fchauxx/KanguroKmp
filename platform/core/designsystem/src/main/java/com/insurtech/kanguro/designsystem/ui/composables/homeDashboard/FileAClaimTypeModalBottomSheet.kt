package com.insurtech.kanguro.designsystem.ui.composables.homeDashboard

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
fun FileAClaimTypeModalBottomSheet(
    modalBottomSheet: Boolean,
    showRentersOption: Boolean = true,
    showPetOption: Boolean = true,
    onDismiss: () -> Unit,
    onPetFileAClaim: () -> Unit,
    onRentersFileAClaim: () -> Unit
) {
    if (modalBottomSheet) {
        KanguroModalBottomSheet(onDismiss = onDismiss) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (showPetOption) {
                    ModalBottomSheetAction(
                        modifier = Modifier.padding(vertical = 16.dp),
                        icon = R.drawable.ic_pet_dog,
                        label = stringResource(id = R.string.pet_insurance_claim),
                        onClick = onPetFileAClaim
                    )
                }

                if (showRentersOption) {
                    ModalBottomSheetAction(
                        modifier = Modifier.padding(vertical = 16.dp),
                        icon = R.drawable.ic_home_2,
                        label = stringResource(id = R.string.renters_insurance_claim),
                        onClick = onRentersFileAClaim
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun FileAClaimTypeModalBottomSheetPreview() {
    FileAClaimTypeModalBottomSheet(
        modalBottomSheet = true,
        onDismiss = {},
        onPetFileAClaim = {},
        onRentersFileAClaim = {}
    )
}
