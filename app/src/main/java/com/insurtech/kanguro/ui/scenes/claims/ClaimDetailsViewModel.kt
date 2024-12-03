package com.insurtech.kanguro.ui.scenes.claims

import android.net.Uri
import androidx.lifecycle.*
import com.haroldadmin.cnradapter.NetworkResponse
import com.insurtech.kanguro.R
import com.insurtech.kanguro.common.enums.ClaimStatus
import com.insurtech.kanguro.core.base.BaseViewModel
import com.insurtech.kanguro.core.base.errorHandling.ErrorWithRetry
import com.insurtech.kanguro.core.repository.files.KanguroFileManager
import com.insurtech.kanguro.core.utils.*
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.data
import com.insurtech.kanguro.data.repository.IRefactoredClaimRepository
import com.insurtech.kanguro.domain.Base64FileInfo
import com.insurtech.kanguro.domain.model.ClaimDocument
import com.insurtech.kanguro.domain.model.CommunicationBody
import com.insurtech.kanguro.networking.dto.ErrorDto
import com.insurtech.kanguro.networking.error.RemoteServiceIntegrationError
import com.squareup.moshi.Moshi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class StatusCard(
    val cardViewBackground: Int,
    val strokeColor: Int,
    val claimStatusCardIcon: Int,
    val claimStatusCardIconTint: Int,
    val claimStatusCardText: Int

) {
    object Warning : StatusCard(
        R.color.warning_lightest,
        R.color.warning_medium,
        R.drawable.ic_info_circle,
        R.color.warning_dark,
        R.string.your_claim_warning
    )

    object Denied : StatusCard(
        R.color.negative_lightest,
        R.color.negative_medium,
        R.drawable.ic_circle_error,
        R.color.negative_dark,
        R.string.your_claim_was_denied_because
    )
}

@HiltViewModel
class ClaimDetailsViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    private val refactoredClaimRepository: IRefactoredClaimRepository,
    private val fileManager: KanguroFileManager,
    moshi: Moshi
) : BaseViewModel() {

    val args = ClaimDetailsBottomSheetArgs.fromSavedStateHandle(savedStateHandle)

    private val fileConverter = moshi.adapter(Base64FileInfo::class.java)

    private val _attachments = MutableLiveData<List<ClaimDocument>>(emptyList())
    val attachments: LiveData<List<ClaimDocument>>
        get() = _attachments

    private val _fetchingAttachment = MutableLiveData<Boolean>()
    val fetchingAttachment: LiveData<Boolean> = _fetchingAttachment

    private val _sendingAttachments = MutableLiveData<Boolean>()
    val sendingAttachments: LiveData<Boolean> = _sendingAttachments

    val openAttachmentEvent = SingleLiveEvent<Uri?>()

    private val _warningCard = MutableLiveData<StatusCard>()
    val warningCard: LiveData<StatusCard> = _warningCard

    private val _hasWarning = MutableLiveData<Boolean>()
    val hasWarning: LiveData<Boolean> = _hasWarning

    private val _hasReason = MutableLiveData<Boolean>()
    val hasReason: LiveData<Boolean> = _hasReason

    private val _showSubmitButton = MutableLiveData<Boolean>()
    val showSubmitButton: LiveData<Boolean> = _showSubmitButton

    init {
        fetchDocuments()
        setWarningCard()
    }

    private fun setWarningCard() {
        _hasWarning.postValue(
            args.claim.hasPendingCommunications == true ||
                args.claim.status == ClaimStatus.Denied ||
                args.claim.status == ClaimStatus.PendingMedicalHistory
        )

        if (args.claim.status == ClaimStatus.Denied) {
            _warningCard.value = StatusCard.Denied
            _hasReason.value = true
        } else {
            _warningCard.value = StatusCard.Warning
            _hasReason.value = false
        }

        _showSubmitButton.value = when (args.claim.status) {
            ClaimStatus.Submitted,
            ClaimStatus.Assigned,
            ClaimStatus.InReview,
            ClaimStatus.PendingMedicalHistory -> true

            else -> false
        }
    }

    private fun fetchDocuments() {
        val claimId = args.claim.id
        claimId ?: return
        launchLoading(Dispatchers.IO) {
            getClaimDocuments(claimId)
        }
    }

    private suspend fun getClaimDocuments(claimId: String) {
        refactoredClaimRepository.getClaimDocuments(claimId)
            .catch { e -> Result.Error(Exception(e)) }
            .collect { claimDocumentsResult ->
                if (claimDocumentsResult is Result.Success) {
                    val attachments = claimDocumentsResult.data
                    _attachments.postValue(attachments)
                    _sendingAttachments.postValue(false)
                } else {
                    val errorResult = claimDocumentsResult as Result.Error

                    if (errorResult.exception.cause !is RemoteServiceIntegrationError.NotFoundClientOrigin) {
                        _networkError.postValue(
                            ErrorWithRetry(
                                NetworkResponse.UnknownError<Unit, ErrorDto>(
                                    errorResult.exception,
                                    null
                                )
                            ) {
                                fetchDocuments()
                            }
                        )
                    }

                    _sendingAttachments.postValue(false)
                }
            }
    }

    fun fetchAttachment(document: ClaimDocument) {
        if (fetchingAttachment.value == true) return

        val claimId = args.claim.id ?: return
        val docId = document.id ?: return
        val filename = document.fileName ?: return

        _fetchingAttachment.value = true
        viewModelScope.launch {
            val file = fileManager.getClaimAttachment(claimId, docId, filename)
            openAttachmentEvent.postValue(file)
            _fetchingAttachment.postValue(false)
        }
    }

    fun sendAttachments(attachments: List<Pair<String, String>>?) {
        launchLoading(Dispatchers.IO) {
            _sendingAttachments.postValue(true)
            val claimId = args.claim.id
            claimId ?: return@launchLoading

            sendClaimAttachments(claimId, attachments)
            getClaimDocuments(args.claim.id!!)
        }
    }

    private suspend fun sendClaimAttachments(
        claimId: String,
        attachments: List<Pair<String, String>>?
    ) {
        val files = attachments?.mapNotNull {
            fileConverter.toJson(Base64FileInfo(it.first, it.second.asValidExtension()))
        }

        if (!files.isNullOrEmpty()) {
            val communication = CommunicationBody(
                message = "",
                files = files
            )

            val sendClaimCommunicationOperationResult = refactoredClaimRepository
                .sendClaimCommunications(
                    claimId = claimId,
                    claimCommunicationBody = communication
                )

            val isSuccessfulSendClaimCommunicationOperation =
                sendClaimCommunicationOperationResult.data

            if (isSuccessfulSendClaimCommunicationOperation != null &&
                !isSuccessfulSendClaimCommunicationOperation
            ) {
                _sendingAttachments.postValue(false)
                _networkError.postValue(
                    ErrorWithRetry(
                        NetworkResponse.UnknownError<Unit, ErrorDto>(
                            Exception(),
                            null
                        )
                    ) {
                        sendAttachments(attachments)
                    }
                )
            }
        }
    }
}
