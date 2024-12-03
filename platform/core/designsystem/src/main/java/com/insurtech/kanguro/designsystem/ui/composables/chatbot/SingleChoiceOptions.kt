package com.insurtech.kanguro.designsystem.ui.composables.chatbot

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.ui.composables.chatbot.model.ChatbotOptionsUi
import com.insurtech.kanguro.designsystem.ui.composables.chatbot.model.ChatbotStylesUi
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.HorizontalDivider
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyBold
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyItalic
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyRegular
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground
import com.insurtech.kanguro.designsystem.ui.theme.NeutralLightest

@Composable
fun SingleChoiceOptions(
    modifier: Modifier = Modifier,
    options: List<ChatbotOptionsUi>,
    onOptionSelected: (ChatbotOptionsUi) -> Unit
) {
    Column(modifier = modifier) {
        HorizontalDivider()
        if (options.size > DOUBLE_OPTIONS) {
            VerticalComponent(options = options, onOptionSelected = onOptionSelected)
        } else {
            HorizontalComponent(options = options, onOptionSelected = onOptionSelected)
        }
    }
}

@Composable
private fun HorizontalComponent(
    options: List<ChatbotOptionsUi>,
    onOptionSelected: (ChatbotOptionsUi) -> Unit
) {
    Row(
        modifier = Modifier.height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.Center
    ) {
        options.forEachIndexed { index, actionOption ->
            OptionComponent(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                option = actionOption,
                onOptionSelected = onOptionSelected
            )
            if (index < options.size - 1) {
                Divider(
                    color = NeutralLightest,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(0.5.dp)
                )
            }
        }
    }
}

@Composable
private fun VerticalComponent(
    options: List<ChatbotOptionsUi>,
    onOptionSelected: (ChatbotOptionsUi) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        options.forEachIndexed { index, actionOption ->
            OptionComponent(
                modifier = Modifier.fillMaxWidth(),
                option = actionOption,
                onOptionSelected = onOptionSelected
            )
            if (index < options.size - 1) {
                HorizontalDivider()
            }
        }
    }
}

@Composable
private fun OptionComponent(
    modifier: Modifier = Modifier,
    option: ChatbotOptionsUi,
    onOptionSelected: (ChatbotOptionsUi) -> Unit
) {
    Box(
        modifier = modifier
            .background(color = NeutralBackground)
            .clickable { onOptionSelected(option) }
    ) {
        val style = when (option.style) {
            ChatbotStylesUi.BOLD -> MobaBodyBold
            ChatbotStylesUi.ITALIC -> MobaBodyItalic
            else -> MobaBodyRegular
        }
        Text(
            text = option.label.orEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 22.dp),
            style = style.copy(textAlign = TextAlign.Center)
        )
    }
}

@Composable
@Preview
private fun SingleChoiceOptionsHorizontalPreview() {
    Surface {
        SingleChoiceOptions(
            modifier = Modifier.padding(16.dp),
            options = listOf(
                ChatbotOptionsUi(
                    id = "1",
                    label = "Option 1",
                    style = ChatbotStylesUi.ITALIC
                ),
                ChatbotOptionsUi(
                    id = "2",
                    label = "Option 2",
                    style = ChatbotStylesUi.BOLD
                )
            ),
            onOptionSelected = {}
        )
    }
}

@Composable
@Preview
private fun SingleChoiceOptionsVerticalPreview() {
    Surface {
        SingleChoiceOptions(
            modifier = Modifier.padding(16.dp),
            options = listOf(
                ChatbotOptionsUi(
                    id = "1",
                    label = "Option 1",
                    style = ChatbotStylesUi.ITALIC
                ),
                ChatbotOptionsUi(
                    id = "2",
                    label = "Option 2",
                    style = ChatbotStylesUi.BOLD
                ),
                ChatbotOptionsUi(
                    id = "3",
                    label = "Option 3",
                    style = null
                )
            ),
            onOptionSelected = {}
        )
    }
}

@Composable
@Preview
private fun OptionComponentPreview() {
    Surface {
        OptionComponent(
            modifier = Modifier.padding(16.dp),
            option = ChatbotOptionsUi(
                id = "1",
                label = "Option 1",
                style = null
            ),
            onOptionSelected = {}
        )
    }
}

private const val DOUBLE_OPTIONS = 2
