package com.insurtech.kanguro.designsystem.ui.composables.liveVet.sections

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.em
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.StepsList
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.StepModel
import com.insurtech.kanguro.designsystem.ui.composables.liveVet.model.LiveVetEvent
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyRegular
import com.insurtech.kanguro.designsystem.ui.theme.PositiveDarkest
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDark
import com.insurtech.kanguro.designsystem.ui.theme.TertiaryDarkest
import com.insurtech.kanguro.designsystem.ui.theme.spacingNano
import com.insurtech.kanguro.domain.model.AirvetUserDetails
import kotlinx.collections.immutable.toImmutableList

@Composable
fun LiveVetStepsSection(
    modifier: Modifier = Modifier,
    isCopyPressed: Boolean,
    onEvent: (LiveVetEvent) -> Unit,
    userId: String
) {
    StepsList(
        modifier = modifier,
        stepList = getStepsList(
            userId = userId,
            isCopyPressed = isCopyPressed,
            onEvent
        ).toImmutableList(),
        spacedBy = spacingNano
    )
}

@Composable
private fun getStepsList(
    userId: String,
    isCopyPressed: Boolean,
    onEvent: (LiveVetEvent) -> Unit
): List<StepModel> = listOf(
    StepModel.CustomInstruction(
        instruction = {
            StepOneInstruction {
                onEvent(
                    LiveVetEvent.OnDownloadPressed(
                        AirvetUserDetails("", "", "", 0)
                    )
                )
            }
        },
        icon = R.drawable.ic_live_vet_step_1
    ),
    StepModel.CustomInstruction(
        instruction = {
            StepTwoInstruction(
                userId = userId,
                isCopyPressed = isCopyPressed,
                onCopyPressed = { }
            )
        },
        icon = R.drawable.ic_live_vet_step_2
    ),
    StepModel.Basic(
        instruction = stepThreeInstruction(),
        icon = R.drawable.ic_live_vet_step_3
    )
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun StepOneInstruction(
    onDownloadPressed: () -> Unit
) {
    val text = buildAnnotatedString {
        val fullText = stringResource(id = R.string.live_vet_step_1_full_text)
        append(fullText)

        val highlightText = stringResource(id = R.string.live_vet_step_1_highlight)
        val startHighlightText = fullText.indexOf(highlightText)
        addStyle(
            style = SpanStyle(fontWeight = FontWeight.Bold),
            start = startHighlightText,
            end = startHighlightText + highlightText.length

        )

        val downloadAppHighlightText =
            stringResource(id = R.string.live_vet_step_1_download_app_highlight)
        val startDownloadAppHighlightText = fullText.indexOf(downloadAppHighlightText)
        addStyle(
            style = SpanStyle(
                color = TertiaryDarkest,
                textDecoration = TextDecoration.Underline
            ),
            start = startDownloadAppHighlightText,
            end = startDownloadAppHighlightText + downloadAppHighlightText.length

        )
    }

    ClickableText(
        text = text,
        onHover = {},
        style = MobaBodyRegular.copy(color = SecondaryDark)
    ) {
        onDownloadPressed()
    }
}

@Composable
private fun StepTwoInstruction(
    userId: String,
    isCopyPressed: Boolean,
    onCopyPressed: () -> Unit
) {
    val iconButtonId = "iconButton"

    val text = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append(stringResource(id = R.string.live_vet_step_2_highlight))
        }

        append(" ")

        append(stringResource(id = R.string.live_vet_step_2, userId))

        append(" ")

        if (isCopyPressed) {
            withStyle(
                style = SpanStyle(color = PositiveDarkest)
            ) {
                append(stringResource(id = R.string.live_vet_step_2_button_clicked))
            }
        } else {
            withStyle(
                style = SpanStyle(color = TertiaryDarkest)
            ) {
                append(stringResource(id = R.string.live_vet_step_2_button))
            }
        }
        appendInlineContent(iconButtonId, " ")
    }

    val inlineContent = mapOf(
        Pair(
            iconButtonId,
            InlineTextContent(
                placeholder = Placeholder(
                    width = 1.3f.em,
                    height = 1.3f.em,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter

                )
            ) {
                if (isCopyPressed) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_outline_check),
                        tint = PositiveDarkest,
                        contentDescription = null
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_copy),
                        tint = TertiaryDarkest,
                        contentDescription = null
                    )
                }
            }
        )
    )

    Text(
        modifier = Modifier.clickable {
            onCopyPressed()
        },
        text = text,
        inlineContent = inlineContent,
        style = MobaBodyRegular.copy(color = SecondaryDark)
    )
}

@Composable
private fun stepThreeInstruction(): AnnotatedString = buildAnnotatedString {
    withStyle(
        style = SpanStyle(fontWeight = FontWeight.Bold)
    ) {
        append(stringResource(id = R.string.live_vet_step_3_highlight))
    }
    append(" ")
    append(stringResource(id = R.string.live_vet_step_3))
}

@Preview
@Composable
private fun StepSection() {
    Surface {
        LiveVetStepsSection(
            userId = "12345",
            isCopyPressed = false,
            onEvent = {}
        )
    }
}

@Preview
@Composable
private fun StepSectionCopied() {
    Surface {
        LiveVetStepsSection(
            userId = "12345",
            isCopyPressed = true,
            onEvent = {}
        )
    }
}
