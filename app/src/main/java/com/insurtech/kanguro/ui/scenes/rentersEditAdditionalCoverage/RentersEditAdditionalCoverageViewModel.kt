package com.insurtech.kanguro.ui.scenes.rentersEditAdditionalCoverage

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.insurtech.kanguro.core.base.BaseViewModel
import com.insurtech.kanguro.core.utils.update
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.repository.IRentersPricingRepository
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.FooterSectionModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.AdditionalCoverageItemTypeModel
import com.insurtech.kanguro.designsystem.ui.composables.rentersEditAdditionalCoverage.domain.AdditionalCoverageCardModel
import com.insurtech.kanguro.domain.model.AdditionalCoverage
import com.insurtech.kanguro.domain.model.AdditionalCoverageType
import com.insurtech.kanguro.domain.model.AdditionalCoveragesEndorsement
import com.insurtech.kanguro.ui.scenes.rentersEditCoverageDetails.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RentersEditAdditionalCoverageViewModel(
    private val policyInfoSharedFlow: PolicyInfoSharedFlow,
    private val rentersPricingRepository: IRentersPricingRepository
) : BaseViewModel() {

    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        rentersPricingRepository: IRentersPricingRepository
    ) : this(
        policyInfoSharedFlow = RentersEditAdditionalCoverageBottomSheetArgs.fromSavedStateHandle(
            savedStateHandle
        ).policyInfoSharedFlow,
        rentersPricingRepository = rentersPricingRepository
    )

    data class UiState(
        val footerSectionModel: FooterSectionModel? = null,
        val additionalCoverageModels: List<AdditionalCoverageCardModel> = emptyList(),
        val isLoading: Boolean = false,
        val isError: Boolean = false
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _isSubmitSuccess = MutableStateFlow<Boolean?>(null)
    val isSubmitSuccess = _isSubmitSuccess.asStateFlow()

    private val _isCalculatePricingError = MutableStateFlow<Boolean?>(null)
    val isCalculatePricingError = _isCalculatePricingError.asStateFlow()

    private var calculatePricingJob: Job? = null

    init {
        getAdditionalCoverages()
    }

    fun getAdditionalCoverages() {
        _uiState.update {
            copy(
                isLoading = true
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
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
                    handleGetAdditionalCoveragesSuccess(result.data)
                }

                is Result.Error -> {
                    _uiState.update {
                        copy(
                            isLoading = false,
                            isError = true
                        )
                    }
                }
            }
        }
    }

    private fun handleGetAdditionalCoveragesSuccess(result: List<AdditionalCoverage>) {
        _uiState.update {
            copy(
                additionalCoverageModels = result.map {
                    val isPreviouslySelected =
                        policyInfoSharedFlow.previouslySelectedAdditionalCoverages.contains(it.type)
                    it.toUiModel(isPreviouslySelected)
                },
                isLoading = false
            )
        }
    }

    fun handleAdditionalCoverageSwitch(additionalCoverageCardModel: AdditionalCoverageCardModel) {
        val updatedAdditionalCoverages =
            _uiState.value.additionalCoverageModels.map { existingCoverage ->
                if (existingCoverage.type == additionalCoverageCardModel.type) {
                    existingCoverage.copy(isSelected = !existingCoverage.isSelected)
                } else {
                    existingCoverage
                }
            }

        _uiState.update {
            copy(
                additionalCoverageModels = updatedAdditionalCoverages
            )
        }

        val selectedAdditionalCoverages = getSelectedAdditionalCoverages()
        if (selectedAdditionalCoverages == policyInfoSharedFlow.previouslySelectedAdditionalCoverages) {
            _uiState.update {
                copy(footerSectionModel = null)
            }
        } else {
            postAdditionalCoveragePricing(selectedAdditionalCoverages)
        }
    }

    private fun postAdditionalCoveragePricing(selectedAdditionalCoverages: List<AdditionalCoverageType>) {
        calculatePricingJob?.cancel()

        _isCalculatePricingError.update { false }

        _uiState.update {
            copy(
                footerSectionModel = _uiState.value.footerSectionModel?.copy(
                    isLoading = true
                )
            )
        }

        calculatePricingJob = viewModelScope.launch(Dispatchers.IO) {
            when (
                val result = rentersPricingRepository.postAdditionalCoveragePricing(
                    policyInfoSharedFlow.policyId,
                    AdditionalCoveragesEndorsement(selectedAdditionalCoverages)
                )
            ) {
                is Result.Success -> {
                    _uiState.update {
                        copy(footerSectionModel = result.data.toUi().copy(isLoading = false))
                    }
                }

                is Result.Error -> {
                    _isCalculatePricingError.update { true }
                    _uiState.update {
                        copy(
                            footerSectionModel = FooterSectionModel(isLoading = false)
                        )
                    }
                }
            }
        }
    }

    private fun getSelectedAdditionalCoverages() = _uiState.value.additionalCoverageModels.filter {
        it.isSelected
    }.map { additionalCoverageCardModel ->
        when (additionalCoverageCardModel.type) {
            AdditionalCoverageItemTypeModel.REPLACEMENT_COST -> AdditionalCoverageType.ReplacementCost
            AdditionalCoverageItemTypeModel.WATER_SEWER_BACKUP -> AdditionalCoverageType.WaterSewerBackup
            AdditionalCoverageItemTypeModel.FRAUD_PROTECTION -> AdditionalCoverageType.FraudProtection
        }
    }

    fun putAdditionalCoverage() {
        _uiState.update {
            copy(footerSectionModel = FooterSectionModel(isLoading = true))
        }

        viewModelScope.launch(Dispatchers.IO) {
            when (
                rentersPricingRepository.putAdditionalCoverage(
                    policyInfoSharedFlow.policyId,
                    AdditionalCoveragesEndorsement(getSelectedAdditionalCoverages())
                )
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
                    footerSectionModel = FooterSectionModel(isLoading = false)
                )
            }
        }
    }
}
