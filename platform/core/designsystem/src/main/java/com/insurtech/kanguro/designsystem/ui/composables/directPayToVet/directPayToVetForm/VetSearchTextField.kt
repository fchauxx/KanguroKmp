package com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.directPayToVetForm

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.outlinedField
import com.insurtech.kanguro.designsystem.outlinedFieldNegativeMedium
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyRegular
import com.insurtech.kanguro.designsystem.ui.theme.MobaCaptionRegular
import com.insurtech.kanguro.designsystem.ui.theme.MobaSubheadRegularNegativeMedium
import com.insurtech.kanguro.designsystem.ui.theme.NeutralLightest
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDark

@Composable
fun VetSearchTextField(
    modifier: Modifier = Modifier,
    initialSearchText: String = "",
    onTextChanged: (String) -> Unit,
    isVetEmailValid: Boolean
) {
    var searchText by remember {
        mutableStateOf(initialSearchText)
    }

    val border = if (isVetEmailValid || initialSearchText.isBlank()) {
        Modifier.outlinedField()
    } else {
        Modifier.outlinedFieldNegativeMedium()
    }

    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.find_your_vet),
            style = MobaCaptionRegular.copy(color = SecondaryDark)
        )

        Spacer(modifier = Modifier.height(8.dp))

        var isFocused by remember { mutableStateOf(false) }

        BasicTextField(
            value = searchText,
            onValueChange = {
                onTextChanged(it)
                searchText = it
            },
            textStyle = MobaBodyRegular,
            modifier = border
                .fillMaxWidth()
                .onFocusEvent {
                    isFocused = it.isFocused
                },
            singleLine = true,
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        if (searchText.isEmpty() && !isFocused) {
                            Text(
                                text = stringResource(id = R.string.your_vet_email),
                                style = MobaBodyRegular.copy(color = NeutralLightest)
                            )
                        } else {
                            innerTextField()
                        }
                    }

                    if (isVetEmailValid || initialSearchText.isBlank()) {
                        Image(
                            modifier = Modifier
                                .padding(start = 8.dp),
                            painter = painterResource(id = R.drawable.ic_search_normal),
                            contentDescription = null
                        )
                    }
                }
            }
        )
        if (!isVetEmailValid && initialSearchText.isNotBlank()) {
            ErrorLabel()
        }
    }
}

@Composable
private fun ErrorLabel() {
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = stringResource(id = R.string.enter_valid_email),
        style = MobaSubheadRegularNegativeMedium
    )
}

@Composable
@Preview
fun VetSearchTextFieldPreview() {
    Surface {
        VetSearchTextField(
            modifier = Modifier.padding(16.dp),
            onTextChanged = {},
            isVetEmailValid = true
        )
    }
}
