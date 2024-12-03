package com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.sections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.model.DeductibleItemsSectionModel
import com.insurtech.kanguro.designsystem.ui.theme.BKSParagraphRegular
import com.insurtech.kanguro.designsystem.ui.theme.MuseoSans15RegularSecondaryDark
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryLight
import java.math.BigDecimal

@Composable
fun DeductibleItemsSection(
    modifier: Modifier = Modifier,
    sectionModel: DeductibleItemsSectionModel?
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (sectionModel?.medicalPaymentsPrice != null) {
            MedicalPayments(price = sectionModel.medicalPaymentsPrice)
        }

        if (sectionModel?.hurricaneDeductible != null) {
            HurricaneDeductible(price = sectionModel.hurricaneDeductible)
        }
    }
}

@Composable
private fun MedicalPayments(
    price: BigDecimal
) {
    Spacer(modifier = Modifier.height(32.dp))

    Text(
        text = "${stringResource(id = R.string.medical_payments)} - ${stringResource(id = R.string.dollar)}${price.toDollarFormat()}",
        style = MuseoSans15RegularSecondaryDark
    )

    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = stringResource(id = R.string.medical_payments_description),
        style = BKSParagraphRegular.copy(color = SecondaryLight)
    )
}

@Composable
private fun HurricaneDeductible(
    price: BigDecimal
) {
    Spacer(modifier = Modifier.height(32.dp))

    Text(
        text = "${stringResource(id = R.string.hurricane_deductible)} - ${stringResource(id = R.string.dollar)}${price.toDollarFormat()}",
        style = MuseoSans15RegularSecondaryDark
    )

    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = stringResource(id = R.string.hurricane_deductible_description),
        style = BKSParagraphRegular.copy(color = SecondaryLight)
    )
}

@Preview
@Composable
private fun DeductibleItemsSectionModelPreview() {
    Surface {
        DeductibleItemsSection(
            sectionModel = DeductibleItemsSectionModel(
                hurricaneDeductible = 1000.00.toBigDecimal(),
                medicalPaymentsPrice = 1000.toBigDecimal()
            )
        )
    }
}
