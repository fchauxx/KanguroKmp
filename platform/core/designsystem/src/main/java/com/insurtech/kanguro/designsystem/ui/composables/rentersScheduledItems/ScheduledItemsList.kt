package com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItems

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.domain.model.ScheduledItemModel
import com.insurtech.kanguro.domain.model.getScheduledItemModelMock

@Composable
fun ScheduledItemsList(
    itemsList: List<ScheduledItemModel>,
    modifier: Modifier = Modifier,
    onDocumentPressed: (String) -> Unit,
    onTrashPressed: (String) -> Unit,
    isReadyOnly: Boolean = false
) {
    LazyColumn(modifier = modifier, contentPadding = PaddingValues(top = 24.dp, bottom = 24.dp)) {
        items(itemsList) {
            ScheduledItem(
                itemName = it.itemName,
                itemType = it.type,
                itemPrice = it.itemPrice,
                isReadyOnly = isReadyOnly,
                isValid = it.isValid,
                onDocumentPressed = { onDocumentPressed(it.id) },
                onTrashPressed = { onTrashPressed(it.id) },
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}

@Preview
@Composable
fun ScheduledItemListPreview() {
    Surface {
        ScheduledItemsList(
            itemsList = getScheduledItemModelMock(),
            modifier = Modifier.padding(32.dp),
            onTrashPressed = {},
            onDocumentPressed = {}
        )
    }
}
