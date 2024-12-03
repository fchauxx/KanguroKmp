package com.insurtech.kanguro.remoteconfigdata.repository

interface IRemoteConfigManager {

    suspend fun getBoolean(key: String): Boolean

    suspend fun getString(key: String): String

    suspend fun getLong(key: String): Long

    suspend fun getDouble(key: String): Double
}
