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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.domain.PetInformation
import com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.domain.PetType
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyRegular
import com.insurtech.kanguro.designsystem.ui.theme.MobaCaptionRegularSDark
import com.insurtech.kanguro.designsystem.ui.theme.NeutralLightest
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryMedium

@Composable
fun DirectPayToVetFormPetName(
    modifier: Modifier = Modifier,
    petInformation: PetInformation?,
    onNamePetClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.pet_name),
            style = MobaCaptionRegularSDark
        )

        Spacer(modifier = Modifier.height(8.dp))

        PetInformation(
            onNamePetClick = onNamePetClick,
            petInformation = petInformation
        )
    }
}

@Composable
private fun PetInformation(
    petInformation: PetInformation?,
    onNamePetClick: () -> Unit
) {
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(
            16.dp
        ),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(width = 1.dp, color = SecondaryMedium),
        onClick = onNamePetClick
    ) {
        if (petInformation != null) {
            Image(
                painter = painterResource(
                    id = petInformation.type.icon
                ),
                contentDescription = null
            )

            Text(
                text = petInformation.name,
                style = MobaBodyRegular,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .weight(1f)
            )
        } else {
            Text(
                text = stringResource(id = R.string.pet_name),
                style = MobaBodyRegular.copy(color = NeutralLightest),
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .weight(1f)
            )
        }

        Image(
            painter = painterResource(id = R.drawable.ic_chevron_down),
            contentDescription = null
        )
    }
}

@Preview
@Composable
fun DirectPayToVetFormPetNamePreview() {
    Surface {
        DirectPayToVetFormPetName(
            modifier = Modifier.padding(16.dp),
            petInformation = PetInformation(
                1L,
                "Oliver",
                PetType.dog
            ),
            onNamePetClick = {}
        )
    }
}

@Preview
@Composable
fun DirectPayToVetFormPetNameNullPreview() {
    Surface {
        DirectPayToVetFormPetName(
            modifier = Modifier.padding(16.dp),
            petInformation = null,
            onNamePetClick = {}
        )
    }
}
