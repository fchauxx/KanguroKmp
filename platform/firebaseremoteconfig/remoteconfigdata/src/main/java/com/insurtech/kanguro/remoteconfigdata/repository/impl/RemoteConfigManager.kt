package com.insurtech.kanguro.remoteconfigdata.repository.impl

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.insurtech.kanguro.remoteconfigdata.BuildConfig
import com.insurtech.kanguro.remoteconfigdata.R
import com.insurtech.kanguro.remoteconfigdata.repository.IRemoteConfigManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import timber.log.Timber
import javax.inject.Inject

/**
 *
 * This class receives an instance of Firebase's remoteconfig via dependency injection since the
 * google service files (dev, stage and prod) are located in the app module.
 *
 */

class RemoteConfigManager @Inject constructor(private val remoteConfig: FirebaseRemoteConfig) :
    IRemoteConfigManager {

    /**
     *
     *The isConnecting object is useful to know when the connection attempt with the service ended
     * (success or failure) since calling any of the methods before the connection has been
     * established will return default values in the xml file instead of real remoteconfig values
     * in success case connection
     *
     */

    private val isConnecting: MutableStateFlow<Boolean> = MutableStateFlow(true)

    init {
        if (BuildConfig.DEBUG) {
            val configSettings = FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(60)
                .build()

            remoteConfig.setConfigSettingsAsync(configSettings)
        }

        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Timber.d("remoteConfig success")
            } else {
                Timber.d("remoteConfig error")
            }
            isConnecting.update { false }
        }
    }

    private suspend fun awaitReady() {
        isConnecting.first { !it }
    }

    override suspend fun getBoolean(key: String): Boolean {
        awaitReady()
        return remoteConfig.getBoolean(key)
    }

    override suspend fun getString(key: String): String {
        awaitReady()
        return remoteConfig.getString(key)
    }

    override suspend fun getLong(key: String): Long {
        awaitReady()
        return remoteConfig.getLong(key)
    }

    override suspend fun getDouble(key: String): Double {
        awaitReady()
        return remoteConfig.getDouble(key)
    }
}
