package com.insurtech.kanguro.core.session

import android.content.SharedPreferences
import androidx.core.content.edit
import com.insurtech.kanguro.domain.model.Login
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import javax.inject.Inject

class SessionManager @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    moshi: Moshi
) : ISessionManager {

    private val moshiAdapter = moshi.adapter(Login::class.java)

    private var _sessionInfo: Login? = null

    override var sessionInfo: Login?
        get() {
            return _sessionInfo ?: loadSessionInfo()
        }
        set(value) {
            _sessionInfo = value
            persistSessionInfo(value)
        }

    private fun persistSessionInfo(sessionInfo: Login?) {
        val sessionJson = sessionInfo?.let { moshiAdapter.toJson(it) }
        sharedPreferences.edit { putString(PREF_SESSION_INFO, sessionJson) }
    }

    private fun loadSessionInfo(): Login? {
        return sharedPreferences.getString(PREF_SESSION_INFO, null)?.let {
            try {
                moshiAdapter.fromJson(it)
            } catch (e: JsonDataException) {
                null
            }
        }.also {
            _sessionInfo = it
        }
    }

    companion object {
        const val PREF_SESSION_INFO = "pref_session_info"
    }
}
