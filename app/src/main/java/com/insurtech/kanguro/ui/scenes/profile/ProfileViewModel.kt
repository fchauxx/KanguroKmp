package com.insurtech.kanguro.ui.scenes.profile

import android.content.Context
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.TextWatcher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.haroldadmin.cnradapter.NetworkResponse
import com.insurtech.kanguro.R
import com.insurtech.kanguro.core.base.BaseViewModel
import com.insurtech.kanguro.core.base.errorHandling.ErrorWithRetry
import com.insurtech.kanguro.core.session.ISessionManager
import com.insurtech.kanguro.core.utils.*
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.repository.IRefactoredUserRepository
import com.insurtech.kanguro.domain.model.Login
import com.insurtech.kanguro.domain.model.UserUpdatePasswordBody
import com.insurtech.kanguro.domain.model.UserUpdateProfileBody
import com.insurtech.kanguro.networking.dto.ErrorDto
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    savedStateHandle: SavedStateHandle,
    private val refactoredUserRepository: IRefactoredUserRepository,
    private val sessionManager: ISessionManager
) : BaseViewModel() {

    private val US_CODE = "US"

    private val navArgs = ProfileFragmentArgs.fromSavedStateHandle(savedStateHandle)

    private val sessionInfo: Login?
        get() = sessionManager.sessionInfo

    val profileSectionOpen = MutableLiveData(navArgs.profileSectionStartsOpen)
    val addressSectionOpen = MutableLiveData(false)
    val passwordSectionOpen = MutableLiveData(false)
    val myAccountSectionOpen = MutableLiveData(false)
    val otherSectionOpen = MutableLiveData(false)

    val firstName = MutableLiveData<String>()
    val surname = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val phoneNumber = MutableLiveData<String>()

    val saveProfileEnabled = MediatorLiveData<Boolean>().apply {
        addSources(firstName, surname, phoneNumber) { checkEditProfileSaveEnabled() }
    }

    val zipCode = MutableLiveData<String>()
    val address = MutableLiveData<String>()
    val aptUnit = MutableLiveData<String>()
    val city = MutableLiveData<String>()
    val state = MutableLiveData<String>()

    val saveAddressEnabled = MediatorLiveData<Boolean>().apply {
        addSources(zipCode, address, aptUnit, city, state) { checkEditAddressSaveEnabled() }
    }

    val currentPassword = MutableLiveData<String>()
    val newPassword = MutableLiveData<String>()
    val repeatPassword = MutableLiveData<String>()
    private val _passwordError = MutableLiveData<Int?>()
    val passwordError: LiveData<Int?> = _passwordError

    val savePasswordEnabled = MediatorLiveData<Boolean>().apply {
        addSources(
            currentPassword,
            newPassword,
            repeatPassword
        ) { checkChangePasswordSaveEnabled() }
    }

    val phoneMaskTextWatcher: TextWatcher = PhoneNumberFormattingTextWatcher(US_CODE)

    init {
        loadFromSessionInformation()
    }

    private fun loadFromSessionInformation() {
        firstName.postValue(sessionInfo?.givenName.orEmpty())
        surname.postValue(sessionInfo?.surname.orEmpty())
        email.postValue(sessionInfo?.email.orEmpty())
        phoneNumber.postValue(sessionInfo?.phone.orEmpty())
    }

    fun switchProfileSection() {
        switchOpenSection(
            if (profileSectionOpen.value == false) {
                profileSectionOpen
            } else {
                MutableLiveData(null)
            }
        )
    }

    fun switchAddressSection() {
        switchOpenSection(
            if (addressSectionOpen.value == false) {
                addressSectionOpen
            } else {
                MutableLiveData(null)
            }
        )
    }

    fun switchPasswordSection() {
        switchOpenSection(
            if (passwordSectionOpen.value == false) {
                passwordSectionOpen
            } else {
                MutableLiveData(null)
            }
        )
    }

    fun switchMyAccountSection() {
        switchOpenSection(
            if (myAccountSectionOpen.value == false) {
                myAccountSectionOpen
            } else {
                MutableLiveData(null)
            }
        )
    }

    fun switchOtherSection() {
        switchOpenSection(
            if (otherSectionOpen.value == false) {
                otherSectionOpen
            } else {
                MutableLiveData(null)
            }
        )
    }

    private fun switchOpenSection(section: LiveData<*>) {
        sequenceOf(
            profileSectionOpen,
            addressSectionOpen,
            passwordSectionOpen,
            myAccountSectionOpen,
            otherSectionOpen
        ).forEach {
            it.value = it == section
        }
    }

    private fun checkEditProfileSaveEnabled() {
        val fields = sequenceOf(firstName.value, surname.value, phoneNumber.value)
        val changedField = firstName.value != sessionInfo?.givenName ||
            surname.value != sessionInfo?.surname ||
            phoneNumber.value != sessionInfo?.phone
        saveProfileEnabled.value = changedField && fields.none { it.isNullOrBlank() }
    }

    private fun checkEditAddressSaveEnabled() {
        // TODO
    }

    private fun checkChangePasswordSaveEnabled() {
        if (currentPassword.value.isNullOrEmpty()) {
            savePasswordEnabled.value = false
            return
        }

        val fields = sequenceOf(newPassword.value, repeatPassword.value)
        savePasswordEnabled.value = fields.none {
            it.isNullOrBlank() || !it.isValidPassword()
        }
    }

    fun onSaveProfilePressed() {
        val givenName = firstName.value ?: return
        val surname = surname.value ?: return
        val phone = phoneNumber.value ?: return

        launchLoading(Dispatchers.IO) {
            val updateUserProfileOperationResult = refactoredUserRepository
                .updateUserProfile(
                    userUpdateProfileBody = UserUpdateProfileBody(
                        givenName = givenName,
                        surname = surname,
                        phone = phone
                    )
                )

            if (updateUserProfileOperationResult is Result.Success) {
                updateLocalSessionInfo(givenName, surname, phone)
            } else {
                _networkError.postValue(
                    ErrorWithRetry(
                        NetworkResponse.UnknownError<Unit, ErrorDto>(
                            Exception(),
                            null
                        )
                    ) {
                        onSaveProfilePressed()
                    }
                )
            }
        }
    }

    private fun updateLocalSessionInfo(givenName: String, surname: String, phone: String) {
        sessionManager.sessionInfo = sessionManager.sessionInfo?.copy(
            givenName = givenName,
            surname = surname,
            phone = phone
        )
        loadFromSessionInformation()
    }

    fun onSaveAddressedPressed() {
        // TODO
    }

    fun onChangePasswordPressed() {
        val email = email.value ?: return
        val oldPwd = currentPassword.value ?: return
        val newPwd = newPassword.value ?: return
        val repeatPwd = repeatPassword.value ?: return

        if (newPwd != repeatPwd) {
            _passwordError.value = R.string.error_passwords_dont_match
            return
        } else {
            _passwordError.value = null
        }

        launchLoading(Dispatchers.IO) {
            val updateUserPasswordOperationResult = refactoredUserRepository.updateUserPassword(
                UserUpdatePasswordBody(
                    email = email,
                    currentPassword = oldPwd,
                    newPassword = newPwd
                )
            )

            if (updateUserPasswordOperationResult is Result.Success) {
                currentPassword.postValue("")
                newPassword.postValue("")
                repeatPassword.postValue("")
            } else {
                _networkError.postValue(
                    ErrorWithRetry(
                        NetworkResponse.UnknownError<Unit, ErrorDto>(
                            Exception(),
                            null
                        )
                    ) {
                        onChangePasswordPressed()
                    }
                )
            }
        }
    }

    fun clearPasswordErrors() {
        _passwordError.value = null
    }

    fun requestDeletion() {
        launchLoading(Dispatchers.IO) {
            val updateDeleteUserFlagOperationResult = refactoredUserRepository
                .updateDeleteUserFlag(true)

            if (updateDeleteUserFlagOperationResult is Result.Success) {
                context.sendLocalBroadcast(KanguroConstants.BROADCAST_USER_LOGGED_OFF)
            } else {
                _networkError.postValue(
                    ErrorWithRetry(
                        NetworkResponse.UnknownError<Unit, ErrorDto>(
                            Exception(),
                            null
                        )
                    ) {
                        requestDeletion()
                    }
                )
            }
        }
    }
}
