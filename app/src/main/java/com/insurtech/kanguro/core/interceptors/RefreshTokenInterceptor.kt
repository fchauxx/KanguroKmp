package com.insurtech.kanguro.core.interceptors

import com.insurtech.kanguro.core.session.ISessionManager
import com.insurtech.kanguro.data.Result
import com.insurtech.kanguro.data.repository.ILoginRepository
import com.insurtech.kanguro.networking.api.UnauthorizedRequest
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation
import timber.log.Timber
import javax.inject.Inject

/**
 * This interceptor tries to update an expired session token before making a request.
 * If the request is masked as not authorized, it skips the refresh logic.
 */
class RefreshTokenInterceptor @Inject constructor(
    private val sessionManager: ISessionManager,
    private val loginRepository: ILoginRepository
) : Interceptor {

    companion object {
        private const val TAG = "RefreshTokenInterceptor"
        private const val JWT_EXPIRATION_LEEWAY_SEC = 10L
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val invocation = chain.request().tag(Invocation::class.java)
        val notAuthorized = invocation?.method()?.getAnnotation(UnauthorizedRequest::class.java)

        if (notAuthorized != null) {
            return chain.proceed(chain.request())
        }

        val request = chain.request().newBuilder()

        Timber.tag(TAG).d("Request: ${chain.request().url}")
        Timber.tag(TAG).d("Token expires at: ${sessionManager.sessionInfo?.accessToken?.expiresAt}")

        /*
        Makes sure the refresh only happens once, otherwise N requests fired with an
        expired token will trigger N refresh tokens.
        This way all requests will have to wait for a token update to check the expiration again.
         */
        synchronized(this) {
            if (sessionManager.sessionInfo?.accessToken?.isExpired(JWT_EXPIRATION_LEEWAY_SEC) == true) {
                updateRefreshToken()
            }
        }

        request.addHeader(
            "Authorization",
            "Bearer ${sessionManager.sessionInfo?.accessToken}"
        )

        return chain.proceed(request.build())
    }

    private fun updateRefreshToken() = runBlocking {
        Timber.tag(TAG).d("Refreshing token...")
        val result =
            loginRepository.refreshToken(sessionManager.sessionInfo?.refreshToken.orEmpty())

        if (result is Result.Success) {
            Timber.tag(TAG).d("Success! ${result.data}")
            sessionManager.sessionInfo = sessionManager.sessionInfo?.copy(
                idToken = result.data.idToken,
                accessToken = result.data.accessToken,
                refreshToken = result.data.refreshToken
            )
            return@runBlocking true
        } else {
            val error = (result as? Result.Error)
            Timber.tag(TAG)
                .e("Failed when trying to refresh token. Error Message: ${error?.exception?.message}. Cause: ${error?.exception?.cause?.message}")
        }
        return@runBlocking false
    }
}
