package com.insurtech.kanguro.ui.compose.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.provider.OpenableColumns
import androidx.exifinterface.media.ExifInterface
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

fun Uri.toFile(context: Context, outputFileName: String): File? {
    val parcelFileDescriptor: ParcelFileDescriptor? =
        context.contentResolver.openFileDescriptor(this, "r")

    return parcelFileDescriptor?.use { pfd ->
        val inputStream = FileInputStream(pfd.fileDescriptor)
        val outputFile = File(context.filesDir, outputFileName)
        val outputStream = FileOutputStream(outputFile)
        inputStream.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
        outputFile
    }
}

fun Uri.getFileNameFromUri(context: Context): String? {
    var result: String? = null

    if (this.scheme == "content") {
        context.contentResolver.query(this, null, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                result =
                    cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
            }
        }
    }

    if (result == null) {
        result = this.path?.substringAfterLast('/')
    }

    return result
}

fun Uri.toBitmap(context: Context, outputFileName: String): Bitmap {
    val image = this.toFile(context, outputFileName)
    val bmOptions = BitmapFactory.Options()
    val bitmap = BitmapFactory.decodeFile(image?.absolutePath, bmOptions)

    val exif = ExifInterface(image?.absolutePath ?: "")
    val rotation = when (exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1)) {
        ExifInterface.ORIENTATION_ROTATE_90 -> 90f
        ExifInterface.ORIENTATION_ROTATE_180 -> 180f
        ExifInterface.ORIENTATION_ROTATE_270 -> 270f
        else -> return bitmap
    }

    val matrix = Matrix()
    matrix.postRotate(rotation)
    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
}
