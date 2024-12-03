package com.insurtech.kanguro.designsystem.ui.composables.petTrackYourClaims

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.ScreenLoader
import com.insurtech.kanguro.designsystem.ui.theme.spacingQuark

@Composable
fun ClaimTrackerPetImage(
    modifier: Modifier = Modifier,
    pictureUrl: String?,
    placeHolder: Int
) {
    Box(
        modifier = modifier.size(48.dp)
    ) {
        SubcomposeAsyncImage(
            model = pictureUrl,
            loading = {
                ScreenLoader(
                    modifier = Modifier
                        .padding(spacingQuark)
                        .fillMaxWidth()
                )
            },
            error = {
                Image(
                    painter = painterResource(id = placeHolder),
                    modifier = Modifier.fillMaxWidth(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            },
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CircleShape)
                .fillMaxWidth()
        )
    }
}
