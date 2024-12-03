package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.theme.MobaCaptionRegular
import com.insurtech.kanguro.designsystem.ui.theme.MobaCaptionRegularSDark
import com.insurtech.kanguro.designsystem.ui.theme.NeutralLightest

@Composable
fun KanguroCustomSquareCheckBox(
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    isChecked: Boolean,
    iconSpace: Double = 8.0,
    onCheckedChange: (Boolean) -> Unit,
    labelContent: @Composable () -> Unit
) {
    val updatedModifier = if (isEnabled) {
        modifier.then(
            Modifier.toggleable(
                value = isChecked,
                onValueChange = { onCheckedChange(!isChecked) },
                role = Role.Checkbox
            )
        )
    } else {
        modifier
    }

    val checkedIcon =
        if (isEnabled) R.drawable.ic_square_checked else R.drawable.ic_square_checked_disabled
    val uncheckedIcon =
        if (isEnabled) R.drawable.ic_square_unchecked else R.drawable.ic_square_unchecked_disabled

    Row(
        modifier = updatedModifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(iconSpace.dp)
    ) {
        Crossfade(
            targetState = isChecked,
            label = ""
        ) { checked ->
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(
                    id = if (checked) checkedIcon else uncheckedIcon
                ),
                contentDescription = null
            )
        }
        labelContent()
    }
}

@Composable
@Preview
private fun KanguroCustomCheckBoxCheckedEnabledPreview() {
    Surface {
        KanguroCustomSquareCheckBox(
            modifier = Modifier.padding(16.dp),
            labelContent = {
                Text(
                    text = "Checked Enabled",
                    style = MobaCaptionRegularSDark
                )
            },
            isChecked = true,
            onCheckedChange = {}
        )
    }
}

@Composable
@Preview
private fun KanguroCustomCheckBoxCheckedDisabledPreview() {
    Surface {
        KanguroCustomSquareCheckBox(
            modifier = Modifier.padding(16.dp),
            labelContent = {
                Text(
                    text = "Checked Disabled",
                    style = MobaCaptionRegular.copy(color = NeutralLightest)
                )
            },
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
        KanguroCustomSquareCheckBox(
            modifier = Modifier.padding(16.dp),
            labelContent = {
                Text(
                    text = "Unchecked Enabled",
                    style = MobaCaptionRegularSDark
                )
            },
            isChecked = false,
            onCheckedChange = {}
        )
    }
}

@Composable
@Preview
private fun KanguroCustomCheckBoxUncheckedDisabledPreview() {
    Surface {
        KanguroCustomSquareCheckBox(
            modifier = Modifier.padding(16.dp),
            labelContent = {
                Text(
                    text = "Unchecked Disabled",
                    style = MobaCaptionRegular.copy(color = NeutralLightest)
                )
            },
            isEnabled = false,
            isChecked = false,
            onCheckedChange = {}
        )
    }
}
