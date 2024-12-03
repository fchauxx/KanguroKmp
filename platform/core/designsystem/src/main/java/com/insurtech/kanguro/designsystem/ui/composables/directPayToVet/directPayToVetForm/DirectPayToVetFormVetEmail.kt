package com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.directPayToVetForm

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyRegular
import com.insurtech.kanguro.designsystem.ui.theme.MobaCaptionRegularSDark
import com.insurtech.kanguro.designsystem.ui.theme.NeutralLightest
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryMedium

@Composable
fun DirectPayToVetFormVetEmail(
    modifier: Modifier = Modifier,
    veterinarianEmail: String? = null,
    onEmailPressed: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.email_vet),
            style = MobaCaptionRegularSDark
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(
                16.dp
            ),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(width = 1.dp, color = SecondaryMedium),
            onClick = onEmailPressed
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = if (veterinarianEmail.isNullOrEmpty()) {
                    stringResource(id = R.string.your_vet_email)
                } else {
                    veterinarianEmail
                },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = if (veterinarianEmail.isNullOrEmpty()) {
                    MobaBodyRegular.copy(color = NeutralLightest)
                } else {
                    MobaBodyRegular
                }
            )

            Image(
                painter = painterResource(id = R.drawable.ic_search_normal),
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
fun DirectPayToVetFormVetEmailPreview() {
    Surface {
        DirectPayToVetFormVetEmail(
            modifier = Modifier.padding(16.dp),
            veterinarianEmail = "lovevet@kanguro.com",
            onEmailPressed = {}
        )
    }
}

@Preview
@Composable
fun DirectPayToVetFormVetEmailEmptyPreview() {
    Surface {
        DirectPayToVetFormVetEmail(
            modifier = Modifier.padding(16.dp),
            onEmailPressed = {}
        )
    }
}
