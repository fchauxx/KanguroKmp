package com.insurtech.kanguro.core.interceptors

import android.os.Build
import com.insurtech.kanguro.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(private val apiKey: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val requestBuilder = original.newBuilder()

        requestBuilder.apply {
            header("api_key", apiKey)
            header("Content-Type", "application/json")
            header("User-Agent", USER_AGENT)
            header("App-Version", APP_VERSION)
        }

        return chain.proceed(requestBuilder.build())
    }

    companion object {
        private val USER_AGENT = "${Build.MANUFACTURER} ${Build.MODEL} API ${Build.VERSION.SDK_INT}"
        private const val APP_VERSION = "${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})"
    }
}
