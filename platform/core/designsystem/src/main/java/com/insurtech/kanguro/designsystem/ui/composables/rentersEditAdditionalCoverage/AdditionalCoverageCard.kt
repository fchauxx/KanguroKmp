package com.insurtech.kanguro.designsystem.ui.composables.rentersEditAdditionalCoverage

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.AdditionalCoverageItemTypeModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.getInfoDialogTitle
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditAdditionalCoverage.domain.AdditionalCoverageCardModel
import com.insurtech.kanguro.designsystem.ui.theme.BKSParagraphRegular
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyBold
import com.insurtech.kanguro.designsystem.ui.theme.NeutralLightest
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDark
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryLight
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryMedium
import com.insurtech.kanguro.designsystem.ui.theme.SwitchTextStyle
import com.insurtech.kanguro.designsystem.ui.theme.TertiaryDarkest
import com.insurtech.kanguro.designsystem.ui.theme.White

@Composable
fun AdditionalCoverageCard(
    modifier: Modifier = Modifier,
    model: AdditionalCoverageCardModel,
    onSwitchChanged: (AdditionalCoverageCardModel) -> Unit
) {
    val itemSelectedColor by animateColorAsState(
        targetValue = if (model.isSelected) TertiaryDarkest else NeutralLightest,
        label = ""
    )

    val backgroundColor by animateColorAsState(
        targetValue = if (model.isSelected) TertiaryDarkest.copy(alpha = 0.02f) else Color.Transparent,
        label = ""
    )

    Surface(
        modifier = modifier,
        color = backgroundColor,
        border = BorderStroke(1.dp, itemSelectedColor),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CoverageTitle(
                    modifier = Modifier.weight(1f),
                    model = model
                )

                SwitchComponent(
                    model = model,
                    itemSelectedColor = itemSelectedColor,
                    onSwitchChanged = onSwitchChanged
                )
            }

            Spacer(
                modifier = Modifier.height(height = 16.dp)
            )

            Text(
                text = model.description,
                style = BKSParagraphRegular.copy(color = SecondaryLight)
            )

            if (model.isPreviouslySelected) {
                Spacer(
                    modifier = Modifier.height(height = 16.dp)
                )

                Text(
                    text = stringResource(id = R.string.this_additional_coverage_is_active),
                    style = BKSParagraphRegular.copy(
                        color = SecondaryLight,
                        fontStyle = FontStyle.Italic
                    )
                )
            }
        }
    }
}

@Composable
private fun SwitchComponent(
    model: AdditionalCoverageCardModel,
    itemSelectedColor: Color,
    onSwitchChanged: (AdditionalCoverageCardModel) -> Unit
) {
    Column(
        modifier = Modifier.padding(start = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.monthly_price, model.monthlyPrice),
            style = SwitchTextStyle.copy(color = itemSelectedColor)
        )
        Switch(
            modifier = Modifier.height(32.dp),
            checked = model.isSelected,
            thumbContent = {},
            colors = SwitchDefaults.colors(
                checkedThumbColor = White,
                checkedTrackColor = TertiaryDarkest,
                uncheckedThumbColor = White,
                uncheckedTrackColor = NeutralLightest,
                uncheckedBorderColor = Color.Transparent
            ),
            onCheckedChange = {
                onSwitchChanged(model)
            }
        )
    }
}

@Composable
private fun CoverageTitle(modifier: Modifier = Modifier, model: AdditionalCoverageCardModel) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Image(
            painter = painterResource(id = model.type.icon),
            colorFilter = ColorFilter.tint(SecondaryMedium),
            contentDescription = stringResource(id = model.type.getInfoDialogTitle())
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = stringResource(id = model.type.getInfoDialogTitle()),
            style = MobaBodyBold.copy(color = SecondaryDark)
        )
    }
}

@Composable
@Preview
private fun AdditionalCoverageCardUnselectedPreview() {
    Surface {
        AdditionalCoverageCard(
            modifier = Modifier.padding(16.dp),
            model = AdditionalCoverageCardModel(
                type = AdditionalCoverageItemTypeModel.FRAUD_PROTECTION,
                description = "Should you need to use your insurance, we'll cover the genuine costs of repairing or replacing items.",
                monthlyPrice = "3.99",
                isPreviouslySelected = false,
                isSelected = false
            ),
            onSwitchChanged = {}
        )
    }
}

@Composable
@Preview
private fun AdditionalCoverageCardSelectedPreview() {
    Surface {
        AdditionalCoverageCard(
            modifier = Modifier.padding(16.dp),
            model = AdditionalCoverageCardModel(
                type = AdditionalCoverageItemTypeModel.FRAUD_PROTECTION,
                description = "Coverage includes up to \$2,500 in damages if a sewer or drain backs up into your home. A \$250 deductible applies.",
                monthlyPrice = "3.99",
                isPreviouslySelected = false,
                isSelected = true
            ),
            onSwitchChanged = {}
        )
    }
}

@Composable
@Preview
private fun AdditionalCoverageCardPreviouslySelectedPreview() {
    Surface {
        AdditionalCoverageCard(
            modifier = Modifier.padding(16.dp),
            model = AdditionalCoverageCardModel(
                type = AdditionalCoverageItemTypeModel.FRAUD_PROTECTION,
                description = "Coverage includes up to \$2,500 in damages if a sewer or drain backs up into your home. A \$250 deductible applies.",
                monthlyPrice = "3.99",
                isPreviouslySelected = true,
                isSelected = true
            ),
            onSwitchChanged = {}
        )
    }
}
