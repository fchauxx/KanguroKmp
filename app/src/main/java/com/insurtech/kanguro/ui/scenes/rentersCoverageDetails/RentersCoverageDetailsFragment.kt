package com.insurtech.kanguro.ui.scenes.rentersCoverageDetails

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.graphics.Insets
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.insurtech.kanguro.R
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.base.FullscreenFragment
import com.insurtech.kanguro.core.utils.IntentUtils
import com.insurtech.kanguro.core.utils.bottomSheetLikeTransitions
import com.insurtech.kanguro.core.utils.getFileExtension
import com.insurtech.kanguro.core.utils.getPetPictureBase64
import com.insurtech.kanguro.core.utils.openImage
import com.insurtech.kanguro.core.utils.openPdf
import com.insurtech.kanguro.core.utils.safeNavigate
import com.insurtech.kanguro.databinding.FragmentRentersCoverageDetailsBinding
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.PolicyStatus
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.InformationDialog
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.RentersCoverageDetailsScreenContent
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.AdditionalCoverageItemModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.AdditionalCoverageItemTypeModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.DwellingType
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.PartyItemModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.PartyItemModelRelation
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.PlanSummaryCardModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.RentersCoverageDetailsEvent
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.getInfoDialogContent
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.getInfoDialogTitle
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.section.AdditionalCoverageSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.section.AdditionalPartiesSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.section.HeaderSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.section.MainInformationSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.section.PaymentSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.section.ScheduledItemsSectionModel
import com.insurtech.kanguro.designsystem.ui.theme.BKSParagraphRegular
import com.insurtech.kanguro.domain.model.FilePickerResult
import com.insurtech.kanguro.domain.model.PolicyDocument
import com.insurtech.kanguro.ui.compose.utils.GetPictureComponent
import com.insurtech.kanguro.ui.scenes.moreActions.MoreFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import java.math.BigDecimal

@AndroidEntryPoint
class RentersCoverageDetailsFragment : FullscreenFragment<FragmentRentersCoverageDetailsBinding>() {

    override val screenName = AnalyticsEnums.Screen.RentersCoverageDetails

    override val viewModel: RentersCoverageDetailsViewModel by viewModels()

    private val contentResolver by lazy {
        requireActivity().contentResolver
    }

    override fun onCreateBinding(inflater: LayoutInflater): FragmentRentersCoverageDetailsBinding =
        FragmentRentersCoverageDetailsBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        setupObservers()
    }

    fun setupObservers() {
        viewModel.openAttachmentEvent.observe(viewLifecycleOwner) { fileUri ->
            if (fileUri == null) {
                val alert = MaterialAlertDialogBuilder(requireContext())
                    .setTitle(requireContext().getString(R.string.error_dialog_title))
                    .setMessage(requireContext().getString(R.string.error_dialog_generic_message))
                    .setPositiveButton(requireContext().getString(R.string.back), null)
                alert.show()
            } else {
                openDocument(fileUri)
            }
        }
    }

    override fun processWindowInsets(insets: Insets) {}

    private fun setupUi() {
        binding.composeView.apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
            setContent {
                RentersCoverageDetailsFragment(
                    viewModel = viewModel,
                    onEvent = ::handleEvent,
                    context = requireContext(),
                    handleCapturedImage = ::handleCapturedImage
                )
            }
        }
    }

    private fun handleEvent(event: RentersCoverageDetailsEvent) {
        when (event) {
            is RentersCoverageDetailsEvent.Back -> findNavController().popBackStack()

            is RentersCoverageDetailsEvent.EditPicturePressed -> viewModel.onEditPicturePressed()

            is RentersCoverageDetailsEvent.DismissPicturePickerDialog -> viewModel.onPictureDone()

            is RentersCoverageDetailsEvent.CapturePicture -> viewModel.onCapturePicturePressed()

            is RentersCoverageDetailsEvent.SelectFromGallery -> viewModel.onSelectPicturePressed()

            is RentersCoverageDetailsEvent.OnBillingPreferencesPressed -> onBillingPreferencesPressed()

            is RentersCoverageDetailsEvent.OnFileClaimPressed -> viewModel.setShowFileClaimDialog(
                true
            )

            is RentersCoverageDetailsEvent.OnEditPolicyDetailsPressed -> viewModel.onEditPolicyPressed(
                showDialog = true
            )

            is RentersCoverageDetailsEvent.OnChangeMyAddressPressed -> viewModel.onEditPolicyPressed(
                showDialog = true
            )

            is RentersCoverageDetailsEvent.OnPhonePressed -> onPhoneNumberPressed()

            is RentersCoverageDetailsEvent.OnEmailPressed -> onEmailPressed()

            is RentersCoverageDetailsEvent.OnFrequentlyAskedQuestionsPressed -> onFrequentlyAskedQuestionsPressed()

            is RentersCoverageDetailsEvent.OnAdditionalCoverageInfoPressed ->
                viewModel.onAdditionalCoverageInfoPressed(event.additionalCoverageItemTypeModel)

            is RentersCoverageDetailsEvent.OnEditAdditionalCoveragePressed -> viewModel.onEditPolicyPressed(
                showDialog = true
            )

            is RentersCoverageDetailsEvent.OnMyScheduledItemsPressed -> onMyScheduledItemsPressed()

            is RentersCoverageDetailsEvent.OnEditAdditionalPartiesPressed -> viewModel.onEditPolicyPressed(
                showDialog = true
            )

            is RentersCoverageDetailsEvent.OnWhatIsCoveredPressed -> onWhatIsCoveredPressed()

            is RentersCoverageDetailsEvent.OnDocumentPressed -> onDocumentPressed(event.document)

            is RentersCoverageDetailsEvent.OnTryAgainPressed -> viewModel.fetchData()

            is RentersCoverageDetailsEvent.OnPullToRefresh -> viewModel.fetchData()

            is RentersCoverageDetailsEvent.OnFileClaimDismissed -> viewModel.setShowFileClaimDialog(
                show = false
            )

            is RentersCoverageDetailsEvent.OnEditPolicyDialogDismissed -> viewModel.onEditPolicyPressed(
                showDialog = false
            )
        }
    }

    private fun onTrackYourClaimPressed() {
        findNavController()
            .safeNavigate(RentersCoverageDetailsFragmentDirections.actionGlobalPetTrackYourClaimsFragment())
    }

    private fun onEditPolicyDetailsPressed() {
        findNavController()
            .safeNavigate(
                RentersCoverageDetailsFragmentDirections
                    .actionRentersCoverageDetailsFragmentToRentersEditCoverageDetailsFragment(
                        viewModel.getPolicyId(),
                        viewModel.getResidenceState(),
                        viewModel.getRentersEditCoverageInitialValues()
                    )
            )
    }

    private fun onChangeMyAddressPressed() {
        findNavController()
            .safeNavigate(
                RentersCoverageDetailsFragmentDirections
                    .actionRentersCoverageDetailsFragmentToRentersChangeMyAddressFragment()
            )
    }

    private fun onFrequentlyAskedQuestionsPressed() {
        findNavController()
            .safeNavigate(
                RentersCoverageDetailsFragmentDirections
                    .actionRentersCoverageDetailsFragmentToRentersFrequentlyAskedQuestionsFragment()
            )
    }

    private fun onPhoneNumberPressed() {
        val phoneNumber = getString(R.string.phone_number_support)
        IntentUtils.openDialIntent(requireContext(), phoneNumber)
    }

    private fun onEmailPressed() {
        IntentUtils.openMailToKanguroRentersClaims(requireContext())
    }

    private fun onBillingPreferencesPressed() {
        findNavController().safeNavigate(
            MoreFragmentDirections.actionGlobalPaymentSettingsFragment()
        )
    }

    private fun handleCapturedImage(result: FilePickerResult<Uri>) {
        when (result) {
            is FilePickerResult.Success -> {
                val petPictureBase64 = result.data.getPetPictureBase64(contentResolver)
                viewModel.onPictureDone()
                viewModel.updateHousePicture(petPictureBase64)
            }

            is FilePickerResult.Error -> {
                // TODO Implement when integration with backend is implemented
            }
        }
    }

    private fun onEditAdditionalCoveragePressed() {
        val policyInfo = viewModel.getPolicyInfoSharedFlow()
        if (policyInfo != null) {
            findNavController().safeNavigate(
                RentersCoverageDetailsFragmentDirections.actionRentersCoverageDetailsFragmentToRentersEditAdditionalCoverageBottomSheet(
                    policyInfo
                )
            )
        }
    }

    private fun onMyScheduledItemsPressed() {
        findNavController().safeNavigate(
            RentersCoverageDetailsFragmentDirections
                .actionRentersCoverageDetailsFragmentToScheduledItemsFragment(
                    viewModel.getPolicyId(),
                    true // TODO When endorsements is done change to -> viewModel.getPolicyStatus() != PolicyStatus.ACTIVE
                )
        )
    }

    private fun onEditAdditionalPartiesPressed() {
        findNavController()
            .safeNavigate(
                RentersCoverageDetailsFragmentDirections
                    .actionRentersCoverageDetailsFragmentToRentersEditAdditionalPartiesFragment(
                        viewModel.getPolicyId()
                    )
            )
    }

    private fun onWhatIsCoveredPressed() {
        findNavController().navigate(
            RentersCoverageDetailsFragmentDirections
                .actionRentersCoverageDetailsFragmentToRentersWhatIsCoveredBottomSheet(),
            bottomSheetLikeTransitions
        )
    }

    private fun onDocumentPressed(policyDocument: PolicyDocument) {
        viewModel.getPolicyDocument(policyDocument)
    }

    private fun openDocument(fileUri: Uri) {
        val extension = fileUri.getFileExtension(contentResolver)

        if (extension == PDF_EXTENSION) {
            requireContext().openPdf(fileUri)
        } else {
            requireContext().openImage(fileUri)
        }
    }

    companion object {
        private const val PDF_EXTENSION = ".pdf"
    }
}

@Composable
private fun RentersCoverageDetailsFragment(
    viewModel: RentersCoverageDetailsViewModel,
    onEvent: (RentersCoverageDetailsEvent) -> Unit,
    context: Context,
    handleCapturedImage: (FilePickerResult<Uri>) -> Unit
) {
    val uiState: RentersCoverageDetailsViewModel.UiState by viewModel.uiState.collectAsState()

    GetPictureComponent(
        context = context,
        showCapturePicture = uiState.showCapturePicture,
        showSelectPicture = uiState.showSelectPicture
    ) { result ->
        handleCapturedImage(result)
    }

    if (uiState.showAdditionalCoverageInfoDialog != null) {
        val title = uiState.showAdditionalCoverageInfoDialog?.getInfoDialogTitle()
        val content = uiState.showAdditionalCoverageInfoDialog?.getInfoDialogContent()

        if (title != null && content != null) {
            InformationDialog(
                title = stringResource(id = title),
                content = {
                    Text(
                        text = stringResource(id = content),
                        style = BKSParagraphRegular
                    )
                },
                onDismiss = {
                    viewModel.onAdditionalCoverageInfoPressed(null)
                }
            )
        }
    }

    RentersCoverageDetailsScreenContent(
        // pictureUrl = "https://www.redentora.com.br/foto_/2023/23017/mirassol-casa-condominio-terras-alphaville-mirassol-30-06-2023_11-11-20-14.webp",
        headerSectionModel = uiState.headerSectionModel,
        mainInformationSectionModel = uiState.mainInformationSectionModel,
        additionalCoverageSectionModel = uiState.additionalCoverageSectionModel,
        scheduledItemsSectionModel = uiState.scheduledItemsSectionModel,
        additionalPartiesSectionModel = uiState.additionalPartiesSectionModel,
        showFileClaimDialog = uiState.showFileClaimDialog,
        paymentSectionModel = uiState.paymentSectionModel,
        showAddPictureBottomSheet = uiState.showAddPictureBottomSheet,
        onEvent = onEvent,
        isLoading = uiState.isScreenLoader,
        showEditPolicyInfoDialog = uiState.showEditPolicyInfoDialog
    )
}

@Preview
@Composable
private fun RentersCoverageDetailsFragmentPreview() {
    Surface {
        RentersCoverageDetailsScreenContent(
            headerSectionModel = HeaderSectionModel(
                userName = "Laurem",
                address = "1234 Main Street, Tampa, FL",
                dwellingType = DwellingType.SingleFamily,
                pictureUrl = "",
                policyNumber = "123456789"
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
                startDate = "10/30/2022",
                endDate = "10/30/2023",
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
                invoiceInterval = R.string.monthly_payment,
                policyStatus = PolicyStatus.ACTIVE
            ),
            showAddPictureBottomSheet = false,
            onEvent = {},
            isLoading = false,
            showEditPolicyInfoDialog = false
        )
    }
}
