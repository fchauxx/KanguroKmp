package com.insurtech.kanguro.designsystem.ui.composables.chatbot.addingItem

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.chatbot.addingItem.model.AddedItemModel
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.HeaderBackAndClose
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.KanguroButton
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.MonetaryTextField
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.SelectedFileCard
import com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.domain.SelectedFileType
import com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.domain.SelectedFileUi
import com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItemsAddItem.ScheduledItemTextField
import com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItemsSelectedPictures.AddButton
import com.insurtech.kanguro.designsystem.ui.theme.LatoBoldSecondaryDarkest16
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyBold
import com.insurtech.kanguro.designsystem.ui.theme.MobaCaptionRegular
import com.insurtech.kanguro.designsystem.ui.theme.MobaSubheadRegular
import com.insurtech.kanguro.designsystem.ui.theme.MobaTitle3
import com.insurtech.kanguro.designsystem.ui.theme.NeutralMedium
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryMedium

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddingItemScreenContent(
    modifier: Modifier = Modifier,
    addedItemModel: AddedItemModel,
    onEvent: (AddingItemScreenEvent) -> Unit
) {
    val (focusRequester) = FocusRequester.createRefs()

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
            onClosePressed = { onEvent(AddingItemScreenEvent.OnClosePressed) }
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
            Spacer(modifier = Modifier.height(16.dp))
            Content(
                addedItemModel = addedItemModel,
                focusRequester = focusRequester,
                onEvent = onEvent
            )
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
            text = stringResource(id = R.string.scheduled_item_submit_label),
            enabled = true
        ) {
            onEvent(AddingItemScreenEvent.OnDonePressed)
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
        modifier = Modifier.padding(top = 8.dp),
        text = stringResource(R.string.adding_an_item),
        style = MobaTitle3
    )
}

@Composable
private fun Content(
    addedItemModel: AddedItemModel,
    focusRequester: FocusRequester,
    onEvent: (AddingItemScreenEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(top = 16.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        ScheduledItemTextField(
            text = addedItemModel.item.orEmpty(),
            keyboardOptionsOnNext = focusRequester,
            onValueChange = {
                onEvent(AddingItemScreenEvent.OnItemChanged(it))
            }
        )

        MonetaryTextField(
            value = addedItemModel.value.orEmpty(),
            keyboardOptionsOnNext = focusRequester,
            onValueChange = {
                onEvent(AddingItemScreenEvent.OnValueChanged(it))
            },
            modifier = Modifier.padding(top = 16.dp),
            title = {
                Text(
                    text = stringResource(id = R.string.item_value),
                    style = MobaSubheadRegular.copy(color = NeutralMedium)
                )
            }
        )

        AddFileComponent(
            title = stringResource(id = R.string.picture_of_your_item),
            instruction = stringResource(id = R.string.picture_of_the_item_instruction),
            onAddPressed = { onEvent(AddingItemScreenEvent.OnAddItemPicturePressed) },
            onDeletePressed = { onEvent(AddingItemScreenEvent.OnDeleteItemPicturePressed) },
            selectedFileUi = addedItemModel.itemSelectedPicture
        )

        AddFileComponent(
            title = stringResource(id = R.string.receipt_or_warranty_of_the_item),
            instruction = stringResource(id = R.string.receipt_or_warranty_of_the_item_instruction),
            onAddPressed = { onEvent(AddingItemScreenEvent.OnAddReceiptPicturePressed) },
            onDeletePressed = { onEvent(AddingItemScreenEvent.OnDeleteReceiptPicturePressed) },
            selectedFileUi = addedItemModel.itemReceiptPicture
        )
    }
}

@Composable
fun AddFileComponent(
    title: String,
    instruction: String,
    onAddPressed: () -> Unit,
    onDeletePressed: () -> Unit,
    selectedFileUi: SelectedFileUi?
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = LatoBoldSecondaryDarkest16,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                modifier = Modifier.weight(0.75f)
            )

            AddButton(
                modifier = Modifier.padding(start = 16.dp),
                canAdd = true,
                onClick = onAddPressed
            )
        }

        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = instruction,
            style = MobaCaptionRegular.copy(fontStyle = FontStyle.Italic)
        )

        AnimatedVisibility(visible = selectedFileUi != null) {
            selectedFileUi?.let {
                SelectedFileCard(modifier = Modifier.padding(top = 16.dp), selectedFileUi = it) {
                    onDeletePressed()
                }
            }
        }
    }
}

@Composable
@Preview(device = Devices.NEXUS_5)
private fun AddingItemScreenContentPreview() {
    Surface {
        AddingItemScreenContent(
            modifier = Modifier.fillMaxSize(),
            addedItemModel = AddedItemModel(),
            onEvent = {}
        )
    }
}

@Composable
@Preview(device = Devices.NEXUS_5)
private fun AddingItemScreenContentFullfilledPreview() {
    Surface {
        AddingItemScreenContent(
            modifier = Modifier.fillMaxSize(),
            addedItemModel = AddedItemModel(
                item = "Macbook Pro",
                value = "10,000",
                itemSelectedPicture = SelectedFileUi(
                    id = 1,
                    fileName = "macbook.jpg",
                    fileUrl = "",
                    type = SelectedFileType.Image
                ),
                itemReceiptPicture = SelectedFileUi(
                    id = 2,
                    fileName = "receipt.pdf",
                    fileUrl = "",
                    type = SelectedFileType.File
                )
            ),
            onEvent = {}
        )
    }
}
