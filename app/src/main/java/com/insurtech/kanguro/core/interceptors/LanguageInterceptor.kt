package com.insurtech.kanguro.core.interceptors

import android.content.Context
import android.content.SharedPreferences
import com.insurtech.kanguro.core.utils.LanguageUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * This interceptor tries to update an expired session token before making a request.
 * If the request is masked as not authorized, it skips the refresh logic.
 */
class LanguageInterceptor @Inject constructor(
    @ApplicationContext private val context: Context,
    private val preferences: SharedPreferences
) : Interceptor {

    companion object {
        private const val HTTP_HEADER_ACCEPT_LANGUAGE = "accept-language"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        if (request.header(HTTP_HEADER_ACCEPT_LANGUAGE) == null) {
            val appLanguage = LanguageUtils.getCurrentLanguage(preferences, context)
            request = request.newBuilder().addHeader(
                HTTP_HEADER_ACCEPT_LANGUAGE,
                appLanguage.language
            ).build()
        }

        return chain.proceed(request)
    }
}
