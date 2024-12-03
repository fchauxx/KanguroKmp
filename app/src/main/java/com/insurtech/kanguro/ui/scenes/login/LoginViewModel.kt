package com.insurtech.kanguro.ui.scenes.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.haroldadmin.cnradapter.NetworkResponse
import com.insurtech.kanguro.common.enums.AppLanguage
import com.insurtech.kanguro.core.base.BaseViewModel
import com.insurtech.kanguro.core.base.errorHandling.ErrorWithRetry
import com.insurtech.kanguro.core.repository.user.IUserRepository
import com.insurtech.kanguro.core.session.ISessionManager
import com.insurtech.kanguro.core.utils.SingleLiveEvent
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.data
import com.insurtech.kanguro.data.repository.ILoginRepository
import com.insurtech.kanguro.data.repository.IPreferencesRepository
import com.insurtech.kanguro.domain.model.LoginBody
import com.insurtech.kanguro.networking.dto.ErrorDto
import com.insurtech.kanguro.networking.error.RemoteServiceIntegrationError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: IUserRepository,
    private val loginRepository: ILoginRepository,
    private val preferencesRepository: IPreferencesRepository,
    private val sessionManager: ISessionManager
) : BaseViewModel() {

    val email = MutableLiveData<String>()
    val countryCode = MutableLiveData<String>()
    val phoneNumber = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _loginSuccessful = SingleLiveEvent<Boolean>()
    val loginSuccessful: LiveData<Boolean> = _loginSuccessful

    private val _needUpdatePassword = SingleLiveEvent<Boolean>()
    val needUpdatePassword: LiveData<Boolean> = _needUpdatePassword

    val isEmailValid = email.map {
        Patterns.EMAIL_ADDRESS.matcher(it.orEmpty()).matches()
    }

    private val _isPasswordCreation = MutableLiveData<Boolean>()
    val isPasswordCreation: LiveData<Boolean>
        get() = _isPasswordCreation

    private val _goToNextScreen = SingleLiveEvent<Boolean>()
    val goToNextScreen: LiveData<Boolean>
        get() = _goToNextScreen

    val isAccountBlocked = SingleLiveEvent<Boolean>()

    private val _updateAppLanguage = SingleLiveEvent<AppLanguage?>()
    val updateAppLanguage: LiveData<AppLanguage?>
        get() = _updateAppLanguage

    fun onContinuePressed() {
        if (isEmailValid.value == true) {
            _goToNextScreen.postValue(true)
        }
    }

    val phoneIsValid = MediatorLiveData<Boolean>().apply {
        addSource(countryCode) { validateFields() }
        addSource(phoneNumber) { validateFields() }
    }

    private fun validateFields() {
        phoneIsValid.value = countryCode.value?.isNotEmpty() == true &&
            phoneNumber.value.orEmpty().length >= 5
    }

    fun sendUserLogin() {
        launchLoading(Dispatchers.IO) {
            loginRepository.sendLogin(
                LoginBody(email = email.value.orEmpty(), password = password.value.orEmpty())
            )
                .catch { e -> Result.Error(Exception(e)) }
                .collect { loginResult ->
                    when (loginResult) {
                        is Result.Success -> {
                            sessionManager.sessionInfo = loginResult.data
                            onLoginSuccessful()
                        }

                        is Result.Error -> {
                            onLoginFail(loginResult.exception)
                        }
                    }
                }
        }
    }

    private fun onLoginFail(exception: Exception) {
        if (exception.cause is RemoteServiceIntegrationError.ForbiddenClientOrigin) {
            isAccountBlocked.postValue(true)
        } else {
            _networkError.postValue(
                ErrorWithRetry(
                    NetworkResponse.UnknownError<Unit, ErrorDto>(
                        exception,
                        null
                    ),
                    ::sendUserLogin
                )
            )
        }
    }

    private suspend fun onLoginSuccessful() {
        updateUserPreferredLanguage()
        if (sessionManager.sessionInfo?.isPasswordUpdateNeeded == true) {
            _needUpdatePassword.postValue(true)
        } else {
            _loginSuccessful.postValue(true)
        }
    }

    private suspend fun updateUserPreferredLanguage() {
        val selectedLanguage = sessionManager.sessionInfo?.language
        val preferredLanguage = preferencesRepository.getPreferredLanguage().data

        if (selectedLanguage == null) {
            userRepository.setPreferredLanguage(preferredLanguage ?: return)
        } else if (selectedLanguage.language != preferredLanguage?.language) {
            _updateAppLanguage.postValue(selectedLanguage)
        }
    }
}
