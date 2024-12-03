package com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.sections

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import coil.compose.SubcomposeAsyncImage
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.ScreenLoader
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.DwellingType
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.RentersCoverageDetailsEvent
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.section.HeaderSectionModel
import com.insurtech.kanguro.designsystem.ui.theme.White

@Composable
fun HeaderSection(
    modifier: Modifier = Modifier,
    progress: Float,
    model: HeaderSectionModel,
    onEvent: (RentersCoverageDetailsEvent) -> Unit,
    onHeightChanged: (Int) -> Unit
) {
    val context = LocalContext.current

    val motionScene = remember {
        context.resources
            .openRawResource(R.raw.renters_coverage_details_screen_content_motion_scene)
            .readBytes()
            .decodeToString()
    }

    MotionLayout(
        modifier = modifier.fillMaxSize(),
        motionScene = MotionScene(content = motionScene),
        progress = progress
    ) {
        Box(
            modifier = Modifier
                .layoutId("header")
        ) {
            HeaderPicture(
                pictureUrl = model.pictureUrl,
                dwellingType = model.dwellingType
            )
            HeaderButtons(onEvent = onEvent)
        }
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .layoutId("propertyInformation")
                .background(
                    color = White,
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )
        ) {
            BasicInformationSection(
                modifier = Modifier
                    .padding(top = 34.dp, bottom = 32.dp)
                    .padding(horizontal = 24.dp)
                    .onSizeChanged {
                        onHeightChanged(it.height)
                    },
                userName = if (!model.isError) model.userName else null,
                address = if (!model.isError) model.address else null,
                dwellingType = if (!model.isError) model.dwellingType else null,
                policyNumber = if (!model.isError) model.policyNumber else null
            )
        }
    }
}

@Composable
private fun HeaderPicture(
    pictureUrl: String,
    dwellingType: DwellingType
) {
    ConstraintLayout {
        val (imageBackground) = createRefs()

        SubcomposeAsyncImage(
            model = pictureUrl,
            loading = {
                ScreenLoader(
                    modifier = Modifier
                        .padding(8.dp)
                        .padding(top = 80.dp)
                )
            },
            error = {
                Image(
                    painter = painterResource(id = dwellingType.icon),
                    modifier = Modifier
                        .height(250.dp)
                        .fillMaxSize(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            },
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .constrainAs(imageBackground) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxSize()
                .height(250.dp)
        )
    }
}

@Composable
private fun HeaderButtons(
    onEvent: (RentersCoverageDetailsEvent) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(top = 52.dp, bottom = 0.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                onEvent(RentersCoverageDetailsEvent.Back)
            }
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_return),
                contentDescription = stringResource(id = R.string.back)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

//        TODO - Enable when integration is done in Backend
//        IconButton(
//            onClick = {
//                onEvent(RentersCoverageDetailsEvent.EditPicturePressed)
//            }
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.ic_edit_picture),
//                contentDescription = stringResource(id = R.string.change_house_picture)
//            )
//        }
    }
}

@Preview
@Composable
private fun HeaderPreview() {
    Surface {
        HeaderSection(
            progress = 0f,
            model = HeaderSectionModel(
                userName = "Laurem",
                address = "1234 Main Street, Tampa, FL",
                dwellingType = DwellingType.SingleFamily,
                policyNumber = "123456789",
                pictureUrl = ""
            ),
            onEvent = { },
            onHeightChanged = {}
        )
    }
}
