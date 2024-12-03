package com.insurtech.kanguro.designsystem.ui.composables.homeDashboard.sections

import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.SubcomposeAsyncImage
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.ScreenLoader
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.PetType
import com.insurtech.kanguro.designsystem.ui.composables.homeDashboard.model.ItemReminderUiModel
import com.insurtech.kanguro.designsystem.ui.composables.homeDashboard.model.ReminderTypeUiModel
import com.insurtech.kanguro.designsystem.ui.theme.LatoBoldSecondaryDarkest16
import com.insurtech.kanguro.designsystem.ui.theme.MobaBodyBold
import com.insurtech.kanguro.designsystem.ui.theme.MobaFootnoteRegularSecondaryDark
import com.insurtech.kanguro.designsystem.ui.theme.MobaSubheadRegular
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground
import com.insurtech.kanguro.designsystem.ui.theme.White
import java.util.Calendar

@Composable
fun ItemReminder(
    modifier: Modifier = Modifier,
    model: ItemReminderUiModel,
    onPressed: (ItemReminderUiModel) -> Unit
) {
    Row(
        modifier = modifier
            .background(
                color = model.type.leftColor,
                shape = RoundedCornerShape(size = 8.dp)
            )
            .widthIn(148.dp)
            .shadow(elevation = 0.dp, shape = RoundedCornerShape(8.dp))
            .clickable {
                onPressed(model)
            }
    ) {
        Spacer(modifier = Modifier.width(5.dp))

        Content(model = model)
    }
}

@Composable
private fun Content(
    model: ItemReminderUiModel
) {
    Box(
        modifier = Modifier
            .widthIn(148.dp)
            .background(
                color = model.type.backgroundColor,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        if (model.type == ReminderTypeUiModel.AddPet) {
            AddPet(modifier = Modifier.align(Alignment.BottomEnd))
        } else {
            GenericLayout(model = model, modifier = Modifier.align(Alignment.TopEnd))
        }
    }
}

@Composable
private fun GenericLayout(
    model: ItemReminderUiModel,
    modifier: Modifier
) {
    Column {
        Header(model = model)
        Message(model = model)
        Pet(model = model)
    }

    if (model.type == ReminderTypeUiModel.DirectPay || model.type == ReminderTypeUiModel.Claim) {
        @DrawableRes val icon = if (model.type == ReminderTypeUiModel.DirectPay) {
            R.drawable.ic_clock
        } else {
            R.drawable.ic_info_circle
        }

        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = modifier.padding(top = 8.dp, start = 11.dp, end = 8.dp)
        )
    }
}

@Composable
private fun AddPet(
    modifier: Modifier
) {
    val context = LocalContext.current

    Column {
        Text(
            modifier = Modifier.padding(top = 16.dp, start = 11.dp, end = 8.dp),
            text = stringResource(id = R.string.add_a_pet_label),
            style = LatoBoldSecondaryDarkest16.copy(color = White)
        )

        AndroidView(
            modifier = Modifier.padding(top = 4.dp, start = 11.dp, end = 36.dp, bottom = 27.dp),
            factory = { context ->
                TextView(context).apply {
                    setTextAppearance(R.style.MobaBodyRegular14)
                }
            },
            update = {
                it.text = context.resources.getText(R.string.get_new_pet_quote)
            }
        )
    }

    Image(
        modifier = modifier.padding(end = 12.dp, bottom = 10.dp),
        painter = painterResource(id = R.drawable.ic_paw),
        contentDescription = null
    )
}

@Composable
private fun Header(
    model: ItemReminderUiModel
) {
    Row(
        modifier = Modifier.padding(top = 8.dp, start = 11.dp, end = 16.dp)
    ) {
        Text(
            text = stringResource(id = model.type.label),
            style = MobaBodyBold,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
private fun Message(
    model: ItemReminderUiModel
) {
    Text(
        modifier = Modifier.padding(start = 11.dp, end = 8.dp),
        text = model.getFormattedMessage(LocalContext.current),
        style = MobaSubheadRegular.copy(color = model.type.textColor)
    )
}

@Composable
private fun Pet(
    model: ItemReminderUiModel
) {
    Row(
        modifier = Modifier.padding(
            top = 16.dp,
            start = 11.dp,
            end = 8.dp,
            bottom = 16.dp
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PetImage(model = model)
        Spacer(modifier = Modifier.width(4.dp))
        if (model.type == ReminderTypeUiModel.FleaMedication && !model.clinicName.isNullOrBlank()) {
            Text(
                text = model.petName,
                style = MobaFootnoteRegularSecondaryDark.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = " - ${model.clinicName}",
                style = MobaFootnoteRegularSecondaryDark
            )
        } else {
            Text(
                text = model.petName,
                style = MobaFootnoteRegularSecondaryDark
            )
        }
    }
}

@Composable
private fun PetImage(
    model: ItemReminderUiModel
) {
    Box(
        modifier = Modifier.size(16.dp)
    ) {
        SubcomposeAsyncImage(
            model = model.petPictureUrl,
            loading = {
                ScreenLoader(
                    modifier = Modifier
                        .fillMaxSize()
                )
            },
            error = {
                Image(
                    painter = painterResource(id = model.petType.placeHolder),
                    modifier = Modifier
                        .fillMaxSize(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            },
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
        )
    }
}

@Preview
@Composable
private fun ItemReminderClaimPreview() {
    Surface(
        color = NeutralBackground
    ) {
        ItemReminder(
            modifier = Modifier.padding(30.dp),
            model = ItemReminderUiModel(
                id = "",
                type = ReminderTypeUiModel.MedicalHistory,
                petId = 0L,
                petName = "Oliver",
                petType = PetType.Dog,
                petPictureUrl = "",
                claimId = ""
            ),
            onPressed = {}
        )
    }
}

@Preview
@Composable
private fun ItemReminderMedicalHistoryPreview() {
    Surface(
        color = NeutralBackground
    ) {
        ItemReminder(
            modifier = Modifier.padding(30.dp),
            model = ItemReminderUiModel(
                id = "",
                type = ReminderTypeUiModel.Claim,
                petId = 0L,
                petName = "Oliver",
                petType = PetType.Dog,
                petPictureUrl = "",
                claimId = ""
            ),
            onPressed = {}
        )
    }
}

@Preview
@Composable
private fun ItemReminderDirectPayPreview() {
    Surface(
        color = NeutralBackground
    ) {
        ItemReminder(
            modifier = Modifier.padding(30.dp),
            model = ItemReminderUiModel(
                id = "",
                type = ReminderTypeUiModel.DirectPay,
                petId = 0L,
                petName = "Oliver",
                petType = PetType.Dog,
                petPictureUrl = "",
                claimId = ""
            ),
            onPressed = {}
        )
    }
}

@Preview
@Composable
private fun ItemReminderFleaMedicationPreview() {
    Surface(
        color = NeutralBackground
    ) {
        ItemReminder(
            modifier = Modifier.padding(30.dp),
            model = ItemReminderUiModel(
                id = "",
                type = ReminderTypeUiModel.FleaMedication,
                petId = 0L,
                petName = "Oliver",
                petType = PetType.Dog,
                petPictureUrl = "",
                clinicName = "Pet Loves Clinic",
                claimId = ""
            ),
            onPressed = {}
        )
    }
}

@Preview
@Composable
private fun ItemReminderAddPetPreview() {
    Surface(
        color = NeutralBackground
    ) {
        ItemReminder(
            modifier = Modifier.padding(30.dp),
            model = ItemReminderUiModel(
                id = "",
                type = ReminderTypeUiModel.AddPet,
                petId = 0L,
                petName = "Oliver",
                petType = PetType.Dog,
                petPictureUrl = "",
                claimId = ""
            ),
            onPressed = {}
        )
    }
}

@Preview
@Composable
private fun ItemReminderPetPicturePreview() {
    Surface(
        color = NeutralBackground
    ) {
        ItemReminder(
            modifier = Modifier.padding(30.dp),
            model = ItemReminderUiModel(
                id = "",
                type = ReminderTypeUiModel.PetPicture,
                petId = 0L,
                petName = "Oliver",
                petType = PetType.Dog,
                petPictureUrl = "",
                dueDate = Calendar.getInstance().apply {
                    add(Calendar.DAY_OF_YEAR, 15)
                }.time,
                claimId = ""
            ),
            onPressed = {}
        )
    }
}
