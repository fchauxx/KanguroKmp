package com.insurtech.kanguro.shared.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.insurtech.kanguro.shared.theme.SecondaryDarkest
import com.insurtech.kanguro.shared.theme.getLatoFontFamily

@Composable
fun KanguroButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean,
    isLoading: Boolean = false,
    fontSize: TextUnit = 16.sp,
    onClick: () -> Unit
) {
    Button(
        onClick = { onClick() },
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp),
        colors = ButtonDefaults.buttonColors( containerColor = SecondaryDarkest),
        shape = RoundedCornerShape(8.dp),
        elevation = ButtonDefaults.elevatedButtonElevation(0.dp),
        enabled = if (isLoading) false else enabled
    ) {
        if (isLoading) {
            ScreenLoader(modifier = Modifier.padding(8.dp))
        } else {
            Text(
                text = text,
                style = TextStyle(
                    fontSize = fontSize,
                    fontFamily = getLatoFontFamily(),
                    fontWeight = FontWeight.Black,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}
