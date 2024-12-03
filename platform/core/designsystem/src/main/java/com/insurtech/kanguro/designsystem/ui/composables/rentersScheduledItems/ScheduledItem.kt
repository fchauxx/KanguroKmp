package com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItems

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.toDollarFormat
import com.insurtech.kanguro.designsystem.ui.theme.GreenLand
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDark
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDarkest
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryLightest
import com.insurtech.kanguro.designsystem.ui.theme.White
import java.math.BigDecimal

@Composable
fun ScheduledItem(
    modifier: Modifier = Modifier,
    itemType: String,
    itemName: String,
    itemPrice: BigDecimal,
    isValid: Boolean,
    onDocumentPressed: () -> Unit,
    onTrashPressed: () -> Unit,
    isReadyOnly: Boolean = false
) {
    Row(
        modifier = modifier
            .validBackground(isValid = isValid)
    ) {
        Spacer(modifier = Modifier.width(3.dp))

        Content(
            itemType,
            itemName = itemName,
            itemPrice = itemPrice,
            isValid = isValid,
            onDocumentPressed = { onDocumentPressed() },
            onTrashPressed = { onTrashPressed() },
            isReadyOnly = isReadyOnly
        )
    }
}

@Composable
private fun Content(
    itemType: String,
    itemName: String,
    itemPrice: BigDecimal,
    isValid: Boolean,
    onDocumentPressed: () -> Unit,
    onTrashPressed: () -> Unit,
    isReadyOnly: Boolean = false
) {
    ConstraintLayout(
        modifier = Modifier
            .withBorder(isValid)
            .background(
                color = White,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        val (iconCheck, itemDescription, itemActions) = createRefs()

        if (isValid) {
            IconCheck(
                modifier = Modifier.constrainAs(iconCheck) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                }
            )
        }

        ItemDescription(
            itemType = itemType,
            itemName = itemName,
            itemPrice = itemPrice,
            isValid = isValid,
            modifier = Modifier
                .constrainAs(itemDescription) {
                    top.linkTo(parent.top)
                    if (isValid) {
                        start.linkTo(iconCheck.end)
                    } else {
                        start.linkTo(parent.start)
                    }
                    end.linkTo(itemActions.start)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                }
        )

        ItemActions(
            onDocumentPressed = { onDocumentPressed() },
            onTrashPressed = { onTrashPressed() },
            isReadyOnly = isReadyOnly,
            modifier = Modifier.constrainAs(itemActions) {
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }
        )
    }
}

@Composable
private fun IconCheck(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.ic_outline_check),
        contentDescription = null,
        modifier = modifier
            .padding(start = 16.dp)
    )
}

@Composable
private fun ItemDescription(
    modifier: Modifier = Modifier,
    itemType: String,
    itemName: String,
    itemPrice: BigDecimal,
    isValid: Boolean
) {
    val padding = if (isValid) 8.dp else 16.dp

    Column(
        modifier = modifier
            .padding(
                top = 16.dp,
                start = padding,
                bottom = 16.dp
            )
    ) {
        ItemName(font = FontWeight.Bold, itemName = itemName)
        ItemName(font = FontWeight.Normal, itemName = itemType)
        ItemPrice(itemPrice = itemPrice)
    }
}

@Composable
private fun ItemActions(
    modifier: Modifier = Modifier,
    onDocumentPressed: () -> Unit,
    isReadyOnly: Boolean,
    onTrashPressed: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(top = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        if (!isReadyOnly) {
            ItemDocument {
                onDocumentPressed()
            }
            ItemTrash {
                onTrashPressed()
            }
        }
    }
}

@Composable
private fun ItemName(font: FontWeight, itemName: String) {
    Text(
        text = itemName,
        style = TextStyle(
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.lato)),
            fontWeight = font,
            color = SecondaryDarkest,
            textAlign = TextAlign.Center
        )
    )
}

@Composable
private fun ItemPrice(itemPrice: BigDecimal) {
    Text(
        text = stringResource(id = R.string.dollar) + itemPrice.toDollarFormat(),
        style = TextStyle(
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.lato)),
            fontWeight = FontWeight(400),
            color = SecondaryDark
        )
    )
}

@Composable
private fun ItemDocument(
    onDocumentPressed: () -> Unit
) {
    IconButton(
        onClick = { onDocumentPressed() },
        modifier = Modifier
            .size(width = 25.dp, height = 25.dp)
            .padding(3.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_document_upload),
            contentDescription = null
        )
    }
}

@Composable
private fun ItemTrash(onTrashPressed: () -> Unit) {
    IconButton(
        onClick = { onTrashPressed() },
        modifier = Modifier
            .size(width = 25.dp, height = 25.dp)
            .padding(3.dp)

    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_trash),
            contentDescription = null
        )
    }
}

private fun Modifier.withBorder(isValid: Boolean): Modifier = this
    .fillMaxWidth()
    .border(
        width = 1.dp,
        color = if (isValid) GreenLand else SecondaryLightest,
        shape = RoundedCornerShape(size = 8.dp)
    )

private fun Modifier.validBackground(isValid: Boolean): Modifier =
    if (isValid) {
        this
            .background(
                color = GreenLand,
                shape = RoundedCornerShape(size = 8.dp)
            )
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp))
    } else {
        this
    }.fillMaxWidth()

@Preview
@Composable
private fun ScheduledItemPreview() {
    Surface {
        ScheduledItem(
            modifier = Modifier.padding(50.dp),
            itemType = "Instrument",
            itemName = "Macbook",
            BigDecimal(4000.00),
            onDocumentPressed = {},
            isValid = true,
            onTrashPressed = {}
        )
    }
}
