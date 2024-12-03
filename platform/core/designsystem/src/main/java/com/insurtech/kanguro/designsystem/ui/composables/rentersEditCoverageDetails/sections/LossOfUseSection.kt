package com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.sections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.model.LossOfUseSectionModel
import com.insurtech.kanguro.designsystem.ui.theme.BKSHeading4
import com.insurtech.kanguro.designsystem.ui.theme.BKSHeading4Small
import com.insurtech.kanguro.designsystem.ui.theme.BKSParagraphRegular
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryLight
import com.insurtech.kanguro.designsystem.ui.theme.TertiaryDarkest
import java.math.BigDecimal

@Composable
fun LossOfUseSection(
    modifier: Modifier = Modifier,
    model: LossOfUseSectionModel
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Title()

        Spacer(modifier = Modifier.height(16.dp))

        CoveragePrice(price = model.price)

        Spacer(modifier = Modifier.height(8.dp))

        CoverageDescription()
    }
}

@Composable
private fun Title() {
    Text(
        text = stringResource(id = R.string.loss_of_use_camel_case),
        style = BKSHeading4,
        modifier = Modifier.padding(horizontal = 32.dp)
    )
}

@Composable
private fun CoveragePrice(
    price: BigDecimal
) {
    Text(
        text = "${stringResource(id = R.string.dollar)} ${price.toDollarFormat()}",
        style = BKSHeading4Small.copy(color = TertiaryDarkest),
        modifier = Modifier.padding(horizontal = 32.dp)
    )
}

@Composable
private fun CoverageDescription() {
    Text(
        text = stringResource(id = R.string.loss_off_use_description),
        style = BKSParagraphRegular.copy(color = SecondaryLight),
        modifier = Modifier.padding(horizontal = 32.dp)
    )
}

@Preview
@Composable
private fun LossOfUseSectionPreview() {
    Surface {
        LossOfUseSection(
            model = LossOfUseSectionModel(1000.toBigDecimal())
        )
    }
}
