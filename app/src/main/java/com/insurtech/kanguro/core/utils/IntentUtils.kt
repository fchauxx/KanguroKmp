package com.insurtech.kanguro.core.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.insurtech.kanguro.BuildConfig
import com.insurtech.kanguro.R
import com.insurtech.kanguro.domain.model.AirvetUserDetails
import timber.log.Timber

object IntentUtils {

    private const val WHATS_APP_PACKAGE = "com.whatsapp"
    private const val SMS_BODY = "sms_body"

    private const val AIRVET_URL = "https://join.airvet.com/kanguro"

    fun openMailToKanguro(context: Context) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto: ${context.getString(R.string.javier_support_email)}")
        startActivity(context, intent, null)
    }

    fun openMailToKanguroRentersClaims(context: Context) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto: ${context.getString(R.string.renters_claims_email)}")
        startActivity(context, intent, null)
    }

    fun openDialIntent(context: Context, number: String) {
        val callIntent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$number")
        }
        startActivity(context, callIntent, null)
    }

    fun openSmsIntent(context: Context, phoneNumber: String, message: String) {
        val smsUrl = "smsto:$phoneNumber"

        try {
            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse(smsUrl)).apply {
                putExtra(SMS_BODY, message)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            val smsNotAvailableMessage = context.getString(R.string.sms_not_available)
            showAlert(context, smsNotAvailableMessage)
        }
    }

    fun openWhatsAppIntent(context: Context, phoneNumber: String, message: String) {
        val whatsappUrl = "https://wa.me/$phoneNumber?text=${Uri.encode(message)}"

        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(whatsappUrl)).apply {
                setPackage(WHATS_APP_PACKAGE)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            val whatsAppNotInstalledMessage = context.getString(R.string.whatsapp_not_installed)
            showAlert(context, whatsAppNotInstalledMessage)
        }
    }

    fun openAirvetIntent(
        context: Context,
        airvetUserDetails: AirvetUserDetails
    ) {
        val airvetDeeplink =
            "$AIRVET_URL?partner_id=${BuildConfig.AIRVET_PARTNER_ID}" +
                "&unique_id=${airvetUserDetails.insuranceId}&email=${airvetUserDetails.email}" +
                "&first=${airvetUserDetails.firstName}&last=${airvetUserDetails.lastName}"
        Timber.tag("airvetDeeplink").d("airvetDeeplink $airvetDeeplink")
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(airvetDeeplink))
            context.startActivity(intent)
        } catch (e: Exception) {
            showAlert(context, context.getString(R.string.airvet_link_error))
        }
    }

    private fun showAlert(context: Context, message: String) {
        MaterialAlertDialogBuilder(context)
            .setTitle(context.getString(R.string.error_dialog_title))
            .setMessage(message)
            .setNegativeButton(context.getString(R.string.back), null)
            .show()
    }
}
