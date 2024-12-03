package com.insurtech.kanguro.networking.transformer

interface ErrorTransformer {

    suspend fun transform(incoming: Exception): Exception
}
