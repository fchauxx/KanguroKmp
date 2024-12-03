package com.insurtech.kanguro.designsystem.ui.composables.rentersWhatIsCovered

import android.view.Gravity
import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.HeaderBackAndClose
import com.insurtech.kanguro.designsystem.ui.composables.rentersWhatIsCovered.model.WhatIsCoveredItem
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyBold
import com.insurtech.kanguro.designsystem.ui.theme.MobaCaptionRegular
import com.insurtech.kanguro.designsystem.ui.theme.MobaTitle1
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDarkest
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryMedium

@Composable
fun RentersWhatIsCoveredScreenContent(
    modifier: Modifier = Modifier,
    onClosePressed: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        HeaderBackAndClose(
            modifier = Modifier.padding(start = 16.dp, end = 8.dp, top = 8.dp),
            onClosePressed = onClosePressed
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Title()
            Content()
        }
    }
}

@Composable
private fun Title() {
    Text(text = stringResource(id = R.string.what_is_covered), style = MobaTitle1)

    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = stringResource(id = R.string.renters_insurance).uppercase(),
        style = MobaBodyBold.copy(color = SecondaryMedium)
    )

    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun Content() {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()).padding(bottom = 36.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        StyledText(
            text = LocalContext.current.resources.getText(R.string.renters_what_is_covered_content)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            WhatIsCoveredItem.values().forEach { item ->
                ItemCard(item)
            }
        }
    }
}

@Composable
private fun ItemCard(item: WhatIsCoveredItem) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        color = NeutralBackground
    ) {
        val title = stringResource(id = item.title)

        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = item.icon),
                contentDescription = title
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = title,
                style = MobaCaptionRegular.copy(color = SecondaryDarkest)
            )
        }
    }
}

@Composable
private fun StyledText(
    modifier: Modifier = Modifier,
    text: CharSequence
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            TextView(context).apply {
                setTextAppearance(R.style.BKSParagraphRegular)
            }
        },
        update = {
            it.text = text
            it.gravity = Gravity.CENTER
        }
    )
}

@Composable
@Preview(device = Devices.NEXUS_5)
private fun RentersWhatIsCoveredScreenContentPreview() {
    Surface {
        RentersWhatIsCoveredScreenContent(onClosePressed = {})
    }
}
