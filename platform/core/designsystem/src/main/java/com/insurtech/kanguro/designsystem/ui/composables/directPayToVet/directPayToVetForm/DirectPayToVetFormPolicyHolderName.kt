package com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.directPayToVetForm

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R

@Composable
fun DirectPayToVetFormPolicyHolderName(
    modifier: Modifier = Modifier,
    policyName: String
) {
    DirectPayToVetFormFieldHolder(
        modifier = modifier,
        title = stringResource(id = R.string.policy_holder_ame),
        value = policyName
    )
}

@Preview
@Composable
fun DirectPayToVetFormPolicyHolderNamePreview() {
    Surface {
        DirectPayToVetFormPolicyHolderName(
            modifier = Modifier.padding(16.dp),
            policyName = "Lauren Ipsum"
        )
    }
}
