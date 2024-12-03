package com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItemsSelectedPictures

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.AddPictureBottomSheet
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.BackButton
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.JavierHeader
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.KanguroButton
import com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItemsSelectedPictures.model.SelectedPictureUi
import com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItemsSelectedPictures.model.SelectedPicturesEvent
import com.insurtech.kanguro.domain.model.ScheduledItemImageType

@Composable
fun ScheduledItemsSelectedPicturesScreenContent(
    modifier: Modifier = Modifier,
    receiptPictures: List<SelectedPictureUi>,
    itemPictures: List<SelectedPictureUi>,
    itemOnReceiptPictures: List<SelectedPictureUi>,
    isSubmitButtonEnabled: Boolean,
    showAddPictureBottomSheet: Boolean = false,
    onEvent: (SelectedPicturesEvent) -> Unit
) {
    if (showAddPictureBottomSheet) {
        AddPictureBottomSheet(
            onDismiss = { onEvent(SelectedPicturesEvent.CloseAddPictureBottomSheet) },
            onTakePicturePressed = {
                onEvent(
                    SelectedPicturesEvent.CapturePicture(
                        ScheduledItemImageType.ReceiptOrAppraisal
                    )
                )
            },
            onSelectFilePressed = {
                onEvent(
                    SelectedPicturesEvent.SelectPicture(
                        ScheduledItemImageType.ReceiptOrAppraisal
                    )
                )
            }
        )
    }

    ConstraintLayout(
        modifier = modifier.fillMaxSize()
    ) {
        val (header, contentSection) = createRefs()

        Header(
            modifier = Modifier.constrainAs(header) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            onBackPressed = { onEvent(SelectedPicturesEvent.Back) }
        )

        LazyColumn(
            modifier = Modifier
                .constrainAs(contentSection) {
                    top.linkTo(header.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                },
            verticalArrangement = Arrangement.SpaceBetween,
            content = contentSection(
                receiptPictures,
                itemPictures,
                itemOnReceiptPictures,
                isSubmitButtonEnabled,
                onEvent
            )
        )
    }
}

@Composable
private fun contentSection(
    receiptPictures: List<SelectedPictureUi>,
    itemPictures: List<SelectedPictureUi>,
    itemOnReceiptPictures: List<SelectedPictureUi>,
    isSubmitButtonEnabled: Boolean,
    onEvent: (SelectedPicturesEvent) -> Unit
): LazyListScope.() -> Unit = {
    item {
        PicturesSections(
            modifier = Modifier,
            receiptPictures = receiptPictures,
            itemPictures = itemPictures,
            itemOnReceiptPictures = itemOnReceiptPictures,
            onEvent = onEvent
        )
    }
    item {
        KanguroButton(
            modifier = Modifier
                .padding(32.dp),
            enabled = isSubmitButtonEnabled,
            text = stringResource(
                id = R.string.submit
            )
        ) { onEvent(SelectedPicturesEvent.Done) }
    }
}

@Composable
fun Header(modifier: Modifier = Modifier, onBackPressed: () -> Unit) {
    Column(
        modifier = modifier.padding(start = 32.dp, top = 32.dp, end = 32.dp),
        horizontalAlignment = Alignment.Start
    ) {
        BackButton {
            onBackPressed()
        }

        JavierHeader(
            title = stringResource(id = R.string.scheduled_items_picture_selected_title),
            modifier = Modifier.padding(top = 32.dp)
        )
    }
}

@Composable
fun PicturesSections(
    modifier: Modifier = Modifier,
    receiptPictures: List<SelectedPictureUi>,
    itemPictures: List<SelectedPictureUi>,
    itemOnReceiptPictures: List<SelectedPictureUi>,
    onEvent: (SelectedPicturesEvent) -> Unit
) {
    Column(modifier = modifier) {
        SelectedPictureSection(
            title = stringResource(id = R.string.picture_of_receipt),
            modifier = Modifier
                .padding(start = 32.dp, top = 32.dp, end = 32.dp)
                .fillMaxWidth(),
            onAddPressed = {
                onEvent(
                    SelectedPicturesEvent.ShowAddPictureBottomSheet(
                        ScheduledItemImageType.ReceiptOrAppraisal
                    )
                )
            },
            content = {
                PicturesComponent(receiptPictures) { pictureId ->
                    onEvent(
                        SelectedPicturesEvent.DeletePicture(
                            pictureId,
                            ScheduledItemImageType.ReceiptOrAppraisal
                        )
                    )
                }
            }
        )

        SelectedPictureSection(
            title = stringResource(id = R.string.picture_of_your_item),
            modifier = Modifier
                .padding(start = 32.dp, top = 32.dp, end = 32.dp)
                .fillMaxWidth(),
            onAddPressed = { onEvent(SelectedPicturesEvent.CapturePicture(ScheduledItemImageType.Item)) },
            content = {
                PicturesComponent(itemPictures) { pictureId ->
                    onEvent(
                        SelectedPicturesEvent.DeletePicture(
                            pictureId,
                            ScheduledItemImageType.Item
                        )
                    )
                }
            }
        )

        SelectedPictureSection(
            title = stringResource(id = R.string.picture_of_your_item_placed_on_receipt),
            modifier = Modifier
                .padding(start = 32.dp, top = 32.dp, end = 32.dp)
                .fillMaxWidth(),
            onAddPressed = { onEvent(SelectedPicturesEvent.CapturePicture(ScheduledItemImageType.ItemWithReceiptOrAppraisal)) },
            content = {
                PicturesComponent(itemOnReceiptPictures) { pictureId ->
                    onEvent(
                        SelectedPicturesEvent.DeletePicture(
                            pictureId,
                            ScheduledItemImageType.ItemWithReceiptOrAppraisal
                        )
                    )
                }
            }
        )
    }
}

@Composable
private fun PicturesComponent(
    receiptPictures: List<SelectedPictureUi>,
    onDeletePicturePressed: (Int) -> Unit
) {
    if (receiptPictures.isNotEmpty()) {
        DeletablePictureListComponent(
            onDeleteItemPressed = onDeletePicturePressed,
            pictures = receiptPictures
        )
    } else {
        Image(
            painter = painterResource(id = R.drawable.ic_image_place_holder),
            contentDescription = ""
        )
    }
}

@Composable
@Preview
private fun ScheduledItemsSelectedPicturesScreenContentPreview() {
    Surface {
        ScheduledItemsSelectedPicturesScreenContent(
            modifier = Modifier.fillMaxWidth(),
            receiptPictures = listOf(
                SelectedPictureUi(
                    id = 1
                ),
                SelectedPictureUi(
                    id = 2
                ),
                SelectedPictureUi(
                    id = 3
                )
            ),
            itemPictures = listOf(),
            itemOnReceiptPictures = listOf(),
            isSubmitButtonEnabled = true,
            onEvent = {}
        )
    }
}
