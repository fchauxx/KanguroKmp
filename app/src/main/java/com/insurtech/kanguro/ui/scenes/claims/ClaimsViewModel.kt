package com.insurtech.kanguro.ui.scenes.claims

import androidx.lifecycle.*
import com.insurtech.kanguro.R
import com.insurtech.kanguro.common.enums.ClaimStatus
import com.insurtech.kanguro.core.base.BaseViewModel
import com.insurtech.kanguro.core.base.errorHandling.ErrorWithRetry
import com.insurtech.kanguro.core.repository.claims.IClaimsRepository
import com.insurtech.kanguro.domain.coverage.Claim
import com.insurtech.kanguro.domain.model.ReimbursementProcess
import com.insurtech.kanguro.networking.extensions.onError
import com.insurtech.kanguro.networking.extensions.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@Deprecated("This class is deprecated. Use TrackYourClaimViewModel instead")
@HiltViewModel
class ClaimsViewModel @Inject constructor(
    private val claimsRepository: IClaimsRepository
) : BaseViewModel() {

    private val openClaims = MutableLiveData<List<Claim>>()
    private val closedClaims = MutableLiveData<List<Claim>>()
    private val draftClaims = MutableLiveData<List<Claim>>()
    private val selectedTab = MutableLiveData(R.id.openButton)

    val isRefreshing = MutableLiveData(false)

    val displayedList = selectedTab.switchMap {
        when (it) {
            R.id.openButton -> openClaims
            R.id.closedButton -> closedClaims
            else -> draftClaims
        }
    }

    init {
        loadClaims()
    }

    private fun loadClaims() {
        launchLoading(Dispatchers.IO) {
            getClaims(onError = ::loadClaims)
        }
    }

    private suspend fun getClaims(onError: () -> Unit) {
        claimsRepository.getClaims().onSuccess {
            val grouped = body.groupBy { it.status }

            val directPayToVetReimbursementProcess = grouped[ClaimStatus.Draft].orEmpty().filter {
                it.reimbursementProcess == ReimbursementProcess.VeterinarianReimbursement
            }.map {
                it.copy(status = ClaimStatus.Submitted)
            }

            openClaims.postValue(
                (grouped[ClaimStatus.Submitted].orEmpty() + grouped[ClaimStatus.InReview].orEmpty() + grouped[ClaimStatus.Assigned].orEmpty() + directPayToVetReimbursementProcess)
                    .sortedByDescending { it.id }
            )

            draftClaims.postValue(grouped[ClaimStatus.Draft].orEmpty())

            closedClaims.postValue(
                grouped[ClaimStatus.Closed].orEmpty() + grouped[ClaimStatus.Denied].orEmpty() + grouped[ClaimStatus.Approved].orEmpty() + grouped[ClaimStatus.Paid].orEmpty()
            )

            isRefreshing.postValue(false)
        }.onError {
            _networkError.postValue(ErrorWithRetry(this) { onError() })
            isRefreshing.postValue(false)
        }
    }

    fun refreshClaims() {
        isRefreshing.postValue(true)
        viewModelScope.launch {
            getClaims(onError = ::refreshClaims)
        }
    }

    fun setFilteredList(id: Int) {
        selectedTab.postValue(id)
    }
}
