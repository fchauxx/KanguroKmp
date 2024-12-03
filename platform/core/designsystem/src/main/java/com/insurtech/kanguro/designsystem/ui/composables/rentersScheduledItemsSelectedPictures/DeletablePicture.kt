package com.insurtech.kanguro.designsystem.ui.composables.rentersScheduledItemsSelectedPictures

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R

@Composable
fun DeletablePictureStateful(
    modifier: Modifier = Modifier,
    @DrawableRes placeholder: Int = R.drawable.ic_image_place_holder,
    onDeletePressed: () -> Unit,
    content: @Composable (() -> Unit)? = null
) {
    var showDelete by remember { mutableStateOf(false) }

    DeletablePicture(
        modifier = modifier,
        placeholder = placeholder,
        showDelete = showDelete,
        onPressed = {
            showDelete = !showDelete
        },
        onDeletePressed = onDeletePressed,
        content = content
    )
}

@Composable
fun DeletablePicture(
    modifier: Modifier = Modifier,
    @DrawableRes placeholder: Int = R.drawable.ic_image_place_holder,
    showDelete: Boolean = false,
    onDeletePressed: () -> Unit,
    onPressed: () -> Unit,
    content: @Composable (() -> Unit)? = null
) {
    Box(
        modifier = modifier
            .clickable { onPressed() }
            .clip(RoundedCornerShape(8.dp))
    ) {
        if (content != null) {
            content()
        } else {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = placeholder),
                contentDescription = null
            )
        }
        AnimatedVisibility(
            visible = showDelete,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            DeleteComponent(onDeletePressed = onDeletePressed)
        }
    }
}

@Composable
fun DeleteComponent(
    modifier: Modifier = Modifier,
    onDeletePressed: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .clickable {
                onDeletePressed()
            }
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_trash),
            colorFilter = ColorFilter.tint(Color.White),
            contentDescription = "",
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.Center)
        )
    }
}

@Composable
@Preview
fun DeletablePictureThumbnailPreview() {
    Surface {
        DeletablePictureStateful(
            modifier = Modifier
                .size(72.dp)
                .padding(8.dp),
            content = {
                Image(
                    painter = painterResource(id = R.drawable.img_dog_cellphone),
                    contentDescription = ""
                )
            },
            onDeletePressed = {}
        )
    }
}

@Composable
@Preview
fun DeletablePlaceHolderPreview() {
    Surface {
        DeletablePictureStateful(
            modifier = Modifier
                .size(72.dp)
                .padding(8.dp),
            content = null,
            onDeletePressed = {}
        )
    }
}

@Composable
@Preview
fun DeleteComponentPreview() {
    Surface {
        DeleteComponent(
            modifier = Modifier
                .size(72.dp)
                .padding(8.dp),
            onDeletePressed = {}
        )
    }
}
