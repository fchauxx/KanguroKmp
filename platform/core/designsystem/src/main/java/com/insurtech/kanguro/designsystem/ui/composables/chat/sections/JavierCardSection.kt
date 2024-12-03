package com.insurtech.kanguro.designsystem.ui.composables.chat.sections

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.theme.BKSParagraphRegular
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyBold
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDark
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryLight
import com.insurtech.kanguro.designsystem.ui.theme.White

@Composable
fun JavierCardSection(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = White),
        shape = RoundedCornerShape(16.dp),
        elevation = ButtonDefaults.elevation(2.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.javier_neutral),
                    contentDescription = null
                )
                Text(
                    text = stringResource(id = R.string.talk_to_javier),
                    style = MobaBodyBold.copy(color = SecondaryDark),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = stringResource(id = R.string.talk_to_javier_specialist_available),
                style = BKSParagraphRegular.copy(color = SecondaryLight)
            )
        }
    }
}

@Preview
@Composable
private fun JavierCardSectionPreview() {
    Surface {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            JavierCardSection(onClick = {})
        }
    }
}
