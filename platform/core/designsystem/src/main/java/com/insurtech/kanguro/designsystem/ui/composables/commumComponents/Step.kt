package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.StepModel
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyRegular
import com.insurtech.kanguro.designsystem.ui.theme.MobaSubheadBlack
import com.insurtech.kanguro.designsystem.ui.theme.PrimaryDarkest
import com.insurtech.kanguro.designsystem.ui.theme.PrimaryLight
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDark
import com.insurtech.kanguro.designsystem.ui.theme.spacingNano

@Composable
fun Step(
    modifier: Modifier = Modifier,
    model: StepModel,
    isLastItem: Boolean,
    order: Int,
    spacedBy: Dp
) {
    Row(
        modifier = modifier.height(IntrinsicSize.Max)
    ) {
        StepIconWrapper(
            isLastItem = isLastItem,
            icon = model.icon
        )

        Column(
            modifier = Modifier.padding(top = 2.dp, start = 12.dp)
        ) {
            Text(
                text = stringResource(id = R.string.step, order),
                style = MobaSubheadBlack.copy(color = PrimaryDarkest)
            )
            Spacer(Modifier.height(spacingNano))

            StepInstructionWrapper(
                modifier = Modifier.fillMaxWidth(),
                model = model
            )

            if (!isLastItem) {
                Spacer(Modifier.height(spacedBy))
            }
        }
    }
}

@Composable
private fun StepIconWrapper(
    @DrawableRes icon: Int,
    isLastItem: Boolean
) {
    if (isLastItem) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null
        )
    } else {
        LineBehind(
            modifier = Modifier.fillMaxHeight()
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = null
            )
        }
    }
}

@Composable
private fun StepInstructionWrapper(
    modifier: Modifier = Modifier,
    model: StepModel
) {
    when (model) {
        is StepModel.Basic -> {
            BasicInstruction(
                modifier = modifier,
                instruction = model.instruction
            )
        }

        is StepModel.CustomInstruction -> {
            CustomInstructionWrapper(
                modifier = modifier
            ) {
                model.instruction()
            }
        }
    }
}

@Composable
private fun BasicInstruction(
    instruction: AnnotatedString,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        style = MobaBodyRegular.copy(color = SecondaryDark),
        text = instruction
    )
}

@Composable
private fun CustomInstructionWrapper(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier) {
        content()
    }
}

@Composable
private fun LineBehind(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .drawBehind {
                drawLine(
                    start = Offset(size.width / 2, 0f),
                    end = Offset(size.width / 2, size.height + 1),
                    color = PrimaryLight,
                    strokeWidth = 2.dp.toPx()
                )
            }
    ) {
        content()
    }
}

@Preview
@Composable
private fun StepPreview() {
    Step(
        model = StepModel.Basic(
            instruction = buildAnnotatedString {
                append(stringResource(id = R.string.direct_pay_to_vet_step_3))
            },
            icon = R.drawable.ic_dtp_step_1
        ),
        isLastItem = false,
        order = 1,
        spacedBy = spacingNano
    )
}

@Preview
@Composable
private fun StepLastItem() {
    Step(
        model = StepModel.Basic(
            instruction = buildAnnotatedString {
                append(stringResource(id = R.string.direct_pay_to_vet_step_3))
            },
            icon = R.drawable.ic_dtp_step_1
        ),
        isLastItem = true,
        order = 1,
        spacedBy = spacingNano
    )
}

// Change this preview
@Preview
@Composable
private fun StepCustomInstructionPreview() {
    Step(
        model = StepModel.CustomInstruction(
            instruction = {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .background(Color.Cyan)
                )
            },
            icon = R.drawable.ic_dtp_step_1
        ),
        isLastItem = false,
        order = 1,
        spacedBy = spacingNano
    )
}
