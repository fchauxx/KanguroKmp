package com.insurtech.kanguro.ui.scenes.rentersScheduledItemsAddItem

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.haroldadmin.cnradapter.NetworkResponse
import com.insurtech.kanguro.core.base.BaseViewModel
import com.insurtech.kanguro.core.base.errorHandling.ErrorWithRetry
import com.insurtech.kanguro.core.utils.update
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.repository.IRentersPolicyRepository
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.FooterSectionModel
import com.insurtech.kanguro.domain.model.ScheduledItemInputModel
import com.insurtech.kanguro.domain.model.ScheduledItemPricingInputModel
import com.insurtech.kanguro.domain.model.ScheduledItemTypeModel
import com.insurtech.kanguro.networking.dto.ErrorDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduledItemsAddItemViewModel(
    private val policyRepository: IRentersPolicyRepository,
    private val args: ScheduledItemsAddItemFragmentArgs
) : BaseViewModel() {

    @Inject
    constructor(
        policyRepository: IRentersPolicyRepository,
        savedStateHandle: SavedStateHandle
    ) : this(
        policyRepository,
        ScheduledItemsAddItemFragmentArgs.fromSavedStateHandle(savedStateHandle)
    )

    data class UiState(
        val newItemName: String = "",
        val newItemPrice: String = "",
        val selectedCategory: ScheduledItemTypeModel,
        val isDone: Boolean = false,
        val footerSection: FooterSectionModel? = null
    )

    private val _uiState =
        MutableStateFlow<UiState>(UiState(selectedCategory = args.scheduledItemType))
    val uiState = _uiState.asStateFlow()

    fun onNewItemNameChange(value: String) {
        _uiState.value = _uiState.value.copy(newItemName = value)
    }

    fun onNewItemPriceChange(value: String) {
        if (value.isBlank()) {
            _uiState.update {
                copy(footerSection = null, newItemPrice = "")
            }
        } else {
            _uiState.value = _uiState.value.copy(newItemPrice = value)
            postScheduledItemPricing()
        }
    }

    private fun postScheduledItemPricing() {
        val valuation = _uiState.value.newItemPrice

        val validValuation = valuation.filter {
            it.isDigit() || it == '.'
        }.toDouble()

        val type = _uiState.value.selectedCategory.id

        val body = ScheduledItemPricingInputModel(type, validValuation)

        _uiState.update {
            copy(
                footerSection = FooterSectionModel(
                    isLoading = true
                )
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            policyRepository.postScheduledItemPricing(args.policyId, body)
                .collect { result ->
                    when (result) {
                        is Result.Success -> {
                            if (_uiState.value.newItemPrice.isNotBlank()) {
                                _uiState.update {
                                    copy(
                                        footerSection = FooterSectionModel(
                                            totalPrice = result.data.billingCyclePolicyPriceDifferenceValue.toBigDecimal(),
                                            buttonPrice = result.data.billingCycleEndorsementPolicyValue.toBigDecimal(),
                                            invoiceInterval = result.data.billingCycle.toUi()
                                        )
                                    )
                                }
                            }
                        }

                        is Result.Error -> {
                            postScheduledItemPricingError(result)
                            _uiState.update {
                                copy(
                                    footerSection = FooterSectionModel()
                                )
                            }
                        }
                    }
                }
        }
    }

    fun postScheduledItem() {
        val body = ScheduledItemInputModel(
            _uiState.value.newItemName,
            _uiState.value.selectedCategory.id,
            _uiState.value.newItemPrice.filter {
                it.isDigit() || it == '.'
            }.toDouble()
        )

        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                copy(footerSection = this.footerSection?.copy(isLoading = true))
            }

            policyRepository.postScheduleItem(args.policyId, body)
                .collect { result ->
                    when (result) {
                        is Result.Success -> {
                            _uiState.update {
                                copy(
                                    footerSection = this.footerSection?.copy(isLoading = false),
                                    isDone = true
                                )
                            }
                        }

                        is Result.Error -> {
                            postScheduledItemError(result)
                            _uiState.update {
                                copy(footerSection = this.footerSection?.copy(isLoading = false))
                            }
                        }
                    }
                }
        }
    }

    private fun postScheduledItemPricingError(result: Result.Error) {
        _networkError.postValue(
            ErrorWithRetry(
                NetworkResponse.UnknownError<Unit, ErrorDto>(
                    result.exception,
                    null
                )
            ) {
                postScheduledItemPricing()
            }
        )
    }

    private fun postScheduledItemError(result: Result.Error) {
        _networkError.postValue(
            ErrorWithRetry(
                NetworkResponse.UnknownError<Unit, ErrorDto>(
                    result.exception,
                    null
                )
            ) {
                postScheduledItem()
            }
        )
    }
}
