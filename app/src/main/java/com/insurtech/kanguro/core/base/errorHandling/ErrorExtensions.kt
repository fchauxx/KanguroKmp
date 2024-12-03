package com.insurtech.kanguro.core.base.errorHandling

import android.content.Context
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.haroldadmin.cnradapter.NetworkResponse
import com.insurtech.kanguro.R
import com.insurtech.kanguro.core.base.BaseActivity
import com.insurtech.kanguro.networking.dto.ErrorDto
import com.insurtech.kanguro.networking.error.NetworkingError
import com.insurtech.kanguro.networking.error.RemoteServiceIntegrationError
import java.net.SocketTimeoutException
import com.insurtech.kanguro.data.Result.Error as ResultError

fun Fragment.handleNetworkError(
    errorWithRetry: ErrorWithRetry<NetworkResponse.Error<*, ErrorDto>>
) {
    handleError(requireContext(), errorWithRetry)
}

fun BaseActivity<*>.handleNetworkError(
    errorWithRetry: ErrorWithRetry<NetworkResponse.Error<*, ErrorDto>>
) {
    handleError(this, errorWithRetry)
}

private fun handleError(
    context: Context,
    errorWithRetry: ErrorWithRetry<NetworkResponse.Error<*, ErrorDto>>
) {
    val alert = MaterialAlertDialogBuilder(context)
        .setTitle(context.getString(R.string.error_dialog_title))

    when (val response = errorWithRetry.response) {
        is NetworkResponse.ServerError -> {
            var message = response.body?.error
                ?: response.error?.localizedMessage
                ?: context.getString(R.string.error_dialog_generic_message)
            (response.body?.statusCode ?: response.code)?.let {
                message += context.getString(R.string.error_dialog_code, it)
            }
            alert.setMessage(message).setPositiveButton(context.getString(R.string.back), null)
        }

        is NetworkResponse.NetworkError -> {
            val message = when (response.error) {
                is SocketTimeoutException -> context.getString(R.string.error_dialog_timeout)
                else -> context.getString(R.string.error_dialog_generic_retry_message)
            }
            alert.setMessage(message)
                .setPositiveButton(context.getString(R.string.error_dialog_retry)) { _, _ ->
                    errorWithRetry.onRetry()
                }
                .setNegativeButton(context.getString(R.string.error_dialog_cancel), null)
        }

        else -> {
            var message = response.error?.localizedMessage
                ?: context.getString(R.string.error_dialog_generic_message)
            (response as? NetworkResponse.UnknownError)?.code?.let {
                message += context.getString(R.string.error_dialog_code, it)
            }
            alert.setMessage(message).setPositiveButton(context.getString(R.string.back), null)
        }
    }

    alert.show()
}

fun Fragment.handleResultError(
    error: ResultError,
    onRetry: () -> Unit
) {
    handleError(requireContext(), error, onRetry)
}

fun BaseActivity<*>.handleResultError(
    error: ResultError,
    onRetry: () -> Unit
) {
    handleError(this, error, onRetry)
}

private fun handleError(
    context: Context,
    error: ResultError,
    onRetry: () -> Unit
) {
    val alert = MaterialAlertDialogBuilder(context)
        .setTitle(context.getString(R.string.error_dialog_title))

    when (error.exception) {
        is NetworkingError -> {
            when (error.exception) {
                // TODO: Specify a resource string to each case
                NetworkingError.HostUnreachable,
                NetworkingError.OperationTimeout,
                NetworkingError.ConnectionSpike -> {
                    val message = error.exception.localizedMessage

                    alert.setMessage(message)
                        .setPositiveButton(context.getString(R.string.error_dialog_retry)) { _, _ ->
                            onRetry()
                        }
                        .setNegativeButton(context.getString(R.string.error_dialog_cancel), null)
                }
            }
        }

        is RemoteServiceIntegrationError -> {
            when (error.exception) {
                // TODO: Specify a resource string to each case
                RemoteServiceIntegrationError.ClientOrigin,
                RemoteServiceIntegrationError.NotFoundClientOrigin,
                RemoteServiceIntegrationError.RemoteSystem,
                RemoteServiceIntegrationError.UnexpectedResponse,
                RemoteServiceIntegrationError.ForbiddenClientOrigin -> {
                    val message = error.exception.localizedMessage
                    alert.setMessage(message)
                        .setPositiveButton(context.getString(R.string.error_dialog_retry)) { _, _ ->
                            onRetry()
                        }
                        .setNegativeButton(context.getString(R.string.error_dialog_cancel), null)
                }
            }
        }

        else -> {
            val message = context.getString(R.string.error_dialog_generic_message)
            alert.setMessage(message).setPositiveButton(context.getString(R.string.back), null)
        }
    }

    alert.show()
}
