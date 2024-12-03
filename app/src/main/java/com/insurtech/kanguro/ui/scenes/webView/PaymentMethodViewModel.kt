package com.insurtech.kanguro.ui.scenes.webView

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.insurtech.kanguro.core.base.BaseViewModel
import com.insurtech.kanguro.core.base.errorHandling.ErrorWithRetry
import com.insurtech.kanguro.core.repository.user.IUserRepository
import com.insurtech.kanguro.core.utils.SingleLiveEvent
import com.insurtech.kanguro.networking.extensions.onError
import com.insurtech.kanguro.networking.extensions.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentMethodViewModel @Inject constructor(
    private val userRepository: IUserRepository
) : BaseViewModel() {

    private val _refreshedToken = SingleLiveEvent<String>()
    val refreshedToken: LiveData<String> = _refreshedToken

    init {
        refreshKeycloakToken()
    }

    private fun refreshKeycloakToken() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.keycloak()
                .onSuccess {
                    _refreshedToken.postValue(body.accessToken?.toString())
                }
                .onError {
                    _networkError.postValue(ErrorWithRetry(this) { refreshKeycloakToken() })
                }
        }
    }
}
