package com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.directPayToVetInstructions

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyRegular
import com.insurtech.kanguro.designsystem.ui.theme.MobaSubheadBlack
import com.insurtech.kanguro.designsystem.ui.theme.PrimaryDarkest
import com.insurtech.kanguro.designsystem.ui.theme.PrimaryLight
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDark

@Composable
fun DirectPayToVetStepsComponent(
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier.fillMaxWidth()
    ) {
        val (
            link,
            step1,
            step2,
            step3
        ) = createRefs()

        Divider(
            modifier = Modifier
                .constrainAs(link) {
                    top.linkTo(step1.top)
                    bottom.linkTo(step3.top)
                    height = Dimension.fillToConstraints
                }
                .padding(start = 15.dp)
                .fillMaxHeight()
                .offset(y = 18.dp)
                .width(2.dp),
            color = PrimaryLight
        )
        Step(
            modifier = Modifier.constrainAs(step1) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            },
            order = 1,
            instruction = buildFirstInstructionText(),
            icon = R.drawable.ic_dtp_step_1
        )

        Step(
            modifier = Modifier
                .constrainAs(step2) {
                    top.linkTo(step1.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
                .padding(top = 16.dp),
            order = 2,
            instruction = buildSecondInstructionText(),
            icon = R.drawable.ic_dtp_step_2
        )

        Step(
            modifier = Modifier
                .constrainAs(step3) {
                    top.linkTo(step2.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
                .padding(top = 16.dp),
            order = 3,
            instruction = buildAnnotatedString {
                append(stringResource(id = R.string.direct_pay_to_vet_step_3))
            },
            icon = R.drawable.ic_dtp_step_3
        )
    }
}

@Composable
private fun buildSecondInstructionText() = buildAnnotatedString {
    withStyle(
        style = SpanStyle(
            fontWeight = FontWeight.Bold
        )
    ) {
        append(stringResource(id = R.string.direct_pay_to_vet_step_2_highlight))
    }
    append(" ")
    append(stringResource(id = R.string.direct_pay_to_vet_step_2))
}

@Composable
private fun buildFirstInstructionText() = buildAnnotatedString {
    withStyle(
        style = SpanStyle(
            fontWeight = FontWeight.Bold
        )
    ) {
        append(stringResource(id = R.string.direct_pay_to_vet_step_1_highlight))
    }
    append(" ")
    append(stringResource(id = R.string.direct_pay_to_vet_step_1))
}

@Composable
private fun Step(
    modifier: Modifier,
    order: Int,
    instruction: AnnotatedString,
    @DrawableRes icon: Int
) {
    Row(modifier = modifier) {
        Image(
            modifier = Modifier,
            painter = painterResource(id = icon),
            contentDescription = null
        )
        Column(
            modifier = Modifier.padding(top = 2.dp, start = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.step, order),
                style = MobaSubheadBlack.copy(color = PrimaryDarkest)
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                style = MobaBodyRegular.copy(color = SecondaryDark),
                text = instruction
            )
        }
    }
}

@Composable
@Preview
private fun DirectPayToVetStepsComponentPreview() {
    Surface {
        DirectPayToVetStepsComponent()
    }
}

@Composable
@Preview(locale = "es")
private fun DirectPayToVetStepsComponentLocalePreview() {
    Surface {
        DirectPayToVetStepsComponent()
    }
}

@Composable
@Preview(widthDp = 200)
private fun DirectPayToVetStepsComponentSmallScreenPreview() {
    Surface {
        DirectPayToVetStepsComponent()
    }
}
