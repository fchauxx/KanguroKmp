package com.insurtech.kanguro.ui.scenes.chatFragment

import androidx.lifecycle.viewModelScope
import com.insurtech.kanguro.core.base.BaseViewModel
import com.insurtech.kanguro.core.utils.update
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.repository.IContactInformationRepository
import com.insurtech.kanguro.designsystem.ui.composables.chat.domain.ChatSupportSectionModel
import com.insurtech.kanguro.domain.model.ContactInformation
import com.insurtech.kanguro.domain.model.ContactInformationType
import com.insurtech.kanguro.usecase.IGetPolicyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getPolicyUseCase: IGetPolicyUseCase,
    private val contactInformationRepository: IContactInformationRepository
) : BaseViewModel() {
    data class ChatUiState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val showSelectFileAClaimTypeDialog: Boolean = false,
        val showRentersFileAClaimDialog: Boolean = false,
        val userHasPets: Boolean = true,
        val userHasRenters: Boolean = true,
        val showLiveVeterinarian: Boolean = false,
        val chatSupportSectionModel: ChatSupportSectionModel = ChatSupportSectionModel()
    )

    private var _contactInformationList: List<ContactInformation>? = null

    private val _chatUiState: MutableStateFlow<ChatUiState> = MutableStateFlow(ChatUiState())
    val chatUiState = _chatUiState.asStateFlow()

    val userHasPets: Boolean
        get() = _chatUiState.value.userHasPets

    val userHasRenters: Boolean
        get() = _chatUiState.value.userHasRenters

    init {
        loadData()
    }

    fun loadData() {
        _chatUiState.update {
            copy(isError = false)
        }

        fetchUserCoverages()
        getSupportInformation()
    }

    private fun fetchUserCoverages() {
        val petPolicies = getPolicyUseCase.getPetPolicies()
        val rentersPolicies = getPolicyUseCase.getRentersPolicies()

        _chatUiState.update {
            copy(
                userHasPets = petPolicies.isNotEmpty(),
                userHasRenters = rentersPolicies.isNotEmpty()
            )
        }
    }

    fun showSelectFileAClaimType(showModal: Boolean) {
        _chatUiState.update {
            copy(showSelectFileAClaimTypeDialog = showModal)
        }
    }

    fun showRentersFileAClaimDialog(showDialog: Boolean) {
        _chatUiState.update {
            copy(showRentersFileAClaimDialog = showDialog)
        }
    }

    private fun getSupportInformation() {
        _chatUiState.update {
            copy(
                isLoading = true
            )
        }
        viewModelScope.launch {
            val result = contactInformationRepository.getContactInformation()
            if (result is Result.Success) {
                _contactInformationList = result.data
                _chatUiState.update {
                    copy(
                        isLoading = false,
                        chatSupportSectionModel = updateSupportSectionState()
                    )
                }
            } else if (result is Result.Error) {
                _chatUiState.update {
                    copy(
                        isLoading = false,
                        isError = true
                    )
                }
            }

            val shouldShowLiveVet = getShouldShowLiveVet()
            _chatUiState.update {
                copy(
                    showLiveVeterinarian = shouldShowLiveVet
                )
            }
        }
    }

    private fun updateSupportSectionState(): ChatSupportSectionModel {
        val actionOpeningHours = getContactInformation(ContactInformationType.Text)
        val actionSms = getContactInformation(ContactInformationType.Sms)
        val actionWhatsapp = getContactInformation(ContactInformationType.Whatsapp)
        val actionPhone = getContactInformation(ContactInformationType.Phone)

        return ChatSupportSectionModel(
            openingHoursText = actionOpeningHours?.data?.text ?: "",
            actionSmsName = actionSms?.action ?: "",
            actionWhatsappName = actionWhatsapp?.action ?: "",
            actionPhoneName = actionPhone?.action ?: ""
        )
    }

    fun getContactInformation(type: ContactInformationType): ContactInformation? {
        return _contactInformationList?.find {
            it.type == type
        }
    }
}
