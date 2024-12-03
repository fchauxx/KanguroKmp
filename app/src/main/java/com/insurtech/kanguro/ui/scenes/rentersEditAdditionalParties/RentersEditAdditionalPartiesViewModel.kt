package com.insurtech.kanguro.ui.scenes.rentersEditAdditionalParties

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.insurtech.kanguro.core.base.BaseViewModel
import com.insurtech.kanguro.core.utils.UiState
import com.insurtech.kanguro.core.utils.update
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.repository.IRentersPolicyRepository
import com.insurtech.kanguro.designsystem.ui.composables.rentersCoverageDetails.model.PartyItemModel
import com.insurtech.kanguro.ui.scenes.rentersCoverageDetails.toPartyItemModel
import com.insurtech.kanguro.ui.scenes.rentersEditAdditionalParties.model.RentersEditAdditionalPartiesModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RentersEditAdditionalPartiesViewModel(
    private val policyId: String,
    private val rentersPolicyRepository: IRentersPolicyRepository
) : BaseViewModel() {

    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        rentersPolicyRepository: IRentersPolicyRepository
    ) : this(
        RentersEditAdditionalPartiesFragmentArgs.fromSavedStateHandle(savedStateHandle).policyId,
        rentersPolicyRepository
    )

    private var _uiState: MutableStateFlow<UiState<RentersEditAdditionalPartiesModel>> =
        MutableStateFlow(UiState.Loading.ScreenLoader)

    val uiState = _uiState.asStateFlow()

    var partyItemDeletionId: String = ""

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            getAdditionalParties()
        }
    }

    private suspend fun getAdditionalParties() {
        when (val result = rentersPolicyRepository.getAdditionalParties(policyId)) {
            is Result.Success -> onGetAdditionalPartiesSuccess(result.data.map { it.toPartyItemModel() })
            is Result.Error -> onGetAdditionalPartiesError()
        }
    }

    private fun onGetAdditionalPartiesSuccess(data: List<PartyItemModel>) {
        val currentState = _uiState.value
        if (currentState is UiState.Success) {
            val updatedModel = currentState.data.copy(
                partyItemList = data
            )

            _uiState.value = UiState.Success(updatedModel)
        }
    }

    private fun onGetAdditionalPartiesError() {
        _uiState.update {
            UiState.Error {
                fetchData()
            }
        }
    }

    fun openDialog() {
        val currentState = _uiState.value
        if (currentState is UiState.Success) {
            val updatedModel = currentState.data.copy(
                isShowingDeleteDialog = true
            )

            _uiState.value = UiState.Success(updatedModel)
        }
    }

    fun handleOnDeleteAdditionalPartyPressed(partyId: String) {
        this.partyItemDeletionId = partyId
        openDialog()
    }

    fun handleOnDeleteAdditionalPartyConfirmed() {
        viewModelScope.launch {
            when (rentersPolicyRepository.deleteAdditionalParty(policyId, partyItemDeletionId)) {
                is Result.Success -> {
                    getAdditionalParties()
                }

                is Result.Error -> {
                    _uiState.value = UiState.Error {
                        handleOnDeleteAdditionalPartyConfirmed()
                    }
                }
            }
        }
    }
}
