package com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.HorizontalDivider
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.section.SectionHeaderModel
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyBold
import com.insurtech.kanguro.designsystem.ui.theme.MobaFootnoteBlack
import com.insurtech.kanguro.designsystem.ui.theme.MobaFootnoteRegular
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryLightest

@Composable
fun SectionHeader(
    modifier: Modifier = Modifier,
    model: SectionHeaderModel = SectionHeaderModel()
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        TopSection(
            icon = model.icon,
            period = stringResource(
                id = R.string.period_dates,
                model.startDate,
                model.endDate
            )
        )
        MainContent(model.title, model.subtitle, model.renewDate)
        HorizontalDivider(
            modifier = Modifier
                .padding(top = 12.dp)
                .height(1.dp),
            color = SecondaryLightest
        )
    }
}

@Composable
private fun TopSection(icon: Int?, period: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (icon != null) {
            Image(painter = painterResource(id = icon), contentDescription = "")
        }

        if (period.isNotBlank()) {
            Text(text = period, style = MobaFootnoteRegular)
        }
    }
}

@Composable
private fun MainContent(
    title: String,
    subtitle: String,
    renewDate: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = title, style = MobaBodyBold)

            if (subtitle.isNotBlank()) {
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = subtitle,
                    style = MobaFootnoteBlack
                )
            }
        }

        if (renewDate.isNotBlank()) {
            Text(
                text = stringResource(id = R.string.renew_date, renewDate),
                style = MobaFootnoteRegular
            )
        }
    }
}

@Composable
@Preview
private fun SectionHeaderFullFilledPreview() {
    Surface {
        SectionHeader(
            modifier = Modifier.padding(16.dp),
            model = SectionHeaderModel(
                title = stringResource(id = R.string.renters_insurance),
                subtitle = stringResource(id = R.string.renters_insurance).uppercase(),
                renewDate = "10/10/2024",
                startDate = "10/10/2023",
                endDate = "10/10/2024",
                icon = R.drawable.ic_renters_coverage_home
            )
        )
    }
}

@Composable
@Preview
private fun SectionHeaderSimplifiedPreview() {
    Surface {
        SectionHeader(
            modifier = Modifier.padding(16.dp),
            model = SectionHeaderModel(
                title = stringResource(id = R.string.renters_additional_coverage),
                icon = R.drawable.ic_renters_award
            )
        )
    }
}
