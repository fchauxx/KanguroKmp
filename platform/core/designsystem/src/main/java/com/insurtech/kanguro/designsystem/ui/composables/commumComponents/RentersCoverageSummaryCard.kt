package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.CoverageStatusUi
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.RentersCoverageSummaryCardModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.DwellingType
import com.insurtech.kanguro.designsystem.ui.theme.ClickableTextStyleNormal
import com.insurtech.kanguro.designsystem.ui.theme.LatoBoldNeutralMedium2Size10
import com.insurtech.kanguro.designsystem.ui.theme.LatoRegularSecondaryDarkSize12
import com.insurtech.kanguro.designsystem.ui.theme.MobaHeadline
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground
import com.insurtech.kanguro.designsystem.ui.theme.PrimaryDarkest

@Composable
fun RentersCoverageSummaryCard(
    modifier: Modifier = Modifier,
    coverage: RentersCoverageSummaryCardModel,
    onPressed: () -> Unit
) {
    CoverageSummaryCardContainer(
        modifier = modifier.width(157.dp),
        onPressed = onPressed
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Image(
                painter = painterResource(id = coverage.type.summaryIcon),
                contentDescription = null
            )

            Text(
                text = stringResource(id = R.string.home),
                style = MobaHeadline.copy(color = PrimaryDarkest)
            )

            Text(
                text = coverage.address,
                style = LatoRegularSecondaryDarkSize12
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.middle_dot),
                    style = LatoRegularSecondaryDarkSize12.copy(color = coverage.status.color)
                )

                Text(
                    text = stringResource(id = coverage.status.label),
                    style = LatoBoldNeutralMedium2Size10.copy(fontWeight = FontWeight.W400)
                )
            }

            ClickableText(
                modifier = Modifier.padding(top = 12.dp),
                style = ClickableTextStyleNormal,
                text = buildAnnotatedString {
                    append(stringResource(id = R.string.see_details))
                },
                onClick = { onPressed() }
            )
        }
    }
}

@Composable
@Preview
fun RentersCoverageSummaryCardPreview() {
    Surface(
        color = NeutralBackground
    ) {
        RentersCoverageSummaryCard(
            modifier = Modifier.padding(16.dp),
            coverage = RentersCoverageSummaryCardModel(
                id = "1",
                address = "Miami, FL",
                type = DwellingType.SingleFamily,
                status = CoverageStatusUi.Active
            ),
            onPressed = {}
        )
    }
}

@Composable
@Preview(locale = "es")
fun RentersCoverageSummaryCardLocalizedPreview() {
    Surface(
        color = NeutralBackground
    ) {
        RentersCoverageSummaryCard(
            modifier = Modifier.padding(16.dp),
            coverage = RentersCoverageSummaryCardModel(
                id = "1",
                address = "Miami, FL",
                type = DwellingType.SingleFamily,
                status = CoverageStatusUi.Active
            ),
            onPressed = {}
        )
    }
}
