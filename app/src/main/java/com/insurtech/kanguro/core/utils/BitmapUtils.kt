package com.insurtech.kanguro.core.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

object BitmapUtils {
    fun resizeBitmap(source: Bitmap, maxLength: Int): Bitmap? {
        return try {
            if (source.height >= source.width) {
                if (source.height <= maxLength) { // if image already smaller than the required height
                    return source
                }
                val aspectRatio =
                    source.width.toDouble() / source.height.toDouble()
                val targetWidth = (maxLength * aspectRatio).toInt()
                Bitmap.createScaledBitmap(source, targetWidth, maxLength, false)
            } else {
                if (source.width <= maxLength) { // if image already smaller than the required height
                    return source
                }
                val aspectRatio =
                    source.height.toDouble() / source.width.toDouble()
                val targetHeight = (maxLength * aspectRatio).toInt()
                Bitmap.createScaledBitmap(source, maxLength, targetHeight, false)
            }
        } catch (exception: Exception) {
            return null
        }
    }

    fun drawableToBitmapDescriptor(
        context: Context,
        @DrawableRes drawable: Int
    ): BitmapDescriptor {
        val vector: Drawable = ResourcesCompat.getDrawable(context.resources, drawable, null)
            ?: return BitmapDescriptorFactory.defaultMarker()

        val bitmap = Bitmap.createBitmap(
            vector.intrinsicWidth,
            vector.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap)
        vector.setBounds(0, 0, canvas.width, canvas.height)
        vector.draw(canvas)

        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}
