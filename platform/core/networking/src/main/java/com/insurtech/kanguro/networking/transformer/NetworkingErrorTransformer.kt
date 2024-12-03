package com.insurtech.kanguro.networking.transformer

import com.insurtech.kanguro.networking.error.NetworkingError
import java.io.IOException
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object NetworkingErrorTransformer : ErrorTransformer {

    override suspend fun transform(incoming: Exception): Exception =
        when {
            (!isNetworkingError(incoming)) -> incoming
            isConnectionTimeout(incoming) -> NetworkingError.OperationTimeout
            cannotReachHost(incoming) -> NetworkingError.HostUnreachable
            else -> NetworkingError.ConnectionSpike
        }

    private fun isNetworkingError(error: Exception): Boolean =
        isCanceledRequest(error) ||
            cannotReachHost(error) ||
            isConnectionTimeout(error)

    private fun isCanceledRequest(error: Exception): Boolean =
        error is IOException &&
            error.message?.contentEquals("Canceled") ?: false

    private fun cannotReachHost(error: Exception): Boolean =
        error is UnknownHostException ||
            error is ConnectException ||
            error is NoRouteToHostException

    private fun isConnectionTimeout(error: Exception): Boolean = error is SocketTimeoutException
}
