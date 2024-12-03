package com.insurtech.kanguro.designsystem.ui.composables.chatbot.scheduledItems

import android.widget.TextView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.chatbot.ScheduledItemCheckBox
import com.insurtech.kanguro.designsystem.ui.composables.chatbot.scheduledItems.model.ChatbotScheduledItem
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.HeaderBackAndClose
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.KanguroButton
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyBold
import com.insurtech.kanguro.designsystem.ui.theme.MobaTitle3
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryMedium

@Composable
fun YourScheduledItemsScreenContent(
    modifier: Modifier = Modifier,
    scheduledItems: List<ChatbotScheduledItem>,
    onEvent: (YourScheduledItemsScreenEvent) -> Unit
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (closeButton, content, doneButton) = createRefs()

        HeaderBackAndClose(
            modifier = Modifier
                .constrainAs(closeButton) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(start = 8.dp, end = 8.dp, top = 8.dp),
            onClosePressed = { onEvent(YourScheduledItemsScreenEvent.OnClosePressed) }
        )

        Column(
            modifier = Modifier
                .constrainAs(content) {
                    top.linkTo(closeButton.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(doneButton.top)
                    height = Dimension.fillToConstraints
                }
                .fillMaxSize()
                .padding(horizontal = 32.dp)
                .padding(top = 10.dp, bottom = 32.dp)
        ) {
            Title()

            ItemsScrollList(scheduledItems, onEvent)
        }

        KanguroButton(
            modifier = Modifier
                .constrainAs(doneButton) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .padding(horizontal = 32.dp)
                .padding(bottom = 32.dp),
            text = stringResource(id = R.string.done),
            enabled = true
        ) {
            onEvent(YourScheduledItemsScreenEvent.OnDonePressed)
        }
    }
}

@Composable
private fun Title() {
    Text(
        text = stringResource(R.string.personal_property_claim).uppercase(),
        style = MobaBodyBold.copy(color = SecondaryMedium)
    )

    Text(
        modifier = Modifier.padding(vertical = 8.dp),
        text = stringResource(R.string.your_scheduled_items),
        style = MobaTitle3.copy(fontWeight = FontWeight.SemiBold)
    )
}

@Composable
private fun ItemsScrollList(
    scheduledItems: List<ChatbotScheduledItem>,
    onEvent: (YourScheduledItemsScreenEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        SelectInstructions(
            modifier = Modifier.padding(vertical = 8.dp),
            text = LocalContext.current.resources.getText(R.string.your_scheduled_items_description)
        )

        scheduledItems.forEach { scheduledItem ->
            ScheduledItemCheckBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                item = scheduledItem.name,
                value = scheduledItem.value,
                isChecked = scheduledItem.selected
            ) {
                onEvent(YourScheduledItemsScreenEvent.OnItemPressed(scheduledItem))
            }
        }
    }
}

@Composable
private fun SelectInstructions(
    modifier: Modifier = Modifier,
    text: CharSequence
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            TextView(context).apply {
                setTextAppearance(R.style.MobaSubheadRegular)
            }
        },
        update = {
            it.text = text
        }
    )
}

@Composable
@Preview(device = Devices.NEXUS_5)
private fun YourScheduledItemsScreenContentPreview() {
    Surface {
        YourScheduledItemsScreenContent(
            modifier = Modifier.fillMaxSize(),
            scheduledItems = listOf(
                ChatbotScheduledItem(id = "1", name = "Item 1", value = 100.toBigDecimal()),
                ChatbotScheduledItem(id = "2", name = "Item 2", value = 200.toBigDecimal()),
                ChatbotScheduledItem(id = "3", name = "Item 3", value = 300.toBigDecimal())
            ),
            onEvent = {}
        )
    }
}
