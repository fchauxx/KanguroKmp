package com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItemsCategory

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.theme.MSansSemiBoldSecondaryDarkest21

@Composable
fun ScheduledItemCategoryTitle(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(id = R.string.scheduled_items_category_title),
        style = MSansSemiBoldSecondaryDarkest21,
        modifier = modifier.fillMaxWidth()
    )
}

@Preview
@Composable
private fun ScheduledItemCategoryTitlePreview() {
    Surface {
        ScheduledItemCategoryTitle()
    }
}
