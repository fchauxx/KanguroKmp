package com.insurtech.kanguro.ui.scenes.liveVet

import androidx.lifecycle.viewModelScope
import com.insurtech.kanguro.core.base.BaseViewModel
import com.insurtech.kanguro.core.utils.update
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.repository.IRefactoredUserRepository
import com.insurtech.kanguro.domain.model.AirvetUserDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LiveVetViewModel @Inject constructor(
    private val refactoredUserRepository: IRefactoredUserRepository
) : BaseViewModel() {

    private val _state: MutableStateFlow<LiveVetUiState> = MutableStateFlow(LiveVetUiState())
    val state: StateFlow<LiveVetUiState> get() = _state.asStateFlow()

    data class LiveVetUiState(
        val isLoading: Boolean = false,
        val isError: Boolean = false,
        val airvetUserDetails: AirvetUserDetails = AirvetUserDetails("", "", "", 0)
    )

    init {
        loadData()
    }

    fun loadData() {
        _state.update { copy(isLoading = true, isError = false) }
        viewModelScope.launch {
            val userDataRequest = viewModelScope.async {
                fetchUserData()
            }

            awaitAll(userDataRequest)
            _state.update { copy(isLoading = false) }
        }
    }

    suspend fun fetchUserData() {
        val result = refactoredUserRepository.getUser()

        if (result is Result.Success) {
            _state.update {
                copy(
                    airvetUserDetails = AirvetUserDetails(
                        firstName = result.data.givenName,
                        lastName = result.data.surname,
                        email = result.data.email ?: "",
                        insuranceId = result.data.insuranceId ?: 0
                    )
                )
            }
        } else if (result is Result.Error) {
            _state.update { copy(isLoading = false, isError = true) }
        }
    }
}
