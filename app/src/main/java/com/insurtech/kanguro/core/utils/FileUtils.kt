package com.insurtech.kanguro.core.utils

import android.content.ActivityNotFoundException
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.util.Base64
import androidx.core.content.FileProvider
import com.insurtech.kanguro.BuildConfig
import com.insurtech.kanguro.R
import com.insurtech.kanguro.domain.pet.PictureBase64
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream

fun File.getProviderUriForFile(context: Context): Uri = FileProvider.getUriForFile(
    context,
    "${BuildConfig.APPLICATION_ID}.fileprovider",
    this
)

fun Bitmap.toFile(context: Context): File? {
    val fileName = "${System.currentTimeMillis()}.jpg"
    val file = File(context.externalCacheDir, fileName)

    return try {
        FileOutputStream(file).use {
            this.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }
        file
    } catch (e: Exception) {
        Timber.e(e)
        null
    }
}

fun Context.openPdf(uri: Uri) {
    val target = Intent(Intent.ACTION_VIEW).apply {
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        setDataAndType(uri, "application/pdf")
    }

    val intent = Intent.createChooser(target, this.getString(R.string.open_file))
    try {
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        // Instruct the user to install a PDF reader here, or something
    }
}

fun Context.openImage(uri: Uri) {
    val target = Intent(Intent.ACTION_VIEW, uri).apply {
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        setDataAndType(uri, "image/*")
    }

    val intent =
        Intent.createChooser(target, this.getString(R.string.open_file))
    startActivity(intent)
}

fun Uri.getBase64(contentResolver: ContentResolver): String {
    val openInputStream = contentResolver.openInputStream(this)
    val availableSize = openInputStream?.available() ?: 0
    val fileBytes = ByteArray(availableSize)

    openInputStream?.use {
        it.read(fileBytes, 0, availableSize)
    }

    return Base64.encodeToString(fileBytes, Base64.DEFAULT)
}

fun Uri.getPetPictureBase64(contentResolver: ContentResolver): PictureBase64 {
    val fileStr = this.getBase64(contentResolver)
    val extension = this.getFileExtension(contentResolver)

    return PictureBase64(fileStr, extension)
}

fun Uri.getFileExtension(contentResolver: ContentResolver): String {
    val url = contentResolver.getType(this)
    val slashIndex = url?.lastIndexOf('/')

    return if (slashIndex != null && slashIndex > 0) {
        ".${url.substring(slashIndex + 1)}"
    } else {
        ""
    }
}
