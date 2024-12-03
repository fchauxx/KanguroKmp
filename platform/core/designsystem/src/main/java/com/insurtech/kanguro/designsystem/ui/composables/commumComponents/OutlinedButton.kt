package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDarkest
import com.insurtech.kanguro.designsystem.ui.theme.White

@Composable
fun OutlinedButton(
    @StringRes text: Int,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 16.sp,
    onClick: () -> Unit
) {
    Button(
        onClick = { onClick() },
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = White),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(width = 1.dp, color = SecondaryDarkest),
        elevation = ButtonDefaults.elevation(0.dp)
    ) {
        Text(
            text = stringResource(id = text),
            style = TextStyle(
                fontSize = fontSize,
                fontFamily = FontFamily(Font(R.font.lato)),
                fontWeight = FontWeight.Black,
                color = SecondaryDarkest,
                textAlign = TextAlign.Center
            )
        )
    }
}

@Preview
@Composable
private fun OutlinedButtonPreview() {
    Surface {
        OutlinedButton(
            modifier = Modifier.padding(30.dp),
            text = R.string.active,
            onClick = {}
        )
    }
}
