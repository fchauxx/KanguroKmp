package com.insurtech.kanguro.networking.extensions

import com.haroldadmin.cnradapter.NetworkResponse

inline fun <S, E> NetworkResponse<S, E>.onSuccess(
    block: NetworkResponse.Success<S, E>.() -> Unit
): NetworkResponse<S, E> {
    if (this is NetworkResponse.Success) {
        block(this)
    }
    return this
}

inline fun <S, E> NetworkResponse<S, E>.onError(
    block: NetworkResponse.Error<S, E>.() -> Unit
): NetworkResponse<S, E> {
    if (this is NetworkResponse.Error) {
        block(this)
    }
    return this
}

/**
 * Maps a NetworkResponse<S,E> to a NetworkResponse<M,E>
 */
fun <S, E, M> NetworkResponse<S, E>.convert(func: (S) -> M): NetworkResponse<M, E> = when (this) {
    is NetworkResponse.Success -> NetworkResponse.Success(func(body), response)
    is NetworkResponse.ServerError -> NetworkResponse.ServerError(body, response)
    is NetworkResponse.NetworkError -> NetworkResponse.NetworkError(error)
    is NetworkResponse.UnknownError -> NetworkResponse.UnknownError(error, response)
}
