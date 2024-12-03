package com.insurtech.kanguro.ui

import com.insurtech.kanguro.core.base.BaseViewModel
import com.insurtech.kanguro.core.session.ISessionManager
import com.insurtech.kanguro.data.repository.ILoginRepository
import com.insurtech.kanguro.domain.model.Login
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject
import com.insurtech.kanguro.data.Result.Error as ResultError
import com.insurtech.kanguro.data.Result.Success as ResultSuccess

@HiltViewModel
class StartViewModel @Inject constructor(
    private val sessionManager: ISessionManager,
    private val loginRepository: ILoginRepository
) : BaseViewModel() {

    enum class NextScreen {
        LOGIN,
        HOME_DASHBOARD
    }

    suspend fun getNextScreen(): NextScreen {
        val sessionInfo = sessionManager.sessionInfo ?: return NextScreen.LOGIN
        val nowInUtc = Date()
        val expiresOn = sessionInfo.expiresOn ?: return NextScreen.LOGIN

        return if (expiresOn < nowInUtc) {
            handleExpiredSession(sessionInfo)
        } else {
            NextScreen.HOME_DASHBOARD
        }
    }

    private suspend fun handleExpiredSession(sessionInfo: Login): NextScreen {
        val refreshToken = sessionInfo.refreshToken ?: return NextScreen.LOGIN

        return when (val result = loginRepository.refreshToken(refreshToken)) {
            is ResultSuccess -> {
                setSessionInfo(result.data)
                NextScreen.HOME_DASHBOARD
            }

            is ResultError -> {
                sessionManager.sessionInfo = null
                NextScreen.LOGIN
            }
        }
    }

    private fun setSessionInfo(login: Login) {
        sessionManager.sessionInfo = sessionManager.sessionInfo?.copy(
            idToken = login.idToken,
            accessToken = login.accessToken,
            refreshToken = login.refreshToken,
            expiresOn = login.expiresOn
        )
    }
}
