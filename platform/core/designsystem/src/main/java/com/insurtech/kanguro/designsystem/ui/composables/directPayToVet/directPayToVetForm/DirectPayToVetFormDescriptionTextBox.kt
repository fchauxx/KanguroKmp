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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.outlinedField
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyRegular
import com.insurtech.kanguro.designsystem.ui.theme.MobaCaptionRegular
import com.insurtech.kanguro.designsystem.ui.theme.NeutralLightest
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDark

@Composable
fun DirectPayToVetFormDescriptionTextBox(
    modifier: Modifier = Modifier,
    description: String = "",
    onDescriptionChanged: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.description),
            style = MobaCaptionRegular.copy(color = SecondaryDark)
        )

        Spacer(modifier = Modifier.height(8.dp))

        var isFocused by remember { mutableStateOf(false) }

        BasicTextField(
            value = description,
            onValueChange = { onDescriptionChanged(it) },
            textStyle = MobaBodyRegular,
            modifier = Modifier
                .outlinedField()
                .fillMaxWidth()
                .height(110.dp)
                .onFocusEvent {
                    isFocused = it.isFocused
                },
            decorationBox = { innerTextField ->
                Box {
                    innerTextField()

                    if (description.isEmpty() && !isFocused) {
                        Text(
                            text = stringResource(id = R.string.about_your_claim),
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
fun DirectPayToVetFormDescriptionTextBoxPreview() {
    Surface {
        DirectPayToVetFormDescriptionTextBox(
            modifier = Modifier.padding(16.dp),
            description = "Lorem ipsum dolor sit amet.",
            onDescriptionChanged = {}
        )
    }
}

@Composable
@Preview
fun DirectPayToVetFormDescriptionTextBoxEmptyPreview() {
    Surface {
        var description by remember { mutableStateOf("") }

        DirectPayToVetFormDescriptionTextBox(
            modifier = Modifier.padding(16.dp),
            description = description,
            onDescriptionChanged = { description = it }
        )
    }
}

@Composable
@Preview(locale = "es")
fun DirectPayToVetFormDescriptionTextBoxEmptyLocalizedPreview() {
    Surface {
        var description by remember { mutableStateOf("") }

        DirectPayToVetFormDescriptionTextBox(
            modifier = Modifier.padding(16.dp),
            description = description,
            onDescriptionChanged = { description = it }
        )
    }
}
