package com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.directPayToVetForm

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.horizontalPayToVetPaddingScreen
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.DatePickerComponent
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.KanguroButton
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.KanguroCustomCheckBox
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.ScreenLoader
import com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.DirectPayToVetTitleComponent
import com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.domain.FormClaimType
import com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.domain.PetInformation
import com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.domain.PetType
import com.insurtech.kanguro.designsystem.ui.composables.directPayToVet.domain.VetInformationUi
import com.insurtech.kanguro.designsystem.ui.theme.MobaCaptionRegular
import com.insurtech.kanguro.designsystem.ui.theme.MobaCaptionRegularSDark
import com.insurtech.kanguro.designsystem.ui.theme.MobaSubheadBold
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground
import com.insurtech.kanguro.designsystem.ui.theme.SecondaryDark
import java.util.Date

@Composable
fun DirectPayToVetFormScreenContent(
    modifier: Modifier = Modifier,
    onClosePressed: () -> Unit,
    onBackButtonPressed: () -> Unit,
    petInformation: PetInformation?,
    onNamePetClick: () -> Unit,
    policyName: String,
    isPetSelectorOpen: Boolean,
    petList: List<PetInformation>,
    onPetSelectorDismiss: () -> Unit,
    onPetSelected: (PetInformation) -> Unit,
    onClaimTypeSelected: (FormClaimType) -> Unit,
    onDateSelected: (Date) -> Unit,
    onVetEmailPressed: () -> Unit,
    allVetsInformation: List<VetInformationUi>,
    isVetSelectorOpen: Boolean,
    onVetSelected: (VetInformationUi) -> Unit,
    onVetSelectorDismiss: () -> Unit,
    vetSearchText: String,
    onVetSearchTextChanged: (String) -> Unit,
    isEditingVetInformationEnabled: Boolean,
    veterinarianEmail: String = "",
    veterinarianName: String = "",
    onVeterinarianNameChanged: (String) -> Unit,
    clinicName: String = "",
    onClinicNameChanged: (String) -> Unit,
    description: String = "",
    onDescriptionChanged: (String) -> Unit,
    isNextButtonEnabled: Boolean,
    onNextPressed: () -> Unit,
    isVetEmailValid: Boolean,
    onContinueEmailButtonClick: (VetInformationUi) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp)

    ) {
        DirectPayToVetTitleComponent(
            modifier = Modifier.padding(horizontal = 16.dp),
            onClosePressed = onClosePressed,
            onBackButtonPressed = onBackButtonPressed
        )

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .horizontalPayToVetPaddingScreen()
                .padding(bottom = 32.dp)
                .border(
                    width = 1.dp,
                    color = NeutralBackground,
                    shape = RoundedCornerShape(size = 8.dp)
                )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                YourInformationSection(
                    petInformation = petInformation,
                    onNamePetClick = onNamePetClick,
                    policyName = policyName,
                    isPetSelectorOpen = isPetSelectorOpen,
                    petList = petList,
                    onPetSelected = onPetSelected,
                    onPetSelectorDismiss = onPetSelectorDismiss
                )

                Spacer(modifier = Modifier.height(32.dp))

                ClaimInformationSection(
                    onClaimTypeSelected = onClaimTypeSelected,
                    onDateSelected = onDateSelected,
                    onVetEmailPressed = onVetEmailPressed,
                    allVetsInformation = allVetsInformation,
                    isVetSelectorOpen = isVetSelectorOpen,
                    onVetSelected = onVetSelected,
                    onVetSelectorDismiss = onVetSelectorDismiss,
                    vetSearchText = vetSearchText,
                    onVetSearchTextChanged = onVetSearchTextChanged,
                    isEditingVetInformationEnabled = isEditingVetInformationEnabled,
                    veterinarianEmail = veterinarianEmail,
                    veterinarianName = veterinarianName,
                    onVeterinarianNameChanged = onVeterinarianNameChanged,
                    clinicName = clinicName,
                    onClinicNameChanged = onClinicNameChanged,
                    description = description,
                    onDescriptionChanged = onDescriptionChanged,
                    isNextButtonEnabled = isNextButtonEnabled,
                    onNextPressed = onNextPressed,
                    isVetEmailValid = isVetEmailValid,
                    onContinueEmailButtonClick = onContinueEmailButtonClick
                )
            }
        }
    }
}

@Composable
fun DirectPayToVetFormScreenLoaderState(
    modifier: Modifier = Modifier,
    onClosePressed: () -> Unit,
    onBackButtonPressed: () -> Unit
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 16.dp)

    ) {
        val (header, loader) = createRefs()

        DirectPayToVetTitleComponent(
            onClosePressed = onClosePressed,
            onBackButtonPressed = onBackButtonPressed,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .constrainAs(header) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        ScreenLoader(
            modifier = Modifier
                .constrainAs(loader) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    linkTo(top = header.bottom, bottom = parent.bottom, bias = 0.4f)
                }
        )
    }
}

@Composable
private fun YourInformationSection(
    petInformation: PetInformation?,
    onNamePetClick: () -> Unit,
    policyName: String,
    isPetSelectorOpen: Boolean,
    petList: List<PetInformation>,
    onPetSelectorDismiss: () -> Unit,
    onPetSelected: (PetInformation) -> Unit
) {
    Text(
        text = stringResource(id = R.string.your_information),
        style = MobaSubheadBold
    )

    Spacer(modifier = Modifier.height(24.dp))

    DirectPayToVetFormPetName(
        petInformation = petInformation,
        onNamePetClick = onNamePetClick
    )

    Spacer(modifier = Modifier.height(16.dp))

    DirectPayToVetFormPolicyHolderName(
        policyName = policyName
    )

    if (isPetSelectorOpen) {
        DirectPayToVetFormPetSelector(
            onDismiss = onPetSelectorDismiss,
            list = petList,
            onPetSelected = onPetSelected
        )
    }
}

@Composable
fun ClaimInformationSection(
    onClaimTypeSelected: (FormClaimType) -> Unit,
    onDateSelected: (Date) -> Unit,
    onVetEmailPressed: () -> Unit,
    allVetsInformation: List<VetInformationUi>,
    isVetSelectorOpen: Boolean,
    onVetSelected: (VetInformationUi) -> Unit,
    onVetSelectorDismiss: () -> Unit,
    vetSearchText: String,
    onVetSearchTextChanged: (String) -> Unit,
    isEditingVetInformationEnabled: Boolean,
    veterinarianEmail: String = "",
    veterinarianName: String = "",
    onVeterinarianNameChanged: (String) -> Unit,
    clinicName: String = "",
    onClinicNameChanged: (String) -> Unit,
    description: String,
    onDescriptionChanged: (String) -> Unit,
    isNextButtonEnabled: Boolean,
    onNextPressed: () -> Unit,
    isVetEmailValid: Boolean,
    onContinueEmailButtonClick: (VetInformationUi) -> Unit
) {
    Text(
        text = stringResource(id = R.string.claim_information),
        style = MobaSubheadBold
    )

    ClaimTypeSelector(onClaimTypeSelected = onClaimTypeSelected)

    Spacer(modifier = Modifier.height(16.dp))

    DatePickerComponent(onDateChange = onDateSelected) {
        Text(
            text = stringResource(id = R.string.date_of_service_label),
            style = MobaCaptionRegular.copy(color = SecondaryDark)
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    DirectPayToVetFormVetEmail(
        veterinarianEmail = veterinarianEmail,
        onEmailPressed = onVetEmailPressed
    )

    Spacer(modifier = Modifier.height(16.dp))

    DirectPayToVetFormTextInput(
        title = stringResource(id = R.string.vet_name),
        placeHolderText = stringResource(id = R.string.your_vet),
        value = veterinarianName,
        isEnabled = isEditingVetInformationEnabled,
        onValueChanged = onVeterinarianNameChanged
    )

    Spacer(modifier = Modifier.height(16.dp))

    DirectPayToVetFormTextInput(
        title = stringResource(id = R.string.clinic_name),
        placeHolderText = stringResource(id = R.string.name_of_the_clinic),
        value = clinicName,
        isEnabled = isEditingVetInformationEnabled,
        onValueChanged = onClinicNameChanged
    )

    Spacer(modifier = Modifier.height(16.dp))

    DirectPayToVetFormDescriptionTextBox(
        description = description,
        onDescriptionChanged = onDescriptionChanged
    )

    Spacer(modifier = Modifier.height(32.dp))

    KanguroButton(
        text = stringResource(id = R.string.next),
        enabled = isNextButtonEnabled,
        onClick = onNextPressed
    )

    if (isVetSelectorOpen) {
        DirectPayToVetFormVeterinarianSelector(
            allVetsInfos = allVetsInformation,
            onDismiss = onVetSelectorDismiss,
            vetSearchText = vetSearchText,
            onVetSearchTextChanged = onVetSearchTextChanged,
            onVetSelected = onVetSelected,
            isVetEmailValid = isVetEmailValid,
            onContinueEmailButtonClick = onContinueEmailButtonClick
        )
    }
}

@Composable
fun ClaimTypeSelector(
    onClaimTypeSelected: (FormClaimType) -> Unit
) {
    val claimTypes = FormClaimType.values()

    var selectedOption by remember { mutableStateOf<FormClaimType?>(null) }

    Column(
        modifier = Modifier.padding(top = 16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.what_is_this_claim_related_to),
            style = MobaCaptionRegularSDark
        )
        Row(
            modifier = Modifier.padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            claimTypes.forEach { formClaimType ->
                KanguroCustomCheckBox(
                    label = stringResource(id = formClaimType.label),
                    isChecked = selectedOption == formClaimType,
                    onCheckedChange = {
                        selectedOption = formClaimType
                        onClaimTypeSelected(formClaimType)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun DirectPayToVetFormScreenContentPreview() {
    Surface {
        DirectPayToVetFormScreenContent(
            onClosePressed = {},
            onBackButtonPressed = {},
            onNamePetClick = {},
            petInformation = PetInformation(
                1L,
                "Oliver",
                PetType.dog
            ),
            policyName = "Lauren Ipsum",
            isPetSelectorOpen = false,
            onPetSelectorDismiss = {},
            onPetSelected = {},
            petList = listOf(
                PetInformation(1L, "Oliver", PetType.dog),
                PetInformation(2L, "Mimi", PetType.cat)
            ),
            onClaimTypeSelected = {},
            onDateSelected = {},
            onVetEmailPressed = {},
            allVetsInformation = emptyList(),
            isVetSelectorOpen = false,
            onVetSelected = { },
            onVetSelectorDismiss = {},
            vetSearchText = "",
            onVetSearchTextChanged = {},
            isEditingVetInformationEnabled = false,
            veterinarianEmail = "",
            veterinarianName = "",
            onVeterinarianNameChanged = {},
            clinicName = "",
            onClinicNameChanged = {},
            description = "",
            onDescriptionChanged = {},
            isNextButtonEnabled = false,
            onNextPressed = {},
            isVetEmailValid = true,
            onContinueEmailButtonClick = {}
        )
    }
}

@Preview
@Composable
fun DirectPayToVetFormScreenLoaderStatePreview() {
    Surface {
        DirectPayToVetFormScreenLoaderState(
            onClosePressed = {},
            onBackButtonPressed = {}
        )
    }
}
