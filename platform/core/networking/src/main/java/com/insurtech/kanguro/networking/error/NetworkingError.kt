package com.insurtech.kanguro.networking.error

sealed class NetworkingError : Exception() {

    object HostUnreachable : NetworkingError()
    object OperationTimeout : NetworkingError()
    object ConnectionSpike : NetworkingError()

    override fun toString(): String =
        when (this) {
            HostUnreachable -> "Cannot reach remote host"
            OperationTimeout -> "Networking operation timed out"
            ConnectionSpike -> "In-flight networking operation interrupted"
        }
}
