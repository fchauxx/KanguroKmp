package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.theme.Heading6ExtraBold
import com.insurtech.kanguro.designsystem.ui.theme.NeutralLightest

@Composable
fun KanguroCustomFeedbackCheckBox(
    modifier: Modifier = Modifier,
    label: String,
    isEnabled: Boolean = true,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val localModifier = if (isEnabled) {
        modifier.then(
            Modifier.clickable { onCheckedChange(!isChecked) }
        )
    } else {
        modifier
    }

    val checkedIcon = if (isEnabled) R.drawable.ic_radio_checked_dark else R.drawable.ic_radio_checked_disabled
    val uncheckedIcon = if (isEnabled) R.drawable.ic_radio_unchecked_dark else R.drawable.ic_radio_unchecked_disabled

    Column(
        modifier = localModifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Crossfade(
            targetState = isChecked,
            label = ""
        ) { checked ->
            Image(
                modifier = Modifier.size(36.dp),
                painter = painterResource(
                    id = if (checked) checkedIcon else uncheckedIcon
                ),
                contentDescription = null
            )
        }
        Text(
            text = label,
            style = if (isEnabled) {
                Heading6ExtraBold
            } else {
                Heading6ExtraBold.copy(color = NeutralLightest)
            }
        )
    }
}

@Composable
@Preview
private fun KanguroCustomCheckBoxCheckedEnabledPreview() {
    Surface {
        KanguroCustomFeedbackCheckBox(
            modifier = Modifier.padding(16.dp),
            label = "Checked Enabled",
            isChecked = true,
            onCheckedChange = {}
        )
    }
}

@Composable
@Preview
private fun KanguroCustomCheckBoxCheckedDisabledPreview() {
    Surface {
        KanguroCustomFeedbackCheckBox(
            modifier = Modifier.padding(16.dp),
            label = "Checked Disabled",
            isEnabled = false,
            isChecked = true,
            onCheckedChange = {}
        )
    }
}

@Composable
@Preview
private fun KanguroCustomCheckBoxUncheckedEnabledPreview() {
    Surface {
        KanguroCustomFeedbackCheckBox(
            modifier = Modifier.padding(16.dp),
            label = "Unchecked Enabled",
            isChecked = false,
            onCheckedChange = {}
        )
    }
}

@Composable
@Preview
private fun KanguroCustomCheckBoxUncheckedDisabledPreview() {
    Surface {
        KanguroCustomFeedbackCheckBox(
            modifier = Modifier.padding(16.dp),
            label = "Unchecked Disabled",
            isEnabled = false,
            isChecked = false,
            onCheckedChange = {}
        )
    }
}
