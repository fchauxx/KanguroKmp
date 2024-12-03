package com.insurtech.kanguro.ui.scenes.cloud

import android.net.Uri
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.haroldadmin.cnradapter.NetworkResponse
import com.insurtech.kanguro.core.base.BaseViewModel
import com.insurtech.kanguro.core.base.errorHandling.ErrorWithRetry
import com.insurtech.kanguro.core.repository.files.KanguroFileManager
import com.insurtech.kanguro.core.utils.SingleLiveEvent
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.repository.ICloudDocumentRepository
import com.insurtech.kanguro.domain.model.CloudClaimDocument
import com.insurtech.kanguro.domain.model.CloudDocumentPolicy
import com.insurtech.kanguro.networking.dto.ErrorDto
import com.insurtech.kanguro.ui.scenes.cloud.adapter.PolicyFilesType
import com.insurtech.kanguro.ui.scenes.cloud.utils.CloudFileMapper
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CloudPolicyViewModel @AssistedInject constructor(
    @Assisted("policyId") private val policyId: String,
    @Assisted("name") val name: String,
    @Assisted("cloudType") val cloudType: CloudType,
    private val cloudDocumentRepository: ICloudDocumentRepository,
    private val fileManager: KanguroFileManager
) : BaseViewModel() {

    private val cloudFileMapper = CloudFileMapper()

    @VisibleForTesting
    internal var selectedPolicy: CloudDocumentPolicy? = null

    var selectedPolicyFilesType: PolicyFilesType? = null

    private val _policyFilesTypes = MutableLiveData<List<PolicyFilesType>>()
    val policyFilesTypes: LiveData<List<PolicyFilesType>>
        get() = _policyFilesTypes

    private val _cloudClaimDocuments = MutableLiveData<List<CloudClaimDocument>>()
    val cloudClaimDocuments: LiveData<List<CloudClaimDocument>>
        get() = _cloudClaimDocuments

    var selectedCloudClaimDocument: CloudClaimDocument? = null

    private val _cloudFileDocument = MutableLiveData<List<CloudFileDocument>>()
    val cloudFileDocument: LiveData<List<CloudFileDocument>>
        get() = _cloudFileDocument

    private val _fetchingAttachment = MutableLiveData<Boolean>()
    val fetchingAttachment: LiveData<Boolean> = _fetchingAttachment

    val openAttachmentEvent = SingleLiveEvent<Uri?>()

    init {
        getAllPolicyDocuments(policyId)
    }

    fun getAllPolicyDocuments(policyId: String) {
        launchLoading(Dispatchers.IO) {
            getCloudDocumentPolicies(policyId)
        }
    }

    private suspend fun getCloudDocumentPolicies(policyId: String) {
        _policyFilesTypes.postValue(emptyList())

        cloudDocumentRepository.getCloudDocumentPolicy(policyId)
            .catch { e -> Result.Error(Exception(e)) }
            .collect { cloudDocumentPolicyResult ->
                if (cloudDocumentPolicyResult is Result.Success) {
                    selectedPolicy = cloudDocumentPolicyResult.data

                    val policyFilesTypes = arrayListOf<PolicyFilesType>()

                    val claimDocumentsFiltered = filterNullPrefixes(selectedPolicy?.claimDocuments)
                    if (claimDocumentsFiltered.isNotEmpty()) {
                        policyFilesTypes.add(PolicyFilesType.ClaimDocuments)
                    }

                    val selectedPolicyDocuments = selectedPolicy?.policyDocuments.orEmpty()
                    if (selectedPolicyDocuments.isNotEmpty()) {
                        policyFilesTypes.add(PolicyFilesType.PolicyDocument)
                    }

                    val selectedPolicyAttachment = selectedPolicy?.policyAttachments.orEmpty()
                    if (selectedPolicyAttachment.isNotEmpty()) {
                        policyFilesTypes.add(PolicyFilesType.PolicyAttachment)
                    }

                    _policyFilesTypes.postValue(policyFilesTypes)
                } else {
                    val errorResult = cloudDocumentPolicyResult as Result.Error

                    val error = Pair(errorResult) {
                        getAllPolicyDocuments(policyId)
                    }
                    _resultError.postValue(error)
                }
            }
    }

    private fun filterNullPrefixes(list: List<CloudClaimDocument>?): List<CloudClaimDocument> {
        if (list == null) return emptyList()
        return list.filter { !it.claimPrefixId.isNullOrEmpty() }
    }

    fun selectPolicyFileType(policyFilesType: PolicyFilesType) {
        selectedPolicyFilesType = policyFilesType
        _cloudFileDocument.value = emptyList()

        when (policyFilesType) {
            PolicyFilesType.ClaimDocuments -> {
                setupCloudClaimDocuments()
            }

            PolicyFilesType.PolicyDocument -> {
                setupPolicyDocuments()
            }

            PolicyFilesType.PolicyAttachment -> {
                setupPolicyAttachments()
            }

            else -> {
                // left empty on purpose
            }
        }
    }

    private fun setupCloudClaimDocuments() {
        val claimDocuments = filterNullPrefixes(selectedPolicy?.claimDocuments)
        if (claimDocuments.isNotEmpty()) {
            _cloudClaimDocuments.value = claimDocuments
        }
    }

    private fun setupPolicyDocuments() {
        val policyDocuments = selectedPolicy?.policyDocuments

        _cloudFileDocument.value = policyDocuments
            ?.map { cloudFileMapper.mapFrom(it) }
            .orEmpty()
    }

    private fun setupPolicyAttachments() {
        val policyAttachments = selectedPolicy?.policyAttachments

        _cloudFileDocument.value = policyAttachments
            ?.map { cloudFileMapper.mapFrom(it) }
            .orEmpty()
    }

    fun selectCloudClaim(cloudClaimDocument: CloudClaimDocument) {
        selectedCloudClaimDocument = cloudClaimDocument

        val policyId = selectedPolicy?.id
        val claimId = cloudClaimDocument.claimId

        if (policyId != null && claimId != null) {
            launchLoading(Dispatchers.IO) {
                getCloudClaimDocument(policyId, claimId)
            }
        }
    }

    private suspend fun getCloudClaimDocument(policyId: String, claimId: String) {
        cloudDocumentRepository.getCloudClaimDocument(policyId, claimId)
            .catch { e -> Result.Error(Exception(e)) }
            .collect { cloudClaimDocumentResult ->
                if (cloudClaimDocumentResult is Result.Success) {
                    val thisCloudClaimDocument = cloudClaimDocumentResult.data
                    val mappedCloudClaimDocuments = thisCloudClaimDocument.claimDocuments
                        ?.map { cloudFileMapper.mapFrom(it) }
                        .orEmpty()

                    _cloudFileDocument.postValue(mappedCloudClaimDocuments)
                } else {
                    val errorResult = cloudClaimDocumentResult as Result.Error

                    _networkError.postValue(
                        ErrorWithRetry(
                            NetworkResponse.UnknownError<Unit, ErrorDto>(
                                errorResult.exception,
                                null
                            )
                        ) {
                            if (selectedCloudClaimDocument != null) {
                                selectCloudClaim(selectedCloudClaimDocument!!)
                            }
                        }
                    )
                }
            }
    }

    fun fetchCloudFile(cloudFileDocument: CloudFileDocument) {
        when (cloudFileDocument.type) {
            PolicyFilesType.ClaimDocuments -> fetchDocument(
                cloudFileDocument,
                selectedCloudClaimDocument?.claimId,
                fileManager::getClaimAttachment
            )

            PolicyFilesType.PolicyDocument ->
                if (cloudType == CloudType.PET) {
                    fetchDocument(
                        cloudFileDocument,
                        selectedPolicy?.id,
                        fileManager::getPolicyDocument
                    )
                } else if (cloudType == CloudType.RENTERS) {
                    fetchDocument(
                        cloudFileDocument,
                        selectedPolicy?.id,
                        fileManager::getPolicyRentersDocument
                    )
                }
            PolicyFilesType.PolicyAttachment -> fetchDocument(
                cloudFileDocument,
                selectedPolicy?.id,
                fileManager::getPolicyAttachment
            )

            else -> {
                // left empty on purpose
            }
        }
    }

    private fun fetchDocument(
        cloudFileDocument: CloudFileDocument,
        id: String?,
        uriGetter: suspend (String, Long, String) -> Uri?
    ) {
        val docId = cloudFileDocument.id

        val fileName = cloudFileDocument.visibleName

        if (id != null && docId != null && fileName.isNotEmpty()) {
            _fetchingAttachment.value = true
            viewModelScope.launch {
                val file = uriGetter(id, docId, fileName)
                openAttachmentEvent.postValue(file)
                _fetchingAttachment.postValue(false)
            }
        }
    }

    fun clearDocuments() {
        _policyFilesTypes.postValue(emptyList())
        selectedPolicy = null
    }

    companion object {
        fun provideFactory(
            cloudPolicyViewModel: CloudPolicyViewModelFactory,
            policyId: String,
            name: String,
            cloudType: CloudType
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return cloudPolicyViewModel.create(policyId, name, cloudType) as T
            }
        }
    }

    @AssistedFactory
    interface CloudPolicyViewModelFactory {
        fun create(
            @Assisted("policyId") policyId: String,
            @Assisted("name") name: String,
            @Assisted("cloudType") cloudType: CloudType
        ): CloudPolicyViewModel
    }
}
