package com.insurtech.kanguro.designsystem.ui.composables.rentersOnboardingVideo.content

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.ErrorComponent
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.HeaderBackAndClose
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.KanguroButton
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.ScreenLoader
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.SelectedFileCard
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.UploadButton
import com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.domain.SelectedFileType
import com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.domain.SelectedFileUi
import com.insurtech.kanguro.designsystem.ui.composables.rentersOnboardingVideo.model.RentersOnboardingVideoEvent
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyRegular
import com.insurtech.kanguro.designsystem.ui.theme.MobaFootnoteRegular
import com.insurtech.kanguro.designsystem.ui.theme.MobaSubheadBlack
import com.insurtech.kanguro.designsystem.ui.theme.MobaTitle3SemiBold
import com.insurtech.kanguro.designsystem.ui.theme.NegativeDarkest
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDark
import com.insurtech.kanguro.designsystem.ui.theme.White

@Composable
fun RentersOnboardingVideoScreenContent(
    isLoading: Boolean = false,
    isError: Boolean = false,
    isUploadingVideo: Boolean = false,
    isUploadingVideoError: Boolean = false,
    doneButtonEnabled: Boolean,
    selectedFileUi: SelectedFileUi? = null,
    onEvent: (RentersOnboardingVideoEvent) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        HeaderBackAndClose(
            modifier = Modifier.padding(start = 16.dp, end = 12.dp, top = 8.dp),
            onBackButtonPressed = { onEvent(RentersOnboardingVideoEvent.OnBackPressed) },
            onClosePressed = { onEvent(RentersOnboardingVideoEvent.OnClosePressed) }
        )

        if (isLoading) {
            Loader()
        } else if (isError) {
            Error(onTryAgainPressed = { onEvent(RentersOnboardingVideoEvent.OnTryAgainPressed) })
        } else {
            Content(
                isUploadingVideo = isUploadingVideo,
                isUploadingVideoError = isUploadingVideoError,
                onEvent = onEvent,
                selectedFileUi = selectedFileUi,
                doneButtonEnabled = doneButtonEnabled
            )
        }
    }
}

@Composable
private fun Loader() {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (loader) = createRefs()
        ScreenLoader(
            color = NeutralBackground,
            modifier = Modifier
                .size(width = 87.dp, height = 84.dp)
                .constrainAs(loader) {
                    linkTo(top = parent.top, bottom = parent.bottom, bias = 0.45f)
                    linkTo(start = parent.start, end = parent.end)
                }

        )
    }
}

@Composable
private fun Error(
    onTryAgainPressed: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .padding(top = 81.dp)
            .padding(horizontal = 22.dp)
            .fillMaxSize()
    ) {
        val (loader) = createRefs()

        ErrorComponent(
            onTryAgainPressed = onTryAgainPressed,
            modifier = Modifier
                .constrainAs(loader) {
                    linkTo(top = parent.top, bottom = parent.bottom, bias = 0.5f)
                    linkTo(start = parent.start, end = parent.end)
                }
        )
    }
}

@Composable
private fun Content(
    isUploadingVideo: Boolean,
    isUploadingVideoError: Boolean = false,
    onEvent: (RentersOnboardingVideoEvent) -> Unit,
    selectedFileUi: SelectedFileUi?,
    doneButtonEnabled: Boolean
) {
    Column(
        modifier = Modifier
            .padding(start = 32.dp, end = 32.dp, top = 81.dp, bottom = 32.dp)
            .fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.upload_a_video),
            style = MobaTitle3SemiBold
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = stringResource(R.string.upload_a_video_description),
            style = MobaBodyRegular.copy(color = SecondaryDark)
        )
        Spacer(modifier = Modifier.height(32.dp))

        UploadButton(
            isUploadingDocument = isUploadingVideo,
            onUploadFilePressed = {
                onEvent(RentersOnboardingVideoEvent.OnSendPressed)
            }
        ) {
            Text(
                text = stringResource(R.string.send_video),
                style = MobaSubheadBlack.copy(color = White)
            )
        }

        AnimatedVisibility(visible = selectedFileUi != null) {
            selectedFileUi?.let {
                SelectedFileCard(
                    modifier = Modifier.padding(vertical = 16.dp),
                    selectedFileUi = it,
                    onDeletedPressed = {
                        onEvent(RentersOnboardingVideoEvent.OnDeleteCapturedVideoPressed)
                    }
                )
            }
        }

        AnimatedVisibility(visible = isUploadingVideoError) {
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = stringResource(id = R.string.upload_video_error_message),
                style = MobaFootnoteRegular.copy(color = NegativeDarkest)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        KanguroButton(
            enabled = doneButtonEnabled && !isUploadingVideo,
            text = stringResource(id = R.string.done)
        ) {
            onEvent(RentersOnboardingVideoEvent.OnDonePressed)
        }
    }
}

@Composable
@Preview(device = Devices.NEXUS_5)
fun RentersOnboardingVideoScreenContentPreview() {
    Surface {
        RentersOnboardingVideoScreenContent(
            isUploadingVideo = false,
            doneButtonEnabled = true,
            selectedFileUi = SelectedFileUi(
                type = SelectedFileType.Video,
                id = 0,
                fileName = "8352234234.mp4"
            ),
            onEvent = {}
        )
    }
}

@Composable
@Preview(device = Devices.NEXUS_5)
fun RentersOnboardingVideoScreenContentUploadErrorPreview() {
    Surface {
        RentersOnboardingVideoScreenContent(
            isUploadingVideo = false,
            isUploadingVideoError = true,
            doneButtonEnabled = false,
            selectedFileUi = null,
            onEvent = {}
        )
    }
}

@Composable
@Preview(device = Devices.NEXUS_5)
fun RentersOnboardingVideoScreenContentLoadingPreview() {
    Surface {
        RentersOnboardingVideoScreenContent(
            isLoading = true,
            doneButtonEnabled = true,
            selectedFileUi = SelectedFileUi(
                type = SelectedFileType.Video,
                id = 0,
                fileName = "8352234234.mp4"
            ),
            onEvent = {}
        )
    }
}

@Composable
@Preview(device = Devices.NEXUS_5)
fun RentersOnboardingVideoScreenContentErrorPreview() {
    Surface {
        RentersOnboardingVideoScreenContent(
            isError = true,
            doneButtonEnabled = true,
            selectedFileUi = SelectedFileUi(
                type = SelectedFileType.Video,
                id = 0,
                fileName = "8352234234.mp4"
            ),
            onEvent = {}
        )
    }
}
