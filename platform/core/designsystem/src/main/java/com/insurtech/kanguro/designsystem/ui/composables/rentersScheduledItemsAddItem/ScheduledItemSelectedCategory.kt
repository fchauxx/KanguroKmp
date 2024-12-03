package com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItemsAddItem

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.getScheduledItemsCategoryType
import com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItemsCategory.domain.ScheduledItemsCategoryItemModelUi
import com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItemsCategory.domain.getIcon
import com.insurtech.kanguro.designsystem.ui.theme.MSansSemiBoldSecondaryDarkest25
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground

@Composable
fun ScheduledItemSelectedCategory(
    modifier: Modifier = Modifier,
    selectedCategory: ScheduledItemsCategoryItemModelUi
) {
    Row(
        modifier = modifier
            .background(
                color = NeutralBackground,
                shape = RoundedCornerShape(
                    topStart = 8.dp,
                    topEnd = 8.dp
                )
            )
            .padding(vertical = 17.dp, horizontal = 28.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(itemId = selectedCategory.id)
        CategoryName(label = selectedCategory.label)
    }
}

@Composable
private fun Icon(
    itemId: String
) {
    Image(
        painter = painterResource(
            id = itemId.getScheduledItemsCategoryType()
                .getIcon()
        ),
        contentDescription = null
    )
}

@Composable
private fun CategoryName(
    label: String
) {
    Text(
        text = label,
        style = MSansSemiBoldSecondaryDarkest25,
        modifier = Modifier.padding(start = 22.dp)
    )
}

@Composable
@Preview
private fun ScheduledItemSelectedCategoryPreview() {
    Surface {
        ScheduledItemSelectedCategory(
            modifier = Modifier
                .padding(16.dp),
            selectedCategory = ScheduledItemsCategoryItemModelUi(
                "Jewelry",
                "Jewelry"
            )
        )
    }
}
