package com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItemsAddItem

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.theme.BKSParagraphRegular
import com.insurtech.kanguro.designsystem.ui.theme.BKSParagraphRegularSDarkest
import com.insurtech.kanguro.designsystem.ui.theme.MobaSubheadRegular
import com.insurtech.kanguro.designsystem.ui.theme.NeutralMedium
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryMedium

@Composable
fun ScheduledItemTextField(
    modifier: Modifier = Modifier,
    text: String,
    keyboardOptionsOnNext: FocusRequester,
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Label()
        Spacer(modifier = Modifier.height(8.dp))
        ComponentTextField(
            text = text,
            keyboardOptionsOnNext = keyboardOptionsOnNext
        ) {
            onValueChange(it)
        }
    }
}

@Composable
private fun Label() {
    Text(
        text = stringResource(id = R.string.item_label),
        style = MobaSubheadRegular.copy(color = NeutralMedium)
    )
}

@Composable
private fun ComponentTextField(
    text: String,
    keyboardOptionsOnNext: FocusRequester,
    onValueChange: (String) -> Unit
) {
    BasicTextField(
        value = text,
        onValueChange = { onValueChange(it) },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = { keyboardOptionsOnNext.requestFocus() }
        ),
        textStyle = BKSParagraphRegularSDarkest,
        modifier = Modifier
            .border(
                width = 2.dp,
                color = SecondaryMedium,
                shape = RoundedCornerShape(size = 8.dp)
            )
            .padding(16.dp)
            .fillMaxWidth(),
        decorationBox = { innerTextField ->
            if (text.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.what_is_the_item),
                    style = BKSParagraphRegular
                )
            }
            innerTextField()
        }
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
private fun ScheduledItemTextFieldPreview() {
    var text by remember { mutableStateOf("") }

    val (focusRequester) = FocusRequester.createRefs()

    Surface {
        ScheduledItemTextField(
            modifier = Modifier.padding(16.dp),
            text = text,
            keyboardOptionsOnNext = focusRequester
        ) {
            text = it
        }
    }
}
