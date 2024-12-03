package com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItems

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.startEndPaddingScreen
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.BottomGradientLine
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.JavierTitleXClose
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.KanguroButton
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.OutlinedButton
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.RightButtonClose
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.TopGradientLine
import com.insurtech.kanguro.domain.model.ScheduledItemModel
import com.insurtech.kanguro.domain.model.getScheduledItemModelMock

@Composable
fun ScheduledItemsScreenContent(
    modifier: Modifier = Modifier,
    itemsList: List<ScheduledItemModel>,
    onClosePressed: () -> Unit,
    onDocumentPressed: (String) -> Unit,
    onTrashPressed: (String) -> Unit,
    onAddMoreItemsPressed: () -> Unit,
    onDoneButtonPressed: () -> Unit,
    isReadyOnly: Boolean = false
) {
    ConstraintLayout(
        modifier = modifier.fillMaxSize()
    ) {
        val (header, topDivider, list, footer, bottomDivider) = createRefs()

        JavierTitleXClose(
            title = stringResource(id = R.string.scheduled_items_title),
            onClosePressed = { onClosePressed() },
            modifier = Modifier.constrainAs(header) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        ScheduledItemsList(
            itemsList = itemsList,
            isReadyOnly = isReadyOnly,
            onDocumentPressed = onDocumentPressed,
            onTrashPressed = onTrashPressed,
            modifier = Modifier
                .startEndPaddingScreen()
                .constrainAs(list) {
                    top.linkTo(header.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(footer.top)
                    height = Dimension.fillToConstraints
                }
        )

        TopGradientLine(
            modifier = Modifier.constrainAs(topDivider) {
                top.linkTo(header.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        BottomGradientLine(
            modifier = Modifier.constrainAs(bottomDivider) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(footer.top)
            }
        )

        Footer(
            onAddMoreItemsPressed = { onAddMoreItemsPressed() },
            onDoneButtonPressed = { onDoneButtonPressed() },
            isReadyOnly = isReadyOnly,
            modifier = Modifier.constrainAs(footer) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }
        )
    }
}

@Composable
fun ScheduledItemsEmptyStateScreenContent(
    modifier: Modifier = Modifier,
    onClosePressed: () -> Unit,
    isReadyOnly: Boolean,
    onAddMoreItemsPressed: () -> Unit,
    onDoneButtonPressed: () -> Unit
) {
    ConstraintLayout(
        modifier = modifier.fillMaxSize()
    ) {
        val (header, topDivider, emptyStateImage, footer, bottomDivider) = createRefs()

        RightButtonClose(
            onClosePressed = { onClosePressed() },
            modifier = Modifier
                .constrainAs(header) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(
                    top = 8.dp,
                    end = 8.dp
                )
        )

        EmptyStateContent(
            modifier = Modifier
                .startEndPaddingScreen()
                .constrainAs(emptyStateImage) {
                    top.linkTo(header.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(footer.top)
                    height = Dimension.fillToConstraints
                }
        )

        TopGradientLine(
            modifier = Modifier.constrainAs(topDivider) {
                top.linkTo(header.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        BottomGradientLine(
            modifier = Modifier.constrainAs(bottomDivider) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(footer.top)
            }
        )

        Footer(
            onAddMoreItemsPressed = { onAddMoreItemsPressed() },
            onDoneButtonPressed = { onDoneButtonPressed() },
            isReadyOnly = isReadyOnly,
            modifier = Modifier.constrainAs(footer) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }
        )
    }
}

@Composable
private fun Footer(
    modifier: Modifier = Modifier,
    isReadyOnly: Boolean,
    onAddMoreItemsPressed: () -> Unit,
    onDoneButtonPressed: () -> Unit
) {
    Column(
        modifier
            .startEndPaddingScreen(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!isReadyOnly) {
            OutlinedButton(
                text = R.string.scheduled_items_add_more
            ) { onAddMoreItemsPressed() }
        }

        Spacer(modifier = Modifier.height(8.dp))

        KanguroButton(
            enabled = true,
            text = stringResource(id = R.string.done)
        ) { onDoneButtonPressed() }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Preview
@Composable
private fun ScheduledItemsFragmentContentPreview() {
    Surface {
        ScheduledItemsScreenContent(
            modifier = Modifier.height(900.dp),
            onClosePressed = {},
            onAddMoreItemsPressed = {},
            onDoneButtonPressed = {},
            itemsList = getScheduledItemModelMock(),
            onDocumentPressed = {},
            onTrashPressed = {}
        )
    }
}

@Composable
@Preview
private fun ScheduledItemsFragmentEmptyStateContentPreview() {
    Surface {
        ScheduledItemsEmptyStateScreenContent(
            modifier = Modifier.height(900.dp),
            onClosePressed = {},
            onAddMoreItemsPressed = {},
            onDoneButtonPressed = {},
            isReadyOnly = false
        )
    }
}
