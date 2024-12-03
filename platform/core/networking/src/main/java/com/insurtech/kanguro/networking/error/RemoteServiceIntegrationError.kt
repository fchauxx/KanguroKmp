package com.insurtech.kanguro.networking.error

sealed class RemoteServiceIntegrationError : Exception() {

    object ClientOrigin : RemoteServiceIntegrationError()
    object NotFoundClientOrigin : RemoteServiceIntegrationError()
    object RemoteSystem : RemoteServiceIntegrationError()
    object UnexpectedResponse : RemoteServiceIntegrationError()
    object ForbiddenClientOrigin : RemoteServiceIntegrationError()

    override fun toString(): String =
        when (this) {
            ClientOrigin -> "Issue incoming from client"
            NotFoundClientOrigin -> "Issue of type \"not found\" incoming from client"
            RemoteSystem -> "Issue incoming from server"
            UnexpectedResponse -> "Broken contract"
            ForbiddenClientOrigin -> "User has its account blocked"
        }
}
