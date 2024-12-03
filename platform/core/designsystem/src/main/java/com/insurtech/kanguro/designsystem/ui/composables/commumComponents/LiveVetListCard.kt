package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.theme.ClickableTextStyleNormal
import com.insurtech.kanguro.designsystem.ui.theme.LatoRegularSecondaryDarkSize12
import com.insurtech.kanguro.designsystem.ui.theme.LightPurple
import com.insurtech.kanguro.designsystem.ui.theme.MobaHeadline
import com.insurtech.kanguro.designsystem.ui.theme.PrimaryDarkest
import com.insurtech.kanguro.designsystem.ui.theme.TertiaryMedium
import com.insurtech.kanguro.designsystem.ui.theme.petCarouselCardHeight

@Composable
fun LiveVetListCard(
    modifier: Modifier = Modifier,
    onPressed: () -> Unit
) {
    CoverageSummaryCardContainer(
        modifier = modifier.height(petCarouselCardHeight),
        border = BorderStroke(1.dp, TertiaryMedium),
        onPressed = onPressed
    ) {
        Column {
            Image(
                modifier = Modifier
                    .height(86.dp)
                    .background(LightPurple)
                    .padding(horizontal = 8.dp),
                painter = painterResource(id = R.drawable.img_live_vet_on_phone),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
            ) {
                Title()
                Description()
                TalkToAVet()
            }
        }
    }
}

@Composable
private fun Title() {
    Text(
        modifier = Modifier,
        text = stringResource(id = R.string.live_vet_list_card_title),
        style = MobaHeadline.copy(color = PrimaryDarkest),
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun Description() {
    Text(
        modifier = Modifier.padding(top = 4.dp),
        text = stringResource(id = R.string.live_vet_list_card_description),
        style = LatoRegularSecondaryDarkSize12,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun TalkToAVet() {
    Text(
        modifier = Modifier.padding(top = 12.dp),
        style = ClickableTextStyleNormal,
        text = buildAnnotatedString {
            append(stringResource(id = R.string.live_vet_list_card_action))
        }
    )
}

@Preview
@Composable
private fun LiveVetListCardPreview() {
    LiveVetListCard {
    }
}

@Preview(locale = "es")
@Composable
private fun LiveVetListCardSpanishPreview() {
    LiveVetListCard {
    }
}
