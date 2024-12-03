package com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.toDollarFormat
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.ActionCardButton
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.PolicyStatus
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.CoverageSectionCard
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.SectionHeader
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.section.PaymentSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.section.SectionHeaderModel
import com.insurtech.kanguro.designsystem.ui.theme.MobaHeadline
import com.insurtech.kanguro.designsystem.ui.theme.MobaTitle3
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDark
import com.insurtech.kanguro.designsystem.ui.theme.SmallRegular
import com.insurtech.kanguro.designsystem.ui.theme.SmallRegularBold
import com.insurtech.kanguro.designsystem.ui.theme.TertiaryDarkest
import java.math.BigDecimal

@Composable
fun PaymentSection(
    modifier: Modifier = Modifier,
    paymentSectionModel: PaymentSectionModel,
    onBillingPreferencesPressed: () -> Unit
) {
    if (paymentSectionModel.policyStatus == PolicyStatus.ACTIVE) {
        CoverageSectionCard(modifier = modifier) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                SectionHeader(
                    model = SectionHeaderModel(
                        title = stringResource(id = R.string.payment),
                        icon = R.drawable.ic_scheduled_items_section
                    )
                )

                PricesDescription(
                    payment = paymentSectionModel.paymentValue,
                    invoiceInterval = stringResource(id = paymentSectionModel.invoiceInterval)
                )

                Footer(
                    onBillingPreferencesPressed = onBillingPreferencesPressed
                )
            }
        }
    }
}

@Composable
private fun PricesDescription(
    payment: BigDecimal,
    invoiceInterval: String
) {
    Spacer(modifier = Modifier.height(24.dp))

    MonthlyPayment(payment = payment, invoiceInterval = invoiceInterval)

    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
private fun Footer(
    onBillingPreferencesPressed: () -> Unit
) {
    ActionCardButton(
        text = stringResource(id = R.string.billing_preferences),
        icon = R.drawable.ic_credit_card,
        onClick = onBillingPreferencesPressed
    )
}

@Composable
private fun MonthlyPayment(
    payment: BigDecimal,
    invoiceInterval: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = invoiceInterval,
                style = MobaTitle3.copy(color = SecondaryDark)
            )
        }

        Text(
            text = stringResource(id = R.string.dollar) + payment.toDollarFormat(),
            style = MobaHeadline.copy(color = TertiaryDarkest)
        )
    }
}

@Composable
private fun ItemPrice(
    primaryDescription: String,
    secondaryDescription: String? = null,
    valuePrice: BigDecimal
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                style = SmallRegular,
                text = buildAnnotatedString {
                    append(primaryDescription)
                    if (!secondaryDescription.isNullOrBlank()) {
                        append(stringResource(id = R.string.pipe_separator))
                        withStyle(
                            style = SpanStyle(
                                fontSize = 10.24.sp
                            )
                        ) {
                            append(secondaryDescription)
                        }
                    }
                }
            )
        }

        Text(
            text = stringResource(id = R.string.dollar) + valuePrice.toDollarFormat(),
            style = SmallRegularBold
        )
    }
}

@Composable
@Preview
private fun PaymentSectionPreview() {
    Surface {
        PaymentSection(
            modifier = Modifier.padding(16.dp),
            paymentSectionModel = PaymentSectionModel(
                paymentValue = 75.toBigDecimal(),
                invoiceInterval = R.string.monthly_payment,
                policyStatus = PolicyStatus.ACTIVE
            ),
            onBillingPreferencesPressed = {}
        )
    }
}
