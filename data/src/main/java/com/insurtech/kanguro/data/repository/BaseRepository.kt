package com.insurtech.kanguro.data.repository

import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

open class BaseRepository : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val job: Job = SupervisorJob()

    protected suspend fun <T : Any, R : Any> getSafeNetworkResponse(
        context: CoroutineContext = Dispatchers.IO,
        call: suspend (() -> NetworkResponse<T, R>)
    ): NetworkResponse<T, R> {
        return withContext(context) { call.invoke() }
    }
}
