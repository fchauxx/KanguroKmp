package com.insurtech.kanguro.networking.transformer

import timber.log.Timber
import kotlin.Exception

private val transformers = listOf(
    NetworkingErrorTransformer,
    HttpErrorTransformer,
    SerializationErrorTransformer
)

suspend fun <T> managedExecution(target: suspend () -> T): T =
    try {
        target.invoke()
    } catch (incoming: Exception) {
        Timber.e(incoming)
        throw transformers
            .map { it.transform(incoming) }
            .reduce { transformed, another ->
                when {
                    transformed == another -> transformed
                    another == incoming -> transformed
                    else -> another
                }
            }
    }
