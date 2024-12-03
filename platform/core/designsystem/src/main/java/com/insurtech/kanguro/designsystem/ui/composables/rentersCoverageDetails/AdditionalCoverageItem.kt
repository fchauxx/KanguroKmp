package com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.toDollarFormat
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.KanguroClickableText
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.PolicyStatus
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.AdditionalCoverageItemModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.AdditionalCoverageItemTypeModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.getInfoDialogTitle
import com.insurtech.kanguro.designsystem.ui.theme.BKSParagraphRegular
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyBold
import com.insurtech.kanguro.designsystem.ui.theme.MobaSubheadRegular
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDark
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryLight
import java.math.BigDecimal

@Composable
fun AdditionalCoverageItem(
    modifier: Modifier = Modifier,
    policyStatus: PolicyStatus,
    item: AdditionalCoverageItemModel,
    onInfoPressed: () -> Unit = {},
    onAddItemPressed: () -> Unit = {}
) {
    Column(modifier = modifier) {
        Title(item, onInfoPressed)
        Content(item, policyStatus, onAddItemPressed)
    }
}

@Composable
private fun Content(
    item: AdditionalCoverageItemModel,
    policyStatus: PolicyStatus,
    onAddItemPressed: () -> Unit
) {
    if (item.isActive) {
        Column(
            modifier = Modifier.padding(top = 8.dp)
        ) {
            if (item.coverageLimit != null) {
                TextAndValue(
                    text = stringResource(id = R.string.coverage_limit),
                    value = item.coverageLimit
                )
            }

            if (item.deductible != null) {
                TextAndValue(
                    modifier = Modifier.padding(top = 16.dp),
                    text = stringResource(id = R.string.deductible_limit),
                    value = item.deductible
                )
            }

            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = stringResource(id = R.string.your_policy_already_have_this_coverage),
                style = BKSParagraphRegular.copy(
                    color = SecondaryLight,
                    fontStyle = FontStyle.Italic
                )
            )
        }
    } else {
        if (policyStatus == PolicyStatus.ACTIVE) {
            KanguroClickableText(
                modifier = Modifier.padding(top = 8.dp),
                text = stringResource(
                    id = R.string.add_to_your_policy,
                    item.intervalTotal.toDollarFormat()
                ),
                onClick = onAddItemPressed
            )
        }
    }
}

@Composable
private fun TextAndValue(modifier: Modifier = Modifier, text: String, value: BigDecimal) {
    Row(modifier = modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.weight(1f),
            text = text,
            style = MobaSubheadRegular.copy(color = SecondaryDark)
        )
        Text(
            text = stringResource(id = R.string.dollar) + value.toDollarFormat(),
            style = MobaSubheadRegular
        )
    }
}

@Composable
private fun Title(
    item: AdditionalCoverageItemModel,
    onInfoPressed: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = item.type.icon),
            contentDescription = stringResource(id = item.type.getInfoDialogTitle())
        )
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            text = stringResource(id = item.type.getInfoDialogTitle()),
            style = MobaBodyBold.copy(color = SecondaryDark)
        )
        Image(
            modifier = Modifier.clickable(onClick = onInfoPressed),
            painter = painterResource(id = R.drawable.ic_renters_coverage_info_circle),
            contentDescription = stringResource(id = R.string.information)
        )
    }
}

@Composable
@Preview
private fun AdditionalCoverageItemPreview() {
    Surface {
        AdditionalCoverageItem(
            modifier = Modifier.padding(16.dp),
            policyStatus = PolicyStatus.ACTIVE,
            item = AdditionalCoverageItemModel(
                type = AdditionalCoverageItemTypeModel.WATER_SEWER_BACKUP,
                coverageLimit = BigDecimal(2500),
                deductible = BigDecimal(250),
                intervalTotal = 3.99.toBigDecimal()
            )
        )
    }
}

@Composable
@Preview
private fun AdditionalCoverageItemNotAddedPreview() {
    Surface {
        AdditionalCoverageItem(
            modifier = Modifier.padding(16.dp),
            policyStatus = PolicyStatus.ACTIVE,
            item = AdditionalCoverageItemModel(
                type = AdditionalCoverageItemTypeModel.REPLACEMENT_COST,
                coverageLimit = null,
                deductible = null,
                intervalTotal = 3.99.toBigDecimal()
            )
        )
    }
}
