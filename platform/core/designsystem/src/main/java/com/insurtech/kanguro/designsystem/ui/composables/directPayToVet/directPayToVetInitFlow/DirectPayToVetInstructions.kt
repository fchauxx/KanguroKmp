package com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.directPayToVetInitFlow

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R

@Composable
fun DirectPayToVetInstructions(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        DirectPayToVetInstruction(
            textId = R.string.accident_and_illness
        )

        Spacer(modifier = Modifier.height(16.dp))

        DirectPayToVetInstruction(
            textId = R.string.vet_needs_to_agree
        )

        Spacer(modifier = Modifier.height(16.dp))

        DirectPayToVetInstruction(
            textId = R.string.diagnosis_medication_treatment
        )

        Spacer(modifier = Modifier.height(16.dp))

        DirectPayToVetInstruction(
            textId = R.string.time_to_submit
        )
    }
}

@Preview
@Composable
fun DirectPayToVetInstructionsPreview() {
    Surface {
        DirectPayToVetInstructions(
            modifier = Modifier.padding(16.dp)
        )
    }
}
