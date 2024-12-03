package com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.toPriceWithDecimalFormat
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.ExpandableCardStateful
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.PlanSummaryCardModel
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyBold
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyRegular
import java.math.BigDecimal

@Composable
fun PlanSummaryCard(modifier: Modifier = Modifier, planSummaryCardModel: PlanSummaryCardModel) {
    ExpandableCardStateful(
        modifier = modifier,
        title = stringResource(id = R.string.plan_summary)
    ) {
        PlanSummaryContent(planSummaryCardModel = planSummaryCardModel)
    }
}

@Composable
private fun PlanSummaryContent(planSummaryCardModel: PlanSummaryCardModel) {
    Column(
        modifier = Modifier.padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PlanSummaryItem(
            label = stringResource(id = R.string.liability),
            value = planSummaryCardModel.liability
        )
        PlanSummaryItem(
            label = stringResource(id = R.string.deductible),
            value = planSummaryCardModel.deductible
        )
        PlanSummaryItem(
            label = stringResource(id = R.string.personal_property),
            value = planSummaryCardModel.personalProperty
        )
        PlanSummaryItem(
            label = stringResource(id = R.string.loss_of_use),
            value = planSummaryCardModel.lossOfUse
        )
    }
}

@Composable
private fun PlanSummaryItem(label: String, value: BigDecimal) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MobaBodyRegular)
        Text(text = value.toPriceWithDecimalFormat(), style = MobaBodyBold)
    }
}

@Composable
@Preview
private fun PlanSummaryCardPreview() {
    Surface {
        PlanSummaryCard(
            modifier = Modifier.padding(16.dp),
            planSummaryCardModel = PlanSummaryCardModel(
                liability = BigDecimal(100.0),
                deductible = BigDecimal(100.0),
                personalProperty = BigDecimal(100.0),
                lossOfUse = BigDecimal(100.0)
            )
        )
    }
}
