package com.insurtech.kanguro.ui.scenes.rentersScheduledItems

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.insurtech.kanguro.core.base.BaseViewModel
import com.insurtech.kanguro.core.utils.UiState
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.repository.IRentersPolicyRepository
import com.insurtech.kanguro.domain.model.ScheduledItemModel
import com.insurtech.kanguro.domain.model.scheduledItemViewModelToScheduledItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduledItemsViewModel(
    private val rentersPolicyRepository: IRentersPolicyRepository,
    private val args: ScheduledItemsFragmentArgs
) : BaseViewModel() {

    @Inject
    constructor(
        policyRepository: IRentersPolicyRepository,
        savedStateHandle: SavedStateHandle
    ) : this(
        policyRepository,
        ScheduledItemsFragmentArgs.fromSavedStateHandle(savedStateHandle)
    )

    private val _uiState =
        MutableStateFlow<UiState<List<ScheduledItemModel>>>(UiState.Loading.ScreenLoader)
    val uiState = _uiState.asStateFlow()

    private var itemIdForDeletion: String = ""

    init {
        getScheduledItems()
    }

    private fun getScheduledItems() {
        _uiState.value = UiState.Loading.ScreenLoader

        viewModelScope.launch {
            rentersPolicyRepository.getScheduledItems(args.policyId)
                .collect { result ->
                    when (result) {
                        is Result.Success -> {
                            val scheduledItems = result.data
                            val scheduledItemsModel =
                                scheduledItems.map(::scheduledItemViewModelToScheduledItemModel)
                            _uiState.value = UiState.Success(scheduledItemsModel)
                        }

                        is Result.Error -> {
                            _uiState.value = UiState.Error {
                                getScheduledItems()
                            }
                        }
                    }
                }
        }
    }

    fun handleOnDeleteScheduledItem(scheduledItemId: String) {
        itemIdForDeletion = scheduledItemId
    }

    fun handleOnDeleteScheduledItemConfirmed() {
        viewModelScope.launch {
            when (rentersPolicyRepository.deleteScheduledItem(args.policyId, itemIdForDeletion)) {
                is Result.Success -> {
                    getScheduledItems()
                }

                is Result.Error -> {
                    _uiState.value = UiState.Error {
                        handleOnDeleteScheduledItemConfirmed()
                    }
                }
            }
        }
    }

    fun getPolicyId() = args.policyId

    fun isReadyOnly() = args.isReadyOnly
}
