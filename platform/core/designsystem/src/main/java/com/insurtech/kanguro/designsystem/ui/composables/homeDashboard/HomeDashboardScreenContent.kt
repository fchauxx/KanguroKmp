package com.insurtech.kanguro.designsystem.ui.composables.homeDashboard

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.insurtech.kanguro.designsystem.R
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.BottomGradientAlpha5
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.CoverageFilterChipGroup
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.DonationBanner
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.PetUpsellingBanner
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.ReferAFriendBanner
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.RentersCoveragesListComponent
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.RentersUpsellingBanner
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.TopGradientLine
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.dialog.FileClaimDialog
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.CoverageFilter
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.CoverageStatusUi
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.PetType
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.PetsCoverageSummaryCardModel
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.RentersCoverageSummaryCardModel
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.petsCoverageList.LiveVetPetsCoveragesListComponent
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.petsCoverageList.PetsCoveragesListComponent
import com.insurtech.kanguro.designsystem.ui.composables.homeDashboard.model.HomeDashboardEvent
import com.insurtech.kanguro.designsystem.ui.composables.homeDashboard.model.ItemReminderUiModel
import com.insurtech.kanguro.designsystem.ui.composables.homeDashboard.model.ReminderTypeUiModel
import com.insurtech.kanguro.designsystem.ui.composables.homeDashboard.sections.HomeEmergencySection
import com.insurtech.kanguro.designsystem.ui.composables.homeDashboard.sections.MoreActionsSection
import com.insurtech.kanguro.designsystem.ui.composables.homeDashboard.sections.RemindersSection
import com.insurtech.kanguro.designsystem.ui.composables.homeDashboard.sections.UpperCaseCaseSectionTitle
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.DwellingType
import com.insurtech.kanguro.designsystem.ui.theme.MobaTitle1
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground20
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground40
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground5
import com.insurtech.kanguro.designsystem.ui.theme.NeutralBackground80
import com.insurtech.kanguro.designsystem.ui.theme.PrimaryDarkest
import java.util.Calendar

@Composable
fun HomeDashboardScreenContent(
    modifier: Modifier = Modifier,
    userName: String,
    petsNames: List<String>,
    rentersCoverages: List<RentersCoverageSummaryCardModel>,
    petsCoverages: List<PetsCoverageSummaryCardModel>,
    reminders: List<ItemReminderUiModel>,
    showRentersUpsellingBanner: Boolean = false,
    showPetUpsellingBanner: Boolean = false,
    showPetCoveragesFilter: Boolean = false,
    showRentersCoveragesFilter: Boolean = false,
    selectedPetCoverageFilter: CoverageFilter = CoverageFilter.All,
    selectedRentersCoverageFilter: CoverageFilter = CoverageFilter.All,
    showSelectFileAClaimTypeDialog: Boolean = false,
    showRentersFileAClaimDialog: Boolean = false,
    showLiveVeterinary: Boolean = false,
    isError: Boolean = false,
    isLoading: Boolean = false,
    onEvent: (HomeDashboardEvent) -> Unit
) {
    if (isLoading) {
        HomeDashboardLoading(
            onNotificationPressed = { onEvent(HomeDashboardEvent.OnNotificationsPressed) }
        )
    } else if (isError) {
        HomeDashboardError(
            onNotificationPressed = { onEvent(HomeDashboardEvent.OnNotificationsPressed) },
            onTryAgainPressed = { onEvent(HomeDashboardEvent.OnTryAgainPressed) }
        )
    } else {
        HomeDashboard(
            modifier = modifier,
            userName = userName,
            petsNames = petsNames,
            rentersCoverages = rentersCoverages,
            petsCoverages = petsCoverages,
            reminders = reminders,
            showRentersUpsellingBanner = showRentersUpsellingBanner,
            showPetUpsellingBanner = showPetUpsellingBanner,
            showPetCoverageFilter = showPetCoveragesFilter,
            showRentersCoverageFilter = showRentersCoveragesFilter,
            selectedPetCoverageFilter = selectedPetCoverageFilter,
            selectedRentersCoverageFilter = selectedRentersCoverageFilter,
            showSelectFileAClaimTypeDialog = showSelectFileAClaimTypeDialog,
            showRentersFileAClaimDialog = showRentersFileAClaimDialog,
            showLiveVeterinary = showLiveVeterinary,
            onEvent = onEvent
        )
    }
}

@Composable
private fun HomeDashboard(
    modifier: Modifier = Modifier,
    userName: String,
    petsNames: List<String>,
    rentersCoverages: List<RentersCoverageSummaryCardModel>,
    petsCoverages: List<PetsCoverageSummaryCardModel>,
    reminders: List<ItemReminderUiModel>,
    showRentersUpsellingBanner: Boolean = false,
    showPetUpsellingBanner: Boolean = false,
    showPetCoverageFilter: Boolean,
    showRentersCoverageFilter: Boolean,
    selectedPetCoverageFilter: CoverageFilter = CoverageFilter.All,
    selectedRentersCoverageFilter: CoverageFilter = CoverageFilter.All,
    showSelectFileAClaimTypeDialog: Boolean,
    showRentersFileAClaimDialog: Boolean,
    showLiveVeterinary: Boolean,
    onEvent: (HomeDashboardEvent) -> Unit
) {
    FileAClaimTypeModalBottomSheet(
        modalBottomSheet = showSelectFileAClaimTypeDialog,
        showRentersOption = !showRentersUpsellingBanner,
        showPetOption = !showPetUpsellingBanner,
        onDismiss = {
            onEvent(HomeDashboardEvent.OnDismissFileAClaimTypeModal)
        },
        onPetFileAClaim = {
            onEvent(HomeDashboardEvent.OnPetFileClaimPressed)
        },
        onRentersFileAClaim = {
            onEvent(HomeDashboardEvent.OnRentersFileClaimPressed)
        }
    )

    FileClaimDialog(
        showFileClaimDialog = showRentersFileAClaimDialog,
        onClick = {
            onEvent(HomeDashboardEvent.OnEmailPressed)
        },
        onDismiss = {
            onEvent(HomeDashboardEvent.OnDismissRentersFileAClaimDialog)
        }
    )

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(color = NeutralBackground)
    ) {
        val (header, gradient, content, bottomGradient) = createRefs()

        HomeHeader(
            modifier = Modifier.constrainAs(header) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        ) {
            onEvent(HomeDashboardEvent.OnNotificationsPressed)
        }

        Column(
            modifier = Modifier
                .constrainAs(content) {
                    top.linkTo(header.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                }
                .verticalScroll(rememberScrollState())
        ) {
            ScreenTitle(userName = userName, petsNames = petsNames)

            if (reminders.isNotEmpty()) {
                Reminders(reminders = reminders, onEvent = onEvent)
            }

            if (!showPetUpsellingBanner) {
                PetsCoverages(
                    petsCoverages = petsCoverages,
                    showPetCoverageFilter = showPetCoverageFilter,
                    selectedPetCoverageFilter = selectedPetCoverageFilter,
                    showLiveVeterinary = showLiveVeterinary,
                    onEvent = onEvent
                )
            }

            if (!showRentersUpsellingBanner) {
                RentersCoverages(
                    rentersCoverages = rentersCoverages,
                    showRentersCoverageFilter = showRentersCoverageFilter,
                    selectedRentersCoverageFilter = selectedRentersCoverageFilter,
                    onEvent = onEvent
                )
            }

            UpsellingBanners(
                showRentersUpsellingBanner = showRentersUpsellingBanner,
                showPetUpsellingBanner = showPetUpsellingBanner,
                onEvent = onEvent
            )

            DonationBanner(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .padding(top = 32.dp)
            ) {
                onEvent(HomeDashboardEvent.OnDonationBannerPressed)
            }

            ReferAFriendBanner(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .padding(top = 32.dp)
            ) {
                onEvent(HomeDashboardEvent.OnReferAFriendBannerPressed)
            }

            if (!showPetUpsellingBanner && showLiveVeterinary) {
                HomeEmergencySection(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .padding(top = 32.dp),
                    onEvent = onEvent
                )
            }

            MoreActions(onEvent = onEvent)
        }

        TopGradientLine(
            colors = listOf(
                NeutralBackground,
                NeutralBackground80,
                NeutralBackground40,
                NeutralBackground20,
                NeutralBackground5
            ),
            modifier = Modifier.constrainAs(gradient) {
                top.linkTo(header.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        BottomGradientAlpha5(
            modifier = Modifier.constrainAs(bottomGradient) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
    }
}

@Composable
private fun ScreenTitle(userName: String, petsNames: List<String>) {
    val showPetTitle = petsNames.isNotEmpty()
    AnimatedContent(targetState = showPetTitle, label = "") { show ->
        if (show) {
            PetTitle(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                userName = userName,
                petsNames = petsNames
            )
        } else {
            RentersTitle(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .padding(top = 12.dp)
                    .fillMaxWidth(),
                userName = userName
            )
        }
    }
}

@Composable
private fun PetTitle(modifier: Modifier, userName: String, petsNames: List<String>) {
    if (userName.isBlank()) {
        Text(
            modifier = modifier,
            style = MobaTitle1,
            text = stringResource(id = R.string.hello_comma)
        )
        return
    }

    if (petsNames.size == 1) {
        Text(
            modifier = modifier,
            style = MobaTitle1,
            text = buildAnnotatedString {
                append(stringResource(id = R.string.pet_greeting, userName))

                withStyle(style = SpanStyle(color = PrimaryDarkest)) {
                    append(" ${petsNames.first()}")
                }
            }
        )
    } else {
        PetDynamicTitle(
            modifier = modifier,
            userName = userName,
            petsNames = petsNames
        )
    }
}

@Composable
private fun Reminders(
    reminders: List<ItemReminderUiModel>,
    onEvent: (HomeDashboardEvent) -> Unit
) {
    Spacer(modifier = Modifier.height(40.dp))
    RemindersSection(
        reminders = reminders,
        onReminderPressed = {
            onEvent(HomeDashboardEvent.OnReminderPressed(it))
        },
        onSeeAllPressed = {
            onEvent(HomeDashboardEvent.OnSeeAllRemindersPressed)
        }
    )
}

@Composable
private fun RentersCoverages(
    onEvent: (HomeDashboardEvent) -> Unit,
    showRentersCoverageFilter: Boolean,
    selectedRentersCoverageFilter: CoverageFilter,
    rentersCoverages: List<RentersCoverageSummaryCardModel>
) {
    Spacer(modifier = Modifier.height(32.dp))

    UpperCaseCaseSectionTitle(
        title = R.string.renters_insurance,
        modifier = Modifier.padding(horizontal = 24.dp)
    )

    Spacer(modifier = Modifier.height(8.dp))

    if (showRentersCoverageFilter) {
        CoverageFilterChipGroup(
            modifier = Modifier.padding(horizontal = 24.dp),
            selectedFilter = selectedRentersCoverageFilter,
            onFilterChanged = {
                onEvent(HomeDashboardEvent.OnRentersCoverageFilterPressed(it))
            }
        )
    }

    Spacer(modifier = Modifier.height(8.dp))

    RentersCoveragesListComponent(
        coverages = rentersCoverages,
        onCoveragePressed = {
            onEvent(HomeDashboardEvent.OnRentersCoveragePressed(it))
        },
        onAddResidencePressed = {
            onEvent(HomeDashboardEvent.OnAddResidencePressed)
        }
    )
}

@Composable
private fun PetsCoverages(
    onEvent: (HomeDashboardEvent) -> Unit,
    showPetCoverageFilter: Boolean,
    selectedPetCoverageFilter: CoverageFilter,
    showLiveVeterinary: Boolean,
    petsCoverages: List<PetsCoverageSummaryCardModel>
) {
    Spacer(modifier = Modifier.height(48.dp))

    UpperCaseCaseSectionTitle(
        title = R.string.pet_upselling_pet_health_plan,
        modifier = Modifier.padding(horizontal = 24.dp)
    )

    Spacer(modifier = Modifier.height(8.dp))

    if (showPetCoverageFilter) {
        CoverageFilterChipGroup(
            modifier = Modifier.padding(horizontal = 24.dp),
            selectedFilter = selectedPetCoverageFilter,
            onFilterChanged = {
                onEvent(HomeDashboardEvent.OnPetCoverageFilterPressed(it))
            }
        )
    }

    Spacer(modifier = Modifier.height(8.dp))

    if (showLiveVeterinary) {
        LiveVetPetsCoveragesListComponent(
            coverages = petsCoverages,
            onCoveragePressed = {
                onEvent(HomeDashboardEvent.OnPetsCoveragePressed(it))
            },
            onAddPetPressed = {
                onEvent(HomeDashboardEvent.OnAddPetsPressed)
            },
            onLiveVetPressed = {
                onEvent(HomeDashboardEvent.OnLiveVetPressed)
            }
        )
    } else {
        PetsCoveragesListComponent(
            coverages = petsCoverages,
            onCoveragePressed = {
                onEvent(HomeDashboardEvent.OnPetsCoveragePressed(it))
            },
            onAddPetPressed = {
                onEvent(HomeDashboardEvent.OnAddPetsPressed)
            }
        )
    }
}

@Composable
private fun UpsellingBanners(
    showRentersUpsellingBanner: Boolean,
    showPetUpsellingBanner: Boolean,
    onEvent: (HomeDashboardEvent) -> Unit
) {
    AnimatedVisibility(visible = showRentersUpsellingBanner) {
        RentersUpsellingBanner(
            modifier = Modifier
                .padding(top = 32.dp)
                .padding(horizontal = 24.dp)
        ) {
            onEvent(HomeDashboardEvent.OnRentersUpsellingBannerPressed)
        }
    }

    AnimatedVisibility(visible = showPetUpsellingBanner) {
        PetUpsellingBanner(
            modifier = Modifier
                .padding(top = 32.dp)
                .padding(horizontal = 24.dp)
        ) {
            onEvent(HomeDashboardEvent.OnPetUpsellingBannerPressed)
        }
    }
}

@Composable
private fun MoreActions(
    onEvent: (HomeDashboardEvent) -> Unit
) {
    MoreActionsSection(
        Modifier
            .padding(
                top = 32.dp
            )
            .padding(horizontal = 24.dp),
        onEvent = onEvent
    )

    Spacer(modifier = Modifier.height(20.dp))
}

@Preview
@Composable
fun HomeDashboardScreenContentPreview() {
    Surface {
        HomeDashboardScreenContent(
            onEvent = {},
            userName = "Lauren",
            petsNames = listOf("Oliver", "Luna"),
            rentersCoverages = listOf(
                RentersCoverageSummaryCardModel(
                    "",
                    "Tampa, FL",
                    DwellingType.SingleFamily,
                    CoverageStatusUi.Active
                )
            ),
            petsCoverages = listOf(
                PetsCoverageSummaryCardModel(
                    id = "1",
                    breed = "Labradoodle",
                    birthDate = Calendar.getInstance().apply {
                        set(Calendar.YEAR, 2019)
                        set(Calendar.MONTH, Calendar.NOVEMBER)
                        set(Calendar.DAY_OF_MONTH, 29)
                    }.time,
                    status = CoverageStatusUi.Active,
                    pictureUrl = "",
                    name = "Oliver",
                    petType = PetType.Cat
                ),
                PetsCoverageSummaryCardModel(
                    id = "1",
                    breed = "Poodle",
                    birthDate = Calendar.getInstance().apply {
                        set(Calendar.YEAR, 2020)
                        set(Calendar.MONTH, Calendar.FEBRUARY)
                        set(Calendar.DAY_OF_MONTH, 15)
                    }.time,
                    status = CoverageStatusUi.Pending,
                    pictureUrl = "",
                    name = "Luna",
                    petType = PetType.Dog
                )
            ),
            reminders = listOf(
                ItemReminderUiModel(
                    id = "",
                    type = ReminderTypeUiModel.AddPet,
                    petId = 0L,
                    petName = "Oliver",
                    petType = PetType.Dog,
                    petPictureUrl = "",
                    claimId = ""
                ),
                ItemReminderUiModel(
                    id = "",
                    type = ReminderTypeUiModel.FleaMedication,
                    petId = 0L,
                    petName = "Oliver",
                    petType = PetType.Dog,
                    petPictureUrl = "",
                    clinicName = "Pet Loves Clinic",
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
                    claimId = ""
                )
            ),
            showPetCoveragesFilter = true,
            showRentersCoveragesFilter = true
        )
    }
}

@Preview
@Composable
fun HomeDashboardScreenContentUpsellingPreview() {
    Surface {
        HomeDashboardScreenContent(
            onEvent = {},
            userName = "Lauren",
            petsNames = listOf("Oliver", "Luna"),
            rentersCoverages = listOf(
                RentersCoverageSummaryCardModel(
                    "",
                    "Tampa, FL",
                    DwellingType.SingleFamily,
                    CoverageStatusUi.Active
                )
            ),
            petsCoverages = listOf(
                PetsCoverageSummaryCardModel(
                    id = "1",
                    breed = "Labradoodle",
                    birthDate = Calendar.getInstance().apply {
                        set(Calendar.YEAR, 2019)
                        set(Calendar.MONTH, Calendar.NOVEMBER)
                        set(Calendar.DAY_OF_MONTH, 29)
                    }.time,
                    status = CoverageStatusUi.Active,
                    pictureUrl = "",
                    name = "Oliver",
                    petType = PetType.Cat
                ),
                PetsCoverageSummaryCardModel(
                    id = "1",
                    breed = "Poodle",
                    birthDate = Calendar.getInstance().apply {
                        set(Calendar.YEAR, 2020)
                        set(Calendar.MONTH, Calendar.FEBRUARY)
                        set(Calendar.DAY_OF_MONTH, 15)
                    }.time,
                    status = CoverageStatusUi.Pending,
                    pictureUrl = "",
                    name = "Luna",
                    petType = PetType.Dog
                )
            ),
            showPetUpsellingBanner = true,
            showRentersUpsellingBanner = true,
            reminders = listOf()
        )
    }
}

@Preview
@Composable
fun PetTitlePreview() {
    Surface {
        PetTitle(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            userName = "Lauren",
            petsNames = listOf("Bobby")
        )
    }
}

@Preview
@Composable
fun PetTitleEmptyPreview() {
    Surface {
        PetTitle(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            userName = "",
            petsNames = emptyList()
        )
    }
}
