package com.insurtech.kanguro.ui.scenes.cloud

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.insurtech.kanguro.core.base.BaseViewModel
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.repository.ICloudDocumentRepository
import com.insurtech.kanguro.domain.model.CloudDocumentPolicy
import com.insurtech.kanguro.domain.model.CloudPet
import com.insurtech.kanguro.domain.model.CloudRenters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KanguroCloudViewModel @Inject constructor(
    private val cloudDocumentRepository: ICloudDocumentRepository
) : BaseViewModel() {

    data class CloudState(
        val pets: List<CloudPet> = emptyList(),
        val renters: List<CloudRenters> = emptyList(),
        val isRefreshing: Boolean = false
    )

    private val _cloudState: MutableStateFlow<CloudState> =
        MutableStateFlow(CloudState())
    val cloudState = _cloudState.asStateFlow()

    private val _isRefreshing = MutableLiveData(false)
    val isRefreshing: LiveData<Boolean>
        get() = _isRefreshing

    var selectedCloud: SelectedCloud? = null

    private val _selectedPolicies = MutableLiveData<List<CloudDocumentPolicy>>()
    val selectedPolicies: LiveData<List<CloudDocumentPolicy>>
        get() = _selectedPolicies

    init {
        fetchDocuments()
    }

    private fun fetchDocuments() {
        launchLoading(Dispatchers.IO) {
            getCloudDocuments()
        }
    }

    private suspend fun getCloudDocuments() {
        _cloudState.value = _cloudState.value.copy(isRefreshing = true)

        cloudDocumentRepository.getCloudDocuments()
            .catch { e -> Result.Error(Exception(e)) }
            .collect { cloudDocumentResult ->
                if (cloudDocumentResult is Result.Success) {
                    val cloudDocuments = cloudDocumentResult.data
                    _cloudState.value = _cloudState.value.copy(
                        pets = cloudDocuments.pets.orEmpty(),
                        renters = cloudDocuments.renters.orEmpty(),
                        isRefreshing = false
                    )
                } else {
                    val errorResult = cloudDocumentResult as Result.Error
                    _resultError.postValue(Pair(errorResult, ::fetchDocuments))
                    _cloudState.value = _cloudState.value.copy(isRefreshing = false)
                }
            }
    }

    fun refreshDocuments() {
        viewModelScope.launch {
            _isRefreshing.postValue(true)
            getCloudDocuments()
            _isRefreshing.postValue(false)
        }
    }

    fun selectCloud(cloud: SelectedCloud) {
        selectedCloud = cloud
        buildCloudPoliciesList(cloud)
    }

    private fun buildCloudPoliciesList(pet: SelectedCloud) {
        _selectedPolicies.value = emptyList()

        val cloudPoliciesByDescending =
            pet.cloudDocumentPolicies?.sortedByDescending { it.policyStartDate } ?: emptyList()

        _selectedPolicies.value = cloudPoliciesByDescending
    }

    fun reverseCloudPoliciesOrder() {
        val selectedPetPoliciesReversed = _selectedPolicies.value?.reversed() ?: emptyList()
        _selectedPolicies.value = selectedPetPoliciesReversed
    }
}
