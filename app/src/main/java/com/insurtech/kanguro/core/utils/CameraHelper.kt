package com.insurtech.kanguro.core.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.exifinterface.media.ExifInterface
import androidx.fragment.app.Fragment
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

typealias CameraHelperCallback = (image: Bitmap, thumbnail: Uri) -> Unit

class CameraHelper(private val fragment: Fragment) {

    private lateinit var currentPhotoPath: String
    private lateinit var currentPhotoUri: Uri
    private var callback: CameraHelperCallback? = null

    private var isTakingPicture = false

    private val startForResult =
        fragment.registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK && isTakingPicture) {
                val rotatedBitmap = rotateBitmap(currentPhotoPath)
                callback?.invoke(rotatedBitmap, currentPhotoUri)
                isTakingPicture = false
            }
        }

    private val permissionResult =
        fragment.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { result ->
            if (result) {
                val callback = this.callback
                if (callback != null) {
                    takePicture(callback)
                }
            }
        }

    fun takePicture(callback: CameraHelperCallback) {
        this.callback = callback

        val context = fragment.requireContext()

        if (ContextCompat.checkSelfPermission(
                fragment.requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            isTakingPicture = true

            startForResult.launch(
                Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                    takePictureIntent.resolveActivity(context.packageManager)?.also {
                        val photoFile: File? = try {
                            createImageFile()
                        } catch (ex: IOException) {
                            null
                        }
                        photoFile?.also {
                            currentPhotoUri = it.getProviderUriForFile(context)
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, currentPhotoUri)
                        }
                    }
                }
            )
        } else {
            requestCameraPermission()
        }
    }

    private fun requestCameraPermission() {
        permissionResult.launch(Manifest.permission.CAMERA)
    }

    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = fragment.requireContext().filesDir
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir).apply {
            currentPhotoPath = absolutePath
        }
    }

    private fun rotateBitmap(path: String): Bitmap {
        val image = File(path)
        val bmOptions = BitmapFactory.Options()
        val bitmap = BitmapFactory.decodeFile(image.absolutePath, bmOptions)

        val exif = ExifInterface(path)
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
}
