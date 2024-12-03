package com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.directPayToVetInitFlow

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.horizontalPayToVetPaddingScreen
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.KanguroButton
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.KeyboardAsState
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.MonetaryTextField
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.Keyboard
import com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.DirectPayToVetTitleComponent
import com.insurtech.kanguro.designsystem.ui.theme.MobaCaptionRegularSDark
import com.insurtech.kanguro.designsystem.ui.theme.MobaHeadline

@Composable
fun DirectPayToVetInitFlowScreenContent(
    modifier: Modifier = Modifier,
    onClosePressed: () -> Unit,
    price: String,
    onPriceChange: (String) -> Unit,
    onNextPressed: () -> Unit,
    isNextEnable: Boolean
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 16.dp, end = 16.dp)

    ) {
        DirectPayToVetTitleComponent(
            modifier = Modifier.padding(start = 15.dp),
            onClosePressed = onClosePressed
        )

        Column(
            modifier = Modifier
                .horizontalPayToVetPaddingScreen()
                .padding(top = 40.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            MiddleScreen()

            Footer(
                price = price,
                onPriceChange = onPriceChange,
                modifier = Modifier.weight(1f),
                onNextPressed = onNextPressed,
                isNextEnable = isNextEnable
            )
        }
    }
}

@Composable
private fun MiddleScreen() {
    Text(
        text = stringResource(id = R.string.before_get_start),
        style = MobaHeadline
    )

    Spacer(modifier = Modifier.height(24.dp))

    DirectPayToVetInstructions()

    Spacer(modifier = Modifier.height(40.dp))
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun Footer(
    price: String,
    isNextEnable: Boolean,
    onPriceChange: (String) -> Unit,
    modifier: Modifier,
    onNextPressed: () -> Unit
) {
    val (focusRequester) = FocusRequester.createRefs()

    val keyboardState by KeyboardAsState()

    MonetaryTextField(
        value = price,
        title = { PriceLabel() },
        keyboardOptionsOnNext = focusRequester,
        onValueChange = onPriceChange
    )

    Spacer(
        modifier = modifier
    )

    if (keyboardState == Keyboard.Closed) {
        KanguroButton(
            text = stringResource(id = R.string.next),
            enabled = isNextEnable,
            onClick = onNextPressed,
            modifier = Modifier.padding(top = 40.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun PriceLabel() {
    Text(
        text = stringResource(id = R.string.claim_value),
        style = MobaCaptionRegularSDark
    )
}

@Preview
@Composable
private fun DirectPayToVetInitFlowScreenContentPreview() {
    Surface {
        DirectPayToVetInitFlowScreenContent(
            onClosePressed = {},
            price = "",
            onPriceChange = {},
            onNextPressed = {},
            isNextEnable = false
        )
    }
}
