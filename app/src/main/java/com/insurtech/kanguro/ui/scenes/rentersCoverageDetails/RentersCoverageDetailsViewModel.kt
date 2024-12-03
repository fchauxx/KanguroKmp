package com.insurtech.kanguro.ui.scenes.rentersCoverageDetails

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.insurtech.kanguro.core.base.BaseViewModel
import com.insurtech.kanguro.core.repository.files.KanguroFileManager
import com.insurtech.kanguro.core.utils.SingleLiveEvent
import com.insurtech.kanguro.core.utils.update
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.repository.ICloudDocumentRepository
import com.insurtech.kanguro.data.repository.IRefactoredUserRepository
import com.insurtech.kanguro.data.repository.IRentersPolicyRepository
import com.insurtech.kanguro.data.repository.IRentersPricingRepository
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.AdditionalCoverageItemTypeModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.section.AdditionalCoverageSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.section.AdditionalPartiesSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.section.HeaderSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.section.MainInformationSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.section.PaymentSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.section.ScheduledItemsSectionModel
import com.insurtech.kanguro.domain.model.CloudDocumentPolicy
import com.insurtech.kanguro.domain.model.PolicyDocument
import com.insurtech.kanguro.domain.pet.PictureBase64
import com.insurtech.kanguro.ui.scenes.rentersEditAdditionalCoverage.PolicyInfoSharedFlow
import com.insurtech.kanguro.ui.scenes.rentersEditCoverageDetails.RentersEditCoverageInitialValues
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RentersCoverageDetailsViewModel(
    private val policyId: String,
    private val userRepository: IRefactoredUserRepository,
    private val rentersPolicyRepository: IRentersPolicyRepository,
    private val rentersPricingRepository: IRentersPricingRepository,
    private val cloudDocumentRepository: ICloudDocumentRepository,
    private val fileManager: KanguroFileManager
) : BaseViewModel() {

    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        userRepository: IRefactoredUserRepository,
        rentersPolicyRepository: IRentersPolicyRepository,
        rentersPricingRepository: IRentersPricingRepository,
        cloudDocumentRepository: ICloudDocumentRepository,
        rentersFileManager: KanguroFileManager
    ) : this(
        RentersCoverageDetailsFragmentArgs.fromSavedStateHandle(savedStateHandle).policyId,
        userRepository,
        rentersPolicyRepository,
        rentersPricingRepository,
        cloudDocumentRepository,
        rentersFileManager
    )

    data class UiState(
        var headerSectionModel: HeaderSectionModel = HeaderSectionModel(),
        var mainInformationSectionModel: MainInformationSectionModel = MainInformationSectionModel(),
        var additionalCoverageSectionModel: AdditionalCoverageSectionModel = AdditionalCoverageSectionModel(),
        var scheduledItemsSectionModel: ScheduledItemsSectionModel = ScheduledItemsSectionModel(),
        var additionalPartiesSectionModel: AdditionalPartiesSectionModel = AdditionalPartiesSectionModel(),
        var paymentSectionModel: PaymentSectionModel = PaymentSectionModel(), // TODO: when integrated do not show if PolicyStatus != PolicyStatus.ACTIVE (check another sections here for reference)
        var showCapturePicture: Boolean = false,
        var showSelectPicture: Boolean = false,
        var showAddPictureBottomSheet: Boolean = false,
        var showAdditionalCoverageInfoDialog: AdditionalCoverageItemTypeModel? = null,
        val isScreenLoader: Boolean = false,
        val showFileClaimDialog: Boolean = false,
        val showEditPolicyInfoDialog: Boolean = false
    )

    private val currentPolicyInfo = RentersCoverageDetailsCurrentPolicyInfo()
    private var userName: String = ""

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private var selectedPolicy: CloudDocumentPolicy? = null

    val openAttachmentEvent = SingleLiveEvent<Uri?>()

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            enableScreenLoading()
            fetchUser()
            getCloudDocumentPolicies()
            fetchPolicy()
            getScheduledItemsTotalValue()
            getAdditionalParties()
            getAdditionalCoverage()
            disableScreenLoading()
        }
    }

    private fun enableScreenLoading() {
        _uiState.update {
            copy(isScreenLoader = true)
        }
    }

    private fun disableScreenLoading() {
        _uiState.update {
            copy(isScreenLoader = false)
        }
    }

    private suspend fun fetchUser() {
        val userResult = userRepository.getUser()

        userName = if (userResult is Result.Success) {
            userResult.data.givenName
        } else {
            ""
        }
    }

    private suspend fun fetchPolicy() {
        val policyResult = rentersPolicyRepository.getPolicyById(policyId)

        if (policyResult is Result.Success) {
            currentPolicyInfo.residenceState = policyResult.data.address.state.orEmpty()
            currentPolicyInfo.residenceZipCode = policyResult.data.address.zipCode.orEmpty()
            currentPolicyInfo.liability = policyResult.data.planSummary.liability.id
            currentPolicyInfo.deductible = policyResult.data.planSummary.deductible.id
            currentPolicyInfo.personalProperty =
                policyResult.data.planSummary.personalProperty.value
            currentPolicyInfo.dwellingType = policyResult.data.dwellingType
            currentPolicyInfo.additionalCoveragesTypes =
                policyResult.data.additionalCoverages?.map { it.type }
            currentPolicyInfo.isInsuranceRequired = policyResult.data.isInsuranceRequired

            _uiState.update {
                copy(
                    headerSectionModel = policyResult.data.toHeaderSectionModel(userName = userName)
                        .copy(isError = false),
                    mainInformationSectionModel = policyResult.data.toMainInformationSectionModel()
                        .apply {
                            documents = selectedPolicy?.policyDocuments.orEmpty()
                        }
                        .copy(isError = false),
                    paymentSectionModel = policyResult.data.toPaymentSectionModel()
                        .copy(isError = false)
                )
            }
        } else {
            _uiState.update {
                copy(
                    headerSectionModel = _uiState.value.headerSectionModel.copy(isError = true),
                    mainInformationSectionModel = _uiState.value.mainInformationSectionModel.copy(
                        isError = true
                    ),
                    paymentSectionModel = _uiState.value.paymentSectionModel.copy(isError = true)
                )
            }
        }
    }

    fun getPolicyDocument(document: PolicyDocument) {
        fetchDocument(document, policyId, fileManager::getPolicyRentersDocument)
    }

    private fun fetchDocument(
        document: PolicyDocument,
        id: String?,
        uriGetter: suspend (String, Long, String) -> Uri?
    ) {
        if (id != null && document.id != null && document.filename?.isNotEmpty() == true) {
            viewModelScope.launch {
                val file = uriGetter(id, document.id!!, document.filename!!)
                this@RentersCoverageDetailsViewModel.openAttachmentEvent.postValue(file)
            }
        }
    }

    private suspend fun getCloudDocumentPolicies() {
        cloudDocumentRepository.getCloudDocumentPolicy(policyId)
            .catch { e -> Result.Error(Exception(e)) }
            .collect { cloudDocumentPolicyResult ->
                if (cloudDocumentPolicyResult is Result.Success) {
                    selectedPolicy = cloudDocumentPolicyResult.data
                } else {
                    val errorResult = cloudDocumentPolicyResult as Result.Error
                    val error = Pair(errorResult) {}
                    _resultError.postValue(error)
                }
            }
    }

    private suspend fun getAdditionalCoverage() {
        val policyInfoSharedFlow = getPolicyInfoSharedFlow()

        if (policyInfoSharedFlow != null) {
            when (
                val result = rentersPricingRepository.getAdditionalCoverages(
                    policyInfoSharedFlow.dwellingType,
                    policyInfoSharedFlow.deductibleId,
                    policyInfoSharedFlow.liabilityId,
                    policyInfoSharedFlow.personalProperty,
                    policyInfoSharedFlow.state,
                    policyInfoSharedFlow.zipCode,
                    policyInfoSharedFlow.isInsuranceRequired
                )
            ) {
                is Result.Success -> {
                    _uiState.update {
                        copy(
                            additionalCoverageSectionModel = result.data.toAdditionalCoverageSectionModel(
                                currentPolicyInfo.additionalCoveragesTypes ?: emptyList()
                            )
                                .copy(
                                    isError = false,
                                    policyStatus = _uiState.value.mainInformationSectionModel.policyStatus
                                )
                        )
                    }
                }

                is Result.Error -> {
                    _uiState.update {
                        copy(
                            additionalCoverageSectionModel = _uiState.value.additionalCoverageSectionModel.copy(
                                isError = true
                            )
                        )
                    }
                }
            }
        }
    }

    private suspend fun getScheduledItemsTotalValue() {
        rentersPolicyRepository.getScheduledItems(policyId).collect { result ->
            if (result is Result.Success) {
                val scheduledItems = result.data
                val totalValuation = scheduledItems.sumOf { it.valuation ?: 0.0 }.toBigDecimal()

                _uiState.update {
                    copy(
                        scheduledItemsSectionModel = ScheduledItemsSectionModel(
                            totalValue = totalValuation,
                            isError = false
                        )
                    )
                }
            } else {
                _uiState.update {
                    copy(
                        scheduledItemsSectionModel = ScheduledItemsSectionModel(
                            isError = true
                        )
                    )
                }
            }
        }
    }

    private suspend fun getAdditionalParties() {
        val result = rentersPolicyRepository.getAdditionalParties(policyId)

        if (result is Result.Success) {
            val additionalParties = result.data

            val additionalPartiesSectionModel = AdditionalPartiesSectionModel(
                additionalParties = additionalParties.map { it.toPartyItemModel() },
                isError = false,
                policyStatus = _uiState.value.mainInformationSectionModel.policyStatus
            )

            _uiState.update {
                copy(
                    additionalPartiesSectionModel = additionalPartiesSectionModel
                )
            }
        } else {
            _uiState.update {
                copy(
                    additionalPartiesSectionModel = _uiState.value.additionalPartiesSectionModel.copy(
                        isError = true
                    )
                )
            }
        }
    }

    fun updateHousePicture(housePictureBase64: PictureBase64) {
        // TODO: integrate image upload
    }

    fun onEditPicturePressed() {
        _uiState.update {
            copy(
                showAddPictureBottomSheet = true,
                showSelectPicture = false,
                showCapturePicture = false
            )
        }
    }

    fun onCapturePicturePressed() {
        _uiState.update {
            copy(
                showAddPictureBottomSheet = false,
                showCapturePicture = true,
                showSelectPicture = false
            )
        }
    }

    fun onSelectPicturePressed() {
        _uiState.update {
            copy(
                showAddPictureBottomSheet = false,
                showSelectPicture = true,
                showCapturePicture = false
            )
        }
    }

    fun onPictureDone() {
        _uiState.update {
            copy(
                showAddPictureBottomSheet = false,
                showSelectPicture = false,
                showCapturePicture = false
            )
        }
    }

    fun onAdditionalCoverageInfoPressed(additionalCoverageItemTypeModel: AdditionalCoverageItemTypeModel?) {
        _uiState.update {
            copy(
                showAdditionalCoverageInfoDialog = additionalCoverageItemTypeModel
            )
        }
    }

    fun getPolicyId() = policyId

    fun getPolicyStatus() = _uiState.value.mainInformationSectionModel.policyStatus

    fun getResidenceState() = currentPolicyInfo.residenceState

    fun getRentersEditCoverageInitialValues() = RentersEditCoverageInitialValues(
        currentLiabilityId = currentPolicyInfo.liability,
        currentDeductibleId = currentPolicyInfo.deductible,
        currentPersonalPropertyValue = currentPolicyInfo.personalProperty
    )

    fun getPolicyInfoSharedFlow(): PolicyInfoSharedFlow? {
        return PolicyInfoSharedFlow(
            policyId = policyId,
            dwellingType = currentPolicyInfo.dwellingType ?: return null,
            deductibleId = currentPolicyInfo.deductible ?: return null,
            liabilityId = currentPolicyInfo.liability ?: return null,
            personalProperty = currentPolicyInfo.personalProperty ?: return null,
            state = currentPolicyInfo.residenceState,
            zipCode = currentPolicyInfo.residenceZipCode,
            previouslySelectedAdditionalCoverages = currentPolicyInfo.additionalCoveragesTypes
                ?: emptyList(),
            isInsuranceRequired = currentPolicyInfo.isInsuranceRequired
        )
    }

    fun setShowFileClaimDialog(show: Boolean) {
        _uiState.update {
            copy(showFileClaimDialog = show)
        }
    }

    fun onEditPolicyPressed(showDialog: Boolean) {
        _uiState.update {
            copy(showEditPolicyInfoDialog = showDialog)
        }
    }
}
