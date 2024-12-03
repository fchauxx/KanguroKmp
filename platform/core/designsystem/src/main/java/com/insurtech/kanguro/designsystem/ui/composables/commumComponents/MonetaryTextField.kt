package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.toDollarFormat
import com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItemsAddItem.domain.MAX_AVAILABLE_CHARACTERS
import com.insurtech.kanguro.designsystem.ui.theme.BKSParagraphRegular
import com.insurtech.kanguro.designsystem.ui.theme.BKSParagraphRegularSDarkest
import com.insurtech.kanguro.designsystem.ui.theme.MobaSubheadRegular
import com.insurtech.kanguro.designsystem.ui.theme.NeutralMedium
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryMedium

@Composable
fun MonetaryTextField(
    modifier: Modifier = Modifier,
    value: String,
    keyboardOptionsOnNext: FocusRequester,
    title: @Composable () -> Unit,
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        title()
        Spacer(modifier = Modifier.height(8.dp))
        ComponentTextField(
            text = value,
            keyboardOptionsOnNext = keyboardOptionsOnNext
        ) {
            onValueChange(it)
        }
    }
}

@Composable
private fun ComponentTextField(
    text: String,
    keyboardOptionsOnNext: FocusRequester,
    onValueChange: (String) -> Unit
) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue(text)) }

    BasicTextField(
        value = textFieldValue,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        onValueChange = {
            if (it.text.filter { it.isDigit() }.length < MAX_AVAILABLE_CHARACTERS) {
                textFieldValue =
                    handleInputChange(it)
                onValueChange(textFieldValue.text)
            }
        },
        textStyle = BKSParagraphRegularSDarkest,
        modifier = Modifier
            .border(
                width = 2.dp,
                color = SecondaryMedium,
                shape = RoundedCornerShape(size = 8.dp)
            )
            .padding(16.dp)
            .fillMaxWidth()
            .focusRequester(keyboardOptionsOnNext),
        decorationBox = { innerTextField ->
            TextField(text = text, innerTextField = innerTextField)
        }
    )
}

@Composable
private fun TextField(text: String, innerTextField: @Composable () -> Unit) {
    Row {
        if (text.isEmpty()) {
            Text(
                text = stringResource(id = R.string.dollar),
                style = BKSParagraphRegular
            )
        } else {
            Text(
                text = stringResource(id = R.string.dollar),
                style = BKSParagraphRegularSDarkest
            )
        }
        Spacer(modifier = Modifier.width(5.dp))
        innerTextField()
    }
}

private fun handleInputChange(newValue: TextFieldValue): TextFieldValue {
    val isValid = newValue.text.filter { it.isDigit() }.trimStart('0').isNotEmpty()

    return if (isValid) {
        val formatted = newValue.text.toDollarFormat()
        TextFieldValue(formatted, TextRange(formatted.length))
    } else {
        TextFieldValue("")
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
private fun MonetaryTextFieldPreview() {
    var text by remember { mutableStateOf("") }

    val (focusRequester) = FocusRequester.createRefs()

    Surface {
        MonetaryTextField(
            modifier = Modifier.padding(16.dp),
            value = text,
            title = {
                Text(
                    text = stringResource(id = R.string.item_value),
                    style = MobaSubheadRegular.copy(color = NeutralMedium)
                )
            },
            keyboardOptionsOnNext = focusRequester
        ) {
            text = it
        }
    }
}
