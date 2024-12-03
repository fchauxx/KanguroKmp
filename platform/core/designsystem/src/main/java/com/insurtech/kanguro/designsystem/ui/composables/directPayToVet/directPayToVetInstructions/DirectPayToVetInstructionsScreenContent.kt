package com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.directPayToVetInstructions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.KanguroButton
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.StyledCustomizableText
import com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.DirectPayToVetTitleComponent
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyRegular
import com.insurtech.kanguro.designsystem.ui.theme.MobaHeadline
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDark
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDarkest

@Composable
fun DirectPayToVetInstructionsScreenContent(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    onClosePressed: () -> Unit,
    onNextPressed: () -> Unit
) {
    Column(modifier = modifier.padding(16.dp)) {
        DirectPayToVetTitleComponent(
            onBackButtonPressed = onBackPressed,
            onClosePressed = onClosePressed
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(top = 32.dp)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            StepsSection()

            NotesSection()

            KanguroButton(
                modifier = Modifier.padding(top = 32.dp),
                text = stringResource(id = R.string.next),
                enabled = true,
                onClick = onNextPressed
            )
        }
    }
}

@Composable
private fun StepsSection() {
    Text(
        text = stringResource(id = R.string.direct_pay_to_vet_getting_started),
        style = MobaHeadline
    )

    DirectPayToVetStepsComponent(
        modifier = Modifier
            .padding(top = 24.dp)
            .offset(x = (-4).dp)
    )
}

@Composable
private fun NotesSection() {
    Text(
        modifier = Modifier.padding(top = 32.dp),
        text = stringResource(id = R.string.direct_pay_to_vet_please_note),
        style = MobaHeadline
    )

    Note(
        text = stringResource(id = R.string.direct_pay_to_vet_please_note_1),
        styledText = stringResource(id = R.string.direct_pay_to_vet_please_note_1_highlight)
    )

    Note(
        text = stringResource(id = R.string.direct_pay_to_vet_please_note_2),
        styledText = stringResource(id = R.string.direct_pay_to_vet_please_note_2_highlight)
    )
}

@Composable
private fun Note(text: String, styledText: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(top = 6.dp)
                .padding(horizontal = 8.dp)
                .size(8.dp)
                .background(
                    color = SecondaryDarkest,
                    shape = CircleShape
                )
        )
        StyledCustomizableText(
            text = text,
            styledText = styledText,
            baseStyle = MobaBodyRegular.copy(color = SecondaryDark),
            highlightColor = SecondaryDark
        )
    }
}

@Composable
@Preview
private fun DirectPayToVetInstructionsScreenContentPreview() {
    Surface {
        DirectPayToVetInstructionsScreenContent(
            onBackPressed = {},
            onClosePressed = {},
            onNextPressed = {}
        )
    }
}
