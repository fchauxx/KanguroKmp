package com.insurtech.kanguro.ui.scenes.forgotPassword

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.insurtech.kanguro.core.base.BaseViewModel
import com.insurtech.kanguro.core.base.errorHandling.ErrorWithRetry
import com.insurtech.kanguro.core.repository.user.IUserRepository
import com.insurtech.kanguro.core.utils.SingleLiveEvent
import com.insurtech.kanguro.networking.extensions.onError
import com.insurtech.kanguro.networking.extensions.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val userRepository: IUserRepository
) : BaseViewModel() {

    private val _isLoading = SingleLiveEvent<Boolean>()
    val isLoadingInstructions: LiveData<Boolean>
        get() = _isLoading

    val email = MutableLiveData<String>()

    val isEmailValid = email.map {
        Patterns.EMAIL_ADDRESS.matcher(it.orEmpty()).matches()
    }

    private val _goToNextScreen = SingleLiveEvent<Boolean>()
    val goToNextScreen: LiveData<Boolean>
        get() = _goToNextScreen

    fun onSendInstructionsPressed() {
        if (isEmailValid.value == true) {
            sendUserLogin()
            _isLoading.postValue(true)
        }
    }

    fun setInitialEmail(initialEmail: String) {
        if (email.value.isNullOrEmpty()) {
            email.value = initialEmail
        }
    }

    private fun sendUserLogin() {
        launchLoading(Dispatchers.IO, isLoading) {
            userRepository.resetPassword(email.value.orEmpty()).onSuccess {
                sendEmailVerification()
            }.onError {
                _networkError.postValue(ErrorWithRetry(this, ::sendUserLogin))
            }
        }
    }

    private fun sendEmailVerification() {
        _goToNextScreen.postValue(true)
        _isLoading.postValue(false)
    }
}
