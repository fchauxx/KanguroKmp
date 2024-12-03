package com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.directPayToVetForm

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.KanguroModalBottomSheet
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.ModalBottomSheetAction
import com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.domain.PetInformation
import com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.domain.PetType

@Composable
fun DirectPayToVetFormPetSelector(
    onDismiss: () -> Unit,
    list: List<PetInformation>,
    onPetSelected: (PetInformation) -> Unit
) {
    KanguroModalBottomSheet(onDismiss = onDismiss) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(list) { pet ->
                ModalBottomSheetAction(
                    modifier = Modifier.padding(vertical = 16.dp),
                    icon = pet.type.icon,
                    label = pet.name,
                    onClick = {
                        onPetSelected(pet)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun DirectPayToVetFormPetSelectorPreview() {
    val context = LocalContext.current

    DirectPayToVetFormPetSelector(
        onDismiss = {},
        list = listOf(
            PetInformation(1L, "Oliver", PetType.dog),
            PetInformation(2L, "Mimi", PetType.cat)
        ),
        onPetSelected = {
            Toast.makeText(context, it.name, Toast.LENGTH_SHORT).show()
        }
    )
}
