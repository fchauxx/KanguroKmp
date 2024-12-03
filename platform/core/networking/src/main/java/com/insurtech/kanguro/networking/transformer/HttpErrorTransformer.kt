package com.insurtech.kanguro.networking.transformer

import com.insurtech.kanguro.networking.error.RemoteServiceIntegrationError
import retrofit2.HttpException

object HttpErrorTransformer : ErrorTransformer {

    override suspend fun transform(incoming: Exception): Exception =
        when (incoming) {
            is HttpException -> translateUsingStatusCode(incoming.code())
            else -> incoming
        }

    private fun translateUsingStatusCode(statusCode: Int): RemoteServiceIntegrationError =
        when (statusCode) {
            in 400..402, in 405..499 -> RemoteServiceIntegrationError.ClientOrigin
            403 -> RemoteServiceIntegrationError.ForbiddenClientOrigin
            404 -> RemoteServiceIntegrationError.NotFoundClientOrigin
            else -> RemoteServiceIntegrationError.RemoteSystem
        }
}
