package com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.DwellingType
import com.insurtech.kanguro.designsystem.ui.theme.MobaCaptionRegular
import com.insurtech.kanguro.designsystem.ui.theme.MobaSubheadRegularSecondaryDark
import com.insurtech.kanguro.designsystem.ui.theme.MobaTitle1
import com.insurtech.kanguro.designsystem.ui.theme.MobaTitle1PrimaryDarkest
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryMedium

@Composable
fun BasicInformationSection(
    modifier: Modifier = Modifier,
    userName: String?,
    address: String?,
    dwellingType: DwellingType?,
    policyNumber: String?
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (!userName.isNullOrBlank()) {
                Text(
                    text = stringResource(id = R.string.hello_comma),
                    style = MobaTitle1
                )

                Spacer(modifier = Modifier.width(5.dp))

                Text(
                    text = userName,
                    style = MobaTitle1PrimaryDarkest
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (dwellingType != null) {
            Text(
                text = stringResource(id = dwellingType.text),
                style = MobaSubheadRegularSecondaryDark
            )
        }

        if (!address.isNullOrBlank()) {
            Text(
                text = address,
                style = MobaSubheadRegularSecondaryDark
            )
        }

        if (!policyNumber.isNullOrBlank()) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(id = R.string.policy_number, policyNumber).uppercase(),
                style = MobaCaptionRegular.copy(color = SecondaryMedium)
            )
        }
    }
}

@Preview
@Composable
fun BasicInformationSectionPreview() {
    Surface {
        BasicInformationSection(
            userName = "Lauren",
            address = "1234 Main Street, Tampa, FL",
            modifier = Modifier.padding(16.dp),
            dwellingType = DwellingType.SingleFamily,
            policyNumber = "123456789"
        )
    }
}
