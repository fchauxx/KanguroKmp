package com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.toDollarFormat
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.model.ChipItemModel
import com.insurtech.kanguro.designsystem.ui.theme.BKSParagraphBold
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDarkest
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryLightest
import com.insurtech.kanguro.designsystem.ui.theme.TertiaryExtraDark
import com.insurtech.kanguro.designsystem.ui.theme.White
import java.math.BigDecimal

@Composable
fun ChipItem(
    modifier: Modifier = Modifier,
    chipItemModel: ChipItemModel,
    onPressed: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (chipItemModel.isMostPopular) {
            Surface(
                modifier = Modifier.heightIn(24.dp),
                shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
                color = TertiaryExtraDark
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                    text = stringResource(id = R.string.most_popular),
                    style = BKSParagraphBold.copy(color = White)
                )
            }
        } else {
            Spacer(modifier = Modifier.height(24.dp))
        }

        CardButton(
            isSelected = chipItemModel.isSelected,
            value = chipItemModel.value,
            onPressed = { onPressed() }
        )
    }
}

@Composable
private fun CardButton(isSelected: Boolean, value: BigDecimal, onPressed: () -> Unit) {
    val backgroundColor by animateColorAsState(
        if (isSelected) SecondaryDarkest else White,
        label = ""
    )
    val textColor by animateColorAsState(
        if (isSelected) White else SecondaryDarkest,
        label = ""
    )

    Button(
        onClick = { onPressed() },
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor),
        shape = RoundedCornerShape(8.dp),
        border = if (isSelected) null else BorderStroke(width = 1.dp, color = SecondaryLightest),
        elevation = ButtonDefaults.elevation(0.dp)
    ) {
        Text(
            text = stringResource(id = R.string.dollar) + value.toDollarFormat(),
            style = BKSParagraphBold.copy(color = textColor)
        )
    }
}

@Composable
@Preview
private fun LiabilityChipItemPreview() {
    Surface {
        ChipItem(
            modifier = Modifier.padding(16.dp),
            chipItemModel = ChipItemModel(
                id = "1",
                value = 1000.0.toBigDecimal(),
                isMostPopular = false,
                isSelected = false
            ),
            onPressed = {}
        )
    }
}

@Composable
@Preview
private fun ChipItemMostPopularPreview() {
    Surface {
        ChipItem(
            modifier = Modifier.padding(16.dp),
            chipItemModel = ChipItemModel(
                id = "1",
                value = 1000.0.toBigDecimal(),
                isMostPopular = true,
                isSelected = false
            ),
            onPressed = {}
        )
    }
}

@Composable
@Preview
private fun ChipItemSelectedPreview() {
    Surface {
        ChipItem(
            modifier = Modifier.padding(16.dp),
            chipItemModel = ChipItemModel(
                id = "1",
                value = 1000.0.toBigDecimal(),
                isMostPopular = false,
                isSelected = false
            ),
            onPressed = {}
        )
    }
}

@Composable
@Preview
private fun ChipItemMostPopularSelectedPreview() {
    Surface {
        ChipItem(
            modifier = Modifier.padding(16.dp),
            chipItemModel = ChipItemModel(
                id = "1",
                value = 1000.0.toBigDecimal(),
                isMostPopular = true,
                isSelected = false
            ),
            onPressed = {}
        )
    }
}
