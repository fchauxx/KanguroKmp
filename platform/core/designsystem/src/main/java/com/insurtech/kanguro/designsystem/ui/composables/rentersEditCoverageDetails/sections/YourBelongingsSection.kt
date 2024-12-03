package com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.sections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.insurtech.kanguro.designsystem.toDollarFormatNoDecimal
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.KanguroSlider
import com.insurtech.kanguro.designsystem.ui.theme.BKSHeading4
import com.insurtech.kanguro.designsystem.ui.theme.BKSParagraphRegular
import com.insurtech.kanguro.designsystem.ui.theme.LatoBoldNeutral
import com.insurtech.kanguro.designsystem.ui.theme.TertiaryDarkest

@Composable
fun YourBelongingsSection(
    selectedValue: Float,
    modifier: Modifier = Modifier,
    minValue: Float,
    maxValue: Float,
    isEnabled: Boolean = true,
    onValueChange: (Float) -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Title()

        Spacer(modifier = Modifier.height(24.dp))

        Instructions()

        Spacer(modifier = Modifier.height(32.dp))

        SelectedValue(value = selectedValue)

        Spacer(modifier = Modifier.height(5.dp))

        KanguroSlider(
            modifier = Modifier.padding(horizontal = 23.dp),
            minValue = minValue,
            maxValue = maxValue,
            isEnablad = isEnabled,
            onValueChange = onValueChange,
            initialValue = selectedValue
        )

        SliderMinMaxValue(
            minValue = minValue,
            maxValue = maxValue
        )
    }
}

@Composable
private fun Title() {
    Text(
        text = stringResource(id = R.string.your_belongings),
        style = BKSHeading4,
        modifier = Modifier.padding(horizontal = 32.dp)
    )
}

@Composable
private fun Instructions() {
    Text(
        text = stringResource(id = R.string.your_belongings_instructions),
        style = BKSParagraphRegular,
        modifier = Modifier.padding(horizontal = 32.dp)
    )
}

@Composable
private fun SelectedValue(
    value: Float
) {
    Text(
        text = "${stringResource(id = R.string.dollar)} ${value.toDollarFormatNoDecimal()}",
        style = BKSHeading4.copy(color = TertiaryDarkest),
        modifier = Modifier.padding(horizontal = 32.dp)
    )
}

@Composable
private fun SliderMinMaxValue(
    minValue: Float,
    maxValue: Float
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
    ) {
        Text(
            text = "${stringResource(id = R.string.dollar)} ${minValue.toDollarFormatNoDecimal()}",
            style = LatoBoldNeutral
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "${stringResource(id = R.string.dollar)} ${maxValue.toDollarFormatNoDecimal()}",
            style = LatoBoldNeutral
        )
    }
}

@Preview
@Composable
private fun YourBelongingsSectionPreview() {
    Surface {
        YourBelongingsSection(
            minValue = 5000.00f,
            maxValue = 50000.00f,
            selectedValue = 5000.00f,
            onValueChange = {}
        )
    }
}
