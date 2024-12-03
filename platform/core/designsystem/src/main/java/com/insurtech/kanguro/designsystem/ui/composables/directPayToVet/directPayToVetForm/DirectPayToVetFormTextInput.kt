package com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.directPayToVetForm

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.outlinedField
import com.insurtech.kanguro.designsystem.outlinedFieldDisabled
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyRegular
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyRegularNeutralLightest
import com.insurtech.kanguro.designsystem.ui.theme.MobaCaptionRegular
import com.insurtech.kanguro.designsystem.ui.theme.MobaCaptionRegularNeutralLightest
import com.insurtech.kanguro.designsystem.ui.theme.NeutralLightest
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDark

@Composable
fun DirectPayToVetFormTextInput(
    modifier: Modifier = Modifier,
    title: String = "",
    placeHolderText: String = "",
    value: String = "",
    isEnabled: Boolean = true,
    onValueChanged: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
    ) {
        Text(
            text = title,
            style = if (isEnabled) {
                MobaCaptionRegular.copy(color = SecondaryDark)
            } else {
                MobaCaptionRegularNeutralLightest
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        var isFocused by remember { mutableStateOf(false) }

        val boxModifier = if (isEnabled) {
            Modifier.outlinedField()
        } else {
            Modifier.outlinedFieldDisabled()
        }

        BasicTextField(
            value = value,
            enabled = isEnabled,
            onValueChange = { onValueChanged(it) },
            textStyle = if (isEnabled) {
                MobaBodyRegular
            } else {
                MobaBodyRegularNeutralLightest
            },
            modifier = boxModifier
                .fillMaxWidth()
                .onFocusEvent {
                    isFocused = it.isFocused
                },
            decorationBox = { innerTextField ->
                Box {
                    innerTextField()

                    if (value.isEmpty() && !isFocused) {
                        Text(
                            text = placeHolderText,
                            style = MobaBodyRegular.copy(color = NeutralLightest)
                        )
                    }
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            )
        )
    }
}

@Composable
@Preview
fun DirectPayToVetFormTextInputPreview() {
    Surface {
        DirectPayToVetFormTextInput(
            modifier = Modifier.padding(16.dp),
            title = "Vet name",
            value = "Love Pet",
            onValueChanged = {}
        )
    }
}

@Composable
@Preview
fun DirectPayToVetFormTextInputDisabledPreview() {
    Surface {
        DirectPayToVetFormTextInput(
            modifier = Modifier.padding(16.dp),
            title = "Vet name",
            value = "Love Pet",
            isEnabled = false,
            onValueChanged = {}
        )
    }
}

@Composable
@Preview
fun DirectPayToVetFormTextInputEmptyPreview() {
    Surface {
        var value by remember { mutableStateOf("") }

        DirectPayToVetFormTextInput(
            modifier = Modifier.padding(16.dp),
            title = "Vet name",
            placeHolderText = "Your vet name",
            value = value,
            onValueChanged = { value = it }
        )
    }
}
