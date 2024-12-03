package com.insurtech.kanguro.ui.scenes.wellnessPreventive

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.haroldadmin.cnradapter.NetworkResponse
import com.insurtech.kanguro.core.base.BaseViewModel
import com.insurtech.kanguro.core.base.errorHandling.ErrorWithRetry
import com.insurtech.kanguro.core.utils.SingleLiveEvent
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.repository.IRefactoredPetRepository
import com.insurtech.kanguro.data.repository.IRefactoredPolicyRepository
import com.insurtech.kanguro.domain.model.Pet
import com.insurtech.kanguro.domain.model.PreventiveCoverageInfo
import com.insurtech.kanguro.networking.dto.ErrorDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class WellnessPreventiveViewModel @Inject constructor(
    private val policyRepository: IRefactoredPolicyRepository,
    private val petsRepository: IRefactoredPetRepository
) : BaseViewModel() {

    private val selectedCoverages: HashSet<String> = HashSet()

    val onCoveragesSelected = SingleLiveEvent<String>()

    private val _reimbursementButtonEnabled = MutableLiveData(false)
    val reimbursementButtonEnabled: LiveData<Boolean>
        get() = _reimbursementButtonEnabled

    private val _coverages = MutableLiveData<List<PreventiveCoverageInfo>>()
    val coverages: LiveData<List<PreventiveCoverageInfo>>
        get() = _coverages

    private val _pet = MutableLiveData<Pet>()
    val pet: LiveData<Pet>
        get() = _pet

    fun getCoveragesFromSession(policyId: String, petId: Int) {
        launchLoading(Dispatchers.IO) {
            getPetDetail(petId)
            getPolicyDetail(policyId, petId)
        }
    }

    private suspend fun getPetDetail(petId: Int) {
        petsRepository.getPetDetail(petId.toLong())
            .catch { e -> Result.Error(Exception(e)) }
            .collect { petResult ->
                if (petResult is Result.Success) {
                    val pet = petResult.data
                    _pet.postValue(pet)
                }
            }
    }

    private suspend fun getPolicyDetail(policyId: String, petId: Int) {
        policyRepository.getPolicyDetail(policyId)
            .catch { e -> Result.Error(Exception(e)) }
            .collect { policyResult ->
                if (policyResult is Result.Success) {
                    val reimbursement =
                        policyResult.data.reimbursement?.amount?.div(100) ?: return@collect
                    val offerId = policyResult.data.policyOfferId
                    getCoverages(policyId, petId, reimbursement, offerId)
                } else {
                    val errorResult = policyResult as Result.Error
                    handleErrorResult(errorResult, policyId, petId)
                }
            }
    }

    private fun handleErrorResult(errorResult: Result.Error, policyId: String, petId: Int) {
        _networkError.postValue(
            ErrorWithRetry(
                NetworkResponse.UnknownError<Unit, ErrorDto>(
                    errorResult.exception,
                    null
                )
            ) {
                getCoveragesFromSession(policyId, petId)
            }
        )
    }

    private suspend fun getCoverages(
        policyId: String,
        petId: Int,
        reimbursement: Double,
        offerId: Int?
    ) {
        if (offerId == null) {
            getPolicyCoverages(policyId, petId, reimbursement)
        } else {
            policyRepository.getPolicyCoverage(policyId, reimbursement, offerId)
                .catch { e -> Result.Error(Exception(e)) }
                .collect { preventiveCoverageIndoResult ->
                    if (preventiveCoverageIndoResult is Result.Success) {
                        updateCoverageItems(preventiveCoverageIndoResult.data)
                    } else {
                        getPolicyCoverages(policyId, petId, reimbursement)
                    }
                }
        }
    }

    private suspend fun getPolicyCoverages(
        policyId: String,
        petId: Int,
        reimbursement: Double
    ) {
        policyRepository.getPolicyCoverage(policyId, reimbursement)
            .catch { e -> Result.Error(Exception(e)) }
            .collect { policyResult ->
                val errorResult = policyResult as Result.Error
                handleErrorResult(errorResult, policyId, petId)
            }
    }

    private fun updateCoverageItems(coverageInfo: List<PreventiveCoverageInfo>) {
        val coverages = coverageInfo.filter {
            !it.containsNumberWordInName()
        }
        _coverages.postValue(coverages)
    }

    fun onItemSelected(varName: String) {
        selectedCoverages.add(varName)
        _reimbursementButtonEnabled.value = selectedCoverages.isNotEmpty()
    }

    fun onItemDeselected(varName: String) {
        selectedCoverages.remove(varName)
        _reimbursementButtonEnabled.value = selectedCoverages.isNotEmpty()
    }

    fun onReimbursementButtonClicked() {
        val coverages = selectedCoverages.joinToString(", ")
        onCoveragesSelected.value = coverages
    }
}
