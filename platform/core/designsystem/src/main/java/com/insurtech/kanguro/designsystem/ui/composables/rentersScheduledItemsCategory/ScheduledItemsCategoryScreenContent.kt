package com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItemsCategory

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.insurtech.kanguro.designsystem.startEndPaddingScreen
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.BackButton
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.CenterProgressBar
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.TopGradientLine
import com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItemsCategory.domain.ScheduledItemsCategoryItemModelUi
import com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItemsCategory.domain.getScheduledItemsCategoryItemModelUiMockList
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDarkest

@Composable
fun ScheduledItemsCategoryScreenContent(
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
    list: List<ScheduledItemsCategoryItemModelUi>,
    onCategoryPressed: (ScheduledItemsCategoryItemModelUi) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .startEndPaddingScreen()
    ) {
        BackButton(
            onBackPressed = { onBackPressed() },
            modifier = Modifier.padding(
                top = 25.dp
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        ScheduledItemCategoryTitle()

        Content(list = list, onCategoryPressed = { onCategoryPressed(it) })
    }
}

@Composable
fun ScheduledItemsCategoryScreenLoaderState(
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
    ) {
        val (header, loader) = createRefs()

        Column(
            modifier = Modifier
                .startEndPaddingScreen()
                .constrainAs(header) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            ScreenLoaderStateHeader(onBackPressed = { onBackPressed() })
        }

        CenterProgressBar(
            color = SecondaryDarkest,
            modifier = Modifier.constrainAs(loader) {
                top.linkTo(header.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                height = Dimension.fillToConstraints
            }
        )
    }
}

@Composable
private fun ScreenLoaderStateHeader(
    onBackPressed: () -> Unit
) {
    BackButton(
        onBackPressed = { onBackPressed() },
        modifier = Modifier.padding(
            top = 25.dp
        )
    )

    Spacer(modifier = Modifier.heightIn(24.dp))

    ScheduledItemCategoryTitle()
}

@Composable
private fun Content(
    list: List<ScheduledItemsCategoryItemModelUi>,
    onCategoryPressed: (ScheduledItemsCategoryItemModelUi) -> Unit
) {
    ConstraintLayout {
        val (gradient, listComponent) = createRefs()

        ScheduledItemsCategoryList(
            list = list,
            onCategoryPressed = { onCategoryPressed(it) },
            modifier = Modifier.constrainAs(listComponent) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }
        )

        TopGradientLine(
            modifier = Modifier
                .constrainAs(gradient) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
    }
}

@Preview
@Composable
private fun ScheduledItemsCategoryScreenContentPreview() {
    Surface {
        ScheduledItemsCategoryScreenContent(
            onBackPressed = {},
            onCategoryPressed = {},
            list = getScheduledItemsCategoryItemModelUiMockList()
        )
    }
}

@Preview
@Composable
private fun ScheduledItemsCategoryScreenLoaderPreview() {
    Surface {
        ScheduledItemsCategoryScreenLoaderState(
            onBackPressed = {}
        )
    }
}
