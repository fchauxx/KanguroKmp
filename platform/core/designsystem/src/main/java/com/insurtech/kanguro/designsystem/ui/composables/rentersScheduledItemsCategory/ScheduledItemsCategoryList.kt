package com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItemsCategory

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.getScheduledItemsCategoryType
import com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItemsCategory.domain.ScheduledItemsCategoryItemModelUi
import com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItemsCategory.domain.getIcon
import com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItemsCategory.domain.getScheduledItemsCategoryItemModelUiMockList

@Composable
fun ScheduledItemsCategoryList(
    list: List<ScheduledItemsCategoryItemModelUi>,
    modifier: Modifier = Modifier,
    onCategoryPressed: (ScheduledItemsCategoryItemModelUi) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(top = 16.dp, bottom = 2.dp)
    ) {
        items(list) {
            ScheduledItemsCategoryItem(
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                onCategoryPressed = { onCategoryPressed(it) },
                text = it.label,
                iconRes = it.id.getScheduledItemsCategoryType()
                    .getIcon()
            )
        }
    }
}

@Preview
@Composable
private fun ScheduledItemsCategoryListPreview() {
    Surface {
        ScheduledItemsCategoryList(
            modifier = Modifier.padding(16.dp),
            onCategoryPressed = {},
            list = getScheduledItemsCategoryItemModelUiMockList()
        )
    }
}
