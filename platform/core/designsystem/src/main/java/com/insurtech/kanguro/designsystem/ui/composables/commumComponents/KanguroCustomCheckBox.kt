package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.theme.MobaCaptionRegular
import com.insurtech.kanguro.designsystem.ui.theme.MobaCaptionRegularSDark
import com.insurtech.kanguro.designsystem.ui.theme.NeutralLightest

@Composable
fun KanguroCustomCheckBox(
    modifier: Modifier = Modifier,
    label: String,
    isEnabled: Boolean = true,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val modifier = if (isEnabled) {
        modifier.then(
            Modifier.clickable { onCheckedChange(!isChecked) }
        )
    } else {
        modifier
    }

    val checkedIcon = if (isEnabled) R.drawable.ic_radio_checked else R.drawable.ic_radio_checked_disabled
    val uncheckedIcon = if (isEnabled) R.drawable.ic_radio_unchecked else R.drawable.ic_radio_unchecked_disabled

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Crossfade(
            targetState = isChecked,
            label = ""
        ) { checked ->
            Image(
                painter = painterResource(
                    id = if (checked) checkedIcon else uncheckedIcon
                ),
                contentDescription = null
            )
        }
        Text(
            text = label,
            style = if (isEnabled) MobaCaptionRegularSDark else MobaCaptionRegular.copy(color = NeutralLightest)
        )
    }
}

@Composable
@Preview
private fun KanguroCustomCheckBoxCheckedEnabledPreview() {
    Surface {
        KanguroCustomCheckBox(
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
        KanguroCustomCheckBox(
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
        KanguroCustomCheckBox(
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
        KanguroCustomCheckBox(
            modifier = Modifier.padding(16.dp),
            label = "Unchecked Disabled",
            isEnabled = false,
            isChecked = false,
            onCheckedChange = {}
        )
    }
}
