package com.insurtech.kanguro.designsystem.ui.composables.upselling

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.theme.TertiaryExtraDark
import com.insurtech.kanguro.designsystem.ui.theme.TextButtonStyle
import com.insurtech.kanguro.designsystem.ui.theme.White

@Composable
fun TellMeMoreButton(modifier: Modifier = Modifier, onTellMeMorePressed: () -> Unit) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(46.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = TertiaryExtraDark),
        shape = RoundedCornerShape(8.dp),
        elevation = ButtonDefaults.elevation(0.dp),
        onClick = onTellMeMorePressed
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = stringResource(id = R.string.upselling_tell_me_more),
                style = TextButtonStyle.copy(color = White)
            )

            Image(
                painter = painterResource(id = R.drawable.ic_simple_star),
                contentDescription = null
            )
        }
    }
}

@Composable
@Preview
private fun TellMeMoreButtonPreview() {
    TellMeMoreButton(onTellMeMorePressed = {})
}
