package com.insurtech.kanguro.ui.scenes.whatsCoveredUsage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.haroldadmin.cnradapter.NetworkResponse
import com.insurtech.kanguro.core.base.BaseViewModel
import com.insurtech.kanguro.core.base.errorHandling.ErrorWithRetry
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.repository.IRefactoredPolicyRepository
import com.insurtech.kanguro.domain.model.PetPolicy
import com.insurtech.kanguro.domain.model.PreventiveCoverageInfo
import com.insurtech.kanguro.networking.dto.ErrorDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class WhatsCoveredViewModel @Inject constructor(
    private val policyRepository: IRefactoredPolicyRepository
) : BaseViewModel() {

    private val _coverageItems = MutableLiveData<List<PreventiveCoverageInfo>>()
    val coverageItems: LiveData<List<PreventiveCoverageInfo>>
        get() = _coverageItems

    fun fetchCoverageItems(policy: PetPolicy) {
        if (coverageItems.value?.isNotEmpty() == true) return

        val policyId = policy.id ?: return
        val reimbursement = policy.reimbursement?.amount?.div(100) ?: return
        val offerId = policy.policyOfferId

        launchLoading(Dispatchers.IO) {
            if (offerId == null) {
                getPolicyCoverage(policy, reimbursement)
            } else {
                getPolicyCoverageWithOfferId(policy, policyId, reimbursement, offerId)
            }
        }
    }

    private suspend fun getPolicyCoverageWithOfferId(
        policy: PetPolicy,
        policyId: String,
        reimbursement: Double,
        offerId: Int?
    ) {
        policyRepository.getPolicyCoverage(policyId, reimbursement, offerId)
            .catch { e -> Result.Error(Exception(e)) }
            .collect { getPolicyCoverageResult ->
                if (getPolicyCoverageResult is Result.Success) {
                    updateCoverageItems(getPolicyCoverageResult.data)
                } else {
                    getPolicyCoverage(policy, reimbursement)
                }
            }
    }

    private suspend fun getPolicyCoverage(policy: PetPolicy, reimbursement: Double) {
        policyRepository.getPolicyCoverage(policy.id!!, reimbursement)
            .catch { e -> Result.Error(Exception(e)) }
            .collect { getPolicyCoverageResult ->
                if (getPolicyCoverageResult is Result.Success) {
                    updateCoverageItems(getPolicyCoverageResult.data)
                } else {
                    val errorResult = getPolicyCoverageResult as Result.Error
                    handleError(policy, errorResult)
                }
            }
    }

    private fun handleError(policy: PetPolicy, errorResult: Result.Error) {
        _networkError.postValue(
            ErrorWithRetry(
                NetworkResponse.UnknownError<Unit, ErrorDto>(errorResult.exception, null)
            ) {
                fetchCoverageItems(policy)
            }
        )
    }

    private fun updateCoverageItems(coverageInfo: List<PreventiveCoverageInfo>) {
        val coverages = coverageInfo.filter {
            !it.containsNumberWordInName()
        }
        _coverageItems.postValue(coverages)
    }
}
