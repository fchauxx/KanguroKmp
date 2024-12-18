package com.insurtech.kanguro.shared.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.insurtech.kanguro.shared.data.AirvetUserDetails
import com.insurtech.kanguro.shared.data.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class LiveVetViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _state: MutableStateFlow<LiveVetUiState> = MutableStateFlow(LiveVetUiState())
    val state: StateFlow<LiveVetUiState> get() = _state.asStateFlow()

    data class LiveVetUiState(
        val isLoading: Boolean = false,
        val isError: Boolean = false,
        val airvetUserDetails: AirvetUserDetails = AirvetUserDetails("", "", "", 0)
    )

    init {
      //  loadData()
    }

    private fun loadData() {
        _state.update { it.copy(isLoading = true, isError = false) }
        viewModelScope.launch {
            userRepository.getUser()
        }
    }
}
