package com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.directPayToVetForm

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyRegularNeutralLightest
import com.insurtech.kanguro.designsystem.ui.theme.MobaCaptionRegularNeutralLightest
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground
import com.insurtech.kanguro.designsystem.ui.theme.NeutralLightest

@Composable
fun DirectPayToVetFormFieldHolder(
    modifier: Modifier = Modifier,
    title: String,
    value: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            style = MobaCaptionRegularNeutralLightest
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .border(width = 1.dp, color = NeutralLightest, shape = RoundedCornerShape(8.dp))
                .background(color = NeutralBackground, shape = RoundedCornerShape(8.dp))
                .fillMaxWidth()
        ) {
            Text(
                text = value,
                style = MobaBodyRegularNeutralLightest,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Preview
@Composable
fun DirectPayToVetFormFieldHolderPreview() {
    Surface {
        DirectPayToVetFormFieldHolder(
            modifier = Modifier.padding(16.dp),
            title = "Vet name",
            value = "Dr. House"
        )
    }
}
