package com.insurtech.kanguro.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.messaging.FirebaseMessaging
import com.insurtech.kanguro.R
import com.insurtech.kanguro.core.base.FullscreenActivity
import com.insurtech.kanguro.core.interceptors.BroadcastUnauthorizedInterceptor
import com.insurtech.kanguro.core.session.ISessionManager
import com.insurtech.kanguro.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : FullscreenActivity<ActivityLoginBinding>() {

    @Inject
    lateinit var preferences: SharedPreferences

    @Inject
    lateinit var sessionManager: ISessionManager

    private var broadcastReceiver: BroadcastReceiver? = null

    override val enforceNavigationBarVisibility: Boolean = true

    override fun onCreateBinding(inflater: LayoutInflater): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        broadcastReceiver = BroadcastUnauthorizedInterceptor.registerUnauthorizedRequestReceiver(
            LocalBroadcastManager.getInstance(this),
            ::onUnauthorizedBroadcastReceived
        )

        window.navigationBarColor = Color.BLACK
    }

    override fun onDestroy() {
        broadcastReceiver?.let {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(it)
        }
        super.onDestroy()
    }

    private fun onUnauthorizedBroadcastReceived() {
        FirebaseMessaging.getInstance().deleteToken()
        sessionManager.sessionInfo = null
        MaterialAlertDialogBuilder(this)
            .setTitle("Login expired!")
            .setMessage("Please, login again.")
            .setCancelable(false)
            .setPositiveButton(R.string.ok) { _, _ ->
                finish()
                startActivity(intent)
            }
            .show()
    }

    companion object {
        fun newInstance(context: Context) = Intent(context, LoginActivity::class.java)
    }
}
