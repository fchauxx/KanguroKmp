package com.insurtech.kanguro.designsystem.ui.composables.chatbot

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyRegular
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground
import com.insurtech.kanguro.designsystem.ui.theme.NeutralLightest
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryMedium

@Composable
fun TextInput(
    modifier: Modifier = Modifier,
    value: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onTextChange: (String) -> Unit,
    onSubmit: (String) -> Unit
) {
    Box(
        modifier = modifier
            .background(color = NeutralBackground)
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    border = BorderStroke(1.dp, NeutralLightest),
                    shape = RoundedCornerShape(16.dp)
                )
                .clip(shape = RoundedCornerShape(16.dp))
                .background(color = NeutralBackground)
                .height(36.dp),
            value = value,
            keyboardActions = KeyboardActions(onDone = {
                if (value.isNotBlank()) {
                    onSubmit(value)
                }
            }),
            keyboardOptions = keyboardOptions.copy(imeAction = ImeAction.Done),
            textStyle = MobaBodyRegular,
            onValueChange = { onTextChange(it) },
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .background(color = Color.White)
                        .padding(vertical = 4.dp)
                        .padding(start = 16.dp, end = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box {
                        if (value.isEmpty()) {
                            Text(
                                text = stringResource(id = R.string.text_message_hint),
                                style = MobaBodyRegular.copy(color = NeutralLightest)
                            )
                        }
                        innerTextField()
                    }
                    IconButton(
                        modifier = Modifier
                            .size(26.dp)
                            .padding(start = 4.dp),
                        onClick = { onSubmit(value) },
                        enabled = value.isNotBlank()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_send),
                            contentDescription = stringResource(
                                id = R.string.send
                            ),
                            colorFilter = ColorFilter.tint(color = if (value.isNotBlank()) SecondaryMedium else NeutralLightest)
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun TextInputStateful(
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onSubmit: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }

    TextInput(
        modifier = modifier,
        value = text,
        keyboardOptions = keyboardOptions,
        onTextChange = { text = it },
        onSubmit = onSubmit
    )
}

@Composable
@Preview
private fun TextInputPreview() {
    Surface {
        TextInput(
            modifier = Modifier.padding(16.dp),
            value = "User input",
            onTextChange = {},
            onSubmit = {}
        )
    }
}

@Composable
@Preview
private fun TextInputStatefulPreview() {
    Surface {
        TextInputStateful(
            modifier = Modifier.padding(16.dp),
            onSubmit = {}
        )
    }
}
