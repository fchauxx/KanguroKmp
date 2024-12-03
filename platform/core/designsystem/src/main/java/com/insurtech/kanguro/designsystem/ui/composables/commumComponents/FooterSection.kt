package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.toDollarFormat
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.FooterSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.InvoiceInterval
import com.insurtech.kanguro.designsystem.ui.theme.MobaCaptionRegularSDark
import java.math.BigDecimal

@Composable
fun FooterSection(
    modifier: Modifier = Modifier,
    model: FooterSectionModel?,
    isEnable: Boolean? = null,
    @StringRes buttonText: Int = R.string.submit,
    @StringRes footerText: Int = R.string.resume_total_price,
    onSubmitPressed: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        KanguroButton(
            text = if (model?.buttonPrice != null && model.invoiceInterval != null) {
                getFormattedPrice(
                    model.invoiceInterval,
                    model.buttonPrice,
                    buttonText
                )
            } else {
                stringResource(id = buttonText)
            },
            enabled = isEnable ?: (model?.buttonPrice != null),
            isLoading = model?.isLoading ?: false
        ) {
            onSubmitPressed()
        }

        if (model?.totalPrice != null && model.invoiceInterval != null) {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = getFormattedPrice(
                    model.invoiceInterval,
                    model.totalPrice,
                    footerText
                ),
                style = MobaCaptionRegularSDark
            )
        }

        Spacer(modifier = Modifier.height(35.dp))
    }
}

@Composable
private fun getFormattedPrice(
    invoiceInterval: InvoiceInterval,
    buttonPrice: BigDecimal,
    @StringRes prefix: Int
): String {
    val price = stringResource(id = invoiceInterval.shortText, buttonPrice.toDollarFormat())

    return "${stringResource(id = prefix)} $price"
}

@Preview
@Composable
private fun FooterSectionPreview() {
    Surface {
        FooterSection(
            model = FooterSectionModel(
                buttonPrice = 1000.00.toBigDecimal(),
                totalPrice = 2000.00.toBigDecimal(),
                invoiceInterval = InvoiceInterval.MONTHLY
            )
        ) {}
    }
}
