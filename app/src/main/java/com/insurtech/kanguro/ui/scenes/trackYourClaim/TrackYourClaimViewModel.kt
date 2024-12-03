package com.insurtech.kanguro.ui.scenes.trackYourClaim

import androidx.lifecycle.viewModelScope
import com.insurtech.kanguro.core.base.BaseViewModel
import com.insurtech.kanguro.core.utils.update
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.repository.IRefactoredClaimRepository
import com.insurtech.kanguro.designsystem.ui.composables.petTrackYourClaims.model.ClaimTrackerCardModel
import com.insurtech.kanguro.domain.model.Claim
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackYourClaimViewModel @Inject constructor(
    private val claimsRepository: IRefactoredClaimRepository
) : BaseViewModel() {

    data class TrackYourClaimUiState(
        val isLoading: Boolean = false,
        val isError: Boolean = false,
        val claims: List<ClaimTrackerCardModel> = emptyList()
    )

    private val _trackYourClaimState: MutableStateFlow<TrackYourClaimUiState> =
        MutableStateFlow(TrackYourClaimUiState())
    val uiState = _trackYourClaimState.asStateFlow()

    var claims: List<Claim> = emptyList()
        private set

    init {
        fetchClaims()
    }

    fun fetchClaims() {
        _trackYourClaimState.update {
            copy(
                isLoading = true,
                isError = false
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            when (val result = claimsRepository.getClaims()) {
                is Result.Error -> {
                    _trackYourClaimState.update {
                        copy(
                            isLoading = false,
                            isError = true
                        )
                    }
                }

                is Result.Success -> {
                    handleSuccessData(result.data)
                }
            }
        }
    }

    private fun handleSuccessData(claimsData: List<Claim>) {
        this.claims = claimsData

        val claimsViewModel = claimsData.mapNotNull { it.toTrackerCardModel() }

        _trackYourClaimState.update {
            copy(
                isLoading = false,
                isError = false,
                claims = claimsViewModel
            )
        }
    }

    fun getClaimById(claimId: String): Claim? {
        return this.claims.find { it.id == claimId }
    }
}
