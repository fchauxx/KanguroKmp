package com.insurtech.kanguro.core.utils

import android.net.Uri

data class KanguroFile(
    val uri: Uri,
    val bytes: ByteArray,
    val extension: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as KanguroFile

        if (uri != other.uri) return false
        if (!bytes.contentEquals(other.bytes)) return false
        if (extension != other.extension) return false

        return true
    }

    override fun hashCode(): Int {
        var result = uri.hashCode()
        result = 31 * result + bytes.contentHashCode()
        result = 31 * result + extension.hashCode()
        return result
    }
}
