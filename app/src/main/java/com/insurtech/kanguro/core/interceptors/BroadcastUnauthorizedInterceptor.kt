package com.insurtech.kanguro.core.interceptors

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.insurtech.kanguro.BuildConfig
import com.insurtech.kanguro.networking.api.UnauthorizedRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation
import javax.inject.Inject

/**
 * This interceptor fires a broadcast after receiving a 401 response.
 */
class BroadcastUnauthorizedInterceptor @Inject constructor(
    @ApplicationContext private val context: Context
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val invocation = chain.request().tag(Invocation::class.java)
        val notAuthorized = invocation?.method()?.getAnnotation(UnauthorizedRequest::class.java)

        if (notAuthorized != null) {
            return chain.proceed(chain.request())
        }

        return chain.proceed(chain.request()).apply {
            if (code == 401) {
                LocalBroadcastManager.getInstance(context)
                    .sendBroadcast(createUnauthorizedBroadcast())
            }
        }
    }

    private fun createUnauthorizedBroadcast() = Intent(ACTION_UNAUTHORIZED_RESPONSE)

    companion object {

        const val ACTION_UNAUTHORIZED_RESPONSE =
            "${BuildConfig.APPLICATION_ID}.action.UNAUTHORIZED_RESPONSE}"

        fun registerUnauthorizedRequestReceiver(
            broadcastManager: LocalBroadcastManager,
            onUnauthorizedRequest: () -> Unit
        ): BroadcastReceiver {
            val receiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    if (intent?.action == ACTION_UNAUTHORIZED_RESPONSE) {
                        onUnauthorizedRequest()
                    }
                }
            }

            broadcastManager.registerReceiver(receiver, IntentFilter(ACTION_UNAUTHORIZED_RESPONSE))

            return receiver
        }
    }
}
