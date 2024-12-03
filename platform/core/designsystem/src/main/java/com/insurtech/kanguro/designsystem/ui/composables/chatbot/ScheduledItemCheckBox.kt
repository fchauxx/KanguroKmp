package com.insurtech.kanguro.designsystem.ui.composables.chatbot

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.toDollarFormat
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.KanguroCustomSquareCheckBox
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyBold
import com.insurtech.kanguro.designsystem.ui.theme.MobaCaptionRegular
import com.insurtech.kanguro.designsystem.ui.theme.NeutralLightest
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDark
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDarkest
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryLightest
import java.math.BigDecimal

@Composable
fun ScheduledItemCheckBox(
    modifier: Modifier = Modifier,
    item: String,
    value: BigDecimal,
    isEnabled: Boolean = true,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    KanguroCustomSquareCheckBox(
        modifier = modifier,
        isEnabled = isEnabled,
        isChecked = isChecked,
        iconSpace = 16.0,
        onCheckedChange = onCheckedChange
    ) {
        Column {
            Text(
                text = item,
                style = if (isEnabled) {
                    MobaBodyBold.copy(color = SecondaryDarkest)
                } else {
                    MobaBodyBold.copy(color = SecondaryLightest)
                }
            )

            Text(
                text = "$${value.toDollarFormat()}",
                style = if (isEnabled) {
                    MobaCaptionRegular.copy(color = SecondaryDark)
                } else {
                    MobaCaptionRegular.copy(color = NeutralLightest)
                }
            )
        }
    }
}

@Composable
@Preview
private fun ScheduledItemCheckBoxCheckedPreview() {
    Surface {
        val (checkedState, onStateChange) = remember { mutableStateOf(true) }

        ScheduledItemCheckBox(
            modifier = Modifier.padding(16.dp),
            item = "Bike",
            value = BigDecimal(500),
            isChecked = checkedState,
            onCheckedChange = onStateChange
        )
    }
}

@Composable
@Preview
private fun ScheduledItemCheckBoxCheckedDisabledPreview() {
    Surface {
        val (checkedState, onStateChange) = remember { mutableStateOf(true) }

        ScheduledItemCheckBox(
            modifier = Modifier.padding(16.dp),
            item = "Bike",
            value = BigDecimal(500),
            isEnabled = false,
            isChecked = checkedState,
            onCheckedChange = onStateChange
        )
    }
}

@Composable
@Preview
private fun ScheduledItemCheckBoxUncheckedPreview() {
    Surface {
        val (checkedState, onStateChange) = remember { mutableStateOf(false) }

        ScheduledItemCheckBox(
            modifier = Modifier.padding(16.dp),
            item = "Bike",
            value = BigDecimal(500),
            isChecked = checkedState,
            onCheckedChange = onStateChange
        )
    }
}

@Composable
@Preview
private fun ScheduledItemCheckBoxUncheckedDisabledPreview() {
    Surface {
        val (checkedState, onStateChange) = remember { mutableStateOf(false) }

        ScheduledItemCheckBox(
            modifier = Modifier.padding(16.dp),
            item = "Bike",
            value = BigDecimal(500),
            isEnabled = false,
            isChecked = checkedState,
            onCheckedChange = onStateChange
        )
    }
}
