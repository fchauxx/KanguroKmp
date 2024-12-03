package com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.AddPictureBottomSheet
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.ErrorComponent
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.ScreenLoader
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.dialog.EditPolicyDialog
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.dialog.FileClaimDialog
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.PolicyStatus
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.AdditionalCoverageItemModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.AdditionalCoverageItemTypeModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.DwellingType
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.PartyItemModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.PartyItemModelRelation
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.PlanSummaryCardModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.RentersCoverageDetailsEvent
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.section.AdditionalCoverageSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.section.AdditionalPartiesSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.section.HeaderSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.section.MainInformationSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.section.PaymentSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.section.ScheduledItemsSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.sections.AdditionalCoverageSection
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.sections.AdditionalPartiesSection
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.sections.HeaderSection
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.sections.MainInformationSection
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.sections.MoreActionsSection
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.sections.PaymentSection
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.sections.ScheduledItemsSection
import com.insurtech.kanguro.designsystem.ui.theme.White
import com.insurtech.kanguro.domain.model.PolicyDocument
import java.math.BigDecimal
import com.insurtech.kanguro.designsystem.dp as pixelToDp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RentersCoverageDetailsScreenContent(
    headerSectionModel: HeaderSectionModel,
    mainInformationSectionModel: MainInformationSectionModel,
    additionalCoverageSectionModel: AdditionalCoverageSectionModel,
    scheduledItemsSectionModel: ScheduledItemsSectionModel,
    additionalPartiesSectionModel: AdditionalPartiesSectionModel,
    paymentSectionModel: PaymentSectionModel,
    showAddPictureBottomSheet: Boolean,
    onEvent: (RentersCoverageDetailsEvent) -> Unit,
    isLoading: Boolean,
    showFileClaimDialog: Boolean = false,
    showEditPolicyInfoDialog: Boolean
) {
    val isScreenError =
        (headerSectionModel.isError && mainInformationSectionModel.isError && additionalCoverageSectionModel.isError && scheduledItemsSectionModel.isError && additionalPartiesSectionModel.isError && paymentSectionModel.isError) || mainInformationSectionModel.isError

    val pullRefreshState = rememberPullRefreshState(
        refreshing = false,
        onRefresh = {
            onEvent(
                RentersCoverageDetailsEvent.OnPullToRefresh
            )
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
            .pullRefresh(pullRefreshState)
    ) {
        if (isLoading) {
            LoaderState()
        } else if (isScreenError) {
            ErrorState(onEvent = onEvent)
        } else {
            Scaffold(
                headerSectionModel = headerSectionModel,
                mainInformationSectionModel = mainInformationSectionModel,
                additionalCoverageSectionModel = additionalCoverageSectionModel,
                scheduledItemsSectionModel = scheduledItemsSectionModel,
                additionalPartiesSectionModel = additionalPartiesSectionModel,
                paymentSectionModel = paymentSectionModel,
                showAddPictureBottomSheet = showAddPictureBottomSheet,
                onEvent = onEvent
            )
        }

        PullRefreshIndicator(
            refreshing = false,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )

        FileClaimDialog(
            showFileClaimDialog = showFileClaimDialog,
            onClick = {
                onEvent(RentersCoverageDetailsEvent.OnEmailPressed)
            },
            onDismiss = {
                onEvent(RentersCoverageDetailsEvent.OnFileClaimDismissed)
            }
        )

        EditPolicyDialog(
            showEditPolicyInfoDialog = showEditPolicyInfoDialog,
            onClick = {
                onEvent(RentersCoverageDetailsEvent.OnEmailPressed)
            },
            onDismiss = {
                onEvent(RentersCoverageDetailsEvent.OnEditPolicyDialogDismissed)
            }
        )
    }
}

@Composable
private fun Content(
    mainInformationSectionModel: MainInformationSectionModel,
    additionalCoverageSectionModel: AdditionalCoverageSectionModel,
    scheduledItemsSectionModel: ScheduledItemsSectionModel,
    additionalPartiesSectionModel: AdditionalPartiesSectionModel,
    paymentSectionModel: PaymentSectionModel,
    propertyInformationHeight: Int,
    scroll: ScrollState,
    onEvent: (RentersCoverageDetailsEvent) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .verticalScroll(scroll)
            .padding(horizontal = 24.dp)
    ) {
        Spacer(Modifier.height(298.dp + propertyInformationHeight.pixelToDp.dp))

        MainInformation(
            mainInformationSectionModel = mainInformationSectionModel,
            onEvent = onEvent
        )

        AdditionalCoverage(
            additionalCoverageSectionModel = additionalCoverageSectionModel,
            onEvent = onEvent
        )

        ScheduledItems(scheduledItemsSectionModel = scheduledItemsSectionModel, onEvent = onEvent)

        AdditionalParties(
            additionalPartiesSectionModel = additionalPartiesSectionModel,
            onEvent = onEvent
        )

        Payment(paymentSectionModel = paymentSectionModel, onEvent = onEvent)

        MoreActions(policyStatus = mainInformationSectionModel.policyStatus, onEvent = onEvent)
    }
}

@Composable
private fun MainInformation(
    mainInformationSectionModel: MainInformationSectionModel,
    onEvent: (RentersCoverageDetailsEvent) -> Unit
) {
    if (!mainInformationSectionModel.isError) {
        MainInformationSection(
            modifier = Modifier,
            model = mainInformationSectionModel,
            onWhatIsCoveredPressed = {
                onEvent(RentersCoverageDetailsEvent.OnWhatIsCoveredPressed)
            },
            onDocumentsPressed = { documentModel ->
                onEvent(RentersCoverageDetailsEvent.OnDocumentPressed(documentModel))
            },
            onEditPolicyDetailPressed = {
                onEvent(RentersCoverageDetailsEvent.OnEditPolicyDetailsPressed)
            }
        )
    }
}

@Composable
private fun AdditionalCoverage(
    additionalCoverageSectionModel: AdditionalCoverageSectionModel,
    onEvent: (RentersCoverageDetailsEvent) -> Unit
) {
    if (!additionalCoverageSectionModel.isError) {
        AdditionalCoverageSection(
            modifier = Modifier.padding(top = 32.dp),
            additionalCoverageSectionModel = additionalCoverageSectionModel,
            onInfoButtonPressed = { type ->
                onEvent(RentersCoverageDetailsEvent.OnAdditionalCoverageInfoPressed(type))
            },
            onEditAdditionalCoveragePressed = {
                onEvent(RentersCoverageDetailsEvent.OnEditAdditionalCoveragePressed)
            }
        )
    }
}

@Composable
private fun ScheduledItems(
    scheduledItemsSectionModel: ScheduledItemsSectionModel,
    onEvent: (RentersCoverageDetailsEvent) -> Unit
) {
    if (!scheduledItemsSectionModel.isError) {
        ScheduledItemsSection(
            modifier = Modifier.padding(top = 32.dp),
            scheduledItemsSectionModel = scheduledItemsSectionModel,
            onMyScheduledItemsPressed = {
                onEvent(RentersCoverageDetailsEvent.OnMyScheduledItemsPressed)
            }
        )
    }
}

@Composable
private fun AdditionalParties(
    additionalPartiesSectionModel: AdditionalPartiesSectionModel,
    onEvent: (RentersCoverageDetailsEvent) -> Unit
) {
    if (!additionalPartiesSectionModel.isError) {
        AdditionalPartiesSection(
            modifier = Modifier.padding(top = 32.dp),
            additionalPartiesSectionModel = additionalPartiesSectionModel,
            onEditAdditionalPartiesPressed = {
                onEvent(RentersCoverageDetailsEvent.OnEditAdditionalPartiesPressed)
            }
        )
    }
}

@Composable
private fun Payment(
    paymentSectionModel: PaymentSectionModel,
    onEvent: (RentersCoverageDetailsEvent) -> Unit
) {
    if (!paymentSectionModel.isError) {
        PaymentSection(
            modifier = Modifier.padding(top = 32.dp),
            paymentSectionModel = paymentSectionModel,
            onBillingPreferencesPressed = {
                onEvent(RentersCoverageDetailsEvent.OnBillingPreferencesPressed)
            }
        )
    }
}

@Composable
private fun MoreActions(
    policyStatus: PolicyStatus,
    onEvent: (RentersCoverageDetailsEvent) -> Unit
) {
    MoreActionsSection(
        modifier = Modifier.padding(top = 64.dp, bottom = 20.dp),
        policyStatus = policyStatus,
        onFileClaimPressed = {
            onEvent(RentersCoverageDetailsEvent.OnFileClaimPressed)
        },
        onTrackYourClaimPressed = {
            onEvent(RentersCoverageDetailsEvent.OnEmailPressed)
        },
        onEditPolicyDetailsPressed = {
            onEvent(RentersCoverageDetailsEvent.OnEditPolicyDetailsPressed)
        },
        onChangeMyAddressPressed = {
            onEvent(RentersCoverageDetailsEvent.OnChangeMyAddressPressed)
        },
        onPhonePressed = {
            onEvent(RentersCoverageDetailsEvent.OnPhonePressed)
        },
        onEmailPressed = {
            onEvent(RentersCoverageDetailsEvent.OnEmailPressed)
        },
        onFrequentlyAskedQuestionsPressed = {
            onEvent(RentersCoverageDetailsEvent.OnFrequentlyAskedQuestionsPressed)
        }
    )
}

@Composable
private fun LoaderState() {
    ScreenLoader(modifier = Modifier.fillMaxSize())
}

@Composable
private fun ErrorState(
    onEvent: (RentersCoverageDetailsEvent) -> Unit
) {
    ErrorComponent(modifier = Modifier.fillMaxSize()) {
        onEvent(RentersCoverageDetailsEvent.OnTryAgainPressed)
    }
}

@Composable
private fun Scaffold(
    headerSectionModel: HeaderSectionModel,
    mainInformationSectionModel: MainInformationSectionModel,
    additionalCoverageSectionModel: AdditionalCoverageSectionModel,
    scheduledItemsSectionModel: ScheduledItemsSectionModel,
    additionalPartiesSectionModel: AdditionalPartiesSectionModel,
    paymentSectionModel: PaymentSectionModel,
    showAddPictureBottomSheet: Boolean,
    onEvent: (RentersCoverageDetailsEvent) -> Unit
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp

    val scroll = rememberScrollState(0)
    var propertyInformationHeight by remember { mutableIntStateOf(0) }
    val initialHeaderPositionRatio = 250f
    val finalHeaderPositionRatio = 130f
    val scrollAmplificationFactor = 1f
    val animationEndPoint = 1f
    val headerStart = screenHeight * (initialHeaderPositionRatio / finalHeaderPositionRatio)

    val progress = java.lang.Float.min(
        scroll.value / (scrollAmplificationFactor * (headerStart - screenHeight)),
        animationEndPoint
    )

    Content(
        mainInformationSectionModel = mainInformationSectionModel,
        propertyInformationHeight = propertyInformationHeight,
        additionalCoverageSectionModel = additionalCoverageSectionModel,
        scheduledItemsSectionModel = scheduledItemsSectionModel,
        additionalPartiesSectionModel = additionalPartiesSectionModel,
        paymentSectionModel = paymentSectionModel,
        scroll = scroll,
        onEvent = onEvent
    )

    HeaderSection(
        progress = progress,
        model = headerSectionModel,
        onEvent = onEvent
    ) {
        propertyInformationHeight = it
    }

    DialogPictureBottomSheet(
        showAddPictureBottomSheet = showAddPictureBottomSheet,
        onEvent = onEvent
    )
}

@Composable
private fun DialogPictureBottomSheet(
    showAddPictureBottomSheet: Boolean,
    onEvent: (RentersCoverageDetailsEvent) -> Unit
) {
    if (showAddPictureBottomSheet) {
        AddPictureBottomSheet(
            onDismiss = { onEvent(RentersCoverageDetailsEvent.DismissPicturePickerDialog) },
            onTakePicturePressed = {
                onEvent(
                    RentersCoverageDetailsEvent.CapturePicture
                )
            },
            onSelectFilePressed = {
                onEvent(
                    RentersCoverageDetailsEvent.SelectFromGallery
                )
            }
        )
    }
}

@Preview
@Composable
fun RentersCoverageDetailsScreenContentPreview(
    modifier: Modifier = Modifier
) {
    Surface {
        RentersCoverageDetailsScreenContent(
            headerSectionModel = HeaderSectionModel(
                userName = "Laurem",
                address = "1234 Main Street, Tampa, FL",
                dwellingType = DwellingType.SingleFamily,
                pictureUrl = ""
            ),
            mainInformationSectionModel = MainInformationSectionModel(
                planSummary = PlanSummaryCardModel(
                    liability = BigDecimal(100),
                    deductible = BigDecimal(100),
                    personalProperty = BigDecimal(100),
                    lossOfUse = BigDecimal(100)
                ),
                documents = listOf(
                    PolicyDocument(
                        id = 0,
                        filename = "Document File Name"
                    )
                ),
                renewDate = "10/30/2023",
                policyStatus = PolicyStatus.ACTIVE
            ),
            additionalCoverageSectionModel = AdditionalCoverageSectionModel(
                listOf(
                    AdditionalCoverageItemModel(
                        type = AdditionalCoverageItemTypeModel.REPLACEMENT_COST,
                        coverageLimit = null,
                        deductible = null,
                        intervalTotal = 3.99.toBigDecimal()
                    ),
                    AdditionalCoverageItemModel(
                        type = AdditionalCoverageItemTypeModel.WATER_SEWER_BACKUP,
                        coverageLimit = BigDecimal(2500),
                        deductible = BigDecimal(250),
                        intervalTotal = 3.99.toBigDecimal()
                    ),
                    AdditionalCoverageItemModel(
                        type = AdditionalCoverageItemTypeModel.FRAUD_PROTECTION,
                        coverageLimit = BigDecimal(15000),
                        deductible = BigDecimal(100),
                        intervalTotal = 3.99.toBigDecimal()
                    )
                )
            ),
            scheduledItemsSectionModel = ScheduledItemsSectionModel(10203.toBigDecimal()),
            additionalPartiesSectionModel = AdditionalPartiesSectionModel(
                listOf(
                    PartyItemModel(
                        id = "1",
                        name = "Benjamin Thompson",
                        relation = PartyItemModelRelation.Spouse
                    ),
                    PartyItemModel(
                        id = "2",
                        name = "Emily Walker Thompson",
                        relation = PartyItemModelRelation.Child
                    ),
                    PartyItemModel(
                        id = "3",
                        name = "Jonathan Walker Thompson",
                        relation = PartyItemModelRelation.Landlord
                    ),
                    PartyItemModel(
                        id = "4",
                        name = "Olivia Roberts",
                        relation = PartyItemModelRelation.Roommate
                    ),
                    PartyItemModel(
                        id = "5",
                        name = "Matthew Anderson",
                        relation = PartyItemModelRelation.PropertyManager
                    )
                )
            ),
            paymentSectionModel = PaymentSectionModel(
                paymentValue = 75.toBigDecimal(),
                invoiceInterval = com.insurtech.kanguro.designsystem.R.string.monthly_payment,
                policyStatus = PolicyStatus.ACTIVE
            ),
            showAddPictureBottomSheet = false,
            onEvent = {},
            isLoading = false,
            showEditPolicyInfoDialog = false
        )
    }
}

@Preview
@Composable
fun RentersCoverageDetailsScreenLoaderPreview(
    modifier: Modifier = Modifier
) {
    Surface {
        RentersCoverageDetailsScreenContent(
            headerSectionModel = HeaderSectionModel(
                userName = "Laurem",
                address = "1234 Main Street, Tampa, FL",
                dwellingType = DwellingType.SingleFamily,
                pictureUrl = ""
            ),
            mainInformationSectionModel = MainInformationSectionModel(
                planSummary = PlanSummaryCardModel(
                    liability = BigDecimal(100),
                    deductible = BigDecimal(100),
                    personalProperty = BigDecimal(100),
                    lossOfUse = BigDecimal(100)
                ),
                documents = listOf(
                    PolicyDocument(
                        id = 0,
                        filename = "Document File Name"
                    )
                ),
                renewDate = "10/30/2023",
                policyStatus = PolicyStatus.ACTIVE
            ),
            additionalCoverageSectionModel = AdditionalCoverageSectionModel(
                listOf(
                    AdditionalCoverageItemModel(
                        type = AdditionalCoverageItemTypeModel.REPLACEMENT_COST,
                        coverageLimit = null,
                        deductible = null,
                        intervalTotal = 3.99.toBigDecimal()
                    ),
                    AdditionalCoverageItemModel(
                        type = AdditionalCoverageItemTypeModel.WATER_SEWER_BACKUP,
                        coverageLimit = BigDecimal(2500),
                        deductible = BigDecimal(250),
                        intervalTotal = 3.99.toBigDecimal()
                    ),
                    AdditionalCoverageItemModel(
                        type = AdditionalCoverageItemTypeModel.FRAUD_PROTECTION,
                        coverageLimit = BigDecimal(15000),
                        deductible = BigDecimal(100),
                        intervalTotal = 3.99.toBigDecimal()
                    )
                )
            ),
            scheduledItemsSectionModel = ScheduledItemsSectionModel(10203.toBigDecimal()),
            additionalPartiesSectionModel = AdditionalPartiesSectionModel(
                listOf(
                    PartyItemModel(
                        id = "1",
                        name = "Benjamin Thompson",
                        relation = PartyItemModelRelation.Spouse
                    ),
                    PartyItemModel(
                        id = "2",
                        name = "Emily Walker Thompson",
                        relation = PartyItemModelRelation.Child
                    ),
                    PartyItemModel(
                        id = "3",
                        name = "Jonathan Walker Thompson",
                        relation = PartyItemModelRelation.Landlord
                    ),
                    PartyItemModel(
                        id = "4",
                        name = "Olivia Roberts",
                        relation = PartyItemModelRelation.Roommate
                    ),
                    PartyItemModel(
                        id = "5",
                        name = "Matthew Anderson",
                        relation = PartyItemModelRelation.PropertyManager
                    )
                )
            ),
            paymentSectionModel = PaymentSectionModel(
                paymentValue = 75.toBigDecimal(),
                invoiceInterval = com.insurtech.kanguro.designsystem.R.string.monthly_payment,
                policyStatus = PolicyStatus.ACTIVE
            ),
            showAddPictureBottomSheet = false,
            onEvent = {},
            isLoading = true,
            showEditPolicyInfoDialog = false
        )
    }
}

@Preview
@Composable
fun RentersCoverageDetailsScreenErrorPreview(
    modifier: Modifier = Modifier
) {
    Surface {
        RentersCoverageDetailsScreenContent(
            headerSectionModel = HeaderSectionModel(
                userName = "Laurem",
                address = "1234 Main Street, Tampa, FL",
                dwellingType = DwellingType.SingleFamily,
                pictureUrl = "",
                isError = true
            ),
            mainInformationSectionModel = MainInformationSectionModel(
                planSummary = PlanSummaryCardModel(
                    liability = BigDecimal(100),
                    deductible = BigDecimal(100),
                    personalProperty = BigDecimal(100),
                    lossOfUse = BigDecimal(100)
                ),
                documents = listOf(
                    PolicyDocument(
                        id = 0,
                        filename = "Document File Name"
                    )
                ),
                renewDate = "10/30/2024",
                startDate = "10/30/2023",
                endDate = "10/30/2024",
                policyStatus = PolicyStatus.ACTIVE,
                isError = true
            ),
            additionalCoverageSectionModel = AdditionalCoverageSectionModel(
                listOf(
                    AdditionalCoverageItemModel(
                        type = AdditionalCoverageItemTypeModel.REPLACEMENT_COST,
                        coverageLimit = null,
                        deductible = null,
                        intervalTotal = 3.99.toBigDecimal()
                    ),
                    AdditionalCoverageItemModel(
                        type = AdditionalCoverageItemTypeModel.WATER_SEWER_BACKUP,
                        coverageLimit = BigDecimal(2500),
                        deductible = BigDecimal(250),
                        intervalTotal = 3.99.toBigDecimal()
                    ),
                    AdditionalCoverageItemModel(
                        type = AdditionalCoverageItemTypeModel.FRAUD_PROTECTION,
                        coverageLimit = BigDecimal(15000),
                        deductible = BigDecimal(100),
                        intervalTotal = 3.99.toBigDecimal()
                    )
                ),
                isError = true
            ),
            scheduledItemsSectionModel = ScheduledItemsSectionModel(
                10203.toBigDecimal(),
                isError = true
            ),
            additionalPartiesSectionModel = AdditionalPartiesSectionModel(
                listOf(
                    PartyItemModel(
                        id = "1",
                        name = "Benjamin Thompson",
                        relation = PartyItemModelRelation.Spouse
                    ),
                    PartyItemModel(
                        id = "2",
                        name = "Emily Walker Thompson",
                        relation = PartyItemModelRelation.Child
                    ),
                    PartyItemModel(
                        id = "3",
                        name = "Jonathan Walker Thompson",
                        relation = PartyItemModelRelation.Landlord
                    ),
                    PartyItemModel(
                        id = "4",
                        name = "Olivia Roberts",
                        relation = PartyItemModelRelation.Roommate
                    ),
                    PartyItemModel(
                        id = "5",
                        name = "Matthew Anderson",
                        relation = PartyItemModelRelation.PropertyManager
                    )
                ),
                isError = true
            ),
            paymentSectionModel = PaymentSectionModel(
                paymentValue = 75.toBigDecimal(),
                invoiceInterval = com.insurtech.kanguro.designsystem.R.string.monthly_payment,
                policyStatus = PolicyStatus.ACTIVE,
                isError = true
            ),
            showAddPictureBottomSheet = false,
            onEvent = {},
            isLoading = false,
            showEditPolicyInfoDialog = false
        )
    }
}
