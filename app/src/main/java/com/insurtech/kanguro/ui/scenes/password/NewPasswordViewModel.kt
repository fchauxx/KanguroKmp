package com.insurtech.kanguro.ui.scenes.password

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
import com.insurtech.kanguro.domain.model.UserUpdatePasswordBody
import com.insurtech.kanguro.networking.dto.ErrorDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class NewPasswordViewModel @Inject constructor(
    private val refactoredUserRepository: IRefactoredUserRepository,
    private val sessionManager: ISessionManager,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val args = NewPasswordFragmentArgs.fromSavedStateHandle(savedStateHandle)

    val password = MutableLiveData<String>()
    val repeatedPassword = MutableLiveData<String>()

    private val _passwordIsValid = MediatorLiveData<Boolean>().apply {
        this.value = false
        addSources(password, repeatedPassword) {
            verifyFields()
        }
    }

    val passwordIsValid: LiveData<Boolean>
        get() = _passwordIsValid

    private val _passwordError = MutableLiveData<Int?>()
    val passwordError: LiveData<Int?> = _passwordError

    private val _passwordNotValidError = MutableLiveData<Int?>()
    val passwordNotValidError: LiveData<Int?> = _passwordNotValidError

    private val _newPasswordSetSuccessful = SingleLiveEvent<Unit>()
    val newPasswordSetSuccessful: LiveData<Unit>
        get() = _newPasswordSetSuccessful

    private fun verifyFields() {
        if (password.value!!.length >= 8 && !password.value!!.isValidPassword()) {
            _passwordNotValidError.value = R.string.error_passwords_invalid
            _passwordIsValid.value = false
            return
        } else {
            _passwordNotValidError.value = null
        }

        val isNotMatchingPassword = !repeatedPassword.value.isNullOrEmpty() &&
            repeatedPassword.value!!.length == password.value!!.length &&
            password.value != repeatedPassword.value

        if (isNotMatchingPassword) {
            _passwordError.value = R.string.error_passwords_dont_match
            _passwordIsValid.value = false
            return
        } else {
            _passwordError.value = null
        }

        _passwordIsValid.value = !password.value.isNullOrEmpty() &&
            password.value!!.isValidPassword() &&
            !repeatedPassword.value.isNullOrEmpty() &&
            password.value == repeatedPassword.value
    }

    fun sendNewPassword() {
        launchLoading(Dispatchers.IO) {
            val updateUserPasswordOperationResult = refactoredUserRepository.updateUserPassword(
                UserUpdatePasswordBody(
                    email = args.email,
                    currentPassword = args.oldPassword,
                    newPassword = password.value!!
                )
            )

            if (updateUserPasswordOperationResult is Result.Success) {
                sessionManager.sessionInfo =
                    sessionManager.sessionInfo?.copy(isPasswordUpdateNeeded = false)

                _newPasswordSetSuccessful.postValue(Unit)
            } else {
                _networkError.postValue(
                    ErrorWithRetry(
                        NetworkResponse.UnknownError<Unit, ErrorDto>(
                            Exception(),
                            null
                        )
                    ) {
                    }
                )
            }
        }
    }
}
