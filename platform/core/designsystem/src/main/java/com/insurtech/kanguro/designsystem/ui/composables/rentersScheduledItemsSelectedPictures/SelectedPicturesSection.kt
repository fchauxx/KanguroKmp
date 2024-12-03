package com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItemsSelectedPictures

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItemsSelectedPictures.model.SelectedPictureUi
import com.insurtech.kanguro.designsystem.ui.theme.LatoBoldSecondaryDarkest16
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDarkest
import com.insurtech.kanguro.designsystem.ui.theme.White

@Composable
fun SelectedPictureSection(
    modifier: Modifier = Modifier,
    title: String,
    canAdd: Boolean = true,
    onAddPressed: () -> Unit,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
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
                canAdd = canAdd,
                onClick = onAddPressed
            )
        }
        content()
    }
}

@Composable
fun AddButton(
    modifier: Modifier = Modifier,
    canAdd: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = canAdd,
        modifier = modifier
            .height(28.dp)
            .wrapContentWidth(),
        colors = ButtonDefaults.buttonColors(backgroundColor = SecondaryDarkest),
        shape = RoundedCornerShape(16.dp),
        elevation = ButtonDefaults.elevation(0.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        Text(
            modifier = Modifier.wrapContentSize(),
            text = stringResource(id = R.string.add),
            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.lato)),
                fontWeight = FontWeight.Black,
                lineHeight = 12.sp,
                color = White,
                textAlign = TextAlign.Center
            )
        )
    }
}

@Composable
@Preview
private fun SelectedPictureSectionEmptyListPreview() {
    Surface {
        SelectedPictureSection(
            title = stringResource(id = R.string.picture_of_your_item_placed_on_receipt),
            onAddPressed = {},
            content = {
                Image(
                    painter = painterResource(id = R.drawable.ic_image_place_holder),
                    contentDescription = ""
                )
            }
        )
    }
}

@Composable
@Preview
private fun SelectedPictureSectionPreview() {
    Surface {
        SelectedPictureSection(
            title = stringResource(id = R.string.picture_of_your_item_placed_on_receipt),
            onAddPressed = {},
            content = {
                DeletablePictureListComponent(
                    onDeleteItemPressed = {},
                    pictures = listOf(
                        SelectedPictureUi(
                            id = 1
                        ),
                        SelectedPictureUi(
                            id = 2
                        ),
                        SelectedPictureUi(
                            id = 3
                        ),
                        SelectedPictureUi(
                            id = 4
                        ),
                        SelectedPictureUi(
                            id = 5
                        ),
                        SelectedPictureUi(
                            id = 6
                        )
                    )
                )
            }
        )
    }
}

@Composable
@Preview
private fun AddButtonEnabledPreview() {
    Surface {
        AddButton(modifier = Modifier.padding(16.dp), onClick = {})
    }
}

@Composable
@Preview
private fun AddButtonDisabledPreview() {
    Surface {
        AddButton(modifier = Modifier.padding(16.dp), canAdd = false, onClick = {})
    }
}
