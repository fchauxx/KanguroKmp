package com.insurtech.kanguro.ui.scenes.directPayToVet.directPayToVetInitFlow

import com.insurtech.kanguro.core.base.BaseViewModel
import com.insurtech.kanguro.domain.model.ClaimDirectPayment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DirectPayToVetInitFlowViewModel @Inject constructor() : BaseViewModel() {

    data class UiState(
        val claimValue: String = "",
        var isNextEnable: Boolean = false
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun onClaimValueChange(value: String) {
        _uiState.value = _uiState.value.copy(claimValue = value)
        updateNextButtonEnable()
    }

    private fun updateNextButtonEnable() {
        val minimumClaimValue = 900.00

        val claimValue = try {
            getClaimValueAsDouble()
        } catch (e: Exception) {
            0.0
        }

        val isValidValue = claimValue >= minimumClaimValue

        _uiState.value = _uiState.value.copy(
            isNextEnable = isValidValue
        )
    }

    private fun getClaimValueAsDouble(): Double {
        val divider = ','
        return _uiState.value.claimValue.filter { character ->
            character != divider
        }.toDouble()
    }

    fun getSharedFlow(): ClaimDirectPayment {
        return ClaimDirectPayment(amount = getClaimValueAsDouble())
    }
}
