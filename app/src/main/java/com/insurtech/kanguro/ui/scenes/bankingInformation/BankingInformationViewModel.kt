package com.insurtech.kanguro.ui.scenes.bankingInformation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.haroldadmin.cnradapter.NetworkResponse
import com.insurtech.kanguro.common.enums.AccountType
import com.insurtech.kanguro.core.base.BaseViewModel
import com.insurtech.kanguro.core.base.errorHandling.ErrorWithRetry
import com.insurtech.kanguro.core.utils.BankAccountUtils
import com.insurtech.kanguro.core.utils.SingleLiveEvent
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.repository.IRefactoredUserRepository
import com.insurtech.kanguro.domain.model.UserAccount
import com.insurtech.kanguro.networking.dto.ErrorDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class BankingInformationViewModel @Inject constructor(
    private val refactoredUserRepository: IRefactoredUserRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val args = BankingInformationBottomSheetArgs.fromSavedStateHandle(savedStateHandle)

    val accountNumber = MutableLiveData<String?>()
    val routingNumber = MutableLiveData<String?>()
    private val _accountType = MutableLiveData<AccountType?>()
    val accountType: LiveData<AccountType?> = _accountType
    val selectedBank = MutableLiveData<String?>(null)

    val isSavingUserInformation = MutableLiveData(false)

    val userInformationUpdated = SingleLiveEvent<UserAccount>()

    private var originalFormState: UserAccount? = null

    val continueButtonEnabled = MediatorLiveData<Boolean>().apply {
        addSource(accountNumber) { checkIfContinueButtonIsEnabled() }
        addSource(routingNumber) { checkIfContinueButtonIsEnabled() }
        addSource(accountType) { checkIfContinueButtonIsEnabled() }
        addSource(selectedBank) { checkIfContinueButtonIsEnabled() }
    }

    init {
        getUserAccount()
    }

    private fun getUserAccount() {
        launchLoading(Dispatchers.IO) {
            refactoredUserRepository.getUserAccount()
                .catch { e -> Result.Error(Exception(e)) }
                .collect { userAccountResult ->
                    if (userAccountResult is Result.Success) {
                        originalFormState = userAccountResult.data
                        accountNumber.postValue(userAccountResult.data.accountNumber?.trim())
                        routingNumber.postValue(userAccountResult.data.routingNumber?.trim())
                        setAccountType(userAccountResult.data.accountType)
                        setBankInfo(userAccountResult.data.bankName)
                    } else {
                        val errorResult = userAccountResult as Result.Error

                        _networkError.postValue(
                            ErrorWithRetry(
                                NetworkResponse.UnknownError<Unit, ErrorDto>(
                                    errorResult.exception,
                                    null
                                )
                            ) {
                                getUserAccount()
                            }
                        )
                    }
                }
        }
    }

    fun setAccountType(accountType: AccountType?) {
        _accountType.postValue(accountType)
    }

    fun updateUserAccount(bankName: String) {
        launchLoading(Dispatchers.IO, isSavingUserInformation) {
            if (accountNumber.value?.trim() == null ||
                routingNumber.value?.trim() == null ||
                accountType.value == null
            ) {
                return@launchLoading
            }

            val userAccount = UserAccount(
                accountNumber = accountNumber.value?.trim(),
                routingNumber = routingNumber.value?.trim(),
                bankName = bankName,
                accountType = _accountType.value
            )
            val setUserAccountOperationResult = refactoredUserRepository.setUserAccount(userAccount)

            if (setUserAccountOperationResult is Result.Success && setUserAccountOperationResult.data) {
                userInformationUpdated.postValue(userAccount)
            } else {
                handleError(Exception("Error updating user account"))
            }
        }
    }

    private fun handleError(error: Exception) {
        _networkError.postValue(
            ErrorWithRetry(
                NetworkResponse.UnknownError<Unit, ErrorDto>(
                    error,
                    null
                )
            ) {
                getUserAccount()
            }
        )
    }

    private fun checkIfContinueButtonIsEnabled() {
        val isEverythingFilled = !accountNumber.value.isNullOrBlank() &&
            !routingNumber.value.isNullOrBlank() &&
            selectedBank.value != null &&
            accountType.value != null

        val isBankAccountNumberValid =
            BankAccountUtils.isValidBankAccountNumber(accountNumber.value ?: "")
        val isBankRoutingNumberValid =
            BankAccountUtils.isValidBankRoutingNumber(routingNumber.value ?: "")

        val buttonEnabled = isEverythingFilled &&
            isBankAccountNumberValid &&
            isBankRoutingNumberValid &&
            if (args.comingFromChatbot) {
                true
            } else {
                accountNumber.value != originalFormState?.accountNumber ||
                    routingNumber.value != originalFormState?.routingNumber ||
                    selectedBank.value != originalFormState?.bankName ||
                    accountType.value != originalFormState?.accountType
            }

        continueButtonEnabled.postValue(buttonEnabled)
    }

    fun setBankInfo(bankInfo: String?) {
        selectedBank.postValue(bankInfo)
    }
}
