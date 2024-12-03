package com.insurtech.kanguro.core.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.text.SpannableString
import android.util.Base64
import androidx.core.graphics.Insets
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import java.nio.charset.StandardCharsets

fun String.asValidExtension() = ".${filter { it.isLetterOrDigit() }}"

fun String.isSupportedMimeType(): Boolean = when (this) {
    "image/jpg", "image/jpeg", "image/png", "image/bmp", "application/pdf" -> true
    else -> false
}

fun BroadcastReceiver.registerForAction(context: Context, action: String) {
    LocalBroadcastManager.getInstance(context).registerReceiver(this, IntentFilter(action))
}

fun Context.sendLocalBroadcast(action: String) {
    LocalBroadcastManager.getInstance(this).sendBroadcast(Intent(action))
}

fun Insets.copy(
    left: Int = this.left,
    top: Int = this.top,
    right: Int = this.right,
    bottom: Int = this.bottom
) = Insets.of(left, top, right, bottom)

fun SpannableString.setSpan(what: Any, substring: String) {
    val subIndex = indexOf(substring)
    setSpan(what, subIndex, subIndex + substring.length, SpannableString.SPAN_INCLUSIVE_INCLUSIVE)
}

fun LatLng.toLocationString() = "${this.latitude},${this.longitude}"

fun String.fromBase64(): String {
    val bytes = Base64.decode(this, Base64.DEFAULT)
    return String(bytes, StandardCharsets.UTF_8)
}

fun String.isValidPassword(): Boolean {
    /**
     *  r'^
     * (?=.{8,})                    // Must be at least 8 characters in length
     * (?=.*?[a-z])                 // should contain at least one lower case
     * (?=.*?[A-Z])                 // should contain at least one upper case
     * (?=.*[@*#$%^&+=!]|.*[0-9])    // should contain at least one digit or one special character
     * .*$
     */
    val regex = "^(?=.{8,})(?=.*[a-z])(?=.*[A-Z])(?=.*[@*#\$%^&+=!]|.*[0-9]).*\$".toRegex()
    return this.matches(regex)
}

inline fun <T : Any> MutableStateFlow<T>.update(transform: T.() -> T) {
    value = transform(value)
}
