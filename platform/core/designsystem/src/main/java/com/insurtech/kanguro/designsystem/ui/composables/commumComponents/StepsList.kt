package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.StepModel
import com.insurtech.kanguro.designsystem.ui.theme.spacingNano
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun StepsList(
    modifier: Modifier = Modifier,
    stepList: ImmutableList<StepModel>,
    spacedBy: Dp
) {
    Column(
        modifier = modifier
    ) {
        repeat(stepList.size) {
            Step(
                isLastItem = it == stepList.lastIndex,
                order = it + 1,
                model = stepList[it],
                spacedBy = spacedBy
            )
        }
    }
}

@Preview
@Composable
private fun StepsListPreview() {
    StepsList(
        stepList = persistentListOf(
            StepModel.Basic(
                instruction = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append(stringResource(id = R.string.direct_pay_to_vet_step_1_highlight))
                    }
                    append(" ")
                    append(stringResource(id = R.string.direct_pay_to_vet_step_1))
                },
                icon = R.drawable.ic_dtp_step_1
            ),
            StepModel.Basic(
                instruction = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append(stringResource(id = R.string.direct_pay_to_vet_step_2_highlight))
                    }
                    append(" ")
                    append(stringResource(id = R.string.direct_pay_to_vet_step_2))
                },
                icon = R.drawable.ic_dtp_step_2
            ),
            StepModel.Basic(
                instruction = buildAnnotatedString {
                    append(stringResource(id = R.string.direct_pay_to_vet_step_3))
                },
                icon = R.drawable.ic_dtp_step_3
            )
        ),
        spacedBy = spacingNano
    )
}
