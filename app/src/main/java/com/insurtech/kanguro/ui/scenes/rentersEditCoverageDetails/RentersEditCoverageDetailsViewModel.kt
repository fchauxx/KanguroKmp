package com.insurtech.kanguro.ui.scenes.rentersEditCoverageDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.insurtech.kanguro.core.base.BaseViewModel
import com.insurtech.kanguro.core.utils.update
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.repository.IRentersPolicyEndorsementRepository
import com.insurtech.kanguro.data.repository.IRentersPricingRepository
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.FooterSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.model.DeductibleItemsSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.model.DeductibleSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.model.LiabilitySectionModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.model.LossOfUseSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditCoverageDetails.model.YourBelongingsUiModel
import com.insurtech.kanguro.domain.model.PolicyEndorsementInput
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RentersEditCoverageDetailsViewModel(
    private val args: RentersEditCoverageDetailsFragmentArgs,
    private val rentersPricingRepository: IRentersPricingRepository,
    private val rentersPolicyEndorsementRepository: IRentersPolicyEndorsementRepository
) : BaseViewModel() {

    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        rentersPricingRepository: IRentersPricingRepository,
        rentersPolicyEndorsementRepository: IRentersPolicyEndorsementRepository
    ) : this(
        RentersEditCoverageDetailsFragmentArgs.fromSavedStateHandle(savedStateHandle),
        rentersPricingRepository,
        rentersPolicyEndorsementRepository
    )

    data class UiState(
        val yourBelongingsUiModel: YourBelongingsUiModel? = null,
        val isYourBelongingsLoading: Boolean = false,
        val liabilityModel: LiabilitySectionModel? = null,
        var isLiabilityLoading: Boolean = false,
        val showLiabilityInformation: Boolean = false,
        val lossOfUseSectionModel: LossOfUseSectionModel? = null,
        val isLossOfUseSectionLoading: Boolean = false,
        val deductibleSectionModel: DeductibleSectionModel? = null,
        val isDeductibleSectionLoading: Boolean = false,
        val deductibleItemsSectionModel: DeductibleItemsSectionModel? = null,
        val isDeductibleSectionItemsLoading: Boolean = false,
        val showDeductibleInformation: Boolean = false,
        val footerSectionModel: FooterSectionModel? = null,
        val isFooterSectionLoading: Boolean = false,
        val isErrorState: Boolean = false
    )

    private val _isSubmitSuccess = MutableStateFlow<Boolean?>(null)
    val isSubmitSuccess = _isSubmitSuccess.asStateFlow()

    private val _uiState = MutableStateFlow<UiState>(UiState())
    val uiState = _uiState.asStateFlow()

    private var calculatePricingJob: Job? = null

    init {
        initViewModel()
    }

    private fun setupCurrentLiability() {
        val list = _uiState.value.liabilityModel?.liabilityItems?.map {
            it.copy(isSelected = it.id.toInt() == args.initialValues.currentLiabilityId)
        } ?: return

        _uiState.update {
            copy(
                liabilityModel = _uiState.value.liabilityModel?.copy(liabilityItems = list)
            )
        }
    }

    private fun setupCurrentPersonalProperty() {
        val personalPropertyValue =
            args.initialValues.currentPersonalPropertyValue?.toFloat() ?: return

        _uiState.update {
            copy(
                yourBelongingsUiModel = _uiState.value.yourBelongingsUiModel?.copy(selectedValue = personalPropertyValue)
            )
        }
    }

    private fun setupCurrentDeductible() {
        val list = _uiState.value.deductibleSectionModel?.items?.map {
            it.copy(isSelected = it.id.toInt() == args.initialValues.currentDeductibleId)
        } ?: return

        _uiState.update {
            copy(
                deductibleSectionModel = _uiState.value.deductibleSectionModel?.copy(items = list)
            )
        }
    }

    private fun initViewModel() {
        initLiability()
        initPersonalProperties()
        initDeductibles()
        initDeductiblesItems()
    }

    private fun initLiability() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                copy(isLiabilityLoading = true)
            }

            when (val result = rentersPricingRepository.getLiabilities(args.state)) {
                is Result.Success -> {
                    _uiState.update {
                        copy(
                            liabilityModel = result.data.toUi()
                        )
                    }
                }

                is Result.Error -> {
                    _uiState.update {
                        copy(isErrorState = true)
                    }
                }
            }

            _uiState.update {
                copy(isLiabilityLoading = false)
            }
            setupCurrentLiability()
        }
    }

    private fun initDeductibles() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                copy(isDeductibleSectionLoading = true)
            }

            when (val result = rentersPricingRepository.getDeductibles(args.state)) {
                is Result.Success -> {
                    _uiState.update {
                        copy(
                            deductibleSectionModel = result.data.toUi()
                        )
                    }
                }

                is Result.Error -> {
                    _uiState.update {
                        copy(isErrorState = true)
                    }
                }
            }

            _uiState.update {
                copy(isDeductibleSectionLoading = false)
            }
            setupCurrentDeductible()
        }
    }

    private fun initDeductiblesItems() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                copy(isDeductibleSectionItemsLoading = true)
            }

            _uiState.update {
                copy(isLossOfUseSectionLoading = true)
            }

            when (val result = rentersPricingRepository.getDeductibleItems(args.state)) {
                is Result.Success -> {
                    _uiState.update {
                        copy(
                            deductibleItemsSectionModel = result.data.toDeductibleItemsSectionModelUi()
                        )
                    }
                    _uiState.update {
                        copy(
                            lossOfUseSectionModel = result.data.toLossOfUseSectionModelUi()
                        )
                    }
                }

                is Result.Error -> {
                    _uiState.update {
                        copy(isErrorState = true)
                    }
                }
            }

            _uiState.update {
                copy(isDeductibleSectionItemsLoading = false)
            }

            _uiState.update {
                copy(isLossOfUseSectionLoading = false)
            }
        }
    }

    private fun initPersonalProperties() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                copy(isYourBelongingsLoading = true)
            }

            when (val result = rentersPricingRepository.getPersonalProperty()) {
                is Result.Success -> {
                    _uiState.update {
                        copy(
                            yourBelongingsUiModel = result.data.toUi()
                        )
                    }
                }

                is Result.Error -> {
                    _uiState.update {
                        copy(isErrorState = true)
                    }
                }
            }

            _uiState.update {
                copy(isYourBelongingsLoading = false)
            }

            setupCurrentPersonalProperty()
        }
    }

    private fun calculatePricing() {
        calculatePricingJob?.cancel()

        calculatePricingJob = viewModelScope.launch(Dispatchers.IO) {
            val body = PolicyEndorsementInput(
                deductibleId = getDeductibleId() ?: return@launch,
                liabilityId = getLiabilityId() ?: return@launch,
                personalProperty = getYourBelongingsSelectedValue() ?: return@launch
            )

            _uiState.update {
                copy(footerSectionModel = FooterSectionModel(isLoading = true))
            }

            when (
                val result = rentersPolicyEndorsementRepository.postPolicyEndorsementPricing(
                    args.policyId,
                    body
                )
            ) {
                is Result.Success -> {
                    _uiState.update {
                        copy(
                            footerSectionModel = result.data.toUi()
                        )
                    }
                }

                is Result.Error -> {
                    _uiState.update {
                        copy(
                            footerSectionModel = null
                        )
                    }
                }
            }

            _uiState.update {
                copy(
                    footerSectionModel = _uiState.value.footerSectionModel?.copy(
                        isLoading = false
                    )
                )
            }
        }
    }

    private fun postPolicyEndorsement() {
        viewModelScope.launch(Dispatchers.IO) {
            val body = PolicyEndorsementInput(
                deductibleId = getDeductibleId() ?: return@launch,
                liabilityId = getLiabilityId() ?: return@launch,
                personalProperty = getYourBelongingsSelectedValue() ?: return@launch
            )

            _uiState.update {
                copy(
                    footerSectionModel = _uiState.value.footerSectionModel?.copy(
                        isLoading = true
                    )
                )
            }

            disableInputs()

            when (
                val result =
                    rentersPolicyEndorsementRepository.postPolicyEndorsement(args.policyId, body)
            ) {
                is Result.Success -> {
                    _isSubmitSuccess.update { true }
                }

                is Result.Error -> {
                    _isSubmitSuccess.update { false }
                }
            }

            _uiState.update {
                copy(
                    footerSectionModel = _uiState.value.footerSectionModel?.copy(
                        isLoading = false
                    )
                )
            }

            enableInputs()
        }
    }

    private fun enableInputs() {
        _uiState.update {
            copy(
                liabilityModel = _uiState.value.liabilityModel?.copy(isInputEnabled = true),
                yourBelongingsUiModel = _uiState.value.yourBelongingsUiModel?.copy(isInputEnabled = true),
                deductibleSectionModel = _uiState.value.deductibleSectionModel?.copy(isInputEnabled = true)
            )
        }
    }

    private fun disableInputs() {
        _uiState.update {
            copy(
                liabilityModel = _uiState.value.liabilityModel?.copy(isInputEnabled = false),
                yourBelongingsUiModel = _uiState.value.yourBelongingsUiModel?.copy(isInputEnabled = false),
                deductibleSectionModel = _uiState.value.deductibleSectionModel?.copy(isInputEnabled = false)
            )
        }
    }

    private fun getLiabilityId() =
        _uiState.value.liabilityModel?.liabilityItems?.firstOrNull { it.isSelected }?.id?.toInt()

    private fun getDeductibleId() =
        _uiState.value.deductibleSectionModel?.items?.firstOrNull { it.isSelected }?.id?.toInt()

    private fun getYourBelongingsSelectedValue() =
        _uiState.value.yourBelongingsUiModel?.selectedValue?.toDouble()

    fun updateYourBelongingsSelectedValue(selectedValue: Float) {
        _uiState.update {
            copy(
                yourBelongingsUiModel = _uiState.value.yourBelongingsUiModel?.copy(selectedValue = selectedValue)
            )
        }

        calculatePricing()
    }

    fun handleLiabilityInformationPressed(show: Boolean) {
        _uiState.update {
            copy(
                showLiabilityInformation = show
            )
        }
    }

    fun handleLiabilitySelected(liabilityId: String) {
        val actualLiabilityModel = _uiState.value.liabilityModel?.liabilityItems?.map {
            it.copy(isSelected = it.id == liabilityId)
        }

        if (actualLiabilityModel != null) {
            _uiState.update {
                copy(
                    liabilityModel = _uiState.value.liabilityModel?.copy(
                        liabilityItems = actualLiabilityModel
                    )
                )
            }
        }

        calculatePricing()
    }

    fun handleDeductibleSelected(deductibleId: String) {
        val actualDeductibleIdModel = _uiState.value.deductibleSectionModel?.items?.map {
            it.copy(isSelected = it.id == deductibleId)
        }

        if (actualDeductibleIdModel != null) {
            _uiState.update {
                copy(
                    deductibleSectionModel = _uiState.value.deductibleSectionModel?.copy(
                        items = actualDeductibleIdModel
                    )
                )
            }
        }

        calculatePricing()
    }

    fun handleDeductibleInformationPressed(show: Boolean) {
        _uiState.update {
            copy(
                showDeductibleInformation = show
            )
        }
    }

    fun onSubmitPressed() {
        postPolicyEndorsement()
    }

    fun refresh() {
        _uiState.update {
            copy(isErrorState = false)
        }
        initViewModel()
    }

    fun clearIsSubmitSuccess() {
        _isSubmitSuccess.update { null }
    }
}
