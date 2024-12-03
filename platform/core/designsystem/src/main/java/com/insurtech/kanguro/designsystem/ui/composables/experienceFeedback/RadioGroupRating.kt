package com.insurtech.kanguro.designsystem.ui.composables.experienceFeedback

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.KanguroCustomFeedbackCheckBox
import com.insurtech.kanguro.designsystem.ui.theme.PrimaryMedium
import com.insurtech.kanguro.designsystem.ui.theme.White

enum class Rating(
    @StringRes val label: Int
) {
    ONE(label = R.string.very_bad),
    TWO(label = R.string.bad),
    THREE(label = R.string.ok),
    FOUR(label = R.string.good),
    FIVE(label = R.string.amazing)
}

@Composable
fun RadioGroupRating(
    modifier: Modifier = Modifier,
    backgroundColor: Color = White,
    onRatingSelected: (Rating) -> Unit
) {
    var selectedOption by remember { mutableStateOf<Rating?>(null) }

    Row(
        modifier = modifier.background(color = backgroundColor),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Rating.values().forEach { rating ->
            KanguroCustomFeedbackCheckBox(
                modifier = Modifier.width(56.dp).background(color = backgroundColor),
                label = (rating.ordinal + 1).toString(),
                isChecked = selectedOption == rating,
                onCheckedChange = {
                    selectedOption = rating
                    onRatingSelected(rating)
                }
            )
        }
    }
}

@Composable
@Preview
fun RadioGroupRatingPreview() {
    Surface {
        RadioGroupRating(
            modifier = Modifier.padding(16.dp)
        ) {}
    }
}

@Composable
@Preview
fun RadioGroupRatingFeedbackPreview() {
    Surface {
        RadioGroupRating(
            backgroundColor = PrimaryMedium
        ) {}
    }
}
