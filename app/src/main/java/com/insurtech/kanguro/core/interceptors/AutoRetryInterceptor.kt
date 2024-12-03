package com.insurtech.kanguro.core.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class AutoRetryInterceptor(maxRetries: Int = 5, val retryOnCode: Int = 429) : Interceptor {

    private val maxRetries = maxRetries.coerceAtLeast(0)

    var retryCount = 0

    override fun intercept(chain: Interceptor.Chain): Response {
        var availableRetries = maxRetries

        val request = chain.request()
        var result = chain.proceed(request)

        while (result.code == retryOnCode && availableRetries-- > 0) {
            Thread.sleep(1000)
            retryCount++
            result.close()
            result = chain.proceed(request)
        }

        return result
    }
}
