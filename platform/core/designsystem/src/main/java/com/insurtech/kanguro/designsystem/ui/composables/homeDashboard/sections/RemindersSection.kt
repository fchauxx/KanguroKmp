package com.insurtech.kanguro.designsystem.ui.composables.homeDashboard.sections

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.PetType
import com.insurtech.kanguro.designsystem.ui.composables.homeDashboard.model.ItemReminderUiModel
import com.insurtech.kanguro.designsystem.ui.composables.homeDashboard.model.ReminderTypeUiModel
import com.insurtech.kanguro.designsystem.ui.theme.MobaSubheadBoldSecondaryMedium
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground
import java.util.Calendar

@Composable
fun RemindersSection(
    modifier: Modifier = Modifier,
    reminders: List<ItemReminderUiModel>,
    onSeeAllPressed: () -> Unit,
    onReminderPressed: (ItemReminderUiModel) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Header(onSeeAllPressed = onSeeAllPressed)

        Spacer(modifier = Modifier.height(8.dp))

        RemindersList(reminders = reminders, onReminderPressed = onReminderPressed)
    }
}

@Composable
private fun Header(
    onSeeAllPressed: () -> Unit
) {
    Row(
        modifier = Modifier.padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        UpperCaseCaseSectionTitle(
            title = R.string.reminders
        )

        Spacer(modifier = Modifier.weight(1f))

        SeeAll(onSeeAllPressed = onSeeAllPressed)
    }
}

@Composable
private fun SeeAll(
    onSeeAllPressed: () -> Unit
) {
    OutlinedButton(
        contentPadding =
        PaddingValues(
            top = 0.dp,
            start = 0.dp,
            bottom = 0.dp,
            end = 0.dp
        ),
        shape = RoundedCornerShape(0.dp),
        border = BorderStroke(width = 0.dp, color = Color.Transparent),
        onClick = onSeeAllPressed
    ) {
        Text(
            modifier = Modifier,
            text = stringResource(id = R.string.see_all),
            style = MobaSubheadBoldSecondaryMedium
        )
        Image(
            painter = painterResource(id = R.drawable.ic_chevron_right),
            contentDescription = null
        )
    }
}

@Composable
fun RemindersList(
    reminders: List<ItemReminderUiModel>,
    onReminderPressed: (ItemReminderUiModel) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(reminders) { reminder ->
            ItemReminder(model = reminder) {
                onReminderPressed(it)
            }
        }
    }
}

@Preview
@Composable
private fun RemindersSectionPreview() {
    Surface() {
        RemindersSection(
            modifier = Modifier.background(color = NeutralBackground),
            reminders = listOf(
                ItemReminderUiModel(
                    id = "",
                    type = ReminderTypeUiModel.AddPet,
                    petId = 0L,
                    petName = "Oliver",
                    petType = PetType.Dog,
                    petPictureUrl = "https://love.doghero.com.br/wp-content/uploads/2018/12/golden-retriever-1.png",
                    claimId = ""
                ),
                ItemReminderUiModel(
                    id = "",
                    type = ReminderTypeUiModel.FleaMedication,
                    petId = 0L,
                    petName = "Oliver",
                    petType = PetType.Dog,
                    petPictureUrl = "https://love.doghero.com.br/wp-content/uploads/2018/12/golden-retriever-1.png",
                    clinicName = "Pet Loves Clinic",
                    medicationDate = Calendar.getInstance().apply {
                        add(Calendar.DAY_OF_YEAR, 30)
                    }.time,
                    claimId = ""
                ),
                ItemReminderUiModel(
                    id = "",
                    type = ReminderTypeUiModel.DirectPay,
                    petId = 0L,
                    petName = "Oliver",
                    petType = PetType.Dog,
                    petPictureUrl = "",
                    claimId = ""
                ),
                ItemReminderUiModel(
                    id = "",
                    type = ReminderTypeUiModel.Claim,
                    petId = 0L,
                    petName = "Oliver",
                    petType = PetType.Dog,
                    petPictureUrl = "",
                    claimId = ""
                ),
                ItemReminderUiModel(
                    id = "",
                    type = ReminderTypeUiModel.MedicalHistory,
                    petId = 0L,
                    petName = "Oliver",
                    petType = PetType.Dog,
                    petPictureUrl = "",
                    dueDate = Calendar.getInstance().apply {
                        add(Calendar.DAY_OF_YEAR, 2)
                    }.time,
                    claimId = ""
                ),
                ItemReminderUiModel(
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
                )
            ),
            onSeeAllPressed = {},
            onReminderPressed = {}
        )
    }
}
