package com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItemsAddItem

import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.startEndPaddingScreen
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.BackButton
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.FooterSection
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.KeyboardAsState
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.MonetaryTextField
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.FooterSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.InvoiceInterval
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.Keyboard
import com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItemsCategory.domain.ScheduledItemsCategoryItemModelUi
import com.insurtech.kanguro.designsystem.ui.theme.MobaSubheadRegular
import com.insurtech.kanguro.designsystem.ui.theme.NeutralMedium

@Composable
fun ScheduledItemsAddItemScreenContent(
    modifier: Modifier = Modifier,
    selectedCategory: ScheduledItemsCategoryItemModelUi,
    newItemName: String,
    onNewItemNameChange: (String) -> Unit,
    onBackPressed: () -> Unit,
    newItemPrice: String,
    onNewItemPriceChange: (String) -> Unit,
    footerSection: FooterSectionModel?,
    onSubmitPressed: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .startEndPaddingScreen()
            .verticalScroll(rememberScrollState())
    ) {
        val isKeyboardOpen by KeyboardAsState()

        Header(
            onBackPressed = { onBackPressed() },
            selectedCategory = selectedCategory
        )

        MiddleScreen(
            newItemName = newItemName,
            onNewItNameChange = { onNewItemNameChange(it) },
            onNewItemPriceChange = { onNewItemPriceChange(it) },
            newItemPrice = newItemPrice
        )

        Spacer(
            modifier = Modifier
                .weight(1f)
        )

        if (isKeyboardOpen == Keyboard.Closed) {
            Footer(
                footerSectionModel = footerSection,
                isEnable = footerSection?.buttonPrice != null && newItemName.isNotEmpty()
            ) {
                onSubmitPressed()
            }
        }
    }
}

@Composable
private fun Header(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    selectedCategory: ScheduledItemsCategoryItemModelUi
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        BackButton(
            onBackPressed = { onBackPressed() },
            modifier = Modifier.padding(
                top = 25.dp
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        ScheduledItemSelectedCategory(selectedCategory = selectedCategory)
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun MiddleScreen(
    modifier: Modifier = Modifier,
    newItemName: String,
    newItemPrice: String,
    onNewItemPriceChange: (String) -> Unit,
    onNewItNameChange: (String) -> Unit
) {
    val (focusRequester) = FocusRequester.createRefs()

    Column(
        modifier = modifier.padding(top = 24.dp)
    ) {
        ScheduledItemTextField(
            text = newItemName,
            keyboardOptionsOnNext = focusRequester,
            onValueChange = {
                onNewItNameChange(it)
            }
        )
        MonetaryTextField(
            value = newItemPrice,
            keyboardOptionsOnNext = focusRequester,
            onValueChange = {
                onNewItemPriceChange(it)
            },
            modifier = Modifier.padding(top = 16.dp),
            title = {
                Text(
                    text = stringResource(id = R.string.item_value),
                    style = MobaSubheadRegular.copy(color = NeutralMedium)
                )
            }
        )
    }
}

@Composable
private fun Footer(
    footerSectionModel: FooterSectionModel?,
    isEnable: Boolean,
    onSubmitPressed: () -> Unit
) {
    FooterSection(
        modifier = Modifier,
        model = footerSectionModel,
        isEnable = isEnable,
        footerText = R.string.resume_feel_price,
        buttonText = R.string.scheduled_item_submit_label,
        onSubmitPressed = onSubmitPressed
    )
    Spacer(modifier = Modifier.height(35.dp))
}

@Preview
@Composable
fun ScheduledItemAddItemScreenContentPreview() {
    Surface {
        ScheduledItemsAddItemScreenContent(
            selectedCategory = ScheduledItemsCategoryItemModelUi("Jewelry", "Jewelry"),
            onBackPressed = {},
            newItemName = "",
            onNewItemNameChange = {},
            onNewItemPriceChange = {},
            newItemPrice = "",
            onSubmitPressed = {},
            footerSection = FooterSectionModel(
                100.00.toBigDecimal(),
                1000.00.toBigDecimal(),
                false,
                InvoiceInterval.MONTHLY
            )
        )
    }
}
