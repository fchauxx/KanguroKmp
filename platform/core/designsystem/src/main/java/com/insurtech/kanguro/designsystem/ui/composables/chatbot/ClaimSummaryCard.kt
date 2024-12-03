package com.insurtech.kanguro.designsystem.ui.composables.chatbot

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.toDollarFormat
import com.insurtech.kanguro.designsystem.ui.composables.chatbot.model.ClaimSummaryInjuryModel
import com.insurtech.kanguro.designsystem.ui.composables.chatbot.model.ClaimSummaryItemReportedModel
import com.insurtech.kanguro.designsystem.ui.composables.chatbot.model.ClaimSummaryModel
import com.insurtech.kanguro.designsystem.ui.composables.chatbot.model.ClaimSummaryType
import com.insurtech.kanguro.designsystem.ui.composables.chatbot.model.ClaimantModel
import com.insurtech.kanguro.designsystem.ui.composables.chatbot.model.ClaimantType
import com.insurtech.kanguro.designsystem.ui.composables.chatbot.model.ItemReportedModel
import com.insurtech.kanguro.designsystem.ui.composables.chatbot.model.ProductType
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.HorizontalDivider
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyBold
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyRegular
import com.insurtech.kanguro.designsystem.ui.theme.MobaFootnoteBlack
import com.insurtech.kanguro.designsystem.ui.theme.MobaSubheadRegular
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground
import com.insurtech.kanguro.designsystem.ui.theme.NeutralMedium2
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDarkest
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryLightest
import com.insurtech.kanguro.designsystem.ui.theme.TertiaryDarkest
import com.insurtech.kanguro.designsystem.ui.theme.TertiaryLightest
import java.math.BigDecimal
import java.util.Date

@Composable
fun ClaimSummaryCard(
    modifier: Modifier = Modifier,
    model: ClaimSummaryModel
) {
    Surface(
        modifier = modifier.border(
            width = 1.dp,
            color = SecondaryLightest,
            shape = RoundedCornerShape(16.dp)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.claim_summary_title),
                style = MobaBodyBold.copy(color = TertiaryDarkest)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(
                    id = R.string.incident_occurred_on,
                    model.formattedIncidentDate
                ),
                style = MobaFootnoteBlack
            )

            Spacer(modifier = Modifier.height(16.dp))

            Informations(model = model)

            if (model.injuryModel != null) {
                InjuryClaimSection(model.injuryModel)
            }

            if (model.itemReportedModel != null) {
                ItemsReportedSection(model.itemReportedModel)
            }
        }
    }
}

@Composable
private fun Informations(model: ClaimSummaryModel) {
    HorizontalDivider(thicknessDp = 1.0, color = NeutralBackground)

    LabelValueComponent(
        label = stringResource(id = R.string.claim_type_label),
        value = stringResource(id = model.claimSummaryType.label)
    )
    LabelValueComponent(
        label = stringResource(id = R.string.policy_label),
        value = stringResource(id = model.policyProductType.label)
    )
    LabelValueComponent(
        label = stringResource(id = R.string.policy_start_date_label),
        value = model.formattedPolicyStartDate
    )
    LabelValueComponent(
        label = stringResource(id = R.string.deductible),
        value = "$${model.deductible.toDollarFormat()}"
    )
    LabelValueComponent(
        label = stringResource(id = R.string.contact_phone_label),
        value = model.contactPhone
    )
}

@Composable
private fun InjuryClaimSection(injuryModel: ClaimSummaryInjuryModel) {
    Column(
        modifier = Modifier.padding(vertical = 24.dp, horizontal = 8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.description_of_the_injury_incident),
            style = MobaBodyBold.copy(color = TertiaryDarkest)
        )
        Text(
            modifier = Modifier.padding(top = 12.dp),
            text = injuryModel.description,
            style = MobaSubheadRegular.copy(color = NeutralMedium2)
        )
    }

    Text(
        text = stringResource(id = R.string.claimants),
        style = MobaBodyBold.copy(color = TertiaryDarkest)
    )

    Spacer(modifier = Modifier.height(8.dp))

    injuryModel.claimants.forEach { claimant ->
        LabelValueComponent(
            label = claimant.name,
            value = stringResource(id = claimant.claimantType.label)
        )
    }
}

@Composable
private fun ItemsReportedSection(itemsReportedModel: ClaimSummaryItemReportedModel) {
    Column(
        modifier = Modifier.padding(vertical = 24.dp, horizontal = 8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.description_of_the_property_incident),
            style = MobaBodyBold.copy(color = TertiaryDarkest)
        )
        Text(
            modifier = Modifier.padding(top = 12.dp),
            text = itemsReportedModel.description,
            style = MobaSubheadRegular.copy(color = NeutralMedium2)
        )
    }

    HorizontalDivider(thicknessDp = 1.0, color = NeutralBackground)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = R.string.items_reported),
            style = MobaBodyBold.copy(color = TertiaryDarkest)
        )

        FloatingActionButton(
            onClick = {
                // TODO - Implement edit action
            },
            modifier = Modifier.size(24.dp),
            backgroundColor = TertiaryLightest,
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp
            )
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_edit_fullfill_small),
                contentDescription = null
            )
        }
    }

    Spacer(modifier = Modifier.height(8.dp))

    itemsReportedModel.items.forEach { itemsReported ->
        ItemsReportedComponent(itemsReported)
    }
}

@Composable
private fun ItemsReportedComponent(item: ItemReportedModel) {
    Box {
        HorizontalDivider(
            modifier = Modifier.align(Alignment.TopCenter),
            thicknessDp = 1.0,
            color = NeutralBackground
        )

        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = item.item,
                style = MobaBodyBold.copy(color = NeutralMedium2)
            )

            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = item.formattedDollarValue,
                style = MobaBodyRegular.copy(color = NeutralMedium2)
            )
        }
    }
}

@Composable
private fun LabelValueComponent(label: String, value: String) {
    Column {
        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = label,
                style = MobaBodyRegular.copy(color = NeutralMedium2)
            )
            Spacer(modifier = Modifier.width(24.dp))
            Text(text = value, style = MobaBodyBold.copy(color = SecondaryDarkest))
        }
        HorizontalDivider(thicknessDp = 1.0, color = NeutralBackground)
    }
}

@Composable
@Preview(heightDp = 1050)
private fun ClaimSummaryCardPreview() {
    Surface {
        ClaimSummaryCard(
            modifier = Modifier.padding(16.dp),
            model = ClaimSummaryModel(
                claimSummaryType = ClaimSummaryType.Injury,
                policyProductType = ProductType.Renters,
                policyStartDate = Date(),
                deductible = BigDecimal(500),
                contactPhone = "(123) 456-7890",
                incidentDate = Date(),
                injuryModel = ClaimSummaryInjuryModel(
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce id lacus vitae ipsum ultrices tempus. Etiam quis risus tincidunt, ultrices leo sed, blandit turpis. Nullam non ante justo. Duis purus lorem, tincidunt at malesuada sit amet, tristique a lacus. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce id lacus vitae ipsum.",
                    claimants = listOf(
                        ClaimantModel(
                            name = "John Doe",
                            claimantType = ClaimantType.PolicyHolder
                        ),
                        ClaimantModel(
                            name = "Jane Doe",
                            claimantType = ClaimantType.AnotherResident
                        ),
                        ClaimantModel(
                            name = "John Smith",
                            claimantType = ClaimantType.SomeoneElse
                        )
                    )
                ),
                itemReportedModel = ClaimSummaryItemReportedModel(
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce id lacus vitae ipsum ultrices tempus. Etiam quis risus tincidunt, ultrices leo sed, blandit turpis. Nullam non ante justo. Duis purus lorem, tincidunt at malesuada sit amet, tristique a lacus. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce id lacus vitae ipsum.",
                    items = listOf(
                        ItemReportedModel(
                            item = "Macbook 2020 14”",
                            value = BigDecimal(1000)
                        ),
                        ItemReportedModel(
                            item = "Macbook 2023 16”",
                            value = BigDecimal(2459)
                        )
                    )
                )
            )
        )
    }
}
