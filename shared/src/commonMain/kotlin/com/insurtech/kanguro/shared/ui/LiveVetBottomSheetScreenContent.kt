package com.insurtech.kanguro.shared.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.insurtech.kanguro.shared.data.AirvetUserDetails
import com.insurtech.kanguro.shared.spacingNanoLarge
import com.insurtech.kanguro.shared.spacingXxs
import com.insurtech.kanguro.shared.spacingXxxs
import com.insurtech.kanguro.shared.theme.MobaBodyRegular
import com.insurtech.kanguro.shared.theme.MobaTitle3
import com.insurtech.kanguro.shared.theme.MobaTitle3SemiBold
import com.insurtech.kanguro.shared.theme.SecondaryDark
import kanguro.shared.generated.resources.Res
import kanguro.shared.generated.resources.img_live_vet_on_phone
import kanguro.shared.generated.resources.live_vet_button
import kanguro.shared.generated.resources.live_vet_description
import kanguro.shared.generated.resources.live_vet_description_2
import kanguro.shared.generated.resources.live_vet_description_3
import kanguro.shared.generated.resources.live_vet_description_4
import kanguro.shared.generated.resources.live_vet_description_highlight
import kanguro.shared.generated.resources.live_vet_policy
import kanguro.shared.generated.resources.live_vet_subtitle
import kanguro.shared.generated.resources.live_vet_title
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

class CustomNestedScrollConnection : NestedScrollConnection {
    // Implement necessary functions for nested scrolling, such as:
    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        // Handle pre-scroll logic here
        return Offset.Zero // Return the consumed scroll offset
    }
}

@Composable
fun LiveVetBottomSheetScreenContent(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    isError: Boolean,
    airvetUserDetails: AirvetUserDetails,
    onEvent: (LiveVetEvent) -> Unit = {}
) {
    val nestedScrollConnection = remember { CustomNestedScrollConnection() }
    Column(
        modifier = modifier
            .padding(top = spacingXxxs, start = spacingXxxs, end = spacingXxxs)
    ) {
        LiveVetHeader(
            modifier = Modifier
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
         /*   LoadingErrorStateOverlay(
                isLoading = isLoading,
                isError = isError,
                loaderColor = NeutralBackground
            ) {
                onEvent(LiveVetEvent.OnTryAgainPressed)
            }*/
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
                painter = painterResource(resource = Res.drawable.img_live_vet_on_phone),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )

            Spacer(modifier = Modifier.height(spacingNanoLarge))

            LiveVetDescriptionText()
        }

        KanguroButton(
            modifier = Modifier
                .padding(top = spacingXxxs, bottom = spacingXxs),
            text = stringResource(resource = Res.string.live_vet_button),
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
        text = stringResource(Res.string.live_vet_title),
        style = MobaTitle3SemiBold()
    )
}

@Composable
private fun LiveVetSubtitleComponent(
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = stringResource(Res.string.live_vet_subtitle),
        style = MobaTitle3().copy(fontSize = 16.sp)
    )
}

@Composable
private fun LiveVetDescriptionText() {
    Text(
        text = buildAnnotatedString {
            append(stringResource(Res.string.live_vet_policy))
        },
        style = MobaBodyRegular().copy(color = SecondaryDark)
    )
    Spacer(modifier = Modifier.height(spacingNanoLarge))
    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Bold
                )
            ) {
                append(stringResource(Res.string.live_vet_description_highlight))
            }
            append(" ")
            append(stringResource(Res.string.live_vet_description))
        },
        style = MobaBodyRegular().copy(color = SecondaryDark)
    )
    Spacer(modifier = Modifier.height(spacingNanoLarge))
    Text(
        text = buildAnnotatedString {
            append(stringResource(Res.string.live_vet_description_2))
        },
        style = MobaBodyRegular().copy(color = SecondaryDark)
    )
    Spacer(modifier = Modifier.height(spacingNanoLarge))
    Text(
        text = buildAnnotatedString {
            append(stringResource(Res.string.live_vet_description_3))
        },
        style = MobaBodyRegular().copy(color = SecondaryDark)
    )
    Spacer(modifier = Modifier.height(spacingNanoLarge))
    Text(
        text = buildAnnotatedString {
            append(stringResource(Res.string.live_vet_description_4))
            append(stringResource(Res.string.live_vet_description_4))
            append(stringResource(Res.string.live_vet_description_4))
            append(stringResource(Res.string.live_vet_description_4))
        },
        style = MobaBodyRegular().copy(color = SecondaryDark)
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
