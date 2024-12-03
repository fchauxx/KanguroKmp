package com.insurtech.kanguro.networking.transformer

import com.insurtech.kanguro.networking.error.RemoteServiceIntegrationError
import kotlinx.serialization.SerializationException

object SerializationErrorTransformer : ErrorTransformer {

    override suspend fun transform(incoming: Exception): Exception =
        when (incoming) {
            is SerializationException -> RemoteServiceIntegrationError.UnexpectedResponse
            else -> incoming
        }
}
