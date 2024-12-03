package com.insurtech.kanguro.ui.scenes.rentersScheduledItemsCategory

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.haroldadmin.cnradapter.NetworkResponse
import com.insurtech.kanguro.core.base.BaseViewModel
import com.insurtech.kanguro.core.base.errorHandling.ErrorWithRetry
import com.insurtech.kanguro.core.utils.UiState
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.repository.IRentersScheduledItemsRepository
import com.insurtech.kanguro.domain.model.ScheduledItemTypeModel
import com.insurtech.kanguro.networking.dto.ErrorDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduledItemsCategoryViewModel(
    private val scheduledItemsRepository: IRentersScheduledItemsRepository,
    private val args: ScheduledItemsCategoryFragmentArgs
) : BaseViewModel() {

    @Inject
    constructor(
        scheduledItemsRepository: IRentersScheduledItemsRepository,
        savedStateHandle: SavedStateHandle
    ) : this(
        scheduledItemsRepository,
        ScheduledItemsCategoryFragmentArgs.fromSavedStateHandle(savedStateHandle)
    )

    private val _uiState =
        MutableStateFlow<UiState<List<ScheduledItemTypeModel>>>(UiState.Loading.ScreenLoader)
    val uiState = _uiState.asStateFlow()

    init {
        fetchScheduledItems()
    }

    private fun fetchScheduledItems() {
        viewModelScope.launch(Dispatchers.IO) {
            scheduledItemsRepository.getScheduledItemType()
                .collect { result ->
                    when (result) {
                        is Result.Success -> {
                            _uiState.value = UiState.Success(result.data)
                        }

                        is Result.Error -> {
                            onScheduledItemsError(result)
                            _uiState.value = UiState.Success(emptyList())
                            // TODO: handle error state with new UiState when design for it is done
                        }
                    }
                }
        }
    }

    private fun onScheduledItemsError(result: Result.Error) {
        _networkError.postValue(
            ErrorWithRetry(
                NetworkResponse.UnknownError<Unit, ErrorDto>(
                    result.exception,
                    null
                )
            ) {
                fetchScheduledItems()
            }
        )
    }

    fun getPolicyId() = args.policyId
}
