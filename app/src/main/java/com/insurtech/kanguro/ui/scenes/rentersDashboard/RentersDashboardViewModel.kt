package com.insurtech.kanguro.ui.scenes.rentersDashboard

import androidx.lifecycle.viewModelScope
import com.insurtech.kanguro.core.base.BaseViewModel
import com.insurtech.kanguro.core.session.ISessionManager
import com.insurtech.kanguro.core.utils.UiState
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.repository.IRentersPolicyRepository
import com.insurtech.kanguro.designsystem.ui.composables.commumComponents.domain.RentersCoverageSummaryCardModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RentersDashboardViewModel @Inject constructor(
    private val repository: IRentersPolicyRepository,
    private val sessionManager: ISessionManager
) : BaseViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<RentersCoverageSummaryCardModel>>> =
        MutableStateFlow(UiState.Loading.ScreenLoader)

    val uiState = _uiState.asStateFlow()

    private val _showFileClaim = MutableStateFlow(false)
    val showFileClaim = _showFileClaim.asStateFlow()

    val userAlreadyChoseCause: Boolean
        get() = sessionManager.sessionInfo?.donation != null

    init {
        fetchPolicies()
    }

    fun fetchPolicies() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                UiState.Loading.ScreenLoader
            }
            getPolicies()
        }
    }

    private suspend fun getPolicies() {
        when (val result = repository.getPolicies()) {
            is Result.Success -> onGetPoliciesSuccess(result.data.toUi())

            is Result.Error -> onGetPoliciesError()
        }
    }

    private fun onGetPoliciesSuccess(data: List<RentersCoverageSummaryCardModel>) {
        _uiState.update {
            UiState.Success(data)
        }
    }

    private fun onGetPoliciesError() {
        _uiState.update {
            UiState.Error {
                fetchPolicies()
            }
        }
    }

    fun setShowFileClaimDialog(show: Boolean) {
        _showFileClaim.update {
            show
        }
    }
}
