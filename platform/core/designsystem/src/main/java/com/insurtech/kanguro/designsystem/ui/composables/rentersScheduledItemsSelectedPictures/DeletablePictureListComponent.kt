package com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItemsSelectedPictures

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.ScreenLoader
import com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItemsSelectedPictures.model.SelectedPictureUi

@Composable
fun DeletablePictureListComponent(
    modifier: Modifier = Modifier,
    onDeleteItemPressed: (Int) -> Unit,
    pictures: List<SelectedPictureUi>
) {
    pictures.forEach { it.selected = false }

    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(pictures) { picture ->
            DeletablePicture(
                modifier = Modifier.size(72.dp),
                showDelete = picture.selected,
                onPressed = {
                    if (pictures.any { it.selected }) {
                        pictures.forEach { it.selected = false }
                    } else {
                        pictures.find { it.id == picture.id }?.selected = true
                    }
                },
                onDeletePressed = { onDeleteItemPressed(picture.id) },
                content = {
                    SubcomposeAsyncImage(
                        model = picture.url,
                        loading = {
                            ScreenLoader(
                                modifier = Modifier.fillMaxSize().padding(8.dp)
                            )
                        },
                        error = {
                            Image(
                                painter = painterResource(id = R.drawable.ic_image_place_holder),
                                contentDescription = null
                            )
                        },
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
            )
        }
    }
}

@Composable
@Preview
fun DeletablePictureListComponentPreview() {
    Surface {
        DeletablePictureListComponent(
            modifier = Modifier.padding(16.dp),
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
}
