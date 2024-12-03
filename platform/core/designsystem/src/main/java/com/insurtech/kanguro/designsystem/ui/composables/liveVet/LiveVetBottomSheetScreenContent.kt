package com.insurtech.kanguro.designsystem.ui.composables.liveVet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.HeaderBackAndClose
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.KanguroButton
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.LoadingErrorStateOverlay
import com.insurtech.kanguro.designsystem.ui.composables.liveVet.model.LiveVetEvent
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyRegular
import com.insurtech.kanguro.designsystem.ui.theme.MobaTitle3
import com.insurtech.kanguro.designsystem.ui.theme.MobaTitle3SemiBold
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDark
import com.insurtech.kanguro.designsystem.ui.theme.spacingNanoLarge
import com.insurtech.kanguro.designsystem.ui.theme.spacingXxs
import com.insurtech.kanguro.designsystem.ui.theme.spacingXxxs
import com.insurtech.kanguro.domain.model.AirvetUserDetails

@Composable
fun LiveVetBottomSheetScreenContent(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    isError: Boolean,
    airvetUserDetails: AirvetUserDetails,
    onEvent: (LiveVetEvent) -> Unit
) {
    Column(
        modifier = modifier
            .padding(top = spacingXxxs, start = spacingXxxs, end = spacingXxxs)
    ) {
        LiveVetHeader(
            modifier = Modifier
                .nestedScroll(
                    rememberNestedScrollInteropConnection()
                )
                .verticalScroll(
                    rememberScrollState()
                ),
            onClosePressed = { onEvent(LiveVetEvent.OnClosePressed) }
        )

        if (!isLoading && !isError) {
            Content(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = spacingXxxs)
                    .padding(horizontal = spacingXxxs),
                airvetUserDetails = airvetUserDetails,
                onEvent = onEvent
            )
        } else {
            LoadingErrorStateOverlay(
                isLoading = isLoading,
                isError = isError,
                loaderColor = NeutralBackground
            ) {
                onEvent(LiveVetEvent.OnTryAgainPressed)
            }
        }
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    airvetUserDetails: AirvetUserDetails,
    onEvent: (LiveVetEvent) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(spacingXxxs))

            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = R.drawable.img_live_vet_on_phone),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )

            Spacer(modifier = Modifier.height(spacingNanoLarge))

            LiveVetDescriptionText()
        }

        KanguroButton(
            modifier = Modifier
                .padding(top = spacingXxxs, bottom = spacingXxs),
            text = stringResource(id = R.string.live_vet_button),
            enabled = true
        ) {
            onEvent(
                LiveVetEvent.OnDownloadPressed(
                    airvetUserDetails
                )
            )
        }
    }
}

@Composable
private fun LiveVetHeader(
    modifier: Modifier = Modifier,
    onClosePressed: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        HeaderBackAndClose(
            onClosePressed = onClosePressed
        )
        Column(
            modifier = Modifier.padding(
                start = spacingXxxs,
                top = spacingNanoLarge,
                end = spacingNanoLarge
            )
        ) {
            LiveVetTitleComponent()

            Spacer(modifier = Modifier.height(spacingNanoLarge))

            LiveVetSubtitleComponent()
        }
    }
}

@Composable
private fun LiveVetTitleComponent(
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = stringResource(id = R.string.live_vet_title),
        style = MobaTitle3SemiBold
    )
}

@Composable
private fun LiveVetSubtitleComponent(
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = stringResource(id = R.string.live_vet_subtitle),
        style = MobaTitle3.copy(fontSize = 16.sp)
    )
}

@Composable
private fun LiveVetDescriptionText() {
    Text(
        text = buildAnnotatedString {
            append(stringResource(id = R.string.live_vet_policy))
        },
        style = MobaBodyRegular.copy(color = SecondaryDark)
    )
    Spacer(modifier = Modifier.height(spacingNanoLarge))
    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Bold
                )
            ) {
                append(stringResource(id = R.string.live_vet_description_highlight))
            }
            append(" ")
            append(stringResource(id = R.string.live_vet_description))
        },
        style = MobaBodyRegular.copy(color = SecondaryDark)
    )
    Spacer(modifier = Modifier.height(spacingNanoLarge))
    Text(
        text = buildAnnotatedString {
            append(stringResource(id = R.string.live_vet_description_2))
        },
        style = MobaBodyRegular.copy(color = SecondaryDark)
    )
    Spacer(modifier = Modifier.height(spacingNanoLarge))
    Text(
        text = buildAnnotatedString {
            append(stringResource(id = R.string.live_vet_description_3))
        },
        style = MobaBodyRegular.copy(color = SecondaryDark)
    )
    Spacer(modifier = Modifier.height(spacingNanoLarge))
    Text(
        text = buildAnnotatedString {
            append(stringResource(id = R.string.live_vet_description_4))
        },
        style = MobaBodyRegular.copy(color = SecondaryDark)
    )
}

@Preview
@Composable
private fun LiveVetBottomSheetPreview() {
    Surface {
        LiveVetBottomSheetScreenContent(
            isLoading = false,
            isError = false,
            airvetUserDetails = AirvetUserDetails("", "", "", 12012),
            onEvent = {}
        )
    }
}

@Preview
@Composable
private fun LiveVetBottomSheetLoadingPreview() {
    Surface {
        LiveVetBottomSheetScreenContent(
            isLoading = true,
            isError = false,
            airvetUserDetails = AirvetUserDetails("", "", "", 12012),
            onEvent = {}
        )
    }
}

@Preview
@Composable
private fun LiveVetBottomSheetErrorPreview() {
    Surface {
        LiveVetBottomSheetScreenContent(
            isLoading = false,
            isError = true,
            airvetUserDetails = AirvetUserDetails("", "", "", 12012),
            onEvent = {}
        )
    }
}
