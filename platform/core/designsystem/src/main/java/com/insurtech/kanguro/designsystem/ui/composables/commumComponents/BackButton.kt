package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R

@Composable
fun BackButton(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit
) {
    IconButton(
        onClick = { onBackPressed() },
        modifier = modifier
            .wrapContentWidth(Alignment.Start)
            .size(24.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_back_arrow),
            contentDescription = null
        )
    }
}

@Preview
@Composable
private fun BackButtonPreview() {
    Surface {
        BackButton {
        }
    }
}
