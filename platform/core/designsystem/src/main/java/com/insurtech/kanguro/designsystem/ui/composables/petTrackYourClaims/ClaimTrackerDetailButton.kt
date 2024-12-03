package com.insurtech.kanguro.designsystem.ui.composables.petTrackYourClaims

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.theme.MobaCaptionRegular
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDarkest
import com.insurtech.kanguro.designsystem.ui.theme.White

@Composable
fun ClaimTrackerDetailButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(28.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = White),
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(width = 1.dp, color = SecondaryDarkest),
        elevation = ButtonDefaults.elevation(0.dp)
    ) {
        Text(
            text = stringResource(id = R.string.claim_card_details),
            style = MobaCaptionRegular.copy(
                color = SecondaryDarkest,
                fontWeight = FontWeight.Black
            )
        )
    }
}
