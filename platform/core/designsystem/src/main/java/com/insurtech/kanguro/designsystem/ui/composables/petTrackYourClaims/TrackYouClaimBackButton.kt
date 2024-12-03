package com.insurtech.kanguro.designsystem.ui.composables.petTrackYourClaims

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.theme.MobaSubheadBold
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDark
import com.insurtech.kanguro.designsystem.ui.theme.iconSize
import com.insurtech.kanguro.designsystem.ui.theme.spacingNano
import com.insurtech.kanguro.designsystem.ui.theme.spacingQuark
import com.insurtech.kanguro.designsystem.ui.theme.spacingXxxs

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TrackYourClaimsBackButton(
    modifier: Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier,
        onClick = onClick,
        color = Color.Transparent,
        shape = RoundedCornerShape(spacingXxxs)
    ) {
        Row(
            modifier = Modifier.padding(end = spacingNano),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(spacingQuark)
        ) {
            Image(
                modifier = Modifier
                    .wrapContentWidth(Alignment.Start)
                    .size(iconSize),
                painter = painterResource(id = R.drawable.ic_back_arrow),
                colorFilter = ColorFilter.tint(color = SecondaryDark),
                contentDescription = null
            )

            Text(
                text = stringResource(id = R.string.claim_card_claims),
                style = MobaSubheadBold.copy(color = SecondaryDark)
            )
        }
    }
}

@Preview
@Composable
private fun TrackYourClaimsBackButtonPreview() {
    TrackYourClaimsBackButton(
        modifier = Modifier.padding(16.dp),
        onClick = {}
    )
}
