package com.insurtech.kanguro.ui.scenes.codeValidation

import android.content.Context
import android.text.SpannedString
import android.text.style.ClickableSpan
import android.view.View
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.core.text.inSpans
import androidx.lifecycle.*
import com.haroldadmin.cnradapter.NetworkResponse
import com.insurtech.kanguro.R
import com.insurtech.kanguro.core.base.BaseViewModel
import com.insurtech.kanguro.core.base.errorHandling.ErrorWithRetry
import com.insurtech.kanguro.core.session.ISessionManager
import com.insurtech.kanguro.core.utils.SingleLiveEvent
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.repository.IRefactoredUserRepository
import com.insurtech.kanguro.networking.dto.ErrorDto
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CodeValidationViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val sessionManager: ISessionManager,
    private val refactoredUserRepository: IRefactoredUserRepository
) : BaseViewModel() {

    private val MESSAGE_RESEND_DELAY = 30

    enum class ValidationState { UNKNOWN, LOADING, INVALID, VALID }

    var digit0 = MutableLiveData<String>()
    var digit1 = MutableLiveData<String>()
    var digit2 = MutableLiveData<String>()
    var digit3 = MutableLiveData<String>()
    var digit4 = MutableLiveData<String>()
    var digit5 = MutableLiveData<String>()

    private val _validationState = MediatorLiveData<ValidationState>().apply {
        value = ValidationState.UNKNOWN
        addSource(digit0) { checkCodeCompletion() }
        addSource(digit1) { checkCodeCompletion() }
        addSource(digit2) { checkCodeCompletion() }
        addSource(digit3) { checkCodeCompletion() }
        addSource(digit4) { checkCodeCompletion() }
        addSource(digit5) { checkCodeCompletion() }
    }
    val validationState: LiveData<ValidationState>
        get() = _validationState

    val inputBackground = validationState.map(::getBackgroundForValidation)

    private var timerJob: Job? = null

    private val _resentTimerText = MutableLiveData<SpannedString>()
    val resendTimerText: LiveData<SpannedString>
        get() = _resentTimerText

    private val resendCodeClickableSpan = object : ClickableSpan() {
        override fun onClick(p0: View) {
            postCodeValidation()
        }
    }

    private val _codeValidated = SingleLiveEvent<Boolean>()
    val codeValidated: LiveData<Boolean>
        get() = _codeValidated

    private val boldColor = context.getColor(R.color.secondary_darkest)

    private val resendCodeSpannedString = buildSpannedString {
        append(context.getString(R.string.code_hasnt_arrived))
        append(" ")
        bold {
            inSpans(resendCodeClickableSpan) {
                append(context.getString(R.string.you_can_resend_it))
            }
        }
    }

    fun postCodeValidation() {
        restartTimer()

        viewModelScope.launch(Dispatchers.IO) {
            val postOtpSmsOperationResult = refactoredUserRepository.postOtpSms()

            if (postOtpSmsOperationResult is Result.Error) {
                _networkError.postValue(
                    ErrorWithRetry(
                        NetworkResponse.UnknownError<Unit, ErrorDto>(
                            Exception(),
                            null
                        )
                    ) {
                        postCodeValidation()
                    }
                )
            }
        }
    }

    // Every time a digit is entered, we check for completion
    private fun checkCodeCompletion() {
        val d0 = digit0.value?.takeIf { it.isNotBlank() } ?: return
        val d1 = digit1.value?.takeIf { it.isNotBlank() } ?: return
        val d2 = digit2.value?.takeIf { it.isNotBlank() } ?: return
        val d3 = digit3.value?.takeIf { it.isNotBlank() } ?: return
        val d4 = digit4.value?.takeIf { it.isNotBlank() } ?: return
        val d5 = digit5.value?.takeIf { it.isNotBlank() } ?: return
        validateCode("$d0$d1$d2$d3$d4$d5")
    }

    private fun getBackgroundForValidation(state: ValidationState) = when (state) {
        ValidationState.LOADING -> R.drawable.bg_textfield_default
        ValidationState.INVALID -> R.drawable.bg_textfield_error
        ValidationState.VALID -> R.drawable.bg_textfield_valid
        else -> R.drawable.bg_textfield
    }

    fun notifyEditTextFocusChanged(view: View, focused: Boolean) {
        if (validationState.value == ValidationState.INVALID && focused) {
            clearAllFields()
            _validationState.value = ValidationState.UNKNOWN
        }
    }

    private fun clearAllFields() {
        digit0.value = ""
        digit1.value = ""
        digit2.value = ""
        digit3.value = ""
        digit4.value = ""
        digit5.value = ""
    }

    private fun validateCode(code: String) {
        val userEmail = sessionManager.sessionInfo?.email

        if (userEmail == null) {
            _validationState.value = ValidationState.INVALID
            return
        }

        viewModelScope.launch {
            _validationState.postValue(ValidationState.LOADING)

            refactoredUserRepository.getCodeValidate(userEmail, code)
                .catch { e -> Result.Error(Exception(e)) }
                .collect { statusOperationResult ->

                    if (statusOperationResult is Result.Success) {
                        val isSuccessfulStatusOperation = statusOperationResult.data

                        if (isSuccessfulStatusOperation) {
                            _validationState.postValue(ValidationState.VALID)
                            delay(1000)
                            _codeValidated.postValue(true)
                        } else {
                            _validationState.postValue(ValidationState.INVALID)
                        }
                    } else {
                        _validationState.postValue(ValidationState.INVALID)
                    }
                }
        }
    }

    private fun restartTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            (MESSAGE_RESEND_DELAY downTo 1).forEach {
                _resentTimerText.value = buildStringForTimer(it)
                delay(1000)
            }
            _resentTimerText.value = resendCodeSpannedString
        }
    }

    private fun buildStringForTimer(time: Int) = buildSpannedString {
        append(context.getString(R.string.code_hasnt_arrived))
        append(" ")
        append(context.getString(R.string.you_can_resend_in))
        append(" ")
        color(boldColor) { bold { append("00:%02d".format(time)) } }
    }
}
