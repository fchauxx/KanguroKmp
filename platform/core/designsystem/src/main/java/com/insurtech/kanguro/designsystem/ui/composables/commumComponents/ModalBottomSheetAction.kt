package com.insurtech.kanguro.designsystem.ui.composables.commumComponents

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyRegular
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDarkest

@Composable
fun ModalBottomSheetAction(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    label: String,
    onClick: () -> Unit = { }
) {
    Box(
        modifier = Modifier
            .clickable {
                onClick()
            }
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = label,
                colorFilter = ColorFilter.tint(SecondaryDarkest)
            )

            Text(
                text = label,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp),
                style = MobaBodyRegular,
                textAlign = TextAlign.Start
            )
        }
    }
}

@Composable
@Preview
private fun ModalBottomSheetActionPreview() {
    Surface {
        ModalBottomSheetAction(
            modifier = Modifier.padding(16.dp),
            icon = R.drawable.ic_camera,
            label = "Take picture(s)"
        )
    }
}
